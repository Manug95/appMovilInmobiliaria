package com.example.appinmobiliaria.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.CambiarContrasenia;
import com.example.appinmobiliaria.request.ApiClient;
import com.example.appinmobiliaria.util.ResultadoValidacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarContraseniaViewModel extends AndroidViewModel {
    private MutableLiveData<String> mErrorContraseniaActual = new MutableLiveData<>();
    private MutableLiveData<String> mErrorNuevaContrasenia = new MutableLiveData<>();
    private MutableLiveData<String> mErrorRepetirContrasenia = new MutableLiveData<>();
    private MutableLiveData<String> mErrorCambiarContrasenia = new MutableLiveData<>();
    private MutableLiveData<String> mContraseniaCambiada = new MutableLiveData<>();

    public CambiarContraseniaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMErrorContraseniaActual() {
        return mErrorContraseniaActual;
    }

    public LiveData<String> getMErrorNuevaContrasenia() {
        return mErrorNuevaContrasenia;
    }

    public LiveData<String> getMErrorRepetirContrasenia() {
        return mErrorRepetirContrasenia;
    }

    public LiveData<String> getMErrorCambiarContrasenia() {
        return mErrorCambiarContrasenia;
    }

    public LiveData<String> getMContraseniaCambiada() {
        return mContraseniaCambiada;
    }

    public void cambiarContrasenia(String contraseniaActual, String nuevaContrasenia, String repetirContrasenia) {
        if (!contraseniasValidas(contraseniaActual, nuevaContrasenia, repetirContrasenia))
            return;

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<String> callCambiarContrasenia = api.cambiarContrasenia(token, contraseniaActual, nuevaContrasenia);

        callCambiarContrasenia.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    mContraseniaCambiada.postValue("Contraseña Cambiada");
                } else {
                    mErrorCambiarContrasenia.postValue("No se pudo Cambiar la contraseña");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mErrorCambiarContrasenia.postValue("Error al cambiar la contraseña");
            }
        });

    }

    private boolean contraseniasValidas(String contraseniaActual, String nuevaContrasenia, String repetirContrasenia) {
        ResultadoValidacion resultadoValidacion = CambiarContrasenia.validarClaveActual(contraseniaActual);
        boolean esValida = resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) {
            mErrorContraseniaActual.setValue(resultadoValidacion.getMensajeError());
        }

        resultadoValidacion = CambiarContrasenia.validarClaveNueva(nuevaContrasenia);
        esValida = esValida && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) {
            mErrorNuevaContrasenia.setValue(resultadoValidacion.getMensajeError());
        }

        if (!nuevaContrasenia.equals(repetirContrasenia)) {
            mErrorRepetirContrasenia.setValue("Las contraseñas no coinciden");
            mErrorNuevaContrasenia.setValue("Las contraseñas no coinciden");
            esValida = false;
        }

        return esValida;
    }
}