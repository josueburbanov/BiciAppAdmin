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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kusu.library.LoadingButton;

import java.util.List;
import java.util.stream.IntStream;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.modelos.BiciCandado;
import app.josueburbano.com.biciapp_admin.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp_admin.datos.modelos.Candado;
import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.BicicletaViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.BicicletaViewModelFactory;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.CandadoViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.CandadoViewModelFactory;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ClienteViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ClienteViewModelFactory;

public class CustomDialogBicicleta extends Dialog implements
        android.view.View.OnClickListener {

    public FragmentActivity c;
    public Dialog d;
    public LoadingButton yes, no;
    public EditText editTextCodigo;
    public Spinner spinnerModelo;
    public Spinner spinnerCandado;
    public Bicicleta bicicleta;
    public Candado candado;

    public CustomDialogBicicleta(FragmentActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public enum marcas{
        SPECIALIZED,
        TREK,
        CANNONDALE,
        GW,
        BMC,
        SANTA_CRUZ
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_nueva_bicicleta);
        yes = (LoadingButton) findViewById(R.id.btnAceptar);
        yes.setOnClickListener(this);

        editTextCodigo = findViewById(R.id.editTextCodigo);
        spinnerModelo = findViewById(R.id.spinnerModelo);
        spinnerCandado = findViewById(R.id.spinnerCandado);
        String[] items = new String[]{marcas.SPECIALIZED.toString(),marcas.TREK.toString(),
        marcas.CANNONDALE.toString(), marcas.GW.toString(), marcas.BMC.toString(), marcas.SANTA_CRUZ.toString()};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerModelo.setAdapter(adapter);

        TextView textViewTitulo = findViewById(R.id.textViewTitulo);
        CargarSpinners();

        if (bicicleta != null) {
            textViewTitulo.setText("Editando (" + bicicleta.getId() + ")");
            int indiceSeleccionado = -1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                indiceSeleccionado = IntStream.range(0, marcas.values().length)
                        .filter(i -> bicicleta.getModelo() == items[i])
                        .findFirst() // first occurence
                        .orElse(-1); // No element found
            }
            spinnerModelo.setSelection(indiceSeleccionado,true);
        }

    }

    private void CargarSpinners() {
        CandadoViewModel viewModelCandado = ViewModelProviders.of(c, new CandadoViewModelFactory())
                .get(CandadoViewModel.class);
        viewModelCandado.ObtenerCandadosSinEstacion();
        viewModelCandado.ObservarCandados().observe(c, new Observer<List<Candado>>() {
            @Override
            public void onChanged(@Nullable List<Candado> candados) {
                ArrayAdapter<Candado> adapterCandados = new ArrayAdapter<Candado>(c, android.R.layout.simple_spinner_dropdown_item, candados);
                spinnerCandado.setAdapter(adapterCandados);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAceptar:
                yes.showLoading();
                v.setEnabled(false);
                if (bicicleta != null) {
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
        BicicletaViewModel viewModel = ViewModelProviders.of(c, new BicicletaViewModelFactory())
                .get(BicicletaViewModel.class);
        Bicicleta bicicletaCrear = new Bicicleta();
        bicicletaCrear.setCodigo(String.valueOf(editTextCodigo.getText()));
        bicicletaCrear.setEstado(false);
        bicicletaCrear.setModelo(String.valueOf(spinnerModelo.getSelectedItem()));

        viewModel.CrearBicicleta(bicicletaCrear);
        viewModel.ObservarBici().observe(c, new Observer<Bicicleta>() {
            @Override
            public void onChanged(@Nullable Bicicleta bicicleta) {
                if (bicicleta != null) {
                    Toast.makeText(c.getApplicationContext(), "Bicicleta creada " + bicicleta.getId(), Toast.LENGTH_LONG).show();
                    CandadoViewModel viewModelCandado = ViewModelProviders.of(c, new CandadoViewModelFactory())
                            .get(CandadoViewModel.class);
                    BiciCandado nuevoRegistro = new BiciCandado();
                    candado = (Candado)spinnerCandado.getSelectedItem();
                    nuevoRegistro.setIdCandado(candado.getId());
                    nuevoRegistro.setIdBici(bicicleta.getId());
                    viewModelCandado.CrearRegitro(nuevoRegistro);
                    viewModelCandado.ObservarRegistroCreado().observe(c, new Observer<BiciCandado>() {
                        @Override
                        public void onChanged(@Nullable BiciCandado biciCandado) {
                            dismiss();
                            c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new BicicletasFragment()).commit();
                        }
                    });
                }
            }
        });
    }

    private void EditarBicicleta() {
        BicicletaViewModel viewModel = ViewModelProviders.of(c, new BicicletaViewModelFactory())
                .get(BicicletaViewModel.class);
        Bicicleta bicicletaEditar = new Bicicleta();
        bicicletaEditar.setCodigo(String.valueOf(editTextCodigo.getText()));
        bicicletaEditar.setEstado(false);
        bicicletaEditar.setModelo(String.valueOf(spinnerModelo.getSelectedItem()));

        viewModel.EditarBicicleta(bicicletaEditar);
        viewModel.ObservarConfirmacion().observe(c, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean confirmacion) {
                if (confirmacion != null) {
                    Toast.makeText(c.getApplicationContext(), "Bicicleta actualizada " + bicicleta.getId(), Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new BicicletasFragment()).commit();
                }
            }
        });
    }
}