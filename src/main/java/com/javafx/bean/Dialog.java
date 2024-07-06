package com.javafx.bean;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.Dimension;

// indica que esta clase está gestionada por spring (similar a @Service, @Controller, ...)
@Component
@Slf4j
public class Dialog {

    private final Dimension DIMENSION = new Dimension(690, 420);

    public void showDialog(Alert.AlertType msgType, String header, String msg, String title) {
        Alert al = new Alert(msgType, msg);
        al.setHeaderText(header);
        al.setTitle(title);
        al.setWidth(DIMENSION.getWidth());
        al.setHeight(DIMENSION.getHeight());
        al.setResizable(true);
        al.show();
    }

    public void showInfoDialog(String header, String msg) {
        Alert al = new Alert(Alert.AlertType.INFORMATION, msg);
        al.setHeaderText(header);
        al.setTitle("Información");
        al.setWidth(DIMENSION.getWidth());
        al.setHeight(DIMENSION.getHeight());
        al.setResizable(true);
        al.show();
    }

    public void showConfirmDialog(String msg, String title, java.util.function.Consumer<? super ButtonType> action) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION, msg);
        al.setTitle(title);
        al.setWidth(DIMENSION.width);
        al.setHeight(DIMENSION.height);
        al.setResizable(true);
        al.showAndWait().ifPresent(action);
    }

    public void exceptionDialog(Exception e) {
        log.error(e.getClass().getName() + ":\n" + e.getMessage());
        showDialog(
              Alert.AlertType.ERROR,
              e.getClass().getName(),
              e.getMessage(),
              "Error"
        );
    }

    public void unfilledFormDialog() {
        showDialog(
              Alert.AlertType.WARNING,
              "Faltan rellenar campos",
              "Rellena los que faltan y volvé a intentar",
              "Advertencia"
        );
    }
}
