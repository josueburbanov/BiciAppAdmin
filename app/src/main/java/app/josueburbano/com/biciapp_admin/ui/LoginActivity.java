package app.josueburbano.com.biciapp_admin.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ClienteViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ClienteViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    public static final String CLIENT_VIEW = "app.josueburbano.com.biciapp_admin.CLIENTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void IniciarSesion(View v){
        ClienteViewModel viewModel = ViewModelProviders.of(this, new ClienteViewModelFactory())
                .get(ClienteViewModel.class);

        //Inicializacion de objetos EditText para tomar valores de la UI
        EditText editTextUsuario = findViewById(R.id.editTextDireccionE);
        EditText editTextContrasena = findViewById(R.id.editTextContrasena);

        //Llamada
        viewModel.IniciarSesion(String.valueOf(editTextUsuario.getText()),String.valueOf(editTextContrasena.getText()));

        //Observar cambios posterior a la llamada
        viewModel.ObservarCliente().observe(this, new Observer<Cliente>() {
            @Override
            public void onChanged(@Nullable Cliente cliente) {
                //Si el cliente obtenido de la llamada no es nulo y es administrador
                if(cliente != null && cliente.getRol().equals( "admin")){
                    //Mensaje de login exitoso
                    Toast.makeText(getApplicationContext(), "Bienvenido "+cliente.getNombre(), Toast.LENGTH_LONG).show();

                    //Apertura de activity principal
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(CLIENT_VIEW,  cliente);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
