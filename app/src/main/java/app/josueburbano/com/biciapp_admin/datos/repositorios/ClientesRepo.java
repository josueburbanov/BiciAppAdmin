package app.josueburbano.com.biciapp_admin.datos.repositorios;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import app.josueburbano.com.biciapp_admin.datos.servicios.IServicioClientes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientesRepo {

    private IServicioClientes webService;
    private MutableLiveData<Cliente> data = new MutableLiveData<>();
    private MutableLiveData<Boolean> confirmacion = new MutableLiveData<>();

    public LiveData<Cliente> getCliente(String usuario, String passw) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioClientes service = retrofit.create(IServicioClientes.class);

        //Llamada HTTP
        Call<Cliente> requestClienteL = service.obtenerClienteLogueado(usuario,passw);
        requestClienteL.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }

    public MutableLiveData<Cliente> getData() {
        return data;
    }


    public LiveData<Cliente> crearCliente(Cliente cliente) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioClientes service = retrofit.create(IServicioClientes.class);

        //Llamada HTTP
        JsonObject clienteJson = new JsonObject();
        clienteJson.addProperty("nombre",cliente.getNombre());
        clienteJson.addProperty("usuario",cliente.getUsuario());
        clienteJson.addProperty("cedula",cliente.getCedula());
        clienteJson.addProperty("correoElectronico",cliente.getCorreoElectronico());
        clienteJson.addProperty("telefono",cliente.getTelefono());
        clienteJson.addProperty("rfid",cliente.getRfid());
        clienteJson.addProperty("direccion",cliente.getDireccion());
        Call<Cliente> requestClienteL = service.crearCliente(clienteJson);
        requestClienteL.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }

    public LiveData<Boolean> editarCliente(Cliente cliente) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioClientes service = retrofit.create(IServicioClientes.class);
        JsonObject clienteJson = new JsonObject();
        clienteJson.addProperty("id", cliente.getId());
        clienteJson.addProperty("nombre",cliente.getNombre());
        clienteJson.addProperty("usuario",cliente.getUsuario());
        clienteJson.addProperty("cedula",cliente.getCedula());
        clienteJson.addProperty("correoElectronico",cliente.getCorreoElectronico());
        clienteJson.addProperty("telefono",cliente.getTelefono());
        clienteJson.addProperty("rfid",cliente.getRfid());
        clienteJson.addProperty("direccion",cliente.getDireccion());
        //Llamada HTTP
        Call<Boolean> requestClienteL = service.editarCliente(clienteJson);
        requestClienteL.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    confirmacion.setValue(null);
                } else {
                    confirmacion.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                confirmacion.setValue(null);

            }
        });
        return confirmacion;
    }
    public MutableLiveData<Boolean> getConfirmacion() {
        return confirmacion;
    }
}
