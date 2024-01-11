package com.javafx.controllers;

import com.javafx.beans.Admin;
import com.javafx.beans.Dialog;
import com.javafx.models.Empleado;
import com.javafx.interfaces.Fillable;
import com.javafx.interfaces.Refreshable;
import com.javafx.services.EmpleadoService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EmpleadosFormController implements Initializable, Refreshable, Fillable<Empleado> {

      @Autowired private EmpleadoService servicio;
      @Autowired private Dialog dialog;
      @Autowired private Admin admin;

      @FXML private TextField idTF;
      @FXML private TextField nombreTF;
      @FXML private TextField apellidoTF;
      @FXML private TextField documentoTF;
      @FXML private TextField edadTF;

      @Setter
      private Empleado seleccionado;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {

      }

      @Override
      public void refresh() {
            fillEntries(seleccionado);
      }

      @Override
      public void fillEntries(Empleado em) {
            idTF.setText(em.getId().toString());
            nombreTF.setText(em.getNombre());
            apellidoTF.setText(em.getApellido());
            documentoTF.setText(em.getDni().toString());
            edadTF.setText(em.getEdad().toString());
      }

      @Override @FXML
      public void clearEntries() {
            idTF.clear();
            nombreTF.clear();
            apellidoTF.clear();
            documentoTF.clear();
            edadTF.clear();
      }

      @Override
      public boolean allFieldsCompleted() {
            return !idTF.getText().isBlank() &&
                    !nombreTF.getText().isBlank() &&
                    !apellidoTF.getText().isBlank() &&
                    !documentoTF.getText().isBlank() &&
                    !edadTF.getText().isBlank();
      }

      @FXML
      private void eliminarAP() {
            servicio.delete(seleccionado);
      }

      @FXML
      private void guardarAP() {
            if (allFieldsCompleted()) {
                  Empleado em = new Empleado(
                          Long.valueOf(idTF.getText()),
                          nombreTF.getText(),
                          apellidoTF.getText(),
                          Integer.valueOf(documentoTF.getText()),
                          Integer.valueOf(edadTF.getText())
                  );

                  servicio.save(em);

            } else {
                  dialog.showDialog(
                          Alert.AlertType.WARNING,
                          "Faltan rellenar campos",
                          "Rellena los que faltan y volvé a intentar",
                          "Advertencia"
                  );
            }
      }
}
