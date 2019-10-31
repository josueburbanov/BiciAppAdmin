package app.josueburbano.com.biciapp_admin.datos;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.modelos.Bicicleta;
import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;
import app.josueburbano.com.biciapp_admin.datos.modelos.Estacion;
import app.josueburbano.com.biciapp_admin.datos.modelos.Reserva;
import app.josueburbano.com.biciapp_admin.ui.CustomDialogBicicleta;
import app.josueburbano.com.biciapp_admin.ui.CustomDialogCliente;
import app.josueburbano.com.biciapp_admin.ui.CustomDialogEstacion;
import app.josueburbano.com.biciapp_admin.ui.CustomDialogReserva;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.BicicletaViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.BicicletaViewModelFactory;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ClienteViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ClienteViewModelFactory;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.EstacionViewModelFactory;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ReservaViewModel;
import app.josueburbano.com.biciapp_admin.ui.ViewModels.ReservaViewModelFactory;

public class MyCustomAdapter<T> extends BaseAdapter implements ListAdapter {
    private List<T> list;
    private Context context;
    private FragmentActivity activity;
    private Class<T> typeKey;
    List<String> list1;
    List<String> list2;
    List<String> list3;
    List<String> list4;

    public void setList(List<T> t){
        this.list = t;
    }
    public void setTypeKey(Class<T> typeKey){
        this.typeKey = typeKey;
    }
    public void prepareLists() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        list1 = new ArrayList<String>(list.size());
        list2 = new ArrayList<String>(list.size());
        list3 = new ArrayList<String>(list.size());
        list4 = new ArrayList<String>(list.size());

        if(typeKey == Estacion.class){
            for (T item : list) {
                list1.add(item.toString());
                Method addInfo1 = Estacion.class.getMethod("addInfo1");
                Method addInfo2 = Estacion.class.getMethod("addInfo2");
                Method getId = Estacion.class.getMethod("getId");
                list2.add((String) addInfo1.invoke(item));
                list3.add((String) getId.invoke(item));
                list4.add((String) addInfo2.invoke(item));
            }
        }

        else if(typeKey == Cliente.class){
            for (T item : list) {
                list1.add(item.toString());
                Method addInfo1 = Cliente.class.getMethod("addInfo1");
                Method addInfo2 = Cliente.class.getMethod("addInfo2");
                Method getId = Cliente.class.getMethod("getId");
                list2.add((String) addInfo1.invoke(item));
                list3.add((String) getId.invoke(item));
                list4.add((String) addInfo2.invoke(item));
            }
        }

        else if(typeKey == Bicicleta.class){
            for (T item : list) {
                list1.add(item.toString());
                Method addInfo1 = Bicicleta.class.getMethod("addInfo1");
                Method addInfo2 = Bicicleta.class.getMethod("addInfo2");
                Method getId = Bicicleta.class.getMethod("getId");
                list2.add((String) addInfo1.invoke(item));
                list3.add((String) getId.invoke(item));
                list4.add((String) addInfo2.invoke(item));
            }
        }

