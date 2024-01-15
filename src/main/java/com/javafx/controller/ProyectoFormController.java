package com.javafx.controller;

import com.javafx.bean.Dialog;
import com.javafx.model.Empleado;
import com.javafx.model.Proyecto;
import com.javafx.interfaces.Form;
import com.javafx.interfaces.Refreshable;
import com.javafx.service.EmpleadoService;
import com.javafx.service.ProyectoService;
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
import java.util.List;
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
      @FXML private MenuButton empleadosMB;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {
            refresh();
      }

      @Override
      public void refresh() {
            empleadosMB.getItems().clear();
            List<Empleado> empl = empService.getAll();
            List<MenuItem> items = empleadosMB.getItems();

            empl.forEach(em -> {
                  MenuItem mi = new MenuItem(em.getNombre());
                  mi.setOnAction(e -> {
                        e.consume();
                        empleadosLV.getItems().add(em);
                  });
                  items.add(mi);
            });
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
