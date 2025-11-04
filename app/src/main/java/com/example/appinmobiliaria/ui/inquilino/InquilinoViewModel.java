package com.example.appinmobiliaria.ui.inquilino;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Contrato;
import com.example.appinmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Contrato>> mContratos;
    private MutableLiveData<String> mErrorContratos;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        getInmueblesAlquilados();
    }

    public LiveData<List<Contrato>> getMContratos() {
        if (mContratos == null) mContratos = new MutableLiveData<>();
        return mContratos;
    }

    public LiveData<String> getMErrorContratos() {
        if (mErrorContratos == null) mErrorContratos = new MutableLiveData<>();
        return mErrorContratos;
    }

    public void getInmueblesAlquilados() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<List<Contrato>> callGetInmueblesAlquilados = api.getContratosVigentes(token);

        callGetInmueblesAlquilados.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful()) {
                    List<Contrato> contratos = response.body();
                    if (contratos != null && contratos.isEmpty())
                        mErrorContratos.postValue("No tiene inmuebles alquilados actualmente");
                    else
                        mContratos.postValue(contratos);
                }
                else {
                    String mensaje = ApiClient.obtenerMensajeError(response.errorBody());
                    mErrorContratos.postValue(mensaje);
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                mErrorContratos.postValue("Error al cargar los inmuebles");
            }
        });
    }
}