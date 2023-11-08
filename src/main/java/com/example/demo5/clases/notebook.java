package com.example.demo5.clases;

import javafx.scene.control.CheckBox;

public class notebook {

    Integer id;

    String marca;

    String modelo;

    CheckBox checkBox= new CheckBox();

    public CheckBox getCheckBox() {
        if (prestada){
            checkBox.setDisable(true);
        }
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    private Boolean prestada=false;

    public String getPrestada() {
        if (prestada){
            return "prestada";
        }else {
            return "disponible";
        }
    }

    public boolean setPrestada(Boolean prestada) {
        this.prestada = prestada;
        return false;
    }

    public notebook(int id, String marca, String modelo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
