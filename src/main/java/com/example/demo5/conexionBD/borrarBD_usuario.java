package com.example.demo5.conexionBD;

import com.example.demo5.clases.usuario;
import com.example.demo5.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class borrarBD_usuario {

    public void borrarBD(usuario usuarioActual) {

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String borrarQuery = "DELETE from usuario where ID_usuario = ?";

        try {

            PreparedStatement preparedStatement = connectDB.prepareStatement(borrarQuery);
            preparedStatement.setInt(1, usuarioActual.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
