package co.escapeideas.eventallocator.comparators;

import java.util.Comparator;

import co.escapeideas.eventallocator.domain.Person;

/**
 * Notice: This software is proprietary to CME, its affiliates, partners and/or licensors.  Unauthorized copying, distribution or use is strictly prohibited.  All rights reserved.
 * Created with IntelliJ IDEA.
 * User: e20856
 * Date: 22/09/2016
 * Time: 13:36
 */
public class GroupComparator implements Comparator<Person> {
  @Override
  public int compare(final Person p1, final Person p2) {
    return p1.getGroup().compareTo(p2.getGroup()) * -1;
  }
}
