package app.admin.com.biciapp_admin.ui;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kusu.library.LoadingButton;

import app.admin.com.biciapp_admin.ui.ViewModels.ClienteViewModelFactory;
import app.josueburbano.com.biciapp_admin.R;
import app.admin.com.biciapp_admin.datos.modelos.Cliente;
import app.admin.com.biciapp_admin.ui.ViewModels.ClienteViewModel;

public class CustomDialogCliente extends Dialog implements
        android.view.View.OnClickListener {

    public FragmentActivity c;
    public Dialog d;
    public LoadingButton yes, no;
    public EditText editTextNombre, editTextUsuario, editTextCedula, editTextCorreo, editTextTelefono
            , editTextRfid, editTextDireccion;
    public Cliente cliente;

    public CustomDialogCliente(FragmentActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_nuevo_cliente);
        yes = (LoadingButton) findViewById(R.id.btnAceptar);
        yes.setOnClickListener(this);
        editTextNombre = findViewById(R.id.editTextNombreCliente);
        editTextUsuario = findViewById(R.id.editTextNombreUsuario);
        editTextCedula = findViewById(R.id.editTextCedula);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextTelefono = findViewById(R.id.editTextHoraFin);
        editTextRfid = findViewById(R.id.editTextRfid);
        editTextDireccion = findViewById(R.id.editTextDireccionCliente);

        TextView textViewTitulo = findViewById(R.id.textViewTitulo);

        if (cliente != null) {
            textViewTitulo.setText("Editando (" + cliente.getCedula() + ")");
            editTextNombre.setText(cliente.getNombre());
            editTextUsuario.setText(cliente.getUsuario());
            editTextCedula.setText(cliente.getCedula());
            editTextCedula.setEnabled(false);
            editTextCorreo.setText(cliente.getCorreoElectronico());
            editTextTelefono.setText(cliente.getTelefono());
            editTextRfid.setText(cliente.getRfid());
            editTextDireccion.setText(cliente.getDireccion());

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.btnAceptar:
                yes.showLoading();
                v.setEnabled(false);
                if (cliente != null) {
                    EditarCliente();
                } else {
                    CrearCliente();
                }

                break;
            default:
                break;
        }

    }

    private void CrearCliente() {
        ClienteViewModel viewModel = ViewModelProviders.of(c, new ClienteViewModelFactory())
                .get(ClienteViewModel.class);
        Cliente clienteCrear = new Cliente();
        clienteCrear.setNombre(String.valueOf(editTextNombre.getText()));
        clienteCrear.setUsuario(String.valueOf(editTextUsuario.getText()));
        clienteCrear.setCedula(String.valueOf(editTextCedula.getText()));
        clienteCrear.setCorreoElectronico(String.valueOf(editTextCorreo.getText()));
        clienteCrear.setTelefono(String.valueOf(editTextTelefono.getText()));
        clienteCrear.setRfid(String.valueOf(editTextRfid.getText()));
        clienteCrear.setDireccion(String.valueOf(editTextDireccion.getText()));

        viewModel.CrearCliente(clienteCrear);
        viewModel.ObservarCliente().observe(c, new Observer<Cliente>() {
            @Override
            public void onChanged(@Nullable Cliente cliente) {
                if (cliente != null) {
                    Toast.makeText(c.getApplicationContext(), "Cliente creado " + cliente.getCedula(), Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ClientesFragment()).commit();
                }
            }
        });
    }

    private void EditarCliente() {
        ClienteViewModel viewModel = ViewModelProviders.of(c, new ClienteViewModelFactory())
                .get(ClienteViewModel.class);
        Cliente clienteEditar = new Cliente();
        clienteEditar.setId(cliente.getId());
        clienteEditar.setNombre(String.valueOf(editTextNombre.getText()));
        clienteEditar.setUsuario(String.valueOf(editTextUsuario.getText()));
        clienteEditar.setCedula(String.valueOf(editTextCedula.getText()));
        clienteEditar.setCorreoElectronico(String.valueOf(editTextCorreo.getText()));
        clienteEditar.setTelefono(String.valueOf(editTextTelefono.getText()));
        clienteEditar.setRfid(String.valueOf(editTextRfid.getText()));
        clienteEditar.setDireccion(String.valueOf(editTextDireccion.getText()));

        viewModel.EditarCliente(clienteEditar);
        viewModel.ObservarConfirmacion().observe(c, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean confirmacion) {
                if (confirmacion != null) {
                    Toast.makeText(c.getApplicationContext(), "Cliente actualizado " + cliente.getCedula(), Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ClientesFragment()).commit();
                }
            }
        });
    }
}