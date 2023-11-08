package com.example.demo5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//conexion con SQLite

public class Conexion {

    Connection connect;
    String direccion = String.valueOf(Conexion.class.getResource("prestamo"));
    public Connection connect(){
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:"+direccion);
        }catch (SQLException ex) {
            System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
        }
        return connect;
    }
}

