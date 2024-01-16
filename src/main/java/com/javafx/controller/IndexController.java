package com.javafx.controller;

import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Slf4j
public class IndexController implements Initializable {

//      @Autowired permite inyectar dependencias
//      permite que spring instancie la clase cuando sea necesario
//      similar a la anotación @FXML, que inyecta los controles de la escena
      @Qualifier("saludoLbl")
      @Autowired private String texto;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {

      }
}
