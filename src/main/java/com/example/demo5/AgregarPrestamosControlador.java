package com.example.demo5;

import com.example.demo5.clases.prestamo;
import com.example.demo5.clases.usuario;
import com.example.demo5.conexionBD.prestamoBD;
import com.example.demo5.conexionBD.seleccionarBD_usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class AgregarPrestamosControlador implements Initializable {

    @FXML
    private TableView<usuario> tabla;
    @FXML
    private TableColumn<usuario, String> nombre;
    @FXML
    private TableColumn<usuario, String> apellido;
    @FXML
    private Button prestar;
    @FXML
    private TextField buscadorUsuario;

    int id = 0;
    ObservableList<Integer> listaDeNotebooks= FXCollections.observableArrayList();
    ObservableList<prestamo> prestamoObservableList = FXCollections.observableArrayList();
    static prestamo prestamo;

    public static prestamo getPrestamo(){
        return prestamo;
    }

    public static void setPrestamo(prestamo prestamo) {
        AgregarPrestamosControlador.prestamo = prestamo;
    }
    ObservableList<usuario> listaUsuario = FXCollections.observableArrayList();
    FilteredList<usuario> filteredData;

    usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        listaUsuario= seleccionarBD_usuario.seleccionar();
        tabla.setItems(listaUsuario);
        usuarioBuscador();
    }

    public void guardar() {

        usuario = tabla.getSelectionModel().getSelectedItem();

        if(usuario == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
            Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(iconoo);

            alert.setTitle("error");
            alert.setHeaderText("seleccione un usuario");
            alert.show();
        }else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
            Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(iconoo);

            alert.setTitle("Guardar");
            alert.setHeaderText("¿Desea guardar el prestamo?");
            alert.showAndWait().ifPresent(
                    response -> {
                        LocalDateTime fechaActual = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
                        String formatofechaActual = fechaActual.format(myFormatObj);
                        if (response == ButtonType.OK) {
                            if(id==0){
                                listaDeNotebooks.forEach(notebookID->{
                                    prestamo = new prestamo( usuario.getId(), notebookID, formatofechaActual, null);
                                    prestamo.setNombre(usuario.getNombre());
                                    prestamo.setApellido(usuario.getApellido());
                                    prestamoBD.guardarBD(prestamo);
                                    prestamoObservableList.add(prestamo);

                                    cerrar();
                                });

                            }else {
                                prestamo prestamo = new prestamo( usuario.getId(), id, formatofechaActual, null);
                                prestamoBD.guardarBD(prestamo);
                                prestamoObservableList.add(prestamo);
                                cerrar();
                            }
                        }
                    }
            );
        }
    }

    public void cerrar() {

        Stage stage = (Stage) prestar.getScene().getWindow();
        stage.close();

    }

    public void agregaUsuario() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Aplicacion.class.getResource("agregar_usuario.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("agregar");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
        stage.getIcons().add(iconoo);
        stage.setResizable(false);

        AgregarUsuarioControlador controlador = fxmlLoader.getController();

        stage.showAndWait();

        listaUsuario.add(controlador.obtenerNuevo());
        tabla.refresh();
    }

    public void usuarioBuscador(){

        filteredData= new FilteredList<usuario>(listaUsuario, p -> true);
        buscadorUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (myObject.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    // Filter matches first name.

                } else if (myObject.getApellido().toLowerCase().contains(lowerCaseFilter)){
                    return true; // Filter matches last name.
                }

                return false; // Does not match.
            });
        });

        SortedList<usuario> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tabla.comparatorProperty());
        tabla.setItems(sortedData);
    }

    public void parametrosMuchasNotebooks(ObservableList<Integer> listaNotebookParaPrestar) {
        this.listaDeNotebooks=listaNotebookParaPrestar;
    }

    public ObservableList<prestamo> obtenerNuevo() {
        return prestamoObservableList;
    }

    public usuario obtenerUsuarioNuevo(){
        return usuario;
    }
}
