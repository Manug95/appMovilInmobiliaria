package com.example.appinmobiliaria.ui.perfil;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.modelos.Propietario;
import com.example.appinmobiliaria.request.ApiClient;
import com.example.appinmobiliaria.util.ResultadoValidacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private final String EDITAR;
    private final String GUARDAR;
    private final MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mEstadoEditar = new MutableLiveData<>();
    private final MutableLiveData<String> mTextoBoton = new MutableLiveData<>();
    private final MutableLiveData<String> mErrorDni = new MutableLiveData<>();
    private final MutableLiveData<String> mErrorNombre = new MutableLiveData<>();
    private final MutableLiveData<String> mErrorApellido = new MutableLiveData<>();
    private final MutableLiveData<String> mErrorEmail = new MutableLiveData<>();
    private final MutableLiveData<String> mErrorTelefono = new MutableLiveData<>();
    private final MutableLiveData<String> mMensajeError = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        EDITAR = getApplication().getResources().getString(R.string.editar);
        GUARDAR = getApplication().getResources().getString(R.string.guardar);
    }

    public LiveData<String> getMErrorDni() {
        return mErrorDni;
    }

    public LiveData<Propietario> getMPropietario() {
        return mPropietario;
    }

    public LiveData<Boolean> getMEstadoEditar() {
        return mEstadoEditar;
    }

    public LiveData<String> getMTextoBoton() {
        return mTextoBoton;
    }

    public LiveData<String> getMErrorNombre() {
        return mErrorNombre;
    }

    public LiveData<String> getMErrorApellido() {
        return mErrorApellido;
    }

    public LiveData<String> getMErrorEmail() {
        return mErrorEmail;
    }

    public LiveData<String> getMErrorTelefono() {
        return mErrorTelefono;
    }

    public LiveData<String> getMMensajeError() {
        return mMensajeError;
    }

    public void editarPropietario(String btnText, String nombre, String apelldio, String dni, String telefono, String email) {
        if (btnText.equals(EDITAR)) {
            cambiarAModoGuardar();
        } else {
            if (!esPropietarioValido(nombre, apelldio, dni, telefono, email))
                return;

            cambiarAModoEditar();

            Propietario propietario = new Propietario(mPropietario.getValue().getId(), nombre, apelldio, dni, telefono, email);
            actualizarPropietario(propietario);
        }
    }

    public void obtenerPropietario() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<Propietario> apiCall = api.getPropietario(token);

        apiCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mPropietario.postValue(response.body());
                } else {
                    mMensajeError.postValue("No se encontraron los datos del propietario");
                    Toast.makeText(getApplication(), "Error al obtener el propietario", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
                mMensajeError.postValue("Error al obtener el propietario");
            }
        });
    }

    private void actualizarPropietario(Propietario propietario) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<Propietario> apiCall = api.putPropietario(token, propietario);

        apiCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mPropietario.postValue(response.body()); //esto en el caso que el actualizar devuelva el propetario actualizado
                } else {
                    mMensajeError.postValue("Error al actualizar el propietario");
                    Toast.makeText(getApplication(), "Error al actualizar el propietario", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
                mMensajeError.postValue("Error al actualizar el propietario");
            }
        });
    }

    private void cambiarAModoEditar() {
        mTextoBoton.setValue(EDITAR);
        mEstadoEditar.setValue(false);
    }

    private void cambiarAModoGuardar() {
        mTextoBoton.setValue(GUARDAR);
        mEstadoEditar.setValue(true);
    }

    private boolean esPropietarioValido(String nombre, String apelldio, String dni, String telefono, String email) {
        boolean esValido = false;
        ResultadoValidacion resultadoValidacion;

        resultadoValidacion = Propietario.validarDni(dni);
        esValido = resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorDni.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Propietario.validarNombre(nombre);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorNombre.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Propietario.validarApellido(apelldio);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorApellido.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Propietario.validarTelefono(telefono);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorTelefono.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Propietario.validarEmail(email);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorEmail.setValue(resultadoValidacion.getMensajeError());

        return esValido;
    }
}