package app.admin.com.biciapp_admin.datos.repositorios;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.Bicicleta;
import app.admin.com.biciapp_admin.datos.servicios.IServicioBicicletas;
import app.admin.com.biciapp_admin.datos.servicios.IServicioClientes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BicicletasRepo {
    private IServicioClientes webService;
    private MutableLiveData<Bicicleta> data = new MutableLiveData<>();
    private MutableLiveData<List<Bicicleta>> bicicletas = new MutableLiveData<>();
    private MutableLiveData<Boolean> confirmacion = new MutableLiveData<>();
    private MutableLiveData<List<Bicicleta>> bicicletasByEstacion = new MutableLiveData<>();

    public LiveData<List<Bicicleta>> obtenerBicicletas() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicicletas service = retrofit.create(IServicioBicicletas.class);

        //Llamada HTTP
        Call<List<Bicicleta>> requestClienteL = service.obtenerBicicletas();
        requestClienteL.enqueue(new Callback<List<Bicicleta>>() {
            @Override
            public void onResponse(Call<List<Bicicleta>> call, Response<List<Bicicleta>> response) {
                if (!response.isSuccessful()) {
                    bicicletas.setValue(null);
                } else {
                    bicicletas.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                bicicletas.setValue(null);

            }
        });
        return bicicletas;
    }

    public MutableLiveData<List<Bicicleta>> getBicicletas() {
        return bicicletas;
    }

    public LiveData<Bicicleta> crearBici(Bicicleta bicicleta) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicicletas service = retrofit.create(IServicioBicicletas.class);

        //Llamada HTTP
        JsonObject biciJson = new JsonObject();
        biciJson.addProperty("codigo",bicicleta.getCodigo());
        biciJson.addProperty("modelo",bicicleta.getModelo());
        biciJson.addProperty("estado",bicicleta.getEstado());

        Call<Bicicleta> requestBiciCreando = service.crearBicicleta(biciJson);
        requestBiciCreando.enqueue(new Callback<Bicicleta>() {
            @Override
            public void onResponse(Call<Bicicleta> call, Response<Bicicleta> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Bicicleta> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }

    public MutableLiveData<Bicicleta> getData() {
        return data;
    }


    public LiveData<Boolean> editarBici(Bicicleta bicicleta) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicicletas service = retrofit.create(IServicioBicicletas.class);

        //Llamada HTTP
        JsonObject biciJson = new JsonObject();
        biciJson.addProperty("id",bicicleta.getId());
        biciJson.addProperty("codigo",bicicleta.getCodigo());
        biciJson.addProperty("modelo",bicicleta.getModelo());
        biciJson.addProperty("estado",bicicleta.getEstado());

        Call<Boolean> requestBiciEditando = service.editarBicicleta(biciJson);
        requestBiciEditando.enqueue(new Callback<Boolean>() {
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

    public LiveData<Boolean> eliminarBici(String idBici) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicicletas service = retrofit.create(IServicioBicicletas.class);

        //Llamada HTTP
        Call<Boolean> requestBiciEliminando = service.eliminarBicicleta(idBici);
        requestBiciEliminando.enqueue(new Callback<Boolean>() {
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

    public LiveData<List<Bicicleta>> obtenerBicisByEstacion(String idEstacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioBicicletas service = retrofit.create(IServicioBicicletas.class);

        Call<List<Bicicleta>> requestBicisEstacion = service.obtenerBicisByEstacion(idEstacion);
        requestBicisEstacion.enqueue(new Callback<List<Bicicleta>>() {
            @Override
            public void onResponse(Call<List<Bicicleta>> call, Response<List<Bicicleta>> response) {
                if (!response.isSuccessful()) {
                    bicicletasByEstacion.setValue(null);
                }else{
                    bicicletasByEstacion.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return bicicletasByEstacion;
    }

    public MutableLiveData<List<Bicicleta>> getBicicletasByEstacion() {
        return bicicletasByEstacion;
    }
}