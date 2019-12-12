package app.admin.com.biciapp_admin.datos.repositorios;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.Reserva;
import app.admin.com.biciapp_admin.datos.servicios.IServicioClientes;
import app.admin.com.biciapp_admin.datos.servicios.IServicioReservas;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservasRepo {
    private IServicioClientes webService;
    private MutableLiveData<Reserva> data = new MutableLiveData<>();
    private MutableLiveData<List<Reserva>> reservas = new MutableLiveData<>();
    private MutableLiveData<Boolean> confirmacionEditar = new MutableLiveData<>();
    private MutableLiveData<Boolean> confirmacionEliminar = new MutableLiveData<>();

    public LiveData<List<Reserva>> obtenerReservas() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        //Llamada HTTP
        Call<List<Reserva>> requestReservaCreando = service.obtenerReservas();
        requestReservaCreando.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                if (!response.isSuccessful()) {
                    reservas.setValue(null);
                } else {
                    reservas.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                reservas.setValue(null);

            }
        });
        return reservas;
    }

    public MutableLiveData<List<Reserva>> getReservas() {
        return reservas;
    }

    public LiveData<Reserva> crearReserva(Reserva reserva) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        JsonObject reservaJson = new JsonObject();
        reservaJson.addProperty("idBici", reserva.getIdBici());
        reservaJson.addProperty("idCliente", reserva.getIdCliente());
        reservaJson.addProperty("fecha", reserva.getFecha());
        reservaJson.addProperty("horaInicio", reserva.getHoraInicio());
        reservaJson.addProperty("horaFin", reserva.getHoraFin());
        reservaJson.addProperty("concreata", reserva.getConcretada());
        reservaJson.addProperty("activa", reserva.isActiva());

        //Llamada HTTP
        Call<Reserva> requestClienteL = service.crearReserva(reservaJson);
        requestClienteL.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<Reserva> getData() {
        return data;
    }


    public LiveData<Boolean> editarReserva(Reserva reserva) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        //Llamada HTTP
        JsonObject reservaJson = new JsonObject();
        reservaJson.addProperty("id", reserva.getId());
        reservaJson.addProperty("idBici", reserva.getIdBici());
        reservaJson.addProperty("idCliente", reserva.getIdCliente());
        reservaJson.addProperty("fecha", reserva.getFecha());
        reservaJson.addProperty("horaInicio", reserva.getHoraInicio());
        reservaJson.addProperty("horaFin", reserva.getHoraFin());
        reservaJson.addProperty("concreata", reserva.getConcretada());
        reservaJson.addProperty("activa", reserva.isActiva());

        Call<Boolean> requestReservaEditando = service.editarReserva(reservaJson);
        requestReservaEditando.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    confirmacionEditar.setValue(null);
                } else {
                    confirmacionEditar.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                confirmacionEditar.setValue(null);

            }
        });
        return confirmacionEditar;
    }

    public MutableLiveData<Boolean> getConfirmacionEditar() {
        return confirmacionEditar;
    }

    public LiveData<Boolean> eliminarReserva(String idReserva) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        //Llamada HTTP
        Call<Boolean> requestReservaEliminando = service.eliminarReserva(idReserva);
        requestReservaEliminando.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    confirmacionEliminar.setValue(null);
                } else {
                    confirmacionEliminar.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                confirmacionEliminar.setValue(null);
            }
        });
        return confirmacionEliminar;
    }

    public MutableLiveData<Boolean> getConfirmacionEliminar() {
        return confirmacionEliminar;
    }

    public LiveData<Reserva> obtenerReservaActiva(String idCliente) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IServicioClientes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicioReservas service = retrofit.create(IServicioReservas.class);

        //Llamada HTTP
        Call<Reserva> requestClienteL = service.obtenerReservaActiva(idCliente);
        requestClienteL.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if (!response.isSuccessful()) {
                    data.setValue(null);
                } else {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
