package com.javafx.controllers;

import com.javafx.beans.Dialog;
import com.javafx.models.Empleado;
import com.javafx.models.Proyecto;
import com.javafx.interfaces.Form;
import com.javafx.interfaces.Refreshable;
import com.javafx.services.EmpleadoService;
import com.javafx.services.ProyectoService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

@Controller
@Slf4j
public class ProyectoFormController implements Initializable, Refreshable, Form<Proyecto> {

      @Autowired private ProyectoService servicio;
      @Autowired private EmpleadoService empService;
      @Autowired private Dialog dialog;

      @FXML private TextField idTF;
      @FXML private TextField nombreTF;
      @FXML private DatePicker fecha_inicioDP;
      @FXML private CheckBox finalizadoCB;
      @FXML private ListView<Empleado> empleadosLV;
      @FXML private ChoiceBox<Empleado> empleadosCB;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            empleadosCB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                  empleadosLV.getItems().add(newVal);
            });
            refresh();
      }

      @Override
      public void refresh() {
            empleadosCB.setItems(FXCollections.observableList(empService.getAll()));
      }

      @Override
      public void fillFields(Proyecto p) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("d/M/yyyy");

            idTF.setText(p.getId().toString());
            nombreTF.setText(p.getNombre());
            fecha_inicioDP.setValue(LocalDate.parse(sdf1.format(p.getFecha_inicio())));
            fecha_inicioDP.getEditor().setText(sdf2.format(p.getFecha_inicio()));
            finalizadoCB.setSelected(p.getFinalizado());
            empleadosLV.setItems(FXCollections.observableList(p.getEmpleados()));
      }

      @Override @FXML
      public void clearFields() {
            idTF.clear();
            nombreTF.clear();
            fecha_inicioDP.getEditor().clear();
            empleadosLV.getItems().clear();
      }

      @Override
      public boolean allFieldsCompleted() {
            return !idTF.getText().isBlank() &&
                    !nombreTF.getText().isBlank() &&
                    !fecha_inicioDP.getEditor().getText().isBlank() &&
                    !empleadosLV.getItems().isEmpty();
      }

      @Override
      @SneakyThrows
      public Proyecto getInstance() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            return new Proyecto(
                    Long.valueOf(idTF.getText()),
                    nombreTF.getText(),
                    sdf.parse(fecha_inicioDP.getValue().toString()),
                    finalizadoCB.isSelected(),
                    empleadosLV.getItems().stream().toList()
            );
      }

      // TODO: usar el boton de guardar en su lugar
      @FXML
      private void editarAP() {
            try {

            Proyecto original = servicio.getProyecto(idTF.getText()).get();
            Proyecto edit = getInstance();

            dialog.showConfirmDialog(
                    "¿Deseas editar '" + original.getNombre() + "'?",
                    "Editar registro",
                    res -> {
                          if (res == ButtonType.OK) {
                                servicio.saveProyecto(edit);
                                dialog.showInfoDialog("Registro editado", "");
                          }
                    }
            );

            } catch (Exception e) {dialog.exceptionDialog(e);}
      }

      @FXML
      private void eliminarSeleccionadoAP() {
            try {

            Empleado sel = empleadosLV.getSelectionModel().getSelectedItem();
            empleadosLV.getItems().remove(sel);

            } catch (Exception e) {dialog.exceptionDialog(e);}
      }

      @FXML
      private void guardarAP() {
            if (!allFieldsCompleted()) {dialog.unfilledFormDialog();}
            else {

            try {

            servicio.saveProyecto(getInstance());
            log.info("Registro guardado con éxito");

            } catch (Exception e) {dialog.exceptionDialog(e);}}
      }
}
