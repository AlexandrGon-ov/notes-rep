package ru.alex.goncharov.db;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.alex.goncharov.db.UserNote;
import ru.alex.goncharov.ui.components.UserNotes;


public class UserTable {

    public TableView<UserNote> createTable() {
        TableView<UserNote> userTable = new TableView<>();

        TableColumn<UserNote, String> userNoteCol = new TableColumn<>("Notes");
        TableColumn<UserNote,String> descriptionCol = new TableColumn<>("Description");
        TableColumn<UserNote, String>  noteDateCol = new TableColumn<>("Date");

        userNoteCol.setMinWidth(100);
        descriptionCol.setMinWidth(100);
        noteDateCol.setMinWidth(128);

        userNoteCol.setCellValueFactory(new PropertyValueFactory<>("noteName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        noteDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        userTable.getColumns().addAll(userNoteCol, descriptionCol, noteDateCol);

        return userTable;
    }
}
