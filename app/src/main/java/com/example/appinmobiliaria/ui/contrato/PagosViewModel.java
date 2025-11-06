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
    private MutableLiveData<List<Pago>> mScrollInfinito;
    private MutableLiveData<String> mErrorPagos;
    private MutableLiveData<Void> mFiltroScrollInfinito;
    private MutableLiveData<Void> mCondicionScrollInfinito;

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

    public void traerMasPagos(int pagina) {
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
                    /*if (pagos != null && pagos.isEmpty() && offset == 1)
                        mErrorPagos.postValue("El contrato no tiene pagos");
                    else {
                        if (offset > 1) {
                            mNuevosPagos.postValue(pagos);
                        } else {
                            mPagos.postValue(pagos);
                        }
                    }*/

                    if (pagos != null && pagos.isEmpty()) {
                        if (offset == 1)
                            mErrorPagos.postValue("El contrato no tiene pagos");
                    } else {
                        if (offset == 1)
                            mPagos.postValue(pagos);
                        else
                            mScrollInfinito.postValue(pagos);
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

    public void filtroScrollInfinito(int dy, boolean estaCargandoPagos, boolean noHayMasPagos) {
        if (dy <= 0) return;
        if (estaCargandoPagos || noHayMasPagos) return;
        mFiltroScrollInfinito.postValue(null);
    }

    public void condicionScrollInfinito(int cantidadItemsVisibles, int totalItems, int posicionPrimerItemVisible) {
        if ((cantidadItemsVisibles + posicionPrimerItemVisible) >= totalItems && posicionPrimerItemVisible >= 0) {
            mCondicionScrollInfinito.setValue(null);
        }
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

    public LiveData<List<Pago>> getMScrollInfinito() {
        if (mScrollInfinito == null) {
            mScrollInfinito = new MutableLiveData<>();
        }
        return mScrollInfinito;
    }

    public LiveData<Void> getMFiltroScrollInfinito() {
        if (mFiltroScrollInfinito == null) {
            mFiltroScrollInfinito = new MutableLiveData<>();
        }
        return mFiltroScrollInfinito;
    }

    public LiveData<Void> getMCondicionScrollInfinito() {
        if (mCondicionScrollInfinito == null) {
            mCondicionScrollInfinito = new MutableLiveData<>();
        }
        return mCondicionScrollInfinito;
    }
}