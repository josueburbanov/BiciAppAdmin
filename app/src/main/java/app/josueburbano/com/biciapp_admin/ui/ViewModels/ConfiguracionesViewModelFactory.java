package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp_admin.datos.repositorios.ClientesRepo;
import app.josueburbano.com.biciapp_admin.datos.repositorios.ConfiguracionesRepo;

public class ConfiguracionesViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ConfiguracionesViewModel.class)) {
            return (T) new ConfiguracionesViewModel(new ConfiguracionesRepo());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
