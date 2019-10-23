package app.josueburbano.com.biciapp_admin.datos.modelos;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Reserva implements Serializable {
    private String id;
    private String idBici;
    private String idCliente;
    private String horaInicio;
    private String horaFin;
    private String fecha;
    private boolean concretada;
    private boolean activa;

    public String getIdBici() {
        return idBici;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdBici(String idBici) {
        this.idBici = idBici;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getConcretada() {
        return concretada;
    }

    public void setConcretada(boolean concretada) {
        this.concretada = concretada;
    }

    @Override
    public String toString() {
        return "Cliente: "+getIdCliente()+ " | Bicicleta: "+getIdBici();
    }

    public String addInfo2(){
        if(isActiva()){
            return "Activa";
        }else{
            if(getConcretada()){
                return "Inactiva | Concretada" ;
            }else {
                return "Inactiva | No Concretada" ;
            }

        }

    }
    public String addInfo1(){
        return "Fecha: "+getFecha()+" | Inicia: "+getHoraInicio()+" | Fin: "+getHoraFin();
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}

