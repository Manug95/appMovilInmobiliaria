package com.example.appinmobiliaria.ui.contrato;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Contrato;

import java.io.Serializable;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> mContrato;
    private MutableLiveData<String> mErrorContrato;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public void recibirContrato(Bundle bundle) {
        if (bundle != null) {
            Serializable serializable = bundle.getSerializable("contrato");
            if (serializable != null) {
                Contrato contrato = (Contrato) serializable;
                mContrato.postValue(contrato);
            } else {
                mErrorContrato.postValue("No se pudo recuperar el contrato");
            }
        } else {
            mErrorContrato.postValue("No se pudo recuperar el contrato");
        }
    }

    public LiveData<Contrato> getMContrato() {
        if (mContrato == null) {
            mContrato = new MutableLiveData<>();
        }
        return mContrato;
    }

    public LiveData<String> getMErrorContrato() {
        if (mErrorContrato == null) {
            mErrorContrato = new MutableLiveData<>();
        }
        return mErrorContrato;
    }
}