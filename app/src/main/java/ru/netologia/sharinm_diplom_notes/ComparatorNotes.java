package ru.netologia.sharinm_diplom_notes;

import android.content.Context;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

class ComparatorNotes implements Comparator {
    private Context context;

    ComparatorNotes(Context context) {
        this.context = context;
    }

    @Override
    public int compare(Object o1, Object o2) {
        Note note1 = (Note) o1;
        Note note2 = (Note) o2;

        Date datedeadline1 = null, datedeadline2 = null, dateUpdate1 = null, dateUpdate2 = null;

        int existenceDateDeadline1 = note1.getDateDeadline().length() != 0 ? 1 : 0;
        int existenceDateDeadline2 = note2.getDateDeadline().length() != 0 ? 1 : 0;

        if (existenceDateDeadline1 == 1) {
            try {
                datedeadline1 = new SimpleDateFormat(context.getString(R.string.formatDate)).parse(note1.getDateDeadline());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (existenceDateDeadline2 == 1) {
            try {
                datedeadline2 = new SimpleDateFormat(context.getString(R.string.formatDate)).parse(note2.getDateDeadline());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        try {
            dateUpdate1 = new SimpleDateFormat(context.getString(R.string.formatDate)).parse(note1.getDateUpdateNote());
            dateUpdate2 = new SimpleDateFormat(context.getString(R.string.formatDate)).parse(note2.getDateUpdateNote());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (existenceDateDeadline1 > existenceDateDeadline2) {
            return -1;
        } else if (existenceDateDeadline2 > existenceDateDeadline1) {
            return 1;
        } else if ((existenceDateDeadline1 == existenceDateDeadline2) && (existenceDateDeadline1 == 1)) {
            if (datedeadline1.equals(datedeadline2)) {
                if (dateUpdate1.after(dateUpdate2)) {
                    return -1;
                }
            }
            if (datedeadline1.before(datedeadline2)) {
                return -1;
            }
        } else if ((existenceDateDeadline1 == existenceDateDeadline2) && (existenceDateDeadline1 == 0)) {
            if (dateUpdate1.after(dateUpdate2)) {
                return -1;
            }
        }

        return 0;
    }
}
