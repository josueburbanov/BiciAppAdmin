package app.josueburbano.com.biciapp_admin.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModelFactory;

public class NuevaEstacion extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nueva_estacion, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnCrear = getActivity().findViewById(R.id.btnCrearE);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EstacionViewModel viewModel = ViewModelProviders.of(getActivity(), new EstacionViewModelFactory())
                        .get(EstacionViewModel.class);
                EditText editTextNombre = getActivity().findViewById(R.id.editTextNombreE);
                EditText editTextDireccion = getActivity().findViewById(R.id.editTextDireccionE);
                EditText editTextLatitud = getActivity().findViewById(R.id.editTextLatitud);
                EditText editTextLongitud = getActivity().findViewById(R.id.editTextLongitud);
                Estacion estacionCrear = new Estacion();
                estacionCrear.setNombre(String.valueOf(editTextNombre.getText().toString()));
                estacionCrear.setDireccion(String.valueOf(editTextDireccion.getText()));
                estacionCrear.setLatitud(Double.valueOf(String.valueOf(editTextLatitud.getText())));
                estacionCrear.setLongitud(Double.valueOf(String.valueOf(editTextLongitud.getText())));
                viewModel.CrearEstacion(estacionCrear);
                viewModel.ObservarEstacionCreada().observe(getActivity(), new Observer<Estacion>() {
                    @Override
                    public void onChanged(@Nullable Estacion estacion) {
                        if(estacion !=null){
                            Toast.makeText(getActivity().getApplicationContext(), "Estación creada "+estacion.getId(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
