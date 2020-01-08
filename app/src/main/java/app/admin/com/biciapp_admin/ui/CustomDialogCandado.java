package app.admin.com.biciapp_admin.ui;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kusu.library.LoadingButton;

import java.util.List;

import app.admin.com.biciapp_admin.ui.ViewModels.CandadoViewModelFactory;
import app.admin.com.biciapp_admin.ui.ViewModels.EstacionViewModel;
import app.admin.com.biciapp_admin.ui.ViewModels.EstacionViewModelFactory;
import app.admin.com.biciapp_admin.R;
import app.admin.com.biciapp_admin.datos.modelos.BiciCandado;
import app.admin.com.biciapp_admin.datos.modelos.Candado;
import app.admin.com.biciapp_admin.datos.modelos.Estacion;
import app.admin.com.biciapp_admin.ui.ViewModels.CandadoViewModel;

public class CustomDialogCandado extends Dialog implements
        android.view.View.OnClickListener {

    public FragmentActivity c;
    public Dialog d;
    public LoadingButton submit;
    public Spinner spinnerEstacion, spinnerPines;
    public Candado candado;

    List<Estacion> estacionesSpinner;

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

        spinnerEstacion = findViewById(R.id.spinnerEstaciones);
        spinnerPines = findViewById(R.id.spinnerPines);

        TextView textViewTitulo = findViewById(R.id.textViewTitulo);

        CargarSpinners();

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

    private void CargarSpinners() {
        EstacionViewModel viewModelE = ViewModelProviders.of(c, new EstacionViewModelFactory())
                .get(EstacionViewModel.class);
        viewModelE.ObtenerEstaciones();
        viewModelE.ObservarEstaciones().observe(c, new Observer<List<Estacion>>() {
            @Override
            public void onChanged(@Nullable List<Estacion> estaciones) {
                estacionesSpinner = estaciones;
                ArrayAdapter<Estacion> adapterEstaciones = new ArrayAdapter<Estacion>(c, android.R.layout.simple_spinner_dropdown_item, estaciones);
                spinnerEstacion.setAdapter(adapterEstaciones);
            }
        });

        spinnerEstacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CandadoViewModel viewModelC = ViewModelProviders.of(c, new CandadoViewModelFactory())
                        .get(CandadoViewModel.class);
                Estacion estacion = (Estacion)spinnerEstacion.getSelectedItem();
                viewModelC.ObtenerPinesDisponibles(estacion.getId());
                viewModelC.ObservarPinesDisponibles().observe(c, new Observer<List<Integer>>() {
                    @Override
                    public void onChanged(@Nullable List<Integer> pines) {
                        ArrayAdapter<Integer> adapterPines = new ArrayAdapter<Integer>(c, android.R.layout.simple_spinner_dropdown_item, pines);
                        spinnerPines.setAdapter(adapterPines);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAceptar:
                submit.showLoading();
                v.setEnabled(false);
                if (candado != null) {
                    EditarCandado();
                } else {
                    CrearCandado();
                }

                break;
            default:
                break;
        }

    }

    private void CrearCandado() {
        CandadoViewModel viewModelCandado = ViewModelProviders.of(c, new CandadoViewModelFactory())
                .get(CandadoViewModel.class);
        Candado candadoCrear = new Candado();
        Estacion estacionSeleccionada = (Estacion)spinnerEstacion.getSelectedItem();
        candadoCrear.setIdEstacion(estacionSeleccionada.getId());
        candadoCrear.setPin(Integer.valueOf(spinnerPines.getSelectedItem().toString()));
        viewModelCandado.CrearCandado(candadoCrear);
        viewModelCandado.ObservarCandado().observe(c, new Observer<Candado>() {
            @Override
            public void onChanged(@Nullable Candado candado) {
                if (candado != null) {
                    Toast.makeText(c.getApplicationContext(), "Candado creado " + candado.getId(), Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new CandadosFragment()).commit();
                }
            }
        });
    }

    private void EditarCandado() {
        CandadoViewModel viewModelCandado = ViewModelProviders.of(c, new CandadoViewModelFactory())
                .get(CandadoViewModel.class);
        Candado candadoEditar = new Candado();
        Estacion estacionSeleccionada = (Estacion)spinnerEstacion.getSelectedItem();
        candadoEditar.setIdEstacion(estacionSeleccionada.getId());
        candadoEditar.setPin(Integer.valueOf(Integer.valueOf(spinnerPines.getSelectedItem().toString())));
        viewModelCandado.EditarCandado(candadoEditar);
        viewModelCandado.ObservarCandado().observe(c, new Observer<Candado>() {
            @Override
            public void onChanged(@Nullable Candado candado) {
                if (candado != null) {
                    Toast.makeText(c.getApplicationContext(), "Candado editado " + candado.getId(), Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new CandadosFragment()).commit();
                }else{
                    Toast.makeText(c.getApplicationContext(), "No se ha podido editar el candado" + candado.getId(), Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new CandadosFragment()).commit();
                }
            }
        });
    }
}