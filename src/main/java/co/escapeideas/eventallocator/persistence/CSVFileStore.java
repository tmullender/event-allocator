package co.escapeideas.eventallocator.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import co.escapeideas.eventallocator.domain.Event;
import co.escapeideas.eventallocator.domain.Person;

/**
 * Created by tim on 21/09/2016.
 */
@Component
public class CSVFileStore implements Store{

    private static final Logger LOG = LoggerFactory.getLogger(CSVFileStore.class);

    private final File storeFile;

    private Map<String, Person> todo = new HashMap<>();
    private Map<Person, String[]> preferences = new HashMap<>();
    private Map<String, Event> allEvents = new HashMap<>();

    public CSVFileStore() {
        storeFile = new File(System.getProperty("storeDir", "store"), "data.csv");
        storeFile.getParentFile().mkdirs();
        loadPreferences();
    }

    private void loadPreferences() {
        if (storeFile.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(storeFile))){
                String line;
                while ((line = reader.readLine()) != null){
                    final String[] data = line.split(",");
                    final Person person = new Person(data[0], data[1]);
                    final String[] preference = new String[data.length - 2];
                    System.arraycopy(data, 2, preference, 0, preference.length);
                    preferences.put(person, preference);
                }
            } catch (IOException ioe) {
                LOG.error("Exception caught loading prefs", ioe);
            }
        }
    }


    @Override
    public void addPeople(List<Person> people) {
        for (Person person : people){
            todo.put(person.getUniqueId(), person);
        }
    }

    @Override
    public void addEvents(List<Event> events) {
        for (Event event: events){
            allEvents.put(event.getUniqueId(), event);
        }
    }

    @Override
    public List<Person> getNewPeople() {
        return new ArrayList<>(todo.values());
    }

    @Override
    public Map<String, Event> getEvents() {
        return new HashMap<>(allEvents);
    }

    @Override
    public Map<Person, String[]> getPreferences() {
        return new HashMap<>(preferences);
    }

    @Override
    public void addPreference(String personId, String[] events) {
        LOG.debug("Adding preference for {}:{}", personId, Arrays.toString(events));
        final Person person = todo.remove(personId);
        preferences.put(person, events);
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storeFile))){
            for (Person person : preferences.keySet()){
                final String line = createLine(person);
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            LOG.error("Error saving preferences", e);
        }
    }

    private String createLine(Person person) {
        final String[] data = new String[allEvents.size() + 2];
        data[0] = person.getName();
        data[1] = person.getGroup();
        final String[] preference = preferences.get(person);
        System.arraycopy(preference, 0, data, 2, preference.length);
        LOG.debug("saving line: {}", Arrays.toString(data));
        return StringUtils.join(data, ",");
    }
}
