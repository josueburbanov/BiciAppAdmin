package app.admin.com.biciapp_admin.datos.repositorios;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.BiciCandado;
import app.admin.com.biciapp_admin.datos.modelos.Candado;
import app.admin.com.biciapp_admin.datos.servicios.IServicioCandados;
import app.admin.com.biciapp_admin.datos.servicios.IServicioClientes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CandadosRepo {
    private IServicioClientes webService;
    private MutableLiveData<Candado> data = new MutableLiveData<>();
    private MutableLiveData<List<Candado>> candados = new MutableLiveData<>();
    private MutableLiveData<Boolean> confirmacion = new MutableLiveData<>();
    private MutableLiveData<BiciCandado> biciCandadoRegistro = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> pinesDisponibles = new MutableLiveData<>();

    public LiveData<List<Candado>> obtenerCandados() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        //Llamada HTTP
        Call<List<Candado>> requestClienteL = service.obtenerCandados();
        requestClienteL.enqueue(new Callback<List<Candado>>() {
            @Override
            public void onResponse(Call<List<Candado>> call, Response<List<Candado>> response) {
                if (!response.isSuccessful()) {
                    candados.setValue(null);
                } else {
                    candados.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Candado>> call, Throwable t) {
                candados.setValue(null);

            }
        });
        return candados;
    }

    public MutableLiveData<List<Candado>> getCandados() {
        return candados;
    }

    public LiveData<Candado> crearCandado(Candado candado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        //Llamada HTTP
        JsonObject candadoJson = new JsonObject();
        candadoJson.addProperty("idEstacion", candado.getIdEstacion());
        candadoJson.addProperty("abierto", candado.isAbierto());
        candadoJson.addProperty("pin", candado.getPin());

        Call<Candado> requestCandadoCreando = service.crearCandado(candadoJson);
        requestCandadoCreando.enqueue(new Callback<Candado>() {
            @Override
            public void onResponse(Call<Candado> call, Response<Candado> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Candado> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }

    public MutableLiveData<Candado> getData() {
        return data;
    }


    public LiveData<Boolean> editarCandado(Candado candado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        //Llamada HTTP
        JsonObject candadoJson = new JsonObject();
        candadoJson.addProperty("id", candado.getId());
        candadoJson.addProperty("idEstacion", candado.getId());
        candadoJson.addProperty("abierto", candado.isAbierto());
        candadoJson.addProperty("pin", candado.getPin());

        Call<Boolean> requestCandadoEditando = service.editarCandado(candadoJson);
        requestCandadoEditando.enqueue(new Callback<Boolean>() {
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

    public LiveData<Boolean> eliminarCandado(String idCandado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        //Llamada HTTP
        Call<Boolean> requestCandadoEliminando = service.eliminarCandado(idCandado);
        requestCandadoEliminando.enqueue(new Callback<Boolean>() {
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

    private MutableLiveData<BiciCandado> biciCandadoMutableLiveData = new MutableLiveData<>();

    public LiveData<BiciCandado> crearRegistroEntrada(BiciCandado biciCandado) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        JsonObject biciCandadoJson = new JsonObject();
        biciCandadoJson.addProperty("entregaRetiro", biciCandado.getEntregaRetiro());
        biciCandadoJson.addProperty("fechaHora", biciCandado.getFechaHora());
        biciCandadoJson.addProperty("idBici", biciCandado.getIdBici());
        biciCandadoJson.addProperty("idCandado", biciCandado.getIdCandado());
        biciCandadoJson.addProperty("statusEntregaRecepcion", biciCandado.getStatusEntregaRecepcion());


        Call<BiciCandado> requestBicisEstacion = service.registrarBiciCandado(biciCandadoJson);
        requestBicisEstacion.enqueue(new Callback<BiciCandado>() {
            @Override
            public void onResponse(Call<BiciCandado> call, Response<BiciCandado> response) {
                if (!response.isSuccessful()) {
                    biciCandadoMutableLiveData.setValue(null);
                } else {
                    biciCandadoMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BiciCandado> call, Throwable t) {
                biciCandadoMutableLiveData.setValue(null);
            }
        });
        return biciCandadoMutableLiveData;
    }

    public MutableLiveData<BiciCandado> getRegistroCreado() {
        return biciCandadoMutableLiveData;
    }

    public LiveData<BiciCandado> obtenerRegistroEntrada(String idCandado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        Call<BiciCandado> requestBicisEstacion = service.obtenerRegistro(idCandado);

        requestBicisEstacion.enqueue(new Callback<BiciCandado>() {
            @Override
            public void onResponse(Call<BiciCandado> call, Response<BiciCandado> response) {
                if (!response.isSuccessful()) {
                    biciCandadoRegistro.setValue(null);
                } else {
                    biciCandadoRegistro.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BiciCandado> call, Throwable t) {
                biciCandadoRegistro.setValue(null);
            }
        });
        return biciCandadoRegistro;

    }

    public LiveData<BiciCandado> getRegistro() {
        return biciCandadoRegistro;
    }

    public LiveData<List<Integer>> obtenerPinesDisponiblesByEstacion(String idEstacion) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        //Llamada HTTP
        Call<List<Integer>> requestClienteL = service.obtenerPinesDisponiblesByEstacion(idEstacion);
        requestClienteL.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (!response.isSuccessful()) {
                    getPinesDisponibles().setValue(null);
                } else {
                    getPinesDisponibles().setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                getPinesDisponibles().setValue(null);

            }
        });
        return getPinesDisponibles();
    }

    public MutableLiveData<List<Integer>> getPinesDisponibles() {
        return pinesDisponibles;
    }

    public LiveData<List<Candado>> obtenerCandadosSinEstacion() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioCandados service = retrofit.create(IServicioCandados.class);

        //Llamada HTTP
        Call<List<Candado>> requestClienteL = service.obtenerCandadosSinEstacion();
        requestClienteL.enqueue(new Callback<List<Candado>>() {
            @Override
            public void onResponse(Call<List<Candado>> call, Response<List<Candado>> response) {
                if (!response.isSuccessful()) {
                    candados.setValue(null);
                } else {
                    candados.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Candado>> call, Throwable t) {
                candados.setValue(null);

            }
        });
        return candados;
    }
}