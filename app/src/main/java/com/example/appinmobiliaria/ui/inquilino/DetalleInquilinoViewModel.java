package com.example.appinmobiliaria.ui.inquilino;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.modelos.Inquilino;

public class DetalleInquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> mInquilino;

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getMInquilino() {
        if (mInquilino == null) mInquilino = new MutableLiveData<>();
        return mInquilino;
    }

    public void recuperarInquilino(Bundle bundle) {
        if (bundle != null) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            if (inmueble != null) {
                //recuperar el inquilino de la BD
            }
        }
    }
}