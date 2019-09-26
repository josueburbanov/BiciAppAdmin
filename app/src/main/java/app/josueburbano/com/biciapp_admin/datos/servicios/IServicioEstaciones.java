package app.josueburbano.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IServicioEstaciones {
    @POST("/EstacionesServicio.svc/estaciones/nueva")
    public Call<Estacion> crearEstacion(@Body JsonObject body);

    @GET("/EstacionesServicio.svc/estaciones")
    public Call<List<Estacion>> obtenerEstaciones();

    @DELETE("/EstacionesServicio.svc/estaciones/{idEstacion}")
    public Call<Boolean> eliminarEstacion(@Path("idEstacion")String idEstacion);

    @PUT("/EstacionesServicio.svc/estaciones")
    public Call<Boolean> editarEstacion(@Body JsonObject body);

}
