package com.example.demo5.clases;

import javafx.scene.control.CheckBox;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class prestamo {

    int id_usuario;
    int id_notebook;
    String fecha_prestamo;
    String fecha_devolucion;
    private String nombre;
    private String apellido;
    private CheckBox checkBox= new CheckBox();

    public CheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getNombre() {
        return nombre + " "+ apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public prestamo(int id_usuario, int id_notebook, String fecha_prestamo, String fecha_devolucion) {
        this.id_usuario = id_usuario;
        this.id_notebook = id_notebook;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }


    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_notebook() {
        return id_notebook;
    }

    public void setId_notebook(int id_notebook) {
        this.id_notebook = id_notebook;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }
}
