package com.javafx.controllers;

import com.javafx.beans.Dialog;
import com.javafx.interfaces.Refreshable;
import com.javafx.models.Empleado;
import com.javafx.services.EmpleadoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class EmpleadoTableController implements Initializable, Refreshable {

      @Autowired private EmpleadoService service;
      @Autowired private Dialog dialog;
      @Autowired private EmpleadoFormController empleadoFormController;

      @FXML private TableView<Empleado> tablaEmpleados;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            tablaEmpleados.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            refresh();
      }

      @Override
      public void refresh() {
            tablaEmpleados.setItems(FXCollections.observableList(service.getAll()));
      }

      private void borrarSeleccion() {
            try {

                  ObservableList<Empleado> selection = tablaEmpleados.getSelectionModel().getSelectedItems();

                  if (selection.isEmpty()) throw new NullPointerException("No hay nada seleccionado");

                  service.deleteAllInList(selection);
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
      private void empleadosTableMC() {
            Empleado em = tablaEmpleados.getSelectionModel().getSelectedItem();

            if (em != null) empleadoFormController.fillFields(em);
      }
}
