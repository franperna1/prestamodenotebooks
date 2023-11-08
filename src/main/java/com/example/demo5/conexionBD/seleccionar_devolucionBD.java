package com.example.demo5.conexionBD;

import com.example.demo5.clases.prestamo;
import com.example.demo5.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class seleccionar_devolucionBD {

    public static ObservableList<prestamo> seleccionar() {

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String ViewQuery = "SELECT id_notebook,not_user_prestamo.id_usuario, nombre,apellido,fecha_prestamo FROM not_user_prestamo join usuario on not_user_prestamo.id_usuario=usuario.ID_usuario where fecha_devolucion is null";

        ObservableList<prestamo> listaNotebook = FXCollections.observableArrayList();

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ViewQuery);

            while (queryOutput.next()) {
                int querynotebook = queryOutput.getInt("id_notebook");
                int queryusuario = queryOutput.getInt("id_usuario");
                String querynombre = queryOutput.getString("nombre");
                String queryapellido = queryOutput.getString("apellido");
                String queryfecha = queryOutput.getString("fecha_prestamo");
                prestamo prestamo = new prestamo(queryusuario,querynotebook, queryfecha,null);
                prestamo.setNombre(querynombre);
                prestamo.setApellido(queryapellido);
                listaNotebook.add(prestamo);
            }

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
        return listaNotebook;
    }
}
