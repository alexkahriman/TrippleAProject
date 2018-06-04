package com.ftn.trippleaproject.system.comparator;

import com.ftn.trippleaproject.domain.Event;

import java.util.Comparator;

public class EventEndDateComparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        if (o1.getEndDate().equals(o2.getEndDate())) {
            return 0;
        } else {
            if (o1.getEndDate().after(o2.getEndDate())) {
                return -1;
            }
        }

        return 1;
    }
}
