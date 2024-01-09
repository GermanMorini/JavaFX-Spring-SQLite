package com.javafx.controllers;

import com.javafx.models.Proyecto;
import com.javafx.models.Trabajador;
import com.javafx.services.ProyectoService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

// indica que esta clase está gestionada por spring (similar a @Service, @Controller, ...)
@Component
@Slf4j
public class IndexController implements Initializable {

//      @Autowired permite inyectar dependencias
//      permite que spring instancie la clase cuando sea necesario
//      similar a la anotación @FXML, que inyecta los controles de la escena
      @Autowired
      @Qualifier("saludoLbl")
      private String texto;

      @Autowired
      private ProyectoService servicio;

      @FXML private TextField idTF;
      @FXML private TextField nombreTF;
      @FXML private DatePicker fecha_inicioDP;
      @FXML private CheckBox finalizadoCB;
      @FXML private ListView<Trabajador> trabajadoresLV;

      @FXML private ListView<String> proyectosLV;

      @Override
      public void initialize(URL url, ResourceBundle resourceBundle) {

      }

      @FXML
      private void guardarAP() {
            try {
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                  Proyecto p = new Proyecto(
                          idTF.getText(),
                          nombreTF.getText(),
                          sdf.parse(fecha_inicioDP.getValue().toString()),
                          finalizadoCB.isSelected(),
                          trabajadoresLV.getItems()
                  );

                  servicio.saveProyecto(p);
                  proyectosLV.getItems().add(p.getNombre());
                  log.info("Archivo guardado con éxito");
            } catch (Exception e) {
                  log.error("Error al guardar el archivo:\n" + e.getMessage());
            }
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
      private void proyectosLVMC() throws IOException {
            try {
                  String proyectoSeleccionado = proyectosLV.getSelectionModel().getSelectedItem();
                  Proyecto p = servicio.getProyecto(proyectoSeleccionado);
                  SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                  trabajadoresLV.getItems().clear();

                  idTF.setText(p.getId());
                  nombreTF.setText(p.getNombre());
                  fecha_inicioDP.getEditor().setText(sdf.format(p.getFecha_inicio()));
                  finalizadoCB.setSelected(p.getFinalizado());
                  trabajadoresLV.getItems().addAll(p.getTrabajadores());
                  log.info("Archivo cargado con éxito");
            } catch (Exception e) {
                  log.error("Error al cargar el archivo:\n" + e.getMessage());
            }
      }
}
