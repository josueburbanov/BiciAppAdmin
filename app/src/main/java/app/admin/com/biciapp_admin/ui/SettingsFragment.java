package app.admin.com.biciapp_admin.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.admin.com.biciapp_admin.ui.ViewModels.ConfiguracionesViewModel;
import app.admin.com.biciapp_admin.ui.ViewModels.ConfiguracionesViewModelFactory;
import app.josueburbano.com.biciapp_admin.R;
import app.admin.com.biciapp_admin.datos.modelos.ConfiguracionAdmin;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final ListView listView = (ListView) getActivity().findViewById(R.id.listViewConfig);
        final ConfiguracionesViewModel viewModel = ViewModelProviders.of(this,
                new ConfiguracionesViewModelFactory()).get(ConfiguracionesViewModel.class);
        viewModel.ObtenerConfiguraciones();
        viewModel.ObservarConfiguraciones().observe(this, new Observer<List<ConfiguracionAdmin>>() {
            @Override
            public void onChanged(@Nullable List<ConfiguracionAdmin> configuracionesAdmin) {
                if(configuracionesAdmin != null){
                    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

                    for (ConfiguracionAdmin item : configuracionesAdmin) {
                        Map<String, String> datum = new HashMap<String, String>(2);
                        datum.put("item", item.getTitulo());
                        datum.put("value", item.getValor());
                        data.add(datum);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), data,
                            android.R.layout.simple_list_item_2,
                            new String[]{"item", "value"},
                            new int[]{android.R.id.text1,
                                    android.R.id.text2});
                    listView.setAdapter(adapter);
                }
                }

        });

        viewModel.ObservarConfigModif().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean respuestaModificacion) {
                if(respuestaModificacion!=null){
                    if(respuestaModificacion){
                        Toast.makeText(getContext(), "Configuración modificada", Toast.LENGTH_SHORT).show();
                        viewModel.ObtenerConfiguraciones();
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItemAtPosition(position);
                final ConfiguracionAdmin item = new ConfiguracionAdmin();
                item.setValor(obj.get("value").toString());
                item.setTitulo(obj.get("item").toString());
                if(item.getTitulo().equals("Duración de la reserva")){
                    AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                    b.setTitle("Escoja el tiempo de duración");
                    final String[] types = {"15m", "30m","45m","1h00m","1h15m","1h30m","1h45m","2h00m","3h00m","4h00m"};
                    b.setItems(types, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            item.setValor(types[which]);
                            viewModel.ModificarConfiguracion(item);
                        }

                    });

                    b.show();
                }
            }
        });
    }
}
