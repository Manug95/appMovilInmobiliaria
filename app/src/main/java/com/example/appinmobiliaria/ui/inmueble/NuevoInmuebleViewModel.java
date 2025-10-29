package com.example.appinmobiliaria.ui.inmueble;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.modelos.TipoInmueble;
import com.example.appinmobiliaria.request.ApiClient;
import com.example.appinmobiliaria.util.ResultadoValidacion;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoInmuebleViewModel extends AndroidViewModel {
    private List<TipoInmueble> tiposInmueble;
    private MutableLiveData<String> mMensajeError;
    private MutableLiveData<String> mMensajeExito;
    private MutableLiveData<String> mErrorCalle;
    private MutableLiveData<String> mErrorNroCalle;
    private MutableLiveData<String> mErrorTipoInmueble;
    private MutableLiveData<String> mErrorUso;
    private MutableLiveData<String> mErrorCantidadAmbientes;
    private MutableLiveData<String> mErrorPrecio;
    private MutableLiveData<Uri> mUriFoto;
    private MutableLiveData<String> mErrorFoto;
    private MutableLiveData<List<TipoInmueble>> mTiposInmueble;

    public NuevoInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public void guardarInmueble(String calle, String nro, String tipoInmueble, String uso, String cantAmbientes, String precio) {
        if (!esInmuebleValido(calle, nro, tipoInmueble, uso, cantAmbientes, precio))
            return;

        Inmueble inmueble = new Inmueble(
                calle,
                Integer.parseInt(nro),
                Integer.parseInt(cantAmbientes),
                tipoInmueble,
                uso,
                Double.parseDouble(precio),
                true
        );
        int idTipoInmueble = getIdTipoInmueble(tipoInmueble);
        if (idTipoInmueble > 0)
            inmueble.setIdTipoInmueble(idTipoInmueble);

        String inmuebleJson = new Gson().toJson(inmueble);
        RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);

        byte[] foto = transformarImagen();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), foto);
        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("foto", "foto.jpg", requestFile);

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<Inmueble> callCrearInmueble = api.crearInmueble(token, imagenPart, inmuebleBody);
        callCrearInmueble.enqueue(new Callback<Inmueble>() {

            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    mMensajeExito.setValue("Inmueble creado correctamente");
                } else {
                    mMensajeError.setValue(ApiClient.obtenerMensajeError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mMensajeError.setValue("Error al hacer la petición al servidor");
            }
        });
    }

    public void getTipoInmuebles() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<List<TipoInmueble>> callGetTiposInmuebles = api.getTiposInmuebles(token);

        callGetTiposInmuebles.enqueue(new Callback<List<TipoInmueble>>() {
            @Override
            public void onResponse(Call<List<TipoInmueble>> call, Response<List<TipoInmueble>> response) {
                if (response.isSuccessful()) {
                    tiposInmueble = response.body();
                    mTiposInmueble.postValue(tiposInmueble);
                }
                else Toast.makeText(getApplication(), "peticion no exitosa", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<TipoInmueble>> call, Throwable t) {
                Toast.makeText(getApplication(), "se ejecuto onFailure", Toast.LENGTH_LONG).show();
            }
        });

    }

    private byte[] transformarImagen() {
        try {
            Uri uri = mUriFoto.getValue();  //lo puedo usar porque estoy en viewmodel
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (
                FileNotFoundException er) {
            Toast.makeText(getApplication(), "No ha seleccinado una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                Uri uri = data.getData();
                mUriFoto.setValue(uri);
            }
        }
    }

    private boolean esInmuebleValido(String calle, String nro, String tipoInmueble, String uso, String cantAmbientes, String precio) {
        boolean esValido = false;
        ResultadoValidacion resultadoValidacion;

        resultadoValidacion = Inmueble.validarCalle(calle);
        esValido = resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorCalle.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Inmueble.validarNroCalle(nro);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorNroCalle.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Inmueble.validarTipoInmueble(tipoInmueble);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorTipoInmueble.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Inmueble.validarUso(uso);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorUso.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Inmueble.validarCantidadAmbientes(cantAmbientes);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorCantidadAmbientes.setValue(resultadoValidacion.getMensajeError());

        resultadoValidacion = Inmueble.validarPrecio(precio);
        esValido = esValido && resultadoValidacion.esValido();
        if (!resultadoValidacion.esValido()) mErrorPrecio.setValue(resultadoValidacion.getMensajeError());

        if (mUriFoto.getValue() == null) {
            mErrorFoto.setValue("Debe seleccionar una foto");
            esValido = false;
        }

        return esValido;
    }

    private int getIdTipoInmueble(String tipo) {
        for (TipoInmueble tipoInmueble : tiposInmueble) {
            if (tipoInmueble.getTipo().equals(tipo)) {
                return tipoInmueble.getId();
            }
        }
        return -1;
    }

    public LiveData<String> getMMensajeError() {
        if (mMensajeError == null) {
            mMensajeError = new MutableLiveData<>();
        }
        return mMensajeError;
    }

    public LiveData<String> getMMensajeExito() {
        if (mMensajeExito == null) {
            mMensajeExito = new MutableLiveData<>();
        }
        return mMensajeExito;
    }

    public LiveData<String> getMErrorCalle() {
        if (mErrorCalle == null) {
            mErrorCalle = new MutableLiveData<>();
        }
        return mErrorCalle;
    }

    public LiveData<String> getMErrorNroCalle() {
        if (mErrorNroCalle == null) {
            mErrorNroCalle = new MutableLiveData<>();
        }
        return mErrorNroCalle;
    }

    public LiveData<String> getMErrorTipoInmueble() {
        if (mErrorTipoInmueble == null) {
            mErrorTipoInmueble = new MutableLiveData<>();
        }
        return mErrorTipoInmueble;
    }

    public LiveData<String> getMErrorUso() {
        if (mErrorUso == null) {
            mErrorUso = new MutableLiveData<>();
        }
        return mErrorUso;
    }

    public LiveData<String> getMErrorCantidadAmbientes() {
        if (mErrorCantidadAmbientes == null) {
            mErrorCantidadAmbientes = new MutableLiveData<>();
        }
        return mErrorCantidadAmbientes;
    }

    public LiveData<String> getMErrorPrecio() {
        if (mErrorPrecio == null) {
            mErrorPrecio = new MutableLiveData<>();
        }
        return mErrorPrecio;
    }

    public LiveData<Uri> getMURIFoto() {
        if (mUriFoto == null) {
            mUriFoto = new MutableLiveData<>();
        }
        return mUriFoto;
    }

    public LiveData<String> getMErrorFoto() {
        if (mErrorFoto == null) {
            mErrorFoto = new MutableLiveData<>();
        }
        return mErrorFoto;
    }

    public LiveData<List<TipoInmueble>> getMTiposInmueble() {
        if (mTiposInmueble == null) {
            mTiposInmueble = new MutableLiveData<>();
        }
        return mTiposInmueble;
    }
}