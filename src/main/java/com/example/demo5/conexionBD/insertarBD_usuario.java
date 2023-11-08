package com.example.demo5.conexionBD;

import com.example.demo5.clases.usuario;
import com.example.demo5.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class insertarBD_usuario {

    public static void guardarBD(usuario usuario) {

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String InsertQuery = "INSERT into usuario (nombre, apellido) values (?,?)";

        try {
            PreparedStatement ps = connectDB.prepareStatement(InsertQuery);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