        else if(typeKey == Reserva.class){
            for (T item : list) {
                list1.add(item.toString());
                Method addInfo1 = Reserva.class.getMethod("addInfo1");
                Method addInfo2 = Reserva.class.getMethod("addInfo2");
                Method getId = Reserva.class.getMethod("getId");
                list2.add((String) addInfo1.invoke(item));
                list3.add((String) getId.invoke(item));
                list4.add((String) addInfo2.invoke(item));
            }
        }
    }

    public MyCustomAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cstm_lstview_crud, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(String.valueOf(list.get(position)));

        TextView listItemText2 = (TextView)view.findViewById(R.id.list_item_string2);
        listItemText2.setText(String.valueOf(list2.get(position)));

        TextView listItemText3 = (TextView)view.findViewById(R.id.list_item_string3);
        listItemText3.setText(String.valueOf(list4.get(position)));

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.delete_btn);
        ImageButton editBtn = (ImageButton)view.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showConfirmationBox("¿Seguro que desea eliminar el item?",context,list3.get(position), position);
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (typeKey.equals(Estacion.class)){
                    CustomDialogEstacion cdd=new CustomDialogEstacion(activity);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.estacion = (Estacion) list.get(position);
                    cdd.show();
                }else if(typeKey.equals(Cliente.class)){
                    CustomDialogCliente cdd=new CustomDialogCliente(activity);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.cliente = (Cliente) list.get(position);
                    cdd.show();
                }else if(typeKey.equals(Bicicleta.class)){
                    CustomDialogBicicleta cdd=new CustomDialogBicicleta(activity);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.bicicleta = (Bicicleta) list.get(position);
                    cdd.show();
                }else if(typeKey.equals(Reserva.class)){
                    Reserva reserva = (Reserva) list.get(position);
                    if(reserva.isActiva()){
                        CustomDialogReserva cdd=new CustomDialogReserva(activity);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.reserva = (Reserva) list.get(position);
                        cdd.show();
                    }else{
                        Toast.makeText(context, "Reserva inactiva no se puede eliminar",
                                Toast.LENGTH_SHORT).show();
                    }

                }

                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void showConfirmationBox(String messageToShow, final Context context, final String item, final int position) {

        // prepare the alert box
        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);

        // set the message to display
        alertbox.setMessage(messageToShow);

        // set a positive/submit button and create a listener
        alertbox.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (typeKey.equals(Estacion.class)){
                            RemoveEstacion(item, position);
                        }else if(typeKey.equals(Cliente.class)){
                            RemoveCliente(item, position);
                        }else if(typeKey.equals(Bicicleta.class)){
                            RemoveBicicleta(item, position);
                        }else if(typeKey.equals(Reserva.class)) {
                            RemoveReserva(item, position);
                        }
                    }
                });

        // set a negative/no button and create a listener
        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {

            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        // display box
        alertbox.show();
    }

    private void RemoveReserva(String idItem, int position) {
        ReservaViewModel viewModel= ViewModelProviders.of(activity, new ReservaViewModelFactory())
                .get(ReservaViewModel.class);
        viewModel.EliminarReserva(idItem);
        viewModel.ObservarConfirmacionEliminacion().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean confirmacion) {
                if (confirmacion != null) {
                    if (confirmacion) {
                        Toast.makeText(context, "Reserva eliminada",
                                Toast.LENGTH_SHORT).show();
                        if (list.size() > position) {
                            list.remove(position);
                            list2.remove(position);
                            list3.remove(position);
                            notifyDataSetChanged();
                        }


                    }
                }
            }});
    }

    private void RemoveEstacion(String idItem, final int position) {
        EstacionViewModel viewModel= ViewModelProviders.of(activity, new EstacionViewModelFactory())
                .get(EstacionViewModel.class);
        viewModel.EliminarEstacion(idItem);
        viewModel.ObservarConfirmacion().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean confirmacion) {
                if(confirmacion != null){
                    if(confirmacion){
                    Toast.makeText(context, "Estación eliminada",
                            Toast.LENGTH_SHORT).show();
                    if(list.size()>position){
                        list.remove(position);
                        list2.remove(position);
                        list3.remove(position);
                        notifyDataSetChanged();
                    }


                }
            }
            }
        });
    }

    private void RemoveCliente(String idItem, final int position) {
        ClienteViewModel viewModel= ViewModelProviders.of(activity, new ClienteViewModelFactory())
                .get(ClienteViewModel.class);
        viewModel.EliminarCliente(idItem);
        viewModel.ObservarConfirmacion().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean confirmacion) {
                if (confirmacion != null) {
                    if (confirmacion) {
                        Toast.makeText(context, "Cliente eliminado",
                                Toast.LENGTH_SHORT).show();
                        if (list.size() > position) {
                            list.remove(position);
                            list2.remove(position);
                            list3.remove(position);
                            notifyDataSetChanged();
                        }


                    }
                }
            }});
        }

    private void RemoveBicicleta(String idItem, final int position) {
        BicicletaViewModel viewModel= ViewModelProviders.of(activity, new BicicletaViewModelFactory())
                .get(BicicletaViewModel.class);
        viewModel.EliminarBicicleta(idItem);
        viewModel.ObservarConfirmacion().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean confirmacion) {
                if (confirmacion != null) {
                    if (confirmacion) {
                        Toast.makeText(context, "Bicicleta eliminada",
                                Toast.LENGTH_SHORT).show();
                        if (list.size() > position) {
                            list.remove(position);
                            list2.remove(position);
                            list3.remove(position);
                            notifyDataSetChanged();
                        }


                    }
                }
            }});
    }
    }
