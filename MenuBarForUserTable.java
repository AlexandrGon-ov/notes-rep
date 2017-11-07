package ru.alex.goncharov.ui.components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import ru.alex.goncharov.db.NewWindow;

public class MenuBarForUserTable {
    public MenuItem newItem;
    public MenuItem exitItem;
    public MenuItem deleteNote;

    public MenuBar tableMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu deleteMenu = new Menu("Delete");

        newItem = new MenuItem("New note");
        exitItem = new MenuItem("Exit");
        deleteNote = new MenuItem("Delete selected note");

        fileMenu.getItems().addAll(newItem, exitItem);
        deleteMenu.getItems().add(deleteNote);

        menuBar.getMenus().addAll(fileMenu, deleteMenu);
        return menuBar;
    }

    public void actionForExitItem() {
        NewWindow newTh = new NewWindow();
        newTh.startThread();
    }

}
