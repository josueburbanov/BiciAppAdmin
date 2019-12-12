package app.admin.com.biciapp_admin.ui;

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

import app.admin.com.biciapp_admin.ui.ViewModels.BicicletaViewModel;
import app.admin.com.biciapp_admin.ui.ViewModels.BicicletaViewModelFactory;
import app.josueburbano.com.biciapp_admin.R;
import app.admin.com.biciapp_admin.datos.MyCustomAdapter;
import app.admin.com.biciapp_admin.datos.modelos.Bicicleta;

public class BicicletasFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bicicletas, container, false);
    }

    List<Bicicleta> fetchedBicicletas;
    public BicicletaViewModel viewModel;
    public FloatingActionButton btnNuevaBici;
    public ProgressBar prgBarLoading;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prgBarLoading = getActivity().findViewById(R.id.progressBarBicicletas);
        prgBarLoading.setVisibility(View.VISIBLE);
        viewModel = ViewModelProviders.of(getActivity(), new BicicletaViewModelFactory())
                .get(BicicletaViewModel.class);
        viewModel.ObtenerBicicletas();
        viewModel.ObservarBicicletas().observe(getActivity(), new Observer<List<Bicicleta>>() {
            @Override
            public void onChanged(@Nullable List<Bicicleta> bicicletas) {
                if (bicicletas != null) {
                    if(bicicletas.size()==0){
                        Toast.makeText(getContext(),
                                "Sin bicicletas", Toast.LENGTH_SHORT);
                    }
                    prgBarLoading.setVisibility(View.INVISIBLE);
                    fetchedBicicletas = bicicletas;

                    //instantiate custom adapter
                    //MyCustomAdapter adapter = new MyCustomAdapter(stringsList, addInfList, idsList, getActivity(),getActivity());
                    MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), getActivity());
                    adapter.setList(fetchedBicicletas);
                    adapter.setTypeKey(Bicicleta.class);
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
                        ListView lView = (ListView) getActivity().findViewById(R.id.lstView_bicicletas);
                        lView.setAdapter(adapter);
                    }

                }

            }
        });
        btnNuevaBici = (FloatingActionButton) getActivity().findViewById(R.id.btn_nueva_bicicleta);
        btnNuevaBici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogBicicleta cdd=new CustomDialogBicicleta(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });


    }
}

