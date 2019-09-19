package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import app.josueburbano.com.biciapp_admin.datos.repositorios.EstacionesRepo;

public class EstacionViewModel extends ViewModel {
    private LiveData<Estacion> estacion;
    private LiveData<Boolean> confirmacion;
    private EstacionesRepo estacionesRepo;
    private LiveData<List<Estacion>> estaciones;

    public EstacionViewModel(EstacionesRepo estacionesRepo){
        this.estacionesRepo = estacionesRepo;
        this.estacion = estacionesRepo.getData();
        this.estaciones = estacionesRepo.getEstaciones();
        this.confirmacion = estacionesRepo.getConfirmacion();
    }

    public void CrearEstacion(Estacion estacionCrear){
        estacion = estacionesRepo.crearReserva(estacionCrear);
    }
    public void ObtenerEstaciones(){
        estaciones = estacionesRepo.obtenerEstaciones();
    }
    public void EliminarEstacion(String idEstacionEliminar){
        confirmacion = estacionesRepo.eliminarEstacion(idEstacionEliminar);
    }

    public LiveData<Estacion> ObservarEstacionCreada(){return this.estacion;}
    public LiveData<List<Estacion>> ObservarEstaciones(){return this.estaciones;}
    public LiveData<Boolean> ObservarConfirmacion(){return this.confirmacion;}
}