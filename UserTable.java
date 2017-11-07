package ru.alex.goncharov.db;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


public class UserTable {
    private int row;
    private TableView<UserNote> userTable;

    public TableView<UserNote> createTable() {
        userTable = new TableView<>();

        userTable.setEditable(true);
        TableColumn<UserNote, String> userNoteCol = new TableColumn<>("NoteName");
        TableColumn<UserNote, String> descriptionCol = new TableColumn<>("Description");
        TableColumn<UserNote, String> noteDateCol = new TableColumn<>("Date");

        userNoteCol.setSortable(true);

        userNoteCol.setMinWidth(150);
        userNoteCol.setMaxWidth(150);
        descriptionCol.setMinWidth(600);
        descriptionCol.setMaxWidth(600);
        noteDateCol.setMinWidth(150);
        noteDateCol.setMaxWidth(150);

        userNoteCol.setCellValueFactory(new PropertyValueFactory<>("usrNote"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        noteDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        userNoteCol.setCellFactory(TextFieldTableCell.forTableColumn());
        userNoteCol.setOnEditCommit(this::editingTableNote);

        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(this::editingTableDescription);


        userTable.getColumns().addAll(userNoteCol, descriptionCol, noteDateCol);

        return userTable;
    }


    protected void editingTableNote(TableColumn.CellEditEvent<UserNote, String> ae) {
        TablePosition<UserNote, String> position = ae.getTablePosition();

        String newFullNotesName = ae.getNewValue();
        row = position.getRow();
        UserNote user = ae.getTableView().getItems().get(row);
        user.setUsrNote(newFullNotesName);
    }

    protected void editingTableDescription(TableColumn.CellEditEvent<UserNote, String> ae) {
        TablePosition<UserNote, String> position = ae.getTablePosition();

        String newFullDescriptionName = ae.getNewValue();
        row = position.getRow();
        UserNote user = ae.getTableView().getItems().get(row);
        user.setDescription(newFullDescriptionName);
    }

    protected void deleteNote() {
        int selected = userTable.getSelectionModel().getSelectedIndex();
        if (selected > -1) {
            userTable.getItems().remove(selected);
        } else DeleteErrorAlert.showAlert();
    }

}
