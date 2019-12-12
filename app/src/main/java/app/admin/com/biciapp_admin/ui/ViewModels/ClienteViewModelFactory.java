package app.admin.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.admin.com.biciapp_admin.datos.repositorios.ClientesRepo;

public class ClienteViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClienteViewModel.class)) {
            return (T) new ClienteViewModel(new ClientesRepo());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

