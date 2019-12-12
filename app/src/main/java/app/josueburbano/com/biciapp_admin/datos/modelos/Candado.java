package app.josueburbano.com.biciapp_admin.datos.modelos;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Candado implements Serializable {
    private String id;
    private String idEstacion;
    private boolean abierto;
    private int pin;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @NonNull
    @Override
    public String toString() {
        return getId();
    }

    public String addInfo1(){
        return "Estación: "+getIdEstacion() +" | Pin: "+getPin();
    }

    public String addInfo2(){
        if(isAbierto())return "Abierto";
        else return "Cerrado";
    }
}

