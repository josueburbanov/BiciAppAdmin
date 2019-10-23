package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp_admin.datos.repositorios.BicicletasRepo;

public class BicicletaViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel > T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BicicletaViewModel.class)) {
            return (T) new BicicletaViewModel(new BicicletasRepo());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
