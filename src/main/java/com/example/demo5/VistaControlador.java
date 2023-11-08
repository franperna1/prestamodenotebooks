package com.example.demo5;

import com.example.demo5.clases.notebook;
import com.example.demo5.clases.prestamo;
import com.example.demo5.clases.usuario;
import com.example.demo5.conexionBD.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.ResourceBundle;

public class VistaControlador implements Initializable {
    @FXML
    public Tab tab_prestamos, tab_devolucion, tab_registro, tab_usuario, tab_notebook;
    public TextField buscadorNotebook;
    @FXML
    private TableView<notebook> tablaNotebook;
    @FXML
    private TableColumn<notebook, Integer> numero_c;
    @FXML
    private TableColumn<notebook, String> marca_c;
    @FXML
    private TableColumn<notebook, String> modelo_c;

    @FXML
    private TableView<usuario> tablaUsuario;
    @FXML
    private TableColumn<usuario, String> nombre_c;
    @FXML
    private TableColumn<usuario, String> apellido_c;

    @FXML
    private TableView<notebook> listaPrestadas;
    @FXML
    private TableColumn<notebook, Integer> notebook;
    @FXML
    private TableColumn<notebook, String> prestada;
    @FXML
    private TableColumn<notebook, CheckBox> list;

    @FXML
    private TableView<prestamo> tabla_devolucion;
    @FXML
    private TableColumn<prestamo, CheckBox> check_devolucion;
    @FXML
    private TableColumn<prestamo, Integer> notebook_devolucion;
    @FXML
    private TableColumn<prestamo, String> nombre_devolucion;
    @FXML
    private TableColumn<prestamo, String> prestamo_fecha;

    @FXML
    private TableView<prestamo> t_historial;
    @FXML
    private TableColumn<prestamo, Integer> num_note;
    @FXML
    private TableColumn<prestamo, String> nombre_h;
    @FXML
    private TableColumn<prestamo, String> fecha_p;
    @FXML
    private TableColumn<prestamo, String> fecha_d;

    @FXML
    private TextField buscador_historial;
    @FXML
    private DatePicker date_historial;
    @FXML
    private TextField buscadorUsuario;
    //-----------------------------------------------------------------------------------------------
    ObservableList<notebook> listaNotebook ;
    ObservableList<notebook> seleccionarNotebook = seleccionarDB_notebook.seleccionar();
    ObservableList<usuario> listaUsuario = seleccionarBD_usuario.seleccionar();
    ObservableList<Integer> listaNotebookprestadas = FXCollections.observableArrayList();
    ObservableList<prestamo> ListaHistorial = seleccionarBD_historial.historial();
    //-----------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Agrega el Label y la ImageView al contenido del Tab
        tab_prestamos.setText("Prestamos");
        tab_prestamos.getStyleClass().add("rotate-tab-text");
        Image prestamos = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/add_circle_FILL1_wght400_GRAD200_opsz48.png"))); // Reemplaza con la ruta a tu imagen
        tab_prestamos.setGraphic(new ImageView(prestamos));

        tab_devolucion.setText("Devoluciónes");
        tab_devolucion.getStyleClass().add("rotate-tab-text");
        Image devolucion = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/check_circle_FILL0_wght400_GRAD0_opsz24.png"))); // Reemplaza con la ruta a tu imagen
        tab_devolucion.setGraphic(new ImageView(devolucion));

        tab_registro.setText("Registro");
        tab_registro.getStyleClass().add("rotate-tab-text");
        Image registro = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/history_FILL1_wght400_GRAD200_opsz48.png"))); // Reemplaza con la ruta a tu imagen
        tab_registro.setGraphic(new ImageView(registro));

        tab_usuario.setText("Usuarios");
        tab_usuario.getStyleClass().add("rotate-tab-text");
        Image usuario = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/round-account-button-with-user-inside_icon-icons.com_72596.png"))); // Reemplaza con la ruta a tu imagen
        tab_usuario.setGraphic(new ImageView(usuario));

