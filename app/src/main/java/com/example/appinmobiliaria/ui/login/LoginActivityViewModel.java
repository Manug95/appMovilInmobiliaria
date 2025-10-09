package com.example.appinmobiliaria.ui.login;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mLoginCorrecto = new MutableLiveData<>();
    private final MutableLiveData<String> mLoginIncorrecto = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mEstaLogueado = new MutableLiveData<>();

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMLoginCorrecto() {
        return mLoginCorrecto;
    }

    public LiveData<String> getMLoginIncorrecto() {
        return mLoginIncorrecto;
    }

    public LiveData<Boolean> getmEstaLogueado() {
        return mEstaLogueado;
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            mLoginIncorrecto.setValue("Email o contraseña vacío(s)");
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<String> apiCall = api.login(email, password);

        apiCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    ApiClient.guardarToken(getApplication(), response.body());
                    mLoginCorrecto.setValue("¡Bienvenido!");
                } else {
                    mLoginIncorrecto.setValue("Email o contraseña incorrecto(s)");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplication(), "Error al hacer la petición", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void comprobarSesion() {
        String token = ApiClient.leerToken(getApplication());
        if (token != null) {
            mEstaLogueado.setValue(true);
        }
    }
}
