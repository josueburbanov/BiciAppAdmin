package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import app.josueburbano.com.biciapp_admin.datos.repositorios.ClientesRepo;

public class ClienteViewModel extends ViewModel {
    private LiveData<Cliente> cliente;
    private LiveData<Boolean> confirmacion;
    private ClientesRepo clienteRepo;

    public ClienteViewModel(ClientesRepo clienteRepo){
        this.clienteRepo = clienteRepo;
        this.cliente = clienteRepo.getData();
        this.confirmacion = clienteRepo.getConfirmacion();
    }

    public void IniciarSesion(String usuario, String contraseña){
        cliente =clienteRepo.getCliente(usuario,contraseña);
    }
    public void CrearCliente(Cliente cliente){
        this.cliente = clienteRepo.crearCliente(cliente);
    }
    public void EditarCliente(Cliente cliente){
        confirmacion = clienteRepo.editarCliente(cliente);
    }

    public LiveData<Cliente> ObservarCliente(){return this.cliente;}
    public LiveData<Boolean> ObservarConfirmacion() {
        return confirmacion;
    }
}
