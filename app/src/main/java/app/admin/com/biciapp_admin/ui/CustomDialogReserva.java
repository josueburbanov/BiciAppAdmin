package app.admin.com.biciapp_admin.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kusu.library.LoadingButton;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.IntStream;

import app.admin.com.biciapp_admin.ui.ViewModels.BicicletaViewModel;
import app.admin.com.biciapp_admin.ui.ViewModels.BicicletaViewModelFactory;
import app.admin.com.biciapp_admin.ui.ViewModels.ClienteViewModelFactory;
import app.admin.com.biciapp_admin.ui.ViewModels.EstacionViewModel;
import app.admin.com.biciapp_admin.ui.ViewModels.EstacionViewModelFactory;
import app.admin.com.biciapp_admin.ui.ViewModels.ReservaViewModel;
import app.admin.com.biciapp_admin.ui.ViewModels.ReservaViewModelFactory;
import app.josueburbano.com.biciapp_admin.R;
import app.admin.com.biciapp_admin.datos.modelos.Bicicleta;
import app.admin.com.biciapp_admin.datos.modelos.Cliente;
import app.admin.com.biciapp_admin.datos.modelos.Estacion;
import app.admin.com.biciapp_admin.datos.modelos.Reserva;
import app.admin.com.biciapp_admin.ui.ViewModels.ClienteViewModel;

