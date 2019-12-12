package app.admin.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.Reserva;
import app.admin.com.biciapp_admin.datos.repositorios.ReservasRepo;

public class ReservaViewModel extends ViewModel {
    private LiveData<Reserva> reserva;
    private LiveData<Boolean> confirmacionEdicion;
    private LiveData<Boolean> confirmacionEliminacion;
    private LiveData<List<Reserva>> reservas;
    private ReservasRepo reservasRepo;
    private LiveData<Reserva> reservaActiva;

    public ReservaViewModel(ReservasRepo reservasRepo){
        this.reservasRepo = reservasRepo;
        this.reserva = reservasRepo.getData();
        this.confirmacionEdicion = reservasRepo.getConfirmacionEditar();
        this.confirmacionEliminacion = reservasRepo.getConfirmacionEliminar();
        this.reservas = reservasRepo.getReservas();
    }

    public void obtenerReservaActiva(String idCliente){
        reservaActiva = reservasRepo.obtenerReservaActiva(idCliente);
    }

    public void CrearReserva(Reserva reserva){
        this.reserva = reservasRepo.crearReserva(reserva);
    }
    public void EditarReserva(Reserva reserva){
        this.confirmacionEdicion = reservasRepo.editarReserva(reserva);
    }
    public void ObtenerReservas(){
        this.reservas = reservasRepo.obtenerReservas();
    }

    public void EliminarReserva(String idItem) {
        this.confirmacionEliminacion = reservasRepo.eliminarReserva(idItem);
    }

    public LiveData<Reserva> ObservarReservaCreada(){return this.reserva;}
    public LiveData<Boolean> ObservarConfirmacionEdicion() {
        return confirmacionEdicion;
    }
    public LiveData<Boolean> ObservarConfirmacionEliminacion() {
        return confirmacionEliminacion;
    }
    public LiveData<List<Reserva>> ObservarReservas(){return reservas;}
    public LiveData<Reserva> ObservarReservaActiva() {
        return this.reservaActiva;
    }
}
