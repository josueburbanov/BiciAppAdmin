package app.admin.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.Bicicleta;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IServicioBicicletas {
    @GET("/BicicletasServicio.svc/bicicletas")
    Call<List<Bicicleta>> obtenerBicicletas();

    @POST("/BicicletasServicio.svc/bicicletas/nueva")
    Call<Bicicleta> crearBicicleta(@Body JsonObject body);



    @PUT("/BicicletasServicio.svc/bicicletas")
    Call<Boolean> editarBicicleta(@Body JsonObject body);

    @DELETE("/BicicletasServicio.svc/bicicletas/{idBici}")
    Call<Boolean> eliminarBicicleta(@Path("idBici")String idBici);

    @GET("/BicisCandadosServicio.svc/bicisCandados/estacion/{idEstacion}/bicis/disponibles")
    Call<List<Bicicleta>> obtenerBicisByEstacion(@Path("idEstacion") String idEstacion);


}