public class CustomDialogReserva extends Dialog implements
        android.view.View.OnClickListener {

    public FragmentActivity c;
    public Dialog d;
    public LoadingButton aceptar;
    public Spinner spinnerEstacion, spinnerCliente, spinnerBicicleta;
    public EditText editTextFecha,editTextHoraInicio, editTextHoraFin;
    public Reserva reserva;
    private Reserva reservaPost;

    //Variables para trabajar con fecha / texto
    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";
    public final Calendar calendar = Calendar.getInstance();
    //Variables para obtener la fecha
    int mes = calendar.get(Calendar.MONTH);
    int dia = calendar.get(Calendar.DAY_OF_MONTH);
    int anio = calendar.get(Calendar.YEAR);
    int horaInicio = calendar.get(Calendar.HOUR_OF_DAY);
    int minutoInicio = calendar.get(Calendar.MINUTE) + 2;
    int horaFin = calendar.get(Calendar.HOUR_OF_DAY) + 1;
    int minutoFin = calendar.get(Calendar.MINUTE) + 2;

    List<Cliente> clientesSpinner;
    List<Estacion> estacionesSpinner;
    List<Bicicleta> bicicletasSpinner;

    public CustomDialogReserva(FragmentActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_nueva_reserva);
        aceptar = findViewById(R.id.btnAceptar);
        aceptar.setOnClickListener(this);
        spinnerCliente= findViewById(R.id.spinnerClientes);
        spinnerEstacion= findViewById(R.id.spinnerEstaciones);
        spinnerBicicleta= findViewById(R.id.spinnerCandado);
        editTextFecha= findViewById(R.id.editTextFecha);
        editTextHoraInicio= findViewById(R.id.editTextHoraInicio);
        editTextHoraFin= findViewById(R.id.editTextHoraFin);

        TextView textViewTitulo = findViewById(R.id.textViewTitulo);



        CargarSpinners();
        if (reserva != null) {
            textViewTitulo.setText("Editando (" + reserva.getFecha() + " - " + reserva.getHoraInicio() + ")");
            ClienteViewModel viewModel = ViewModelProviders.of(c, new ClienteViewModelFactory())
                    .get(ClienteViewModel.class);
            viewModel.ObtenerCliente(reserva.getIdCliente());
            viewModel.ObservarClienteById().observe(c, new Observer<Cliente>() {
                @Override
                public void onChanged(@Nullable Cliente cliente) {
                    if (cliente != null) {
                        int indiceSeleccionado = -1;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            indiceSeleccionado = IntStream.range(0, clientesSpinner.size())
                                    .filter(i -> cliente.getId() == clientesSpinner.get(i).getId())
                                    .findFirst() // first occurence
                                    .orElse(-1); // No element found
                        }
                        spinnerEstacion.setSelection(indiceSeleccionado,true);
                    }
                }
            });
            EstacionViewModel viewModelE = ViewModelProviders.of(c, new EstacionViewModelFactory())
                    .get(EstacionViewModel.class);
            viewModelE.ObtenerEstacionByBici(reserva.getIdBici());
            viewModelE.ObservarEstacionByBici().observe(c, new Observer<Estacion>() {
                @Override
                public void onChanged(@Nullable Estacion estacion) {
                    if (estacion != null) {
                        int indiceSeleccionado = -1;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            indiceSeleccionado = IntStream.range(0, estacionesSpinner.size())
                                    .filter(i -> estacion.getId() == estacionesSpinner.get(i).getId())
                                    .findFirst() // first occurence
                                    .orElse(-1); // No element found
                        }
                        spinnerEstacion.setSelection(indiceSeleccionado,true);
                    }
                }
            });
            int indiceSeleccionado = -1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                indiceSeleccionado = IntStream.range(0, estacionesSpinner.size())
                        .filter(i -> reserva.getIdBici() == bicicletasSpinner.get(i).getId())
                        .findFirst() // first occurence
                        .orElse(-1); // No element found
            }
            spinnerEstacion.setSelection(indiceSeleccionado,true);

            editTextFecha.setText(reserva.getFecha());
            editTextHoraInicio.setText(reserva.getHoraInicio());
            editTextHoraFin.setText(reserva.getHoraFin());
        }

        //*****************************************
        //Inicializando dialog para fechas
        //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
        final int mesActual = mes + 1;
        //Formateo el día obtenido: antepone el 0 si son menores de 10
        String diaFormateado = (dia < 10) ? CERO + String.valueOf(dia) : String.valueOf(dia);
        //Formateo el mes obtenido: antepone el 0 si son menores de 10
        String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
        //Muestro la fecha con el formato deseado
        editTextFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + anio);

        //Formateo el hora obtenido: antepone el 0 si son menores de 10
        String horaFormateada = (horaInicio < 10) ? String.valueOf(CERO + horaInicio) : String.valueOf(horaInicio);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minutoInicio < 10) ? String.valueOf(CERO + minutoInicio) : String.valueOf(minutoInicio);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM;
        if (horaInicio < 12) {
            AM_PM = "a.m.";
        } else {
            AM_PM = "p.m.";
        }
        //Muestro la hora con el formato deseado
        editTextHoraInicio.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);

        //Formateo el hora obtenido: antepone el 0 si son menores de 10
        String horaFormateadaFin = (horaFin < 10) ? String.valueOf(CERO + horaFin) : String.valueOf(horaFin);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateadoFin = (minutoFin < 10) ? String.valueOf(CERO + minutoFin) : String.valueOf(minutoFin);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM_Fin;
        if (horaFin < 12) {
            AM_PM_Fin = "a.m.";
        } else {
            AM_PM_Fin = "p.m.";
        }
        //Muestro la hora con el formato deseado
        editTextHoraFin.setText(horaFormateadaFin + DOS_PUNTOS + minutoFormateadoFin + " " + AM_PM_Fin);


        editTextFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    selectDate(view);
                } else {

                }
            }
        });

        editTextHoraInicio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectStartTime(v);
                } else {

                }
            }
        });

        editTextHoraFin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectEndTime(v);
                } else {

                }
            }
        });
    }

    //Método controlador para la acción del Button para desplegar el dialogDate
    public void selectDate(View view) {
        DatePickerDialog recogerFecha = new DatePickerDialog(c, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                editTextFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                anio = year;
                mes = month;
                dia = dayOfMonth;
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

    public void selectStartTime(View view) {
        TimePickerDialog recogerHora = new TimePickerDialog(c, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                editTextHoraInicio.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                horaInicio = hourOfDay;
                minutoInicio = minute;
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, horaInicio, minutoInicio, false);

        recogerHora.show();
    }

    public void selectEndTime(View view) {
        TimePickerDialog recogerHora = new TimePickerDialog(c, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                editTextHoraFin.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                horaFin = hourOfDay;
                minutoFin = minute;
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, horaFin, minutoFin, false);

        recogerHora.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAceptar:
                aceptar.showLoading();
                v.setEnabled(false);
                if (reserva != null) {
                    EditarReserva();
                } else {
                    CrearReserva();
                }

                break;
            default:
                break;
        }

    }

    private void CrearReserva() {

        ReservaViewModel viewModel = ViewModelProviders.of(c, new ReservaViewModelFactory())
                .get(ReservaViewModel.class);
        Cliente clienteReserva = (Cliente)spinnerCliente.getSelectedItem();
        Bicicleta bicicletaReserva = (Bicicleta)spinnerBicicleta.getSelectedItem();
        reservaPost = gatherFieldsReserva(clienteReserva.getId(),bicicletaReserva.getId());
        if (reservaPost == null) {
            Toast.makeText(c, "Por favor revise que la fecha y/o horas " +
                    "correspondan", Toast.LENGTH_LONG).show();
        } else {

            new AlertDialog.Builder(c)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Confirmación")
                    .setMessage("Fecha: " + reservaPost.getFecha() + "\n Hora inicial: " + reservaPost.getHoraInicio()
                            + "\nHora entrega: " + reservaPost.getHoraFin() + "\nBiclicleta: " + reservaPost.getIdBici())
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewModel.obtenerReservaActiva(reservaPost.getIdCliente());
                            viewModel.ObservarReservaActiva().observe(c, new Observer<Reserva>() {
                                @Override
                                public void onChanged(@Nullable Reserva reserva) {
                                    if (reserva == null || !reserva.isActiva()) {
                                        viewModel.CrearReserva(reservaPost);
                                        viewModel.ObservarReservaActiva().removeObserver(this);
                                    } else {
                                        Toast.makeText(c, "No se ha podido reservar debido a " +
                                                "que ya tiene una reserva activa. El " +
                                                reserva.getFecha() + " a las " + reserva.getHoraInicio() + " " +
                                                "hasta las " + reserva.getHoraFin(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }



        viewModel.ObservarReservaCreada().observe(c, new Observer<Reserva>() {
            @Override
            public void onChanged(@Nullable Reserva reserva) {
                if (reserva == null) {

                } else if (reservaPost.getIdBici().equals(reserva.getIdBici()) &&
                        reservaPost.getIdCliente().equals(reserva.getIdCliente()) &&
                        reservaPost.getFecha().equals(reserva.getFecha()) &&
                        reservaPost.getHoraInicio().equals(reserva.getHoraInicio()) &&
                        reservaPost.getHoraFin().equals(reserva.getHoraFin())){
                    Toast.makeText(c, "Reserva realizada", Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ReservasFragment()).commit();
                }
            }
        });
    }

    private void CargarSpinners() {
        EstacionViewModel viewModelE = ViewModelProviders.of(c, new EstacionViewModelFactory())
                .get(EstacionViewModel.class);
        viewModelE.ObtenerEstaciones();
        viewModelE.ObservarEstaciones().observe(c, new Observer<List<Estacion>>() {
            @Override
            public void onChanged(@Nullable List<Estacion> estaciones) {
                estacionesSpinner = estaciones;
                ArrayAdapter<Estacion> adapterEstaciones = new ArrayAdapter<Estacion>(c, android.R.layout.simple_spinner_dropdown_item, estaciones);
                spinnerEstacion.setAdapter(adapterEstaciones);
            }
        });
        spinnerEstacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BicicletaViewModel viewModelR = ViewModelProviders.of(c, new BicicletaViewModelFactory())
                        .get(BicicletaViewModel.class);
                Estacion estacion = (Estacion)spinnerEstacion.getSelectedItem();
                viewModelR.ObtenerBicisByEstacion(estacion.getId());
                viewModelR.ObservarBicicletasByEstacion().observe(c, new Observer<List<Bicicleta>>() {
                    @Override
                    public void onChanged(@Nullable List<Bicicleta> bicicletas) {
                        bicicletasSpinner = bicicletas;
                        ArrayAdapter<Bicicleta> adapterBicicletas = new ArrayAdapter<Bicicleta>(c, android.R.layout.simple_spinner_dropdown_item, bicicletas);
                        spinnerBicicleta.setAdapter(adapterBicicletas);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ClienteViewModel viewModelC = ViewModelProviders.of(c, new ClienteViewModelFactory())
                .get(ClienteViewModel.class);
        viewModelC.ObtenerClientes();
        viewModelC.ObservarClientes().observe(c, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(@Nullable List<Cliente> clientes) {
                clientesSpinner = clientes;
                ArrayAdapter<Cliente> adapterClientes = new ArrayAdapter<Cliente>(c, android.R.layout.simple_spinner_dropdown_item, clientes);
                spinnerCliente.setAdapter(adapterClientes);
            }
        });
    }

    private Reserva gatherFieldsReserva(String idCliente, String idBici) {
        Reserva reserva = new Reserva();
        reserva.setIdCliente(idCliente);
        reserva.setIdBici(idBici);
        Date date = new GregorianCalendar(anio, mes, dia).getTime();
        if (System.currentTimeMillis() < date.getTime()) {
            return null;
        }
        reserva.setFecha(DateFormat.format("yyyy/MM/dd", date).toString());
        Date horaInicioRes = new GregorianCalendar(anio, mes, dia, horaInicio, minutoInicio).getTime();
        if (new Date().after(horaInicioRes)) {
            return null;
        }
        reserva.setHoraInicio(DateFormat.format("HH:mm", horaInicioRes).toString());
        Date horaFinRes = new GregorianCalendar(anio, mes, dia, horaFin, minutoFin).getTime();
        if (new Date().after(horaFinRes) || new Date().after(horaInicioRes)) {
            return null;
        }
        reserva.setHoraFin(DateFormat.format("HH:mm", horaFinRes).toString());
        reserva.setActiva(true);
        reserva.setConcretada(false);
        return reserva;
    }

    private void EditarReserva() {
        ReservaViewModel viewModel = ViewModelProviders.of(c, new ReservaViewModelFactory())
                .get(ReservaViewModel.class);
        Cliente clienteReserva = (Cliente)spinnerCliente.getSelectedItem();
        Bicicleta bicicletaReserva = (Bicicleta) spinnerBicicleta.getSelectedItem();
        reservaPost = gatherFieldsReserva(clienteReserva.getId(),bicicletaReserva.getId());
        if (reservaPost == null) {
            Toast.makeText(c, "Por favor revise que la fecha y/o horas " +
                    "correspondan", Toast.LENGTH_LONG).show();
        } else {

            new AlertDialog.Builder(c)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Confirmación")
                    .setMessage("Fecha: " + reservaPost.getFecha() + "\n Hora inicial: " + reservaPost.getHoraInicio()
                            + "\nHora entrega: " + reservaPost.getHoraFin() + "\nBiclicleta: " + reservaPost.getIdBici())
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewModel.obtenerReservaActiva(reservaPost.getIdCliente());
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        viewModel.ObservarReservaActiva().observe(c, new Observer<Reserva>() {
            @Override
            public void onChanged(@Nullable Reserva reserva) {
                if (reserva == null || !reserva.isActiva()) {
                    viewModel.EditarReserva(reservaPost);
                    viewModel.ObservarReservaActiva().removeObserver(this);
                } else {
                    Toast.makeText(c, "No se ha podido reservar debido a " +
                            "que ya tiene una reserva activa. El " +
                            reserva.getFecha() + " a las " + reserva.getHoraInicio() + " " +
                            "hasta las " + reserva.getHoraFin(), Toast.LENGTH_LONG).show();
                }
            }
        });
        viewModel.ObservarConfirmacionEdicion().observe(c, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean confirmacion) {
                if(confirmacion){
                    Toast.makeText(c.getApplicationContext(), "Reserva Actualizada " + reservaPost.toString(), Toast.LENGTH_LONG).show();
                    dismiss();
                    c.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ReservasFragment()).commit();
                }
            }
        });
    }
}