package ru.alex.goncharov.db;

import javafx.scene.control.Alert;


class DeleteErrorAlert {

    static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("DeleteError");
        alert.setContentText("Не выделена строка для удаления записки!");
        alert.showAndWait();
    }
}
