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
    private int idContrato;
    private MutableLiveData<List<Pago>> mPagos;
    private MutableLiveData<List<Pago>> mNuevosPagos;
    private MutableLiveData<String> mErrorPagos;

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public void traerPagos(Bundle bundle) {
        if (bundle == null)
            mErrorPagos.setValue("No se puede recueperar los pagos del contrato");

        Contrato contrato = (Contrato) bundle.getSerializable("contrato");

        if (contrato == null)
            mErrorPagos.setValue("No se puede recueperar los pagos del contrato");

        idContrato = contrato.getId();
        hacerPeticion(idContrato, 1);
    }

    public void traerNuevosPagos(int pagina) {
        hacerPeticion(idContrato, pagina);
    }

    private void hacerPeticion(int id, int offset) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<List<Pago>> callGetPagos = api.getPagos(token, id, offset);

        callGetPagos.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    List<Pago> pagos = response.body();
                    if (pagos != null && pagos.isEmpty() && offset == 1)
                        mErrorPagos.postValue("El contrato no tiene pagos");
                    else {
                        if (offset > 1) {
                            mNuevosPagos.postValue(pagos);
                        } else {
                            mPagos.postValue(pagos);
                        }
                    }
                }
                else {
                    String mensaje = ApiClient.obtenerMensajeError(response.errorBody());
                    mErrorPagos.postValue(mensaje);
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
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

    public LiveData<List<Pago>> getMNuevosPagos() {
        if (mNuevosPagos == null) {
            mNuevosPagos = new MutableLiveData<>();
        }
        return mNuevosPagos;
    }
}