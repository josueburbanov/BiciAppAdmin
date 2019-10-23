package app.josueburbano.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.josueburbano.com.biciapp_admin.datos.modelos.Reserva;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IServicioReservas {
    @GET("/ReservasServicio.svc/reservas")
    Call<List<Reserva>> obtenerReservas();

    @POST("/ReservasServicio.svc/reservas/nueva")
    Call<Reserva> crearReserva(@Body JsonObject body);

    @PUT("/ReservasServicio.svc/reservas")
    Call<Boolean> editarReserva(@Body JsonObject body);

    @DELETE("/ReservasServicio.svc/reservas/{idReserva}")
    Call<Boolean> eliminarReserva(@Path("idReserva")String idReserva);

    @GET("/ReservasServicio.svc/reservas/cliente/{idCliente}/activa")
    Call<Reserva> obtenerReservaActiva(@Path("idCliente") String idCliente);
}
