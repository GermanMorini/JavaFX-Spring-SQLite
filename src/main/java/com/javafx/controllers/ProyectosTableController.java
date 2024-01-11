package com.javafx.controllers;

import com.javafx.beans.Admin;
import com.javafx.beans.Dialog;
import com.javafx.models.Proyecto;
import com.javafx.interfaces.Refreshable;
import com.javafx.services.ProyectoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Slf4j
public class ProyectosTableController implements Initializable, Refreshable {

      @Autowired private ProyectoService servicio;
      @Autowired private Dialog dialog;
      @Autowired private Admin admin;
      @Autowired private IndexController indexController;

      @FXML private TableView<Proyecto> tablaProyectos; // ver la anotación que hay en el .fxml línea ~160

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            tablaProyectos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            refresh();
      }

      private void borrarSeleccion() {
            try {

            ObservableList<Proyecto> selection = tablaProyectos.getSelectionModel().getSelectedItems();

            if (selection.isEmpty()) throw new NullPointerException("No hay nada seleccionado");

            servicio.deleteAllById(selection);
            refresh();
            indexController.refresh();
            } catch (Exception e) {admin.manageException(e);}
      }

      @FXML
      private void eliminarSeleccionados() {
            dialog.showConfirmDialog(
                    "¿Eliminar las entradas seleccionadas?",
                    "Eliminar",
                    res -> {if (res == ButtonType.OK) borrarSeleccion();}
            );
      }

      @Override @FXML
      public void refresh() {
            tablaProyectos.setItems(FXCollections.observableList(servicio.getAll()));
      }
}
