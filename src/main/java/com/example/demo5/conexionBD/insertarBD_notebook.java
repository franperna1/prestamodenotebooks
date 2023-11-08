package com.example.demo5.conexionBD;

import com.example.demo5.clases.notebook;
import com.example.demo5.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class insertarBD_notebook {
    public static void guardarBD(notebook not) {

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String InsertQuery = "INSERT into notebook (marca, modelo) values (?,?)";

        try {
            PreparedStatement ps = connectDB.prepareStatement(InsertQuery);
            ps.setString(1, not.getMarca());
            ps.setString(2, not.getModelo());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
