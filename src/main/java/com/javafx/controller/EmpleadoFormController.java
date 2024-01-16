package com.javafx.controller;

import com.javafx.bean.Dialog;
import com.javafx.model.Empleado;
import com.javafx.interfaces.Form;
import com.javafx.interfaces.Refreshable;
import com.javafx.service.EmpleadoService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Controller
public class EmpleadoFormController implements Initializable, Refreshable, Form<Empleado> {

      @Autowired private EmpleadoService servicio;
      @Autowired private Dialog dialog;
      @Autowired private ProyectoFormController proyectoFormController;

      @FXML private TextField idTF;
      @FXML private TextField nombreTF;
      @FXML private TextField apellidoTF;
      @FXML private TextField documentoTF;
      @FXML private TextField edadTF;
      @FXML private TextField telefonoTF;
      @FXML private TextField emailTF;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {

      }

      @Override
      public void refresh() {

      }

      @Override
      public void fillFields(Empleado em) {
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
      public Empleado getInstance() {
            return new Empleado(
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
      private void guardarAP() {
            if (!allFieldsCompleted()) {dialog.unfilledFormDialog();}
            else {

            try {

            servicio.save(getInstance());
            proyectoFormController.refresh();

            } catch (Exception e) {dialog.exceptionDialog(e);}}
      }
}
