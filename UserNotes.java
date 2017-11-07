package ru.alex.goncharov.db;


import java.util.Date;

public class UserNotes extends UserNote {

    private int id;
    private String usrNote;
    private String description;
    private Date date;


    public UserNotes(String usrNote, String description, Date date) {
        this.usrNote = usrNote;
        this.description = description;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsrNote() {
        return usrNote;
    }

    public void setUsrNote(String usrNote) {
        this.usrNote = usrNote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
