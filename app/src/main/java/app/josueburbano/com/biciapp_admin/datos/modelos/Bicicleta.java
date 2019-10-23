package app.josueburbano.com.biciapp_admin.datos.modelos;

import java.io.Serializable;

public class Bicicleta implements Serializable {
    private boolean estado;
    private String modelo;
    private String id;
    private String codigo;

    public boolean getEstado() {
        return estado;
    }

    public String getModelo() {
        return modelo;
    }

    @Override
    public String toString() {
        return modelo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String addInfo1(){
        return getModelo();
    }

    public String addInfo2(){
        if(getEstado())return "Disponible";
        else return "No disponible";
    }


}

