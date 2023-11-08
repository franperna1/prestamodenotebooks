package com.example.demo5.conexionBD;


import com.example.demo5.clases.prestamo;
import com.example.demo5.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class seleccionarBD_historial {
    public static ObservableList<prestamo> historial (){

        Conexion cone = new Conexion();
        Connection connectDB = cone.connect();

        String ViewQuery = "select not_user_prestamo.id_notebook,not_user_prestamo.id_usuario, nombre, apellido, fecha_prestamo, fecha_devolucion from not_user_prestamo join notebook on notebook.id_notebook = not_user_prestamo.id_notebook join usuario on usuario.id_usuario = not_user_prestamo.id_usuario";

        ObservableList<prestamo> historialPrestamo = FXCollections.observableArrayList();

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ViewQuery);

            while (queryOutput.next()){
                int querynotebook = queryOutput.getInt("id_notebook");
                int queryusuario = queryOutput.getInt("id_usuario");
                String querynombre = queryOutput.getString("nombre");
                String queryapellido = queryOutput.getString("apellido");
                String queryfecha = queryOutput.getString("fecha_prestamo");
                String queryfechaD = queryOutput.getString("fecha_devolucion");
                prestamo prestamo = new prestamo(queryusuario, querynotebook, queryfecha,queryfechaD);
                prestamo.setNombre(querynombre);
                prestamo.setApellido(queryapellido);
                historialPrestamo.add(prestamo);
            }

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
        return historialPrestamo;
    }
}
