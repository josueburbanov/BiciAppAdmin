package app.josueburbano.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IServicioEstaciones {
    @POST("/EstacionesServicio.svc/estaciones/nueva")
    public Call<Estacion> crearEstacion(@Body JsonObject body);
}
