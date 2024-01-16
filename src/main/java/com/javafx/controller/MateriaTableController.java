package com.javafx.controller;

import com.javafx.bean.Dialog;
import com.javafx.model.Materia;
import com.javafx.interfaces.Refreshable;
import com.javafx.service.MateriaService;
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
public class MateriaTableController implements Initializable, Refreshable {

      @Autowired private MateriaService servicio;
      @Autowired private Dialog dialog;
      @Autowired private MateriaFormController materiaFormController;

      @FXML private TableView<Materia> tablaMaterias; // ver la anotación que hay en el .fxml línea ~160

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            tablaMaterias.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            refresh();
      }

      @Override @FXML
      public void refresh() {
            tablaMaterias.setItems(FXCollections.observableList(servicio.getAll()));
      }

      private void borrarSeleccion() {
            try {

            ObservableList<Materia> selection = tablaMaterias.getSelectionModel().getSelectedItems();

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
            Materia p = tablaMaterias.getSelectionModel().getSelectedItem();

            if (p != null) materiaFormController.fillFields(p);
      }
}
