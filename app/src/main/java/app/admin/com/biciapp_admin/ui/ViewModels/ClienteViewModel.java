package app.admin.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import app.admin.com.biciapp_admin.datos.modelos.Cliente;
import app.admin.com.biciapp_admin.datos.repositorios.ClientesRepo;

public class ClienteViewModel extends ViewModel {
    private LiveData<Cliente> cliente;
    private LiveData<Boolean> confirmacion;
    private ClientesRepo clienteRepo;
    private LiveData<List<Cliente>> clientes;
    private LiveData<Cliente> clienteById;

    public ClienteViewModel(ClientesRepo clienteRepo){
        this.clienteRepo = clienteRepo;
        this.cliente = clienteRepo.getData();
        this.clienteById = clienteRepo.getCliente();
        this.confirmacion = clienteRepo.getConfirmacion();
        this.clientes = clienteRepo.getClientes();
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
    public void ObtenerCliente(String idCliente){
        clienteById = clienteRepo.obtenerClienteById(idCliente);
    }
    public void ObtenerClientes(){
        this.clientes = clienteRepo.obtenerClientes();
    }

    public void EliminarCliente(String idItem) {
        this.confirmacion = clienteRepo.eliminarCliente(idItem);
    }

    public LiveData<Cliente> ObservarCliente(){return this.cliente;}
    public LiveData<Cliente> ObservarClienteById(){return this.clienteById;}
    public LiveData<Boolean> ObservarConfirmacion() {
        return confirmacion;
    }
    public LiveData<List<Cliente>> ObservarClientes(){return clientes;}


}
