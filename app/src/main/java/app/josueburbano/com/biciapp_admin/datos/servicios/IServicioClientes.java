package app.josueburbano.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IServicioClientes {
    public static final String BASE_URL = "http:/192.168.100.66:45455";

    @POST("/ClientesServicio.svc/clientes/authentication")
    public Call<Cliente> obtenerClienteLogueado(@Query("usuario") String usuario, @Query("passw")String passw);

    @POST("/ClientesServicio.svc/clientes/nuevo")
    public Call<Cliente> crearCliente(@Body JsonObject body);

    @PUT("/ClientesServicio.svc/clientes")
    Call<Boolean> editarCliente(@Body JsonObject body);
}
