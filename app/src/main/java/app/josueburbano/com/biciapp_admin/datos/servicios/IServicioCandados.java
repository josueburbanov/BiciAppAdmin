package app.josueburbano.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.josueburbano.com.biciapp_admin.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp_admin.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp_admin.datos.modelos.Candado;
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

    @POST("/BiciCandadosServicio.svc/biciCandados")
    Call<BiciCandado> registrarBiciCandado(@Body JsonObject body);

    @GET("/bicisCandados/biciCandadoActual?candado={idCandado}")
    Call<BiciCandado> obtenerRegistro(String idCandado);
}
