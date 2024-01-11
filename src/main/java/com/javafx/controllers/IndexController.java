package com.javafx.controllers;

import com.javafx.beans.Dialog;
import com.javafx.beans.Admin;
import com.javafx.models.Proyecto;
import com.javafx.models.Empleado;
import com.javafx.interfaces.Refreshable;
import com.javafx.services.ProyectoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

// indica que esta clase está gestionada por spring (similar a @Service, @Controller, ...)
@Component
@Slf4j
public class IndexController implements Initializable, Refreshable {

//      @Autowired permite inyectar dependencias
//      permite que spring instancie la clase cuando sea necesario
//      similar a la anotación @FXML, que inyecta los controles de la escena
      @Qualifier("saludoLbl")
      @Autowired private String texto;
      @Autowired private ProyectoService servicio;
      @Autowired private Dialog dialog;
      @Autowired private Admin utilities;

      @Autowired private ProyectosFormController proyectosFormController;

      @FXML private ListView<Proyecto> proyectosLV;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            refresh();
      }

      @Override @FXML
      public void refresh() {
            ObservableList<Proyecto> data = FXCollections.observableList(servicio.getAll());
            proyectosLV.setItems(data);
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
                                refresh();
                                dialog.showInfoDialog("Registro eliminado", "");
                          }
                    }
            );

            } catch (Exception e) {utilities.manageException(e);}
      }

      @FXML
      private void proyectosLVMC(MouseEvent me) {
            Proyecto seleccion = proyectosLV.getSelectionModel().getSelectedItem();

            if (me.getButton() == MouseButton.PRIMARY && seleccion != null) {
            try {

            proyectosFormController.fillEntries(servicio.getProyecto(seleccion.toString()).get());
            log.info("Archivo cargado con éxito");

            } catch (Exception e) {utilities.manageException(e);}}
      }
}
