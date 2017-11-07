package ru.alex.goncharov.db;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "usn", schema = "notes", catalog = "")
public class UserNote {
    private int id;
    private String usrNote;
    private String description;
    private Date date;


    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "usrNote", nullable = true, length = 60)
    public String getUsrNote() {
        return usrNote;
    }

    public void setUsrNote(String usrNote) {
        this.usrNote = usrNote;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserNote userNote = (UserNote) o;

        if (id != userNote.id) return false;
        if (usrNote != null ? !usrNote.equals(userNote.usrNote) : userNote.usrNote != null) return false;
        if (description != null ? !description.equals(userNote.description) : userNote.description != null)
            return false;
        if (date != null ? !date.equals(userNote.date) : userNote.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (usrNote != null ? usrNote.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }


}