package com.example.appinmobiliaria.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;
    private MutableLiveData<Boolean> mErrorActualizacion;
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble() {
        if (mInmueble == null) mInmueble = new MutableLiveData<>();
        return mInmueble;
    }

    public LiveData<Boolean> getMErroActualizacion() {
        if (mErrorActualizacion == null) mErrorActualizacion = new MutableLiveData<>();
        return mErrorActualizacion;
    }

    public void recuperarInmueble(Bundle bundle) {
        if (bundle != null) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            if (inmueble != null) mInmueble.setValue(inmueble);
        }
    }

    public void cambiarDisponibilidad(boolean estado) {
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(estado);
        inmueble.setId(mInmueble.getValue().getId());

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<Inmueble> callActualizarInmueble = api.actualizarInmueble(token, inmueble);

        callActualizarInmueble.enqueue(new Callback<Inmueble>() {

            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Disponibilidad Actualizada", Toast.LENGTH_SHORT).show();
                } else {
                    mErrorActualizacion.postValue(!estado);
                    Toast.makeText(getApplication(), "No se pudo actualizar la disponibilidad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mErrorActualizacion.postValue(!estado);
                Toast.makeText(getApplication(), "Error al actualizar la disponibilidad", Toast.LENGTH_SHORT).show();
            }
        });

    }

}