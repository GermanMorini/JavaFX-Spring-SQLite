package com.javafx.controller;

import com.javafx.bean.Dialog;
import com.javafx.model.Alumno;
import com.javafx.interfaces.Form;
import com.javafx.interfaces.Refreshable;
import com.javafx.service.AlumnoService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

@Controller
public class AlumnoFormController implements Initializable, Refreshable, Form<Alumno> {

//      @Autowired permite inyectar dependencias
//      permite que spring instancie la clase cuando sea necesario
//      similar a la anotación @FXML, que inyecta los controles de la escena
      @Autowired private AlumnoService servicio;
      @Autowired private Dialog dialog;
      @Autowired private MateriaFormController materiaFormController;

      @FXML private TextField idTF;
      @FXML private TextField nombreTF;
      @FXML private TextField apellidoTF;
      @FXML private TextField documentoTF;
      @FXML private TextField edadTF;
      @FXML private TextField telefonoTF;
      @FXML private TextField emailTF;

      private final Random r = new Random();

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {

      }

      @Override
      public void refresh() {

      }

      @Override
      public void fillFields(Alumno em) {
            idTF.setText(em.getId().toString());
            nombreTF.setText(em.getNombre());
            apellidoTF.setText(em.getApellido());
            documentoTF.setText(em.getDni().toString());
            edadTF.setText(em.getEdad().toString());
            telefonoTF.setText(em.getTelefono().toString());
            emailTF.setText(em.getEmail());
      }

      @Override @FXML
      public void clearFields() {
            idTF.clear();
            nombreTF.clear();
            apellidoTF.clear();
            documentoTF.clear();
            edadTF.clear();
            telefonoTF.clear();
            emailTF.clear();
      }

      @Override
      public boolean allFieldsCompleted() {
            return !idTF.getText().isBlank() &&
                    !nombreTF.getText().isBlank() &&
                    !apellidoTF.getText().isBlank() &&
                    !documentoTF.getText().isBlank() &&
                    !edadTF.getText().isBlank() &&
                    !telefonoTF.getText().isBlank() &&
                    !emailTF.getText().isBlank();
      }

      @Override
      public Alumno getInstance() {
            return new Alumno(
                    Long.valueOf(idTF.getText()),
                    nombreTF.getText(),
                    apellidoTF.getText(),
                    Integer.valueOf(edadTF.getText()),
                    Integer.valueOf(documentoTF.getText()),
                    Long.valueOf(telefonoTF.getText()),
                    emailTF.getText(),
                    new ArrayList<>()
            );
      }

      @FXML
      private void generarID() {
            idTF.setText(r.nextLong(1000000, 9999999) + "");
      }

      @FXML
      private void guardarAP() {
            if (!allFieldsCompleted()) {dialog.unfilledFormDialog();}
            else {

            try {

            servicio.save(getInstance());
            materiaFormController.refresh();

            } catch (Exception e) {dialog.exceptionDialog(e);}}
      }
}
