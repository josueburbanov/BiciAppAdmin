package app.josueburbano.com.biciapp_admin.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.MyCustomAdapter;
import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModelFactory;

public class EstacionFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estaciones, container, false);
    }

    List<Estacion> fetchedEstaciones;
    public EstacionViewModel viewModel;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel= ViewModelProviders.of(getActivity(), new EstacionViewModelFactory())
                .get(EstacionViewModel.class);
        viewModel.ObtenerEstaciones();
        viewModel.ObservarEstaciones().observe(getActivity(), new Observer<List<Estacion>>() {
            @Override
            public void onChanged(@Nullable List<Estacion> estaciones) {
                if(estaciones != null){
                    fetchedEstaciones = estaciones;

                    //instantiate custom adapter
                    //MyCustomAdapter adapter = new MyCustomAdapter(stringsList, addInfList, idsList, getActivity(),getActivity());
                    MyCustomAdapter adapter = new MyCustomAdapter(getActivity(),getActivity());
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
                    if(getActivity()!=null){
                        ListView lView = (ListView)getActivity().findViewById(R.id.lstView_estaciones);
                        lView.setAdapter(adapter);
                    }

                }

            }
        });

    }
}
