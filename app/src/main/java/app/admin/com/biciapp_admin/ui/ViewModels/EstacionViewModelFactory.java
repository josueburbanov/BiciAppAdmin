package app.admin.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.admin.com.biciapp_admin.datos.repositorios.EstacionesRepo;

public class EstacionViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstacionViewModel.class)) {
            return (T) new EstacionViewModel(new EstacionesRepo());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

