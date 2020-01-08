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

import app.admin.com.biciapp_admin.ui.ViewModels.ClienteViewModelFactory;
import app.admin.com.biciapp_admin.R;
import app.admin.com.biciapp_admin.datos.MyCustomAdapter;
import app.admin.com.biciapp_admin.datos.modelos.Cliente;
import app.admin.com.biciapp_admin.ui.ViewModels.ClienteViewModel;

public class ClientesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clientes, container, false);
    }

    List<Cliente> fetchedClientes;
    public ClienteViewModel viewModel;
    public FloatingActionButton btnNuevoCliente;
    public ProgressBar prgBarLoading;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prgBarLoading = getActivity().findViewById(R.id.progressBar);
        prgBarLoading.setVisibility(View.VISIBLE);
        viewModel = ViewModelProviders.of(getActivity(), new ClienteViewModelFactory())
                .get(ClienteViewModel.class);
        viewModel.ObtenerClientes();
        viewModel.ObservarClientes().observe(getActivity(), new Observer<List<Cliente>>() {
            @Override
            public void onChanged(@Nullable List<Cliente> clientes) {
                if (clientes != null) {
                    if(clientes.size()==0){
                        Toast.makeText(getContext(),
                                "Sin clientes", Toast.LENGTH_SHORT);
                    }
                    prgBarLoading.setVisibility(View.INVISIBLE);
                    fetchedClientes = clientes;

                    //instantiate custom adapter
                    //MyCustomAdapter adapter = new MyCustomAdapter(stringsList, addInfList, idsList, getActivity(),getActivity());
                    MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), getActivity());
                    adapter.setList(fetchedClientes);
                    adapter.setTypeKey(Cliente.class);
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
        btnNuevoCliente = (FloatingActionButton) getActivity().findViewById(R.id.btn_nuevo_cliente);
        btnNuevoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogCliente cdd=new CustomDialogCliente(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });


    }
}
