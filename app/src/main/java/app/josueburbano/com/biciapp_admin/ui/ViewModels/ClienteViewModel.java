package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import app.josueburbano.com.biciapp_admin.datos.repositorios.ClientesRepo;

public class ClienteViewModel extends ViewModel {
    private LiveData<Cliente> cliente;
    private ClientesRepo clienteRepo;

    public ClienteViewModel(ClientesRepo clienteRepo){
        this.clienteRepo = clienteRepo;
        this.cliente = clienteRepo.getData();
    }

    public void IniciarSesion(String usuario, String contraseña){
        cliente =clienteRepo.getCliente(usuario,contraseña);
    }

    public LiveData<Cliente> ObservarCliente(){return this.cliente;}
}
