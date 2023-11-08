package com.example.demo5.conexionBD;

import com.example.demo5.clases.prestamo;
import com.example.demo5.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class devolucionBD {

    public static void guardarBD(prestamo prestamo) {

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String InsertQuery = "UPDATE not_user_prestamo SET fecha_devolucion = ? WHERE ID_not_user_prestamo =  (select ID_not_user_prestamo from not_user_prestamo where fecha_devolucion is null and id_notebook = ?);";

        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        String formatofechaActual = fechaActual.format(myFormatObj);

        try {
            PreparedStatement ps = connectDB.prepareStatement(InsertQuery);

            ps.setString(1, formatofechaActual);
            ps.setInt(2, prestamo.getId_notebook());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
