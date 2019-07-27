package ru.netologia.sharinm_diplom_notes;

import androidx.annotation.Nullable;

import java.util.Date;

public class Note {

    private String headline;
    private String textNote;
    private String dateDeadline;
    private String dateUpdateNote;

    public Note(@Nullable String headline, String textNote, @Nullable String dateDeadline, @Nullable String dateUpdateNote) {
        this.headline = headline;
        this.textNote = textNote;
        this.dateDeadline = dateDeadline;
        this.dateUpdateNote = dateUpdateNote;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public String getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(String dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public String getDateUpdateNote() {
        return dateUpdateNote;
    }

    public void setDateUpdateNote(String dateUpdateNote) {
        this.dateUpdateNote = dateUpdateNote;
    }
}
