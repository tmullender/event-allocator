package co.escapeideas.eventallocator.persistence;

import co.escapeideas.eventallocator.domain.Event;
import co.escapeideas.eventallocator.domain.Person;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

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
    public List<Event> getEvents() {
        return new ArrayList<>(allEvents.values());
    }

    @Override
    public void addPreference(String personId, String[] events) {
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
        return StringUtils.join(data, ",");
    }
}
