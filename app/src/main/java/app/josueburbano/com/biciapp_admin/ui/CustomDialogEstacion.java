package app.josueburbano.com.biciapp_admin.ui;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModelFactory;

public class CustomDialogEstacion extends Dialog implements
        android.view.View.OnClickListener {

    public FragmentActivity c;
    public Dialog d;
    public Button yes, no;
    EditText editTextNombre, editTextLongitud, editTextDireccion, editTextLatitud;

    public CustomDialogEstacion(FragmentActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_nueva_estacion);
        yes = (Button) findViewById(R.id.btnCrearE);
        yes.setOnClickListener(this);
        editTextNombre = findViewById(R.id.editTextNombreE);
        editTextDireccion = findViewById(R.id.editTextDireccionE);
        editTextLatitud = findViewById(R.id.editTextLatitud);
        editTextLongitud = findViewById(R.id.editTextLongitud);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCrearE:
                EstacionViewModel viewModel = ViewModelProviders.of(c, new EstacionViewModelFactory())
                        .get(EstacionViewModel.class);

                Estacion estacionCrear = new Estacion();
                estacionCrear.setNombre(String.valueOf(editTextNombre.getText().toString()));
                estacionCrear.setDireccion(String.valueOf(editTextDireccion.getText()));
                estacionCrear.setLatitud(Double.valueOf(String.valueOf(editTextLatitud.getText())));
                estacionCrear.setLongitud(Double.valueOf(String.valueOf(editTextLongitud.getText())));
                viewModel.CrearEstacion(estacionCrear);
                viewModel.ObservarEstacionCreada().observe(c, new Observer<Estacion>() {
                    @Override
                    public void onChanged(@Nullable Estacion estacion) {
                        if(estacion !=null){
                            Toast.makeText(c.getApplicationContext(), "Estación creada "+estacion.getId(), Toast.LENGTH_LONG).show();
                            dismiss();
                            c.recreate();
                        }
                    }
                });
                break;
            default:
                break;
        }

    }

    public static class CustomDialogCliente extends Dialog implements
            View.OnClickListener {

        public FragmentActivity c;
        public Dialog d;
        public Button yes, no;
        EditText editTextNombre, editTextLongitud, editTextDireccion, editTextLatitud;

        public CustomDialogCliente(FragmentActivity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.fragment_nueva_estacion);
            yes = (Button) findViewById(R.id.btnCrearE);
            yes.setOnClickListener(this);
            editTextNombre = findViewById(R.id.editTextNombreE);
            editTextDireccion = findViewById(R.id.editTextDireccionE);
            editTextLatitud = findViewById(R.id.editTextLatitud);
            editTextLongitud = findViewById(R.id.editTextLongitud);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnCrearE:
                    EstacionViewModel viewModel = ViewModelProviders.of(c, new EstacionViewModelFactory())
                            .get(EstacionViewModel.class);

                    Estacion estacionCrear = new Estacion();
                    estacionCrear.setNombre(String.valueOf(editTextNombre.getText().toString()));
                    estacionCrear.setDireccion(String.valueOf(editTextDireccion.getText()));
                    estacionCrear.setLatitud(Double.valueOf(String.valueOf(editTextLatitud.getText())));
                    estacionCrear.setLongitud(Double.valueOf(String.valueOf(editTextLongitud.getText())));
                    viewModel.CrearEstacion(estacionCrear);
                    viewModel.ObservarEstacionCreada().observe(c, new Observer<Estacion>() {
                        @Override
                        public void onChanged(@Nullable Estacion estacion) {
                            if(estacion !=null){
                                Toast.makeText(c.getApplicationContext(), "Estación creada "+estacion.getId(), Toast.LENGTH_LONG).show();
                                dismiss();
                                c.recreate();
                            }
                        }
                    });
                    break;
                default:
                    break;
            }

        }
    }
}
