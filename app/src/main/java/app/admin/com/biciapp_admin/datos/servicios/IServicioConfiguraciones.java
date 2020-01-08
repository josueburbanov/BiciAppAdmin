package app.admin.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.ConfiguracionAdmin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IServicioConfiguraciones {
    @GET("/ServicioConfiguraciones.svc/configuraciones")
    public Call<List<ConfiguracionAdmin>> obtenerConfiguraciones();

    @POST("/ServicioConfiguraciones.svc/configuracion")
    public Call<Boolean> modificarConfiguracion(@Body JsonObject body);

}
