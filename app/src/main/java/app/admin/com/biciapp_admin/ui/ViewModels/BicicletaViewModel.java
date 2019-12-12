package app.admin.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.Bicicleta;
import app.admin.com.biciapp_admin.datos.repositorios.BicicletasRepo;

public class BicicletaViewModel extends ViewModel {
    private LiveData<Bicicleta> bicicleta;
    private LiveData<Boolean> confirmacion;
    private BicicletasRepo bicicletasRepo;
    private LiveData<List<Bicicleta>> bicicletas;
    private LiveData<List<Bicicleta>> bicicletasByEstacion;

    public BicicletaViewModel(BicicletasRepo bicicletasRepo){
        this.bicicletasRepo = bicicletasRepo;
        this.bicicleta = bicicletasRepo.getData();
        this.confirmacion = bicicletasRepo.getConfirmacion();
        this.bicicletas = bicicletasRepo.getBicicletas();
        this.bicicletasByEstacion = bicicletasRepo.getBicicletasByEstacion();
    }

    public void CrearBicicleta(Bicicleta bici){
        this.bicicleta = bicicletasRepo.crearBici(bici);
    }
    public void EditarBicicleta(Bicicleta bici){
        confirmacion = bicicletasRepo.editarBici(bici);
    }
    public void ObtenerBicicletas(){
        this.bicicletas = bicicletasRepo.obtenerBicicletas();
    }
    public void EliminarBicicleta(String idItem) {
        this.confirmacion = bicicletasRepo.eliminarBici(idItem);
    }
    public void ObtenerBicisByEstacion(String idEstacion){
        this.bicicletasByEstacion = bicicletasRepo.obtenerBicisByEstacion(idEstacion);
    }

    public LiveData<Bicicleta> ObservarBici(){return this.bicicleta;}
    public LiveData<Boolean> ObservarConfirmacion() {
        return confirmacion;
    }
    public LiveData<List<Bicicleta>> ObservarBicicletas(){return bicicletas;}
    public LiveData<List<Bicicleta>> ObservarBicicletasByEstacion(){return bicicletasByEstacion;}



}
