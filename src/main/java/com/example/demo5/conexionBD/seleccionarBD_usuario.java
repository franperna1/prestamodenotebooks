package com.example.demo5.conexionBD;

import com.example.demo5.clases.usuario;
import com.example.demo5.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class seleccionarBD_usuario {

    public static ObservableList<usuario> seleccionar (){

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String ViewQuery = "SELECT ID_usuario, nombre, apellido FROM usuario;" ;

        ObservableList<usuario> listaUsuario = FXCollections.observableArrayList();

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ViewQuery);

            while (queryOutput.next()){
                int queryid = queryOutput.getInt("ID_usuario");
                String querynombre= queryOutput.getString("nombre");
                String queryapellido= queryOutput.getString("apellido");

                listaUsuario.add(new usuario(queryid,querynombre,queryapellido));
            }

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
        return listaUsuario;
    }
}
