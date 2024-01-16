package com.javafx.controller;

import com.javafx.bean.Dialog;
import com.javafx.model.Proyecto;
import com.javafx.interfaces.Refreshable;
import com.javafx.service.ProyectoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Slf4j
public class ProyectoTableController implements Initializable, Refreshable {

      @Autowired private ProyectoService servicio;
      @Autowired private Dialog dialog;
      @Autowired private ProyectoFormController proyectoFormController;

      @FXML private TableView<Proyecto> tablaProyectos; // ver la anotación que hay en el .fxml línea ~160

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            tablaProyectos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            refresh();
      }

      @Override @FXML
      public void refresh() {
            tablaProyectos.setItems(FXCollections.observableList(servicio.getAll()));
      }

      private void borrarSeleccion() {
            try {

            ObservableList<Proyecto> selection = tablaProyectos.getSelectionModel().getSelectedItems();

            if (selection.isEmpty()) throw new NullPointerException("No hay nada seleccionado");

            servicio.deleteAllById(selection);
            refresh();
            } catch (Exception e) {dialog.exceptionDialog(e);}
      }

      @FXML
      private void eliminarSeleccionados() {
            dialog.showConfirmDialog(
                    "¿Eliminar las entradas seleccionadas?",
                    "Eliminar",
                    res -> {if (res == ButtonType.OK) borrarSeleccion();}
            );
      }

      @FXML
      private void proyectosTableMC() {
            Proyecto p = tablaProyectos.getSelectionModel().getSelectedItem();

            if (p != null) proyectoFormController.fillFields(p);
      }
}