package com.example.demo5;

import com.example.demo5.clases.usuario;
import com.example.demo5.conexionBD.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class AgregarUsuarioControlador {

    @FXML
    private TextField nombre_t;
    @FXML
    private TextField apellido_t;
    @FXML
    private Button agregar;
    int id = 0;
    usuario Usuario;
    ObservableList<usuario> listaDeusuarios = FXCollections.observableArrayList();

    public void parametrosUsuario(usuario usario) {
        Usuario=usario;
        nombre_t.setText(usario.getNombre());
        apellido_t.setText(usario.getApellido());
        agregar.setText("actualizar");
        this.id = usario.getId();
    }

    public void agregar() {

        if(nombre_t.getText().isEmpty() || apellido_t.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
            Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(iconoo);

            alert.setTitle("error");
            alert.setHeaderText("agrege nombre o apellido");
            alert.showAndWait();

        }else {

            if (id == 0) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
                Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(iconoo);

                alert.setTitle("Guardar");
                alert.setHeaderText("¿Desea guardar el usuario?");
                alert.showAndWait().ifPresent(
                        response -> {
                            if (response == ButtonType.OK) {

                                String nombre = nombre_t.getText();
                                String apellido = apellido_t.getText();

                                Usuario = new usuario(0, nombre, apellido);
                                insertarBD_usuario.guardarBD(Usuario);

                                listaDeusuarios =seleccionarBD_usuario.seleccionar();
                                Usuario.setId(listaDeusuarios.get(listaDeusuarios.size()-1).getId());
                                cerrar();
                            }
                        }
                );

            } else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
                Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(iconoo);

                alert.setTitle("Modificar");
                alert.setHeaderText("¿Desea actualizar el usuario?");
                alert.showAndWait().ifPresent(
                        response -> {
                            if (response == ButtonType.OK) {

                                String nombre = nombre_t.getText();
                                String apellido = apellido_t.getText();
                                Usuario.setNombre(nombre);
                                Usuario.setApellido(apellido);
                                actualizarBD_usuario.Actualizar(Usuario);
                                cerrar();
                            }
                        }
                );
            }
        }
    }

    public void cerrar() {

        Stage stage = (Stage) agregar.getScene().getWindow();
        stage.close();

    }

    public usuario obtenerNuevo() {
        return Usuario;
    }
}
