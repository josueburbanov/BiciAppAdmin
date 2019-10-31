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
import app.josueburbano.com.biciapp_admin.datos.modelos.Candado;
import app.josueburbano.com.biciapp_admin.datos.modelos.Reserva;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.CandadoViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.CandadoViewModelFactory;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ReservaViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ReservaViewModelFactory;

public class CandadosFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_candados, container, false);
    }
    List<Candado> fetchedCandados;
    public CandadoViewModel viewModel;
    public FloatingActionButton btnNuevo;
    public ProgressBar prgBarLoading;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prgBarLoading = getActivity().findViewById(R.id.progressBar);
        prgBarLoading.setVisibility(View.VISIBLE);
        viewModel = ViewModelProviders.of(getActivity(), new CandadoViewModelFactory())
                .get(CandadoViewModel.class);
        viewModel.ObtenerCandados();
        viewModel.ObservarCandados().observe(getActivity(), new Observer<List<Candado>>() {
            @Override
            public void onChanged(@Nullable List<Candado> candados) {
                if (candados != null) {
                    if(candados.size()==0){
                        Toast.makeText(getActivity().getApplication(),
                                "Sin candados", Toast.LENGTH_SHORT);
                    }
                    prgBarLoading.setVisibility(View.INVISIBLE);
                    fetchedCandados = candados;

                    //instantiate custom adapter
                    MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), getActivity());
                    adapter.setList(fetchedCandados);
                    adapter.setTypeKey(Candado.class);
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
        btnNuevo = (FloatingActionButton) getActivity().findViewById(R.id.btn_nuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogCandado cdd=new CustomDialogCandado(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });


    }
}


