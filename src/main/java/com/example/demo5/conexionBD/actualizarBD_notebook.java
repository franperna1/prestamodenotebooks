package com.example.demo5.conexionBD;

import com.example.demo5.clases.notebook;
import com.example.demo5.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class actualizarBD_notebook {

    public static void Actualizar(notebook not) {

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String InsertPaisQuery = "UPDATE notebook  SET marca = ?, modelo = ? where ID_notebook=?";

        try {

            PreparedStatement ps = connectDB.prepareStatement(InsertPaisQuery);
            ps.setString(1, not.getMarca());
            ps.setString(2, not.getModelo());
            ps.setInt(3, not.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
