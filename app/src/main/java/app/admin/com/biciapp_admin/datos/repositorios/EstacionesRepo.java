package app.admin.com.biciapp_admin.datos.repositorios;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.Estacion;
import app.admin.com.biciapp_admin.datos.servicios.IServicioClientes;
import app.admin.com.biciapp_admin.datos.servicios.IServicioEstaciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstacionesRepo {
    private IServicioEstaciones webService;
    private MutableLiveData<Estacion> data = new MutableLiveData<>();
    private MutableLiveData<Boolean> confirmacion = new MutableLiveData<>();
    private MutableLiveData<List<Estacion>> estaciones = new MutableLiveData<>();
    private MutableLiveData<Estacion> estacionByBici = new MutableLiveData<>();

    public LiveData<List<Estacion>> obtenerEstaciones(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioEstaciones service = retrofit.create(IServicioEstaciones.class);

        //Llamada HTTP
        Call<List<Estacion>> requestNuevaEstacion = service.obtenerEstaciones();
        requestNuevaEstacion.enqueue(new Callback<List<Estacion>>() {
            @Override
            public void onResponse(Call<List<Estacion>> call, Response<List<Estacion>> response) {
                if (!response.isSuccessful()) {
                    estaciones.setValue(null);
                } else {
                    estaciones.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Estacion>> call, Throwable t) {
                estaciones.setValue(null);
            }
        });
        return estaciones;
    }

    public MutableLiveData<List<Estacion>> getEstaciones() {
        return estaciones;
    }

    public LiveData<Estacion> crearReserva(Estacion estacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioEstaciones service = retrofit.create(IServicioEstaciones.class);

        JsonObject nuevaEstacionJson = new JsonObject();
        nuevaEstacionJson.addProperty("nombre", estacion.getNombre());
        nuevaEstacionJson.addProperty("direccion", estacion.getDireccion());
        nuevaEstacionJson.addProperty("latitud", estacion.getLatitud());
        nuevaEstacionJson.addProperty("longitud", estacion.getLongitud());

        //Llamada HTTP
        Call<Estacion> requestNuevaEstacion = service.crearEstacion(nuevaEstacionJson);
        requestNuevaEstacion.enqueue(new Callback<Estacion>() {
            @Override
            public void onResponse(Call<Estacion> call, Response<Estacion> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Estacion> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }

    public MutableLiveData<Estacion> getData() {
        return data;
    }

    public LiveData<Boolean> eliminarEstacion(String idEstacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioEstaciones service = retrofit.create(IServicioEstaciones.class);

        //Llamada HTTP
        Call<Boolean> requestNuevaEstacion = service.eliminarEstacion(idEstacion);
        requestNuevaEstacion.enqueue(new Callback<Boolean>() {
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

    public LiveData<Boolean> editarEstacion(Estacion estacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioEstaciones service = retrofit.create(IServicioEstaciones.class);


        JsonObject estacionUpdtJson = new JsonObject();
        estacionUpdtJson.addProperty("id", estacion.getId());
        estacionUpdtJson.addProperty("nombre", estacion.getNombre());
        estacionUpdtJson.addProperty("direccion", estacion.getDireccion());
        estacionUpdtJson.addProperty("latitud", estacion.getLatitud());
        estacionUpdtJson.addProperty("longitud", estacion.getLongitud());
        //Llamada HTTP
        Call<Boolean> requestNuevaEstacion = service.editarEstacion(estacionUpdtJson);
        requestNuevaEstacion.enqueue(new Callback<Boolean>() {
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

    public LiveData<Estacion> obtenerEstacionByBici(String idBici){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioEstaciones service = retrofit.create(IServicioEstaciones.class);

        //Llamada HTTP
        Call<Estacion> requestEstacionByBici = service.obtenerEstacionByBici(idBici);
        requestEstacionByBici.enqueue(new Callback<Estacion>() {
            @Override
            public void onResponse(Call<Estacion> call, Response<Estacion> response) {
                if (!response.isSuccessful()) {
                    estacionByBici.setValue(null);
                } else {
                    estacionByBici.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Estacion> call, Throwable t) {
                estacionByBici.setValue(null);
            }
        });
        return estacionByBici;
    }

    public MutableLiveData<Estacion> getEstacionByBici() {
        return estacionByBici;
    }


}
