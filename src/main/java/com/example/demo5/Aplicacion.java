package com.example.demo5;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Aplicacion extends Application {

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {

        Stage ventanaBienvenida = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("presentacion.fxml"));
        Scene escenaBienvenida = new Scene(fxmlLoader.load());
        ventanaBienvenida.setScene(escenaBienvenida);
        primaryStage.setScene(escenaBienvenida);
        Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
        primaryStage.getIcons().add(iconoo);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Configurar un temporizador para cerrar la ventana de bienvenida después de 3 segundos
        PauseTransition temporizador = new PauseTransition(Duration.seconds(3));
        temporizador.setOnFinished(event -> {
            primaryStage.close();
            try {
                mostrarVentanaPrincipal();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        temporizador.play();
    }

    private void mostrarVentanaPrincipal() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("vista.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage vista = new Stage();
        vista.setTitle("prestamo de notebooks");
        Image iconoo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("ICONOS/icono-notebook.png")));
        vista.getIcons().add(iconoo);
        vista.setScene(scene);
        vista.setResizable(false);
        vista.show();
    }
}