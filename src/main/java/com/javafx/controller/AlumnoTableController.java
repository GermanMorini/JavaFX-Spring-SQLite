package com.javafx.controller;

import com.javafx.bean.Dialog;
import com.javafx.interfaces.Refreshable;
import com.javafx.model.Alumno;
import com.javafx.service.AlumnoService;
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
public class AlumnoTableController implements Initializable, Refreshable {

      @Autowired private AlumnoService service;
      @Autowired private Dialog dialog;
      @Autowired private AlumnoFormController alumnoFormController;

      @FXML private TableView<Alumno> tablaEmpleados;

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

                  ObservableList<Alumno> selection = tablaEmpleados.getSelectionModel().getSelectedItems();

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
            Alumno em = tablaEmpleados.getSelectionModel().getSelectedItem();

            if (em != null) alumnoFormController.fillFields(em);
      }
}
