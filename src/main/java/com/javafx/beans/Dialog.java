package com.javafx.beans;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class Dialog {

      private final Dimension DIMENSION = new Dimension(690, 420);

      public void showMessageDialog(Alert.AlertType msgType, String header, String msg, String title) {
            Alert al = new Alert(msgType, msg);
            al.setHeaderText(header);
            al.setTitle(title);
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
}
