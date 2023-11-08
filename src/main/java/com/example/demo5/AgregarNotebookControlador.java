package com.example.demo5;

import com.example.demo5.clases.notebook;
import com.example.demo5.conexionBD.seleccionarDB_notebook;
import com.example.demo5.conexionBD.actualizarBD_notebook;
import com.example.demo5.conexionBD.insertarBD_notebook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class AgregarNotebookControlador {

    @FXML
    private TextField marca;
    @FXML
    private TextField modelo;
    @FXML
    private Button guardar;
    int id = 0;
    notebook Notebook;
    ObservableList<notebook> listaDeNotebook = FXCollections.observableArrayList();

    public void parametros(notebook notebookActual) {

        Notebook=notebookActual;
        marca.setText(notebookActual.getMarca());
        modelo.setText(notebookActual.getModelo());
        guardar.setText("actualizar");
        this.id = notebookActual.getId();
    }

    public void agregar() {

        if(marca.getText().isEmpty() || modelo.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
            Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(iconoo);

            alert.setTitle("error");
            alert.setHeaderText("agrege marca o modelo");
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
                alert.setHeaderText("¿Desea guardar la notebook?");
                alert.showAndWait().ifPresent(
                        response -> {
                            if (response == ButtonType.OK) {

                                String marcaa = marca.getText();
                                String modeloo = modelo.getText();

                                Notebook = new notebook(0, marcaa, modeloo);
                                insertarBD_notebook.guardarBD(Notebook);

                                listaDeNotebook = seleccionarDB_notebook.seleccionar();
                                Notebook.setId(listaDeNotebook.get(listaDeNotebook.size()-1).getId());
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
                alert.setHeaderText("¿Desea actualizar la notebook?");
                alert.showAndWait().ifPresent(
                        response -> {
                            if (response == ButtonType.OK) {

                                String marcaa = marca.getText();
                                String modeloo = modelo.getText();

                                Notebook.setMarca(marcaa);
                                Notebook.setModelo(modeloo);

                                actualizarBD_notebook.Actualizar(Notebook);

                                cerrar();
                            }
                        }
                );
            }
        }
    }

    public void cerrar() {

        Stage stage = (Stage) marca.getScene().getWindow();
        stage.close();

    }

    public notebook obtenerNuevo() {
        return Notebook;
    }
}
