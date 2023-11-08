package com.example.demo5.conexionBD;

import com.example.demo5.clases.prestamo;
import com.example.demo5.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class prestamoBD {

    public static void guardarBD(prestamo prestamo) {

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String InsertQuery = "INSERT into not_user_prestamo (id_usuario, id_notebook, fecha_prestamo) values (?,?,?)";

        try {
            PreparedStatement ps = connectDB.prepareStatement(InsertQuery);
            ps.setInt(1, prestamo.getId_usuario());
            ps.setInt(2, prestamo.getId_notebook());
            ps.setString(3, String.valueOf(prestamo.getFecha_prestamo()));

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
