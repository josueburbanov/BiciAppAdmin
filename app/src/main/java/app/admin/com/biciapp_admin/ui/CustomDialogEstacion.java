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

import app.admin.com.biciapp_admin.ui.ViewModels.EstacionViewModel;
import app.admin.com.biciapp_admin.ui.ViewModels.EstacionViewModelFactory;
import app.josueburbano.com.biciapp_admin.R;
import app.admin.com.biciapp_admin.datos.modelos.Estacion;

public class CustomDialogEstacion extends Dialog implements
        android.view.View.OnClickListener {

    public FragmentActivity c;
    public Dialog d;
    public LoadingButton yes, no;
    public EditText editTextNombre, editTextLongitud, editTextDireccion, editTextLatitud;
    public Estacion estacion;

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
        yes = (LoadingButton) findViewById(R.id.btnCrearEstacion);
        yes.setOnClickListener(this);
        editTextNombre = findViewById(R.id.editTextNombreE);
        editTextDireccion = findViewById(R.id.editTextDireccionE);
        editTextLatitud = findViewById(R.id.editTextLatitud);
        editTextLongitud = findViewById(R.id.editTextLongitud);
        TextView textViewTitulo = findViewById(R.id.textViewTitulo);

        if(estacion != null){
            textViewTitulo.setText("Editando ("+estacion.getNombre()+")");
            editTextNombre.setText(estacion.getNombre());
            editTextNombre.setEnabled(false);
            editTextDireccion.setText(estacion.getDireccion());
            editTextLatitud.setText(String.valueOf(estacion.getLatitud()));
            editTextLongitud.setText(String.valueOf(estacion.getLongitud()));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCrearEstacion:
                yes.showLoading();
                v.setEnabled(false);
                if(estacion!=null){
                    EditarEstacion();
                }else{
                    CrearEstacion();
                }

                break;
            default:
                break;
        }

    }

    private void CrearEstacion() {
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
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new EstacionesFragment()).commit();
                }
            }
        });
    }

    private void EditarEstacion(){
        EstacionViewModel viewModel = ViewModelProviders.of(c, new EstacionViewModelFactory())
                .get(EstacionViewModel.class);
        Estacion estacionEditar = new Estacion();
        estacionEditar.setId(estacion.getId());
        estacionEditar.setNombre(String.valueOf(editTextNombre.getText().toString()));
        estacionEditar.setDireccion(String.valueOf(editTextDireccion.getText()));
        estacionEditar.setLatitud(Double.valueOf(String.valueOf(editTextLatitud.getText())));
        estacionEditar.setLongitud(Double.valueOf(String.valueOf(editTextLongitud.getText())));
        viewModel.EditarEstacion(estacionEditar);
        viewModel.ObservarConfirmacion().observe(c, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean confirmacion) {
                if(confirmacion !=null){
                    Toast.makeText(c.getApplicationContext(), "Estación actualizada "+estacion.getId(), Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new EstacionesFragment()).commit();
                }
            }
        });
    }
}