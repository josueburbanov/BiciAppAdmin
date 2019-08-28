package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import app.josueburbano.com.biciapp_admin.datos.repositorios.EstacionesRepo;

public class EstacionViewModel extends ViewModel {
    private LiveData<Estacion> estacion;
    private EstacionesRepo estacionesRepo;

    public EstacionViewModel(EstacionesRepo estacionesRepo){
        this.estacionesRepo = estacionesRepo;
        this.estacion = estacionesRepo.getData();
    }

    public void CrearEstacion(Estacion estacionCrear){
        estacion = estacionesRepo.crearReserva(estacionCrear);
    }

    public LiveData<Estacion> ObservarEstacionCreada(){return this.estacion;}
}
