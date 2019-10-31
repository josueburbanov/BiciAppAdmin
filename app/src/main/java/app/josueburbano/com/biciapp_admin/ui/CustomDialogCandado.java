package app.josueburbano.com.biciapp_admin.ui;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kusu.library.LoadingButton;

import java.util.stream.IntStream;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp_admin.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.BicicletaViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.BicicletaViewModelFactory;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.CandadoViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.CandadoViewModelFactory;

class CustomDialogCandado extends Dialog implements
        android.view.View.OnClickListener {

    public FragmentActivity c;
    public Dialog d;
    public LoadingButton submit;
    public EditText editTextPin;
    public Spinner spinnerEstacion, spinnerBicicleta;
    public Bicicleta candado;

    public CustomDialogCandado(FragmentActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_nuevo_candado);
        submit = findViewById(R.id.btnAceptar);
        submit.setOnClickListener(this);

        editTextPin = findViewById(R.id.editTextPin);
        spinnerEstacion = findViewById(R.id.spinnerEstaciones);
        spinnerBicicleta = findViewById(R.id.spinnerBicicletas);

        TextView textViewTitulo = findViewById(R.id.textViewTitulo);

        if (candado != null) {
            textViewTitulo.setText("Editando (" + candado.getId() + ")");
            CandadoViewModel viewModel = ViewModelProviders.of(c, new CandadoViewModelFactory())
                    .get(CandadoViewModel.class);
            viewModel.ObtenerRegistro(candado.getId());
            viewModel.ObservarRegistro().observe(c, new Observer<BiciCandado>() {
                @Override
                public void onChanged(@Nullable BiciCandado biciCandado) {
                    if (biciCandado != null){

                    }
                }
            });

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAceptar:
                submit.showLoading();
                v.setEnabled(false);
                if (candado != null) {
                    EditarBicicleta();
                } else {
                    CrearBicicleta();
                }

                break;
            default:
                break;
        }

    }

    private void CrearBicicleta() {

    }

    private void EditarBicicleta() {

    }
}