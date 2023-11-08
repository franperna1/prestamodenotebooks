package com.example.demo5.conexionBD;

import com.example.demo5.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class notebooks_prestadas {

    public static ObservableList<Integer> seleccionar (){

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String ViewQuery = "SELECT distinct id_notebook from not_user_prestamo where fecha_devolucion is null;" ;

        ObservableList<Integer> listaNotebook = FXCollections.observableArrayList();

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ViewQuery);

            while (queryOutput.next()){
                listaNotebook.add(queryOutput.getInt("ID_notebook"));
            }

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
        return listaNotebook;
    }
}
