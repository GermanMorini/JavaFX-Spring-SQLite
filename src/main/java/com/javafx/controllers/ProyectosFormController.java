package com.javafx.controllers;

import com.javafx.beans.Dialog;
import com.javafx.beans.Admin;
import com.javafx.models.Empleado;
import com.javafx.models.Proyecto;
import com.javafx.interfaces.Fillable;
import com.javafx.interfaces.Refreshable;
import com.javafx.services.EmpleadoService;
import com.javafx.services.ProyectoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
@Slf4j
public class ProyectosFormController implements Initializable, Refreshable, Fillable<Proyecto> {

      @Autowired private ProyectoService servicio;
      @Autowired private EmpleadoService empService;
      @Autowired private Dialog dialog;
      @Autowired private Admin admin;

      @FXML private TextField idTF;
      @FXML private TextField nombreTF;
      @FXML private DatePicker fecha_inicioDP;
      @FXML private CheckBox finalizadoCB;
      @FXML private ListView<Empleado> empleadosLV;
      @FXML private ChoiceBox<Empleado> empleadosCB;

      @Setter
      private Proyecto seleccionado;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            empleadosCB.getSelectionModel().selectedItemProperty().addListener((obs, newVal, oldVal) -> {
                  empleadosLV.getItems().add(newVal);
            });
      }

      @Override
      public void refresh() {
            fillEntries(seleccionado);
      }

      @Override
      public void fillEntries(Proyecto p) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("d/M/yyyy");

            idTF.setText(p.getId().toString());
            nombreTF.setText(p.getNombre());
            fecha_inicioDP.setValue(LocalDate.parse(sdf1.format(p.getFecha_inicio())));
            fecha_inicioDP.getEditor().setText(sdf2.format(p.getFecha_inicio()));
            finalizadoCB.setSelected(p.getFinalizado());
            empleadosLV.getItems().clear();
            empleadosCB.setItems(FXCollections.observableList(empService.getAll()));
      }

      @Override @FXML
      public void clearEntries() {
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

      @FXML
      private void eliminarAP() {
            try {

            dialog.showConfirmDialog(
                    "¿Deseas borrar '" + seleccionado.getNombre() + "'?",
                    "Eliminar registro",
                    res -> {
                          if (res == ButtonType.OK) {
                                servicio.deleteProyecto(seleccionado);
                                seleccionado = null;
                                dialog.showInfoDialog("Registro eliminado", "");
                          }
                    }
            );

            } catch (Exception e) {admin.manageException(e);}
      }

      @FXML
      private void eliminarSeleccionadoAP() {
            Empleado sel = empleadosLV.getSelectionModel().getSelectedItem();

            try {

            dialog.showConfirmDialog(
                    "¿Deseas borrar '" + sel.getNombre() + "'?",
                    "Eliminar registro",
                    res -> {
                          if (res == ButtonType.OK) {
                                empService.delete(sel);
                                dialog.showInfoDialog("Empleado '"+sel.getNombre()+"' eliminado", "");
                          }
                    }
            );

            } catch (Exception e) {admin.manageException(e);}
      }

      @FXML
      private void guardarAP() {
            if (allFieldsCompleted()) {
                  try {

                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                  Proyecto p = new Proyecto(
                          Long.valueOf(idTF.getText()),
                          nombreTF.getText(),
                          sdf.parse(fecha_inicioDP.getValue().toString()),
                          finalizadoCB.isSelected(),
                          empleadosLV.getItems().stream().toList()
                  );

                  servicio.saveProyecto(p);
                  log.info("Registro guardado con éxito");

                  } catch (Exception e) {admin.manageException(e);}
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
