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

import app.admin.com.biciapp_admin.ui.ViewModels.ReservaViewModel;
import app.admin.com.biciapp_admin.ui.ViewModels.ReservaViewModelFactory;
import app.josueburbano.com.biciapp_admin.R;
import app.admin.com.biciapp_admin.datos.MyCustomAdapter;
import app.admin.com.biciapp_admin.datos.modelos.Reserva;

public class ReservasFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reservas, container, false);
    }
    List<Reserva> fetchedReservas;
    public ReservaViewModel viewModel;
    public FloatingActionButton btnNuevaReserva;
    public ProgressBar prgBarLoading;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prgBarLoading = getActivity().findViewById(R.id.progressBarReservas);
        prgBarLoading.setVisibility(View.VISIBLE);
        viewModel = ViewModelProviders.of(getActivity(), new ReservaViewModelFactory())
                .get(ReservaViewModel.class);
        viewModel.ObtenerReservas();
        viewModel.ObservarReservas().observe(getActivity(), new Observer<List<Reserva>>() {
            @Override
            public void onChanged(@Nullable List<Reserva> reservas) {
                if (reservas != null) {
                    if(reservas.size()==0){
                        Toast.makeText(getActivity().getApplication(),
                                "Sin Reservas", Toast.LENGTH_SHORT);
                    }
                    prgBarLoading.setVisibility(View.INVISIBLE);
                    fetchedReservas = reservas;

                    //instantiate custom adapter
                    //MyCustomAdapter adapter = new MyCustomAdapter(stringsList, addInfList, idsList, getActivity(),getActivity());
                    MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), getActivity());
                    adapter.setList(fetchedReservas);
                    adapter.setTypeKey(Reserva.class);
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
        btnNuevaReserva = (FloatingActionButton) getActivity().findViewById(R.id.btn_nueva_reserva);
        btnNuevaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogReserva cdd=new CustomDialogReserva(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });


    }
}


