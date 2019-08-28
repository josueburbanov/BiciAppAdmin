package app.josueburbano.com.biciapp_admin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import app.josueburbano.com.biciapp_admin.R;
import app.josueburbano.com.biciapp_admin.datos.modelos.Cliente;

import static app.josueburbano.com.biciapp_admin.ui.LoginActivity.CLIENT_VIEW;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new HomeFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new DashboardFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new NotificationFragment()).commit();
                    return true;

                    case R.id.navigation_estaciones:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new EstacionFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    private DrawerLayout drawer;
    private Cliente clienteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bnavView = findViewById(R.id.bnav_view);
        bnavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        Intent intent = getIntent();
        clienteView = (Cliente) intent.getSerializableExtra(CLIENT_VIEW);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        NavigationView navigationView =(NavigationView) findViewById(R.id.nav_view);
        switch(menuItem.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                navigationView.getMenu().getItem(0).setChecked(true);
                navigationView.getMenu().getItem(1).setChecked(false);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                navigationView.getMenu().getItem(0).setChecked(false);
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NuevaReserva()).commit();
                break;
            }
            case R.id.navigation_dashboard: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NuevaBicicleta()).commit();
                break;
            }
            case R.id.navigation_notifications: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NuevoCliente()).commit();
                break;
            }
            case R.id.navigation_estaciones:{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NuevaEstacion()).commit();
                break;
            }


        }
        return true;
    }
}
