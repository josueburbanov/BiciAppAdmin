package app.josueburbano.com.biciapp_admin.ui.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.josueburbano.com.biciapp_admin.datos.repositorios.ReservasRepo;

public class ReservaViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReservaViewModel.class)) {
            return (T) new ReservaViewModel(new ReservasRepo());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