        tab_notebook.setText("Notebook");
        tab_notebook.getStyleClass().add("rotate-tab-text");
        Image notebooks = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/laptop_chromebook_FILL1_wght400_GRAD200_opsz48.png"))); // Reemplaza con la ruta a tu imagen
        tab_notebook.setGraphic(new ImageView(notebooks));

        obtenerListaNotebook();

        cambioFormato();

        date_historial.getEditor().setDisable(true);

        //REALIZAR PRESTAMOS
        notebook.setCellValueFactory(new PropertyValueFactory<>("id"));
        prestada.setCellValueFactory(new PropertyValueFactory<>("prestada"));
        list.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        listaPrestadas.setItems(listaNotebook);

        //REALIZAR DEVOLUCION
        notebook_devolucion.setCellValueFactory(new PropertyValueFactory<>("id_notebook"));
        check_devolucion.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        nombre_devolucion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        prestamo_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));

        tabla_devolucion.setItems(seleccionar_devolucionBD.seleccionar());

        //HISTORIAL
        num_note.setCellValueFactory(new PropertyValueFactory<>("id_notebook"));
        nombre_h.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fecha_p.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));
        fecha_d.setCellValueFactory(new PropertyValueFactory<>("fecha_devolucion"));

        t_historial.setItems(ListaHistorial);

        //MOSTRAR USUARIO
        nombre_c.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellido_c.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        tablaUsuario.setItems(listaUsuario);

        //MOSTRAR NOTEBOOK
        numero_c.setCellValueFactory(new PropertyValueFactory<>("id"));
        marca_c.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modelo_c.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        tablaNotebook.setItems(seleccionarNotebook);

        Callback<TableColumn<notebook, String>, TableCell<notebook, String>> cellFactory
                =
                new Callback<TableColumn<notebook, String>, TableCell<notebook, String>>() {
                    @Override
                    public TableCell<notebook, String> call(final TableColumn<notebook, String> param) {
                        final TableCell<notebook, String> cell = new TableCell<notebook, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setAlignment(Pos.CENTER);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    setText(item);
                                    TableRow<notebook> row = getTableRow();
                                    if (row.getItem().getPrestada().contains("disponible")) {
                                        row.getStyleClass().clear();
                                        row.setStyle("-fx-background-color: #2fd6ff;");
                                    } else {
                                        row.getStyleClass().clear();
                                        row.setStyle("-fx-background-color: #2FB7FF;");
                                    }
                                }
                            }
                        };
                        return cell;
                    }
                };
        prestada.setCellFactory(cellFactory);

        //-------esto cambia al calendario----------
        Callback<DatePicker, DateCell> dayCellFactory
                = date -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                this.setDisable(false);
                this.setBackground(null);
                this.setTextFill(Color.WHITE);

                // deshabilitar las fechas futuras
                if (item.isAfter(LocalDate.now())) {
                    this.setDisable(true);
                }

                // fines de semana en color rojo
                DayOfWeek dayweek = item.getDayOfWeek();
                if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                    Paint color = Color.rgb(255, 92, 70);
                    BackgroundFill fill = new BackgroundFill(color, null, null);
                    this.setBackground(new Background(fill));
                    this.setTextFill(Color.WHITE);
                    this.setDisable(true);
                } else {
                    Paint color = Color.rgb(56, 147, 255);
                    BackgroundFill fill = new BackgroundFill(color, null, null);
                    this.setBackground(new Background(fill));
                }
            }
        };
        date_historial.setDayCellFactory(dayCellFactory);

        usuarioBuscador();

        historialBuscador();

        notebookBuscador();
    }

    public void borrarFecha() {

        date_historial.getEditor().setText("");
        date_historial.setValue(null);
        buscador_historial.setText("");
    }

    public void devolver() {
        tabla_devolucion.getItems().forEach(prestamo -> {
            if (prestamo.getCheckBox().isSelected()) {
                listaNotebookprestadas.add(prestamo.getId_notebook());
            }
        });
        if (listaNotebookprestadas.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            styleAndIconForAlertBEBUGORDI(alert);

            alert.setTitle("Error");
            alert.setHeaderText("Seleccione al menos una notebook");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            styleAndIconForAlertBEBUGORDI(alert);

            alert.setTitle("Guardar");
            alert.setHeaderText("¿Desea devolver la notebook?");
            alert.showAndWait().ifPresent(
                    response -> {
                        LocalDateTime fechaActual = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
                        String formatofechaActual = fechaActual.format(myFormatObj);

                        if (response == ButtonType.OK) {
                            tabla_devolucion.getItems().forEach(prestamo -> {
                                if (prestamo.getCheckBox().isSelected()) {
                                    ListaHistorial.forEach(prestamoHistorial -> {
                                        if (prestamo.getId_notebook() == prestamoHistorial.getId_notebook() && Objects.equals(prestamo.getFecha_prestamo(), prestamoHistorial.getFecha_prestamo())) {
                                            prestamoHistorial.setFecha_devolucion(formatofechaActual);
                                        }
                                    });
                                    prestamo.setFecha_devolucion(formatofechaActual);
                                    devolucionBD.guardarBD(prestamo);
                                    obtenerListaNotebook();
                                    listaPrestadas.setItems(listaNotebook);
                                    tabla_devolucion.setItems(seleccionar_devolucionBD.seleccionar());
                                    t_historial.refresh();
                                }
                            });
                        }
                    }
            );
        }
    }

    public void prestar() throws IOException {

        ObservableList<Integer> listaNotebookParaPrestar = FXCollections.observableArrayList();
        listaNotebook.forEach(notebook -> {
            if (notebook.getCheckBox().isSelected()) {
                listaNotebookParaPrestar.add(notebook.getId());
            }
        });
        if (listaNotebookParaPrestar.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            styleAndIconForAlertBEBUGORDI(alert);

            alert.setTitle("Error");
            alert.setHeaderText("Seleccione al menos una notebook");
            alert.showAndWait();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Aplicacion.class.getResource("agregar_prestamos.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("agregar");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            AgregarPrestamosControlador controlador = fxmlLoader.getController();
            controlador.parametrosMuchasNotebooks(listaNotebookParaPrestar);

            Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
            stage.getIcons().add(iconoo);
            stage.setResizable(false);
            stage.showAndWait();

            obtenerListaNotebook();

            listaPrestadas.setItems(listaNotebook);
            ListaHistorial.addAll(controlador.obtenerNuevo());
            t_historial.refresh();
            tabla_devolucion.setItems(seleccionar_devolucionBD.seleccionar());
            tablaUsuario.setItems(seleccionarBD_usuario.seleccionar());
        }
    }

    private void styleAndIconForAlertBEBUGORDI(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(iconoo);
    }

    private void obtenerListaNotebook() {
        listaNotebook = seleccionarDB_notebook.seleccionar();
        ObservableList<Integer> prestadasNotebook = notebooks_prestadas.seleccionar();
        prestadasNotebook.forEach(
                prestada -> {
                    listaNotebook.forEach(notebook -> {
                                if (prestada == notebook.getId()) {
                                    notebook.setPrestada(true);
                                }
                            }
                    );
                });
    }

    public void guardarNotebook() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Aplicacion.class.getResource("agregar_notebook.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("agregar");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
        stage.getIcons().add(iconoo);
        stage.setResizable(false);

        AgregarNotebookControlador controlador = fxmlLoader.getController();

        stage.showAndWait();

        if (controlador.obtenerNuevo() != null) {
            seleccionarNotebook.add(controlador.obtenerNuevo());
        }
        obtenerListaNotebook();
        listaPrestadas.setItems(listaNotebook);
        tablaNotebook.setItems(seleccionarNotebook);
        listaPrestadas.refresh();
        tablaNotebook.refresh();
    }

    public void actualizarNotebook() throws IOException {

        notebook notebookActual =  tablaNotebook.getSelectionModel().getSelectedItem();

        if (notebookActual == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            styleAndIconForAlertBEBUGORDI(alert);

            alert.setTitle("error");
            alert.setHeaderText("seleccione una notebook");
            alert.show();

        } else {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Aplicacion.class.getResource("agregar_notebook.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Actualizar");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
            stage.getIcons().add(iconoo);
            stage.setResizable(false);

            AgregarNotebookControlador controlador = fxmlLoader.getController();
            controlador.parametros(notebookActual);

            stage.showAndWait();

            tablaNotebook.refresh();
        }
    }

    public void agregarUsuario() throws IOException {

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

        if (controlador.obtenerNuevo() != null) {
            listaUsuario.add(controlador.obtenerNuevo());
        }
        tablaUsuario.refresh();
    }

    public void actualizarUsuario() throws IOException {

        usuario usuarioActual = tablaUsuario.getSelectionModel().getSelectedItem();

        if (usuarioActual == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            styleAndIconForAlertBEBUGORDI(alert);

            alert.setTitle("error");
            alert.setHeaderText("seleccione un usuario");
            alert.show();

        } else {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Aplicacion.class.getResource("agregar_usuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Actualizar");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
            stage.getIcons().add(iconoo);
            stage.setResizable(false);

            AgregarUsuarioControlador controlador = fxmlLoader.getController();
            controlador.parametrosUsuario(usuarioActual);

            stage.showAndWait();

            tablaUsuario.refresh();
        }
    }

    public void usuarioBuscador() {

        FilteredList<usuario> filteredData = new FilteredList<usuario>(listaUsuario, p -> true);
        buscadorUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (myObject.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                } else if (myObject.getApellido().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<usuario> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablaUsuario.comparatorProperty());
        tablaUsuario.setItems(sortedData);
    }

    public void historialBuscador() {
        date_historial.setValue(LocalDate.now());
        FilteredList<prestamo> filteredData = new FilteredList<prestamo>(t_historial.getItems(), p -> true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

        //filtrar con DatePicker
        date_historial.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(prestamo -> {
                if (newValue == null) {
                    return true;
                } else {
                    String fechaTexto = prestamo.getFecha_prestamo();
                    try {
                        LocalDateTime fechaPrestamo = LocalDateTime.parse(fechaTexto, formatter);
                        if (fechaPrestamo.toLocalDate().isEqual(newValue)) {
                            return true;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return false;
            });
        });

        buscador_historial.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                //filtrar segun...
                if (String.valueOf(myObject.getId_notebook()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                } else if (String.valueOf(myObject.getNombre()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                } else if (String.valueOf(myObject.getApellido()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<prestamo> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(t_historial.comparatorProperty());
        t_historial.setItems(sortedData);
    }

    public void cambioFormato() {

        //para cambiar el formato del datePiker
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        date_historial.setConverter(converter);
    }

    public void borrarusuario() {

        usuario usuarioActual = tablaUsuario.getSelectionModel().getSelectedItem();

        if (usuarioActual == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            styleAndIconForAlertBEBUGORDI(alert);

            alert.setTitle("error");
            alert.setHeaderText("seleccione un usuario");
            alert.show();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            styleAndIconForAlertBEBUGORDI(alert);

            alert.setTitle("borrar");
            alert.setHeaderText("¿Desea borrar el usuario?");
            alert.showAndWait().ifPresent(
                    response -> {

                        if (response == ButtonType.OK) {

                            borrarBD_usuario borrarBDUsuario = new borrarBD_usuario();
                            borrarBDUsuario.borrarBD(usuarioActual);

                            obtenerListaNotebook();
                            listaUsuario.remove(usuarioActual);
                            tablaUsuario.setItems(seleccionarBD_usuario.seleccionar());
                            tablaUsuario.refresh();
                        }
                    }
            );
        }
    }

    public void notebookBuscador() {

        FilteredList<notebook> filteredData = new FilteredList<notebook>(listaNotebook, p -> true);
        buscadorNotebook.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (myObject.getMarca().toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                } else if (myObject.getModelo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                }else if (myObject.getId().toString().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<notebook> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablaNotebook.comparatorProperty());
        tablaNotebook.setItems(sortedData);
    }
}