package app.admin.com.biciapp_admin.datos.repositorios;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.ConfiguracionAdmin;
import app.admin.com.biciapp_admin.datos.servicios.IServicioClientes;
import app.admin.com.biciapp_admin.datos.servicios.IServicioConfiguraciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfiguracionesRepo {
    private IServicioConfiguraciones webService;
    private MutableLiveData<List<ConfiguracionAdmin>> data = new MutableLiveData<>();
    private MutableLiveData<Boolean> data_response = new MutableLiveData<>();

    public LiveData<List<ConfiguracionAdmin>> getConfiguraciones() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioConfiguraciones service = retrofit.create(IServicioConfiguraciones.class);

        //Llamada HTTP
        Call<List<ConfiguracionAdmin>> requestClienteL = service.obtenerConfiguraciones();
        requestClienteL.enqueue(new Callback<List<ConfiguracionAdmin>>() {
            @Override
            public void onResponse(Call<List<ConfiguracionAdmin>> call, Response<List<ConfiguracionAdmin>> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ConfiguracionAdmin>> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }

    public LiveData<Boolean> updateConfiguracion(ConfiguracionAdmin config) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioConfiguraciones service = retrofit.create(IServicioConfiguraciones.class);

        JsonObject configJson = new JsonObject();
        configJson.addProperty("id", config.getId());
        configJson.addProperty("titulo", config.getTitulo());
        configJson.addProperty("valor", config.getValor());
        //Llamada HTTP
        Call<Boolean> requestClienteL = service.modificarConfiguracion(configJson);
        requestClienteL.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    data_response.setValue(false);
                } else {
                    data_response.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                data_response.setValue(null);

            }
        });
        return data_response;
    }

    public MutableLiveData<List<ConfiguracionAdmin>> getData() {
        return data;
    }
    public MutableLiveData<Boolean> getResponse() {
        return data_response;
    }
}
