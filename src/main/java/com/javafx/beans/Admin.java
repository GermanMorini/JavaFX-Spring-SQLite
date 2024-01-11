package com.javafx.beans;

import javafx.scene.control.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Admin {

      @Autowired private Dialog dialog;

      public void manageException(Exception e) {
            log.error(e.getClass().getName() + ":\n" + e.getMessage());
            dialog.showDialog(
                    Alert.AlertType.ERROR,
                    e.getClass().getName(),
                    e.getMessage(),
                    "Error"
            );
      }

}
