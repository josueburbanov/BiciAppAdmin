package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import app.josueburbano.com.biciapp_admin.datos.modelos.ConfiguracionAdmin;
import app.josueburbano.com.biciapp_admin.datos.repositorios.ClientesRepo;
import app.josueburbano.com.biciapp_admin.datos.repositorios.ConfiguracionesRepo;

public class ConfiguracionesViewModel extends ViewModel {
    private LiveData<List<ConfiguracionAdmin>> cliente;
    private ConfiguracionesRepo clienteRepo;
    private LiveData<Boolean> configModif;

    public ConfiguracionesViewModel(ConfiguracionesRepo clienteRepo){
        this.clienteRepo = clienteRepo;
        this.cliente = clienteRepo.getData();
        this.configModif = clienteRepo.getResponse();
    }

    public void ObtenerConfiguraciones(){
        cliente =clienteRepo.getConfiguraciones();
    }

    public void ModificarConfiguracion(ConfiguracionAdmin config){
        configModif = clienteRepo.updateConfiguracion(config);
    }

    public LiveData<List<ConfiguracionAdmin>> ObservarConfiguraciones(){return this.cliente;}
    public LiveData<Boolean> ObservarConfigModif(){return this.configModif;}
}
