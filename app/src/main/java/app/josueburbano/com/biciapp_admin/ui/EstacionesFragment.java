package app.josueburbano.com.biciapp_admin.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.MyCustomAdapter;
import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModelFactory;

public class EstacionesFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estaciones, container, false);
    }

    List<Estacion> fetchedEstaciones;
    public EstacionViewModel viewModel;
    public FloatingActionButton btnNuevaEstacion;
    public ProgressBar prgBarLoading;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prgBarLoading = getActivity().findViewById(R.id.progressBar);
        prgBarLoading.setVisibility(View.VISIBLE);
        viewModel = ViewModelProviders.of(getActivity(), new EstacionViewModelFactory())
                .get(EstacionViewModel.class);
        viewModel.ObtenerEstaciones();
        viewModel.ObservarEstaciones().observe(getActivity(), new Observer<List<Estacion>>() {
            @Override
            public void onChanged(@Nullable List<Estacion> estaciones) {
                if (estaciones != null) {
                    if(estaciones.size()==0){
                        Toast.makeText(getContext(),
                                "Sin estaciones", Toast.LENGTH_SHORT);
                    }
                    prgBarLoading.setVisibility(View.INVISIBLE);
                    fetchedEstaciones = estaciones;

                    //instantiate custom adapter
                    //MyCustomAdapter adapter = new MyCustomAdapter(stringsList, addInfList, idsList, getActivity(),getActivity());
                    MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), getActivity());
                    adapter.setList(fetchedEstaciones);
                    adapter.setTypeKey(Estacion.class);
                    try {
                        adapter.prepareLists();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }

                    //handle listview and assign adapter
                    if (getActivity() != null) {
                        ListView lView = (ListView) getActivity().findViewById(R.id.lstView);
                        lView.setAdapter(adapter);
                    }

                }

            }
        });
        btnNuevaEstacion = (FloatingActionButton) getActivity().findViewById(R.id.btn_nuevo);
        btnNuevaEstacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogEstacion cdd=new CustomDialogEstacion(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });


    }
}