package com.example.appinmobiliaria.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.EditarDisponible;
import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;
    private MutableLiveData<String> mExitoCambioDisponibilidad;
    private MutableLiveData<String> mErrorActualizacion;
    private MutableLiveData<Boolean> mEstadoDisponible;
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble() {
        if (mInmueble == null) mInmueble = new MutableLiveData<>();
        return mInmueble;
    }

    public LiveData<String> getMEXitoCambioDisponibilidad() {
        if (mExitoCambioDisponibilidad == null) mExitoCambioDisponibilidad = new MutableLiveData<>();
        return mExitoCambioDisponibilidad;
    }

    public LiveData<String> getMErroActualizacion() {
        if (mErrorActualizacion == null) mErrorActualizacion = new MutableLiveData<>();
        return mErrorActualizacion;
    }

    public LiveData<Boolean> getMEstadoDisponible() {
        if (mEstadoDisponible == null) mEstadoDisponible = new MutableLiveData<>();
        return mEstadoDisponible;
        }

    public void recuperarInmueble(Bundle bundle) {
        if (bundle != null) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            if (inmueble != null) mInmueble.setValue(inmueble);
        }
    }

    public void cambiarDisponibilidad(boolean estado) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        EditarDisponible editarDisponible = new EditarDisponible(mInmueble.getValue().getId(), estado);

        Call<Void> callCambiarDisponible = api.cambiarDisponible(token, editarDisponible);

        callCambiarDisponible.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mExitoCambioDisponibilidad.postValue("Disponibilidad Actualizada");
                    //Toast.makeText(getApplication(), "Disponibilidad Actualizada", Toast.LENGTH_SHORT).show();
                } else {
                    mErrorActualizacion.postValue(ApiClient.obtenerMensajeError(response.errorBody()));
                    mEstadoDisponible.postValue(!estado);
                    //Toast.makeText(getApplication(), "No se pudo actualizar la disponibilidad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mErrorActualizacion.postValue("Error al actualizar la disponibilidad");
                //mErrorActualizacion.postValue(t.getMessage());
                Log.d("asd", t.getLocalizedMessage());
                mEstadoDisponible.postValue(!estado);
                //Toast.makeText(getApplication(), "Error al actualizar la disponibilidad", Toast.LENGTH_SHORT).show();
            }
        });

    }

}