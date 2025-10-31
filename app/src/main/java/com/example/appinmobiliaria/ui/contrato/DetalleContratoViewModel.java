package com.example.appinmobiliaria.ui.contrato;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Contrato;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> mContrato;
    private MutableLiveData<String> mErrorContrato;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public void recibirContrato(Bundle bundle) {
        if (bundle != null) {
            Contrato contrato = (Contrato) bundle.getSerializable("contrato");
            mContrato.postValue(contrato);
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