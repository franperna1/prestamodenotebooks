package com.example.demo5.conexionBD;

import com.example.demo5.clases.notebook;
import com.example.demo5.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class seleccionarDB_notebook {

    public static ObservableList<notebook> seleccionar (){

        Conexion con = new Conexion();
        Connection connectDB = con.connect();

        String ViewQuery = "SELECT ID_notebook, marca, modelo FROM notebook;" ;

        ObservableList<notebook> listaNotebook = FXCollections.observableArrayList();

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ViewQuery);

            while (queryOutput.next()){
                int queryid = queryOutput.getInt("ID_notebook");
                String queryMarca= queryOutput.getString("marca");
                String queryModelo= queryOutput.getString("modelo");

                listaNotebook.add(new notebook(queryid,queryMarca,queryModelo));
            }

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
        return listaNotebook;
    }
}
