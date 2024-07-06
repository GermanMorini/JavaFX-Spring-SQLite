package com.javafx.controller;

import com.javafx.bean.Dialog;
import com.javafx.model.Alumno;
import com.javafx.model.Materia;
import com.javafx.interfaces.Form;
import com.javafx.interfaces.Refreshable;
import com.javafx.service.AlumnoService;
import com.javafx.service.MateriaService;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@Controller
@Slf4j
public class MateriaFormController implements Initializable, Refreshable, Form<Materia> {

    @Autowired private MateriaService servicio;
    @Autowired private AlumnoService alService;
    @Autowired private Dialog dialog;

    @FXML private TextField idTF;
    @FXML private TextField nombreTF;
    @FXML private DatePicker fecha_inicioDP;
    @FXML private ChoiceBox<Character> catedraCB;
    @FXML private ListView<Alumno> alumnosLV;
    @FXML private MenuButton alumnosMB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Character> list = new ArrayList<>();
        for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
            list.add(c);
        }
        catedraCB.setItems(FXCollections.observableList(list));
        refresh();
    }

    @Override
    public void refresh() {
        alumnosMB.getItems().clear();
        List<Alumno> alumnos = alService.getAll();
        List<MenuItem> items = alumnosMB.getItems();

        alumnos.forEach(al -> {
            MenuItem mi = new MenuItem(al.getNombre());
            mi.setOnAction(e -> {
                e.consume();
                alumnosLV.getItems().add(al);
            });
            items.add(mi);
        });
    }

    @Override
    public void fillFields(Materia mat) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("d/M/yyyy");

        idTF.setText(mat.getId().toString());
        nombreTF.setText(mat.getNombre());
        fecha_inicioDP.setValue(LocalDate.parse(sdf1.format(mat.getFecha_inicio())));
        fecha_inicioDP.getEditor().setText(sdf2.format(mat.getFecha_inicio()));
        catedraCB.setValue(mat.getCatedra());
        alumnosLV.setItems(FXCollections.observableList(mat.getAlumnos()));
    }

    @Override @FXML
    public void clearFields() {
        idTF.clear();
        nombreTF.clear();
        fecha_inicioDP.getEditor().clear();
// TODO: cuando selecciono un registro no se cargan los alumnos al ListView del form
// esto lo arregla pero no se por qué, invertigarrrr
        alumnosLV.setItems(null);
    }

    @Override
    public boolean allFieldsCompleted() {
        return !nombreTF.getText().isBlank() &&
              !fecha_inicioDP.getEditor().getText().isBlank() &&
              !alumnosLV.getItems().isEmpty();
    }

    @Override
    @SneakyThrows
    public Materia getInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return new Materia(
              UUID.fromString(idTF.getText()),
              nombreTF.getText(),
              sdf.parse(fecha_inicioDP.getValue().toString()),
              catedraCB.getValue(),
              alumnosLV.getItems().stream().toList()
        );
    }

    @FXML
    private void eliminarSeleccionadoAP() {
        try {

        Alumno sel = alumnosLV.getSelectionModel().getSelectedItem();
        alumnosLV.getItems().remove(sel);

        } catch (Exception e) {dialog.exceptionDialog(e);}
    }

    @FXML
    private void guardarAP() {
        if (!allFieldsCompleted()) {dialog.unfilledFormDialog();}
        else {

        try {

        servicio.saveMateria(getInstance());
        log.info("Registro guardado con éxito");

        } catch (Exception e) {dialog.exceptionDialog(e);}}
    }

    @FXML
    private void generarUUID() {
        idTF.setText(UUID.randomUUID().toString());
    }
}
