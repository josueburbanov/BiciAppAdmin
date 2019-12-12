package app.admin.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.BiciCandado;
import app.admin.com.biciapp_admin.datos.modelos.Candado;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IServicioCandados {
    @GET("/CandadosServicio.svc/candados")
    Call<List<Candado>> obtenerCandados();

    @POST("/CandadosServicio.svc/candados/nuevo")
    Call<Candado> crearCandado(@Body JsonObject body);

    @PUT("/CandadosServicio.svc/candados")
    Call<Boolean> editarCandado(@Body JsonObject body);

    @DELETE("/CandadosServicio.svc/candados/{idCandado}")
    Call<Boolean> eliminarCandado(@Path("idCandado")String idCandado);

    @POST("/BicisCandadosServicio.svc/biciCandados")
    Call<BiciCandado> registrarBiciCandado(@Body JsonObject body);

    @GET("/BicisCandadosServicio.svc/bicisCandados/biciCandadoActual?candado={idCandado}")
    Call<BiciCandado> obtenerRegistro(String idCandado);

    @GET("CandadosServicio.svc/candados/estacion/pinesDisponibles/{idEstacion}")
    Call<List<Integer>> obtenerPinesDisponiblesByEstacion(@Path("idEstacion")String idEstacion);

    @GET("/BicisCandadosServicio.svc/bicisCandados/candados/sinEstacion")
    Call<List<Candado>> obtenerCandadosSinEstacion();
}
