package com.example.demo5.conexionBD;

import com.example.demo5.clases.usuario;
import com.example.demo5.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class actualizarBD_usuario {

    public static void Actualizar(usuario usuario) {

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String InsertPaisQuery = "UPDATE usuario  SET nombre = ?, apellido = ? where ID_usuario=?";

        try {

            PreparedStatement ps = connectDB.prepareStatement(InsertPaisQuery);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setInt(3, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
