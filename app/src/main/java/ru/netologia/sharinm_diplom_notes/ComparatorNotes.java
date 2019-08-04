package ru.netologia.sharinm_diplom_notes;

import java.util.Comparator;

class ComparatorNotes implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Note note1 = (Note) o1;
        Note note2 = (Note) o2;

        int xz1 = note1.getDateDeadline().length() != 0 ? 1 : 0;
        int xz2 = note2.getDateDeadline().length() != 0 ? 1 : 0;

        if (xz1 > xz2) {
           return -1;
        } else if (xz2 > xz1) {
            return 1;
        }

        return 0;
    }
}
