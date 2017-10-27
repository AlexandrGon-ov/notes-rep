package ru.alex.goncharov.db;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import ru.alex.goncharov.ui.components.UserNotes;

import java.util.Date;

import static javafx.geometry.Pos.*;


public class NewWindow implements Runnable {
    private Date noteDate = new Date();
    private TextArea textArea;
    private Button saveButton;
    private Stage newWindow;
    private TextField dateField;
    private TextField noteName;
    private static String getNoteDescription;
    private static String getNoteName;
    private static java.sql.Date userDate;
    private static TableView<UserNotes> table;
    protected AppRunner appTable;
    protected static ObservableList<UserNote> list;

    public NewWindow(ObservableList<UserNote> userNotesTableContent) {
        this.list = userNotesTableContent;
    }

    public NewWindow() {
    }

    protected Stage createNewWindow() {

        dateField = new TextField(noteDate.toString());
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

        saveButton = saveButton();


        StackPane secondRoot = new StackPane();
        secondRoot.setPadding(new Insets(5));
        StackPane.setAlignment(noteName, TOP_RIGHT);
        StackPane.setAlignment(saveButton, BOTTOM_RIGHT);
        StackPane.setAlignment(dateField, BOTTOM_LEFT);
        secondRoot.getChildren().addAll(noteName, textArea, dateField, saveButton);
        Scene secondScene = new Scene(secondRoot, 300, 100);

        newWindow = new Stage();
        newWindow.setTitle("Создать заметку");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);

        return newWindow;
    }

    private Button saveButton() {
        Button b = new Button("Save");
        b.setOnAction((ae) -> {
            getNoteDescription = textArea.getText();
            getNoteName = noteName.getText();
            userDate = new java.sql.Date(new java.util.Date().getTime());
            UserNote user = new UserNotes(getNoteName, getNoteDescription, userDate);
            appTable = new AppRunner();
            list.add(user);
            AppRunner.table.setItems(list);

            new Thread(this).start();
            newWindow.close();
        });

        return b;
    }
    @Override
    public void run() {

        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();

        UserNote userNotesEntity = new UserNote();
        userNotesEntity.setDescription(getNoteDescription);
        userNotesEntity.setUsrNote(getNoteName);
        userNotesEntity.setDate(userDate);
        session.save(userNotesEntity);
        session.getTransaction().commit();
        session.close();

    }

}
