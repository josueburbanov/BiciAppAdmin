package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.josueburbano.com.biciapp_admin.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp_admin.datos.modelos.Candado;
import app.josueburbano.com.biciapp_admin.datos.repositorios.CandadosRepo;

public class CandadoViewModel extends ViewModel {
    private LiveData<Candado> candado;
    private LiveData<Boolean> confirmacion;
    private CandadosRepo candadosRepo;
    private LiveData<List<Candado>> candados;
    private LiveData<BiciCandado> registroCreado;
    private LiveData<BiciCandado> registro;
    private LiveData<List<Integer>> pinesDisponibles;

    public CandadoViewModel(CandadosRepo candadosRepo){
        this.candadosRepo = candadosRepo;
        this.candado = candadosRepo.getData();
        this.confirmacion = candadosRepo.getConfirmacion();
        this.candados = candadosRepo.getCandados();
        this.registro = candadosRepo.getRegistro();
        this.registroCreado = candadosRepo.getRegistroCreado();
        this.pinesDisponibles = candadosRepo.getPinesDisponibles();
    }

    public void ObtenerPinesDisponibles(String idEstacion){
        pinesDisponibles = candadosRepo.obtenerPinesDisponiblesByEstacion(idEstacion);
    }
    public void CrearCandado(Candado candado){
        this.candado = candadosRepo.crearCandado(candado);
    }
    public void EditarCandado(Candado candado){
        confirmacion = candadosRepo.editarCandado(candado);
    }
    public void ObtenerCandados(){
        this.candados = candadosRepo.obtenerCandados();
    }
    public void ObtenerCandadosSinEstacion(){
        this.candados = candadosRepo.obtenerCandadosSinEstacion();
    }

    public void EliminarCandado(String idItem) {
        this.confirmacion = candadosRepo.eliminarCandado(idItem);
    }
    public void CrearRegitro(BiciCandado biciCandado){
        this.registroCreado = candadosRepo.crearRegistroEntrada(biciCandado);
    }

    public void ObtenerRegistro(String idCandado){
        this.registro = candadosRepo.obtenerRegistroEntrada(idCandado);
    }

    public LiveData<Candado> ObservarCandado(){return this.candado;}
    public LiveData<Boolean> ObservarConfirmacion() {
        return confirmacion;
    }
    public LiveData<List<Candado>> ObservarCandados(){return candados;}
    public LiveData<BiciCandado> ObservarRegistroCreado(){return registroCreado;}
    public LiveData<BiciCandado> ObservarRegistro(){return registro;}
    public LiveData<List<Integer>> ObservarPinesDisponibles(){return pinesDisponibles;}



}