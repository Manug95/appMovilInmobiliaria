package com.example.appinmobiliaria.ui.contrato;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Contrato;
import com.example.appinmobiliaria.modelos.Pago;
import com.example.appinmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> mPagos;
    private MutableLiveData<String> mErrorPagos;

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public void recuperarContrato(Bundle bundle) {

    }

    public void traerPagos(Bundle bundle) {
        if (bundle == null) {
            mErrorPagos.setValue("No se puede recueperar los pagos del contrato");
        }

        Contrato contrato = (Contrato) bundle.getSerializable("contrato");

        if (contrato == null) {
            mErrorPagos.setValue("No se puede recueperar los pagos del contrato");
        }

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<List<Pago>> callGetPagos = api.getPagos(token, contrato.getId());

        callGetPagos.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    List<Pago> pagos = response.body();
                    if (pagos != null && pagos.isEmpty())
                        mErrorPagos.postValue("El contrato no tiene pagos");
                    else
                        mPagos.postValue(pagos);
                }
                else {
                    String mensaje = ApiClient.obtenerMensajeError(response.errorBody());
                    //Toast.makeText(getApplication(), mensaje, Toast.LENGTH_LONG).show();
                    mErrorPagos.postValue(mensaje);
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                //Toast.makeText(getApplication(), "Error al cargar los contratos", Toast.LENGTH_LONG).show();
                mErrorPagos.postValue("Error al cargar los pagos");
            }
        });
    }

    public LiveData<List<Pago>> getMPagos() {
        if (mPagos == null) {
            mPagos = new MutableLiveData<>();
        }
        return mPagos;
    }

    public LiveData<String> getMErrorPagos() {
        if (mErrorPagos == null) {
            mErrorPagos = new MutableLiveData<>();
        }
        return mErrorPagos;
    }
}