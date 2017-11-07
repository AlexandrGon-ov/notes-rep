package ru.alex.goncharov.db;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import org.hibernate.Query;
import org.hibernate.Session;
import ru.alex.goncharov.ui.components.MenuBarForUserTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AppRunner extends Application {

    protected NewWindow newWindow;
    protected Stage secondStage;
    protected static TableView<UserNote> table;
    protected UserTable newTable;
    protected static ObservableList<UserNote> userNote1TableContent = FXCollections.observableArrayList();
    protected List<UserNote> userList = new ArrayList<>();
    protected UserNote loadedNote;
    protected List loadedList;
    private MenuBarForUserTable menuBar = new MenuBarForUserTable();


    public static void main(String[] args) {
        launch(args);
    }

    public void init() {
        getUser();
        newTable = new UserTable();
        table = newTable.createTable();
        userNote1TableContent.addAll(userList);
        table.setItems(userNote1TableContent);

    }

    public void start(Stage myStage) {
        myStage.setTitle("Notes");
        MenuBar bar = menuBar.tableMenuBar();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(2));
        root.setTop(bar);
        root.setCenter(table);

        menuBar.newItem.setOnAction((ae) -> {
            newWindow = new NewWindow(userNote1TableContent);
            secondStage = newWindow.createNewWindow();
            secondStage.initOwner(myStage);
            secondStage.show();
        });
        menuBar.exitItem.setOnAction((ae) -> menuBar.actionForExitItem());
        menuBar.deleteNote.setOnAction((ae) -> newTable.deleteNote());

        myStage.setTitle("Заметки");
        Scene myScene = new Scene(root, 910, 420);
        myStage.setScene(myScene);
        myStage.show();
        myStage.setOnCloseRequest((ae) ->
        {
           NewWindow newThread = new NewWindow();
           newThread.startThread();
        });

    }

    private void getUser() {

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from UserNote where id > 0");
        loadedList = query.list();

        for (int i = 1; i <= loadedList.size(); i++) {
            loadedNote = session.load(UserNote.class, i);
            String getNoteFromSqlTable = loadedNote.getUsrNote();
            String getDescriptionFromSqlTable = loadedNote.getDescription();
            Date getDateFromSqlTable = loadedNote.getDate();

            UserNotes userNotes = new UserNotes(getNoteFromSqlTable, getDescriptionFromSqlTable, getDateFromSqlTable);
            userList.add(userNotes);
        }
        session.close();
    }

}
