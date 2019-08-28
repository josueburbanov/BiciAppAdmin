package app.josueburbano.com.biciapp_admin.datos.modelos;

import java.io.Serializable;

public class Estacion implements Serializable {
    private double longitud;
    private double latitud;
    private String nombre;
    private String id;
    private String direccion;

    public double getLongitud() {
        return longitud;
    }


    public double getLatitud() {
        return latitud;
    }


    public String getNombre() {
        return nombre;
    }

    public String getId(){return id;}

    public String getDireccion() {
        return direccion;
    }


    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
