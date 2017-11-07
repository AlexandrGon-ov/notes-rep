package ru.alex.goncharov.db;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.Date;

import static javafx.geometry.Pos.*;


public class NewWindow implements Runnable {
    private Date noteDate = new Date();
    private TextArea textArea;
    private Stage newStage;
    private TextField noteName;
    private static String getNoteDescription;
    private static String getNoteName;
    private static java.sql.Date userDate;
    private static ObservableList<UserNote> list;
    private Thread thread;

    public NewWindow(ObservableList<UserNote> userNote1TableContent) {
        list = userNote1TableContent;
    }

    public NewWindow() {
    }

    protected Stage createNewWindow() {

        TextField dateField = new TextField(noteDate.toString());
        dateField.setEditable(false);
        dateField.setMaxWidth(300);

        textArea = new TextArea();
        textArea.setPromptText("Описание заметки");
        final int MAX_CHARS = 100;
        textArea.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= MAX_CHARS ? change : null));
        textArea.setMaxWidth(300);
        textArea.setMaxHeight(1);
        textArea.setWrapText(true);

        noteName = new TextField();
        noteName.setMaxWidth(300);
        noteName.setPromptText("Имя заметки");

        Button saveButton = saveButton();


        StackPane secondRoot = new StackPane();
        secondRoot.setPadding(new Insets(5));
        ;
        StackPane.setAlignment(noteName, TOP_RIGHT);
        StackPane.setAlignment(saveButton, BOTTOM_RIGHT);
        StackPane.setAlignment(dateField, BOTTOM_LEFT);
        secondRoot.getChildren().addAll(noteName, textArea, dateField, saveButton);
        Scene secondScene = new Scene(secondRoot, 300, 100);


        newStage = new Stage();
        newStage.setTitle("Создать заметку");
        newStage.setScene(secondScene);
        newStage.initModality(Modality.WINDOW_MODAL);

        return newStage;
    }

    private Button saveButton() {

        Button b = new Button("Save");
        b.setOnAction((ae) -> {
            getNoteDescription = textArea.getText();
            getNoteName = noteName.getText();
            userDate = new java.sql.Date(new java.util.Date().getTime());
            UserNotes user = new UserNotes(getNoteName, getNoteDescription, userDate);
            list.add(user);
            AppRunner.table.setItems(list);
            newStage.close();
        });

        return b;
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    synchronized public void run() {
        Session session1 = HibernateSessionFactory.getSessionFactory().openSession();
        session1.beginTransaction();
        session1.createSQLQuery("truncate table usn").executeUpdate();
        session1.close();


        for (int i = 0; i < AppRunner.userNote1TableContent.size(); i++) {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();

            String userNote;
            String userDescription;
            Date date;

            UserNote currentUserFromJavaTable = AppRunner.userNote1TableContent.get(i);
            userNote = currentUserFromJavaTable.getUsrNote();
            userDescription = currentUserFromJavaTable.getDescription();
            date = currentUserFromJavaTable.getDate();

            UserNote addNoteInSqlTable = new UserNote();
            addNoteInSqlTable.setUsrNote(userNote);
            addNoteInSqlTable.setDescription(userDescription);
            addNoteInSqlTable.setDate((java.sql.Date) date);

            session.saveOrUpdate(addNoteInSqlTable);
            session.flush();
            session.getTransaction().commit();
            session.close();
        }

        thread.interrupt();
        System.exit(0);


    }
}
