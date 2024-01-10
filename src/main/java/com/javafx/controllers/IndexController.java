package com.javafx.controllers;

import com.javafx.beans.Dialog;
import com.javafx.models.Proyecto;
import com.javafx.models.Trabajador;
import com.javafx.services.ProyectoService;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

// indica que esta clase está gestionada por spring (similar a @Service, @Controller, ...)
@Component
@Slf4j
public class IndexController implements Initializable {

//      @Autowired permite inyectar dependencias
//      permite que spring instancie la clase cuando sea necesario
//      similar a la anotación @FXML, que inyecta los controles de la escena
      @Qualifier("saludoLbl")
      @Autowired private String texto;
      @Autowired private ProyectoService servicio;
      @Autowired private Dialog dialog;

      @FXML private TextField idTF;
      @FXML private TextField nombreTF;
      @FXML private DatePicker fecha_inicioDP;
      @FXML private CheckBox finalizadoCB;
      @FXML private ListView<Trabajador> trabajadoresLV;
      @FXML private ListView<Proyecto> proyectosLV;
      @FXML private TableView<Proyecto> tabla; // ver la anotación que hay en el .fxml línea ~160

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            ObservableList<Proyecto> data = FXCollections.observableList(servicio.getAll());
            proyectosLV.setItems(data);
            tabla.setItems(data);
            tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      }

      private void manageException(Exception e) {
            log.error(e.getClass().getName() + ":\n" + e.getMessage());
            dialog.showDialog(
                    Alert.AlertType.ERROR,
                    e.getClass().getName(),
                    e.getMessage(),
                    "Error"
            );
      }

      private void refreshData() {
            ObservableList<Proyecto> data = FXCollections.observableList(servicio.getAll());

            proyectosLV.getItems().clear();
            proyectosLV.setItems(data);
            tabla.setItems(data);
      }

      private void deleteSelection() {
            try {

            log.info("Eliminando registros desde la tabla");
            ObservableList<Proyecto> selection = tabla.getSelectionModel().getSelectedItems();
            servicio.deleteAllById(selection);
            refreshData();

            } catch (Exception e) {manageException(e);}
      }

      @FXML
      private void guardarAP() {
            try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Proyecto p = new Proyecto(
                    Long.valueOf(idTF.getText()),
                    nombreTF.getText(),
                    sdf.parse(fecha_inicioDP.getValue().toString()),
                    finalizadoCB.isSelected(),
                    trabajadoresLV.getItems().stream().toList()
            );

            servicio.saveProyecto(p);
            refreshData();
            log.info("Registro guardado con éxito");

            } catch (Exception e) {manageException(e);}
      }

      @FXML
      private void descartarAP() {
            idTF.clear();
            nombreTF.clear();
            fecha_inicioDP.getEditor().clear();
            trabajadoresLV.getItems().clear();

            log.info("Eliminados todos los campos");
      }

      @FXML
      private void eliminarAP() {
            Proyecto p = proyectosLV.getSelectionModel().getSelectedItem();

            try {

            dialog.showConfirmDialog(
                    "¿Deseas borrar '" + p.getNombre() + "'?",
                    "Eliminar registro",
                    res -> {
                          if (res == ButtonType.OK) {
                                servicio.deleteProyecto(p);
                                refreshData();
                                dialog.showInfoDialog("Registro eliminado", "");
                          }
                    }
            );

            } catch (Exception e) {manageException(e);}
      }

      @FXML
      private void proyectosLVMC() {
            try {

            String seleccion = proyectosLV.getSelectionModel().getSelectedItem().toString();
            Proyecto p = servicio.getProyecto(seleccion).get();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("d/M/yyyy");
            trabajadoresLV.getItems().clear();

            idTF.setText(p.getId().toString());
            nombreTF.setText(p.getNombre());
            fecha_inicioDP.setValue(LocalDate.parse(sdf1.format(p.getFecha_inicio())));
            fecha_inicioDP.getEditor().setText(sdf2.format(p.getFecha_inicio()));
            finalizadoCB.setSelected(p.getFinalizado());
            trabajadoresLV.getItems().addAll(p.getTrabajadores());
            log.info("Archivo cargado con éxito");

            } catch (Exception e) {manageException(e);}
      }

      @FXML
      private void eliminarSeleccionAP() {
            dialog.showConfirmDialog(
                    "¿Eliminar las entradas seleccionadas?",
                    "Eliminar",
                    res -> {if (res == ButtonType.OK) deleteSelection();}
            );
      }
}
