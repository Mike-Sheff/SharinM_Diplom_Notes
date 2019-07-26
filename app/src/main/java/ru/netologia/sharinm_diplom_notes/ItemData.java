package ru.netologia.sharinm_diplom_notes;

import androidx.annotation.Nullable;

import java.util.Date;

public class ItemData {

    private String headline;
    private String textNote;
    private Date dateDeadline;
    private Date dateUpdateNote;

    public ItemData(String headline, String textNote, @Nullable Date  dateDeadline, @Nullable Date dateUpdateNote) {
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

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public Date getDateUpdateNote() {
        return dateUpdateNote;
    }

    public void setDateUpdateNote(Date dateUpdateNote) {
        this.dateUpdateNote = dateUpdateNote;
    }
}
