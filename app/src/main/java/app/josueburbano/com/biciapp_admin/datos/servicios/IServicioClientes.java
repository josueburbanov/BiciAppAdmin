package app.josueburbano.com.biciapp_admin.datos.servicios;

import com.google.gson.JsonObject;

import java.util.List;

import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServicioClientes {
    //public static final String BASE_URL = "http://172.29.173.202:45457/";
    public static final String BASE_URL = "http://ec2-34-217-59-122.us-west-2.compute.amazonaws.com/";

    @POST("/ClientesServicio.svc/clientes/authentication")
    public Call<Cliente> obtenerClienteLogueado(@Query("usuario") String usuario, @Query("passw")String passw);

    @POST("/ClientesServicio.svc/clientes/nuevo")
    public Call<Cliente> crearCliente(@Body JsonObject body);

    @PUT("/ClientesServicio.svc/clientes")
    Call<Boolean> editarCliente(@Body JsonObject body);

    @GET("/ClientesServicio.svc/clientes")
    Call<List<Cliente>> obtenerClientes();

    @DELETE("/ClientesServicio.svc/clientes/{idCliente}")
    Call<Boolean> eliminarCliente(@Path("idCliente")String idCliente);

    @GET("/ClientesServicio.svc/clientes/{idCliente}")
    Call<Cliente> obtenerCliente(@Path("idCliente") String idCliente);
}
