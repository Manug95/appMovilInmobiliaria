package com.example.appinmobiliaria.ui.inmueble;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> mInmuebles;
    private MutableLiveData<String> mErrorInmuebles;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        //getInmuebles();
    }

    public LiveData<List<Inmueble>> getMInmuebles() {
        if (mInmuebles == null) mInmuebles = new MutableLiveData<>();
        return mInmuebles;
    }

    public LiveData<String> getMErrorInmeubles() {
        if (mErrorInmuebles == null) mErrorInmuebles = new MutableLiveData<>();
        return mErrorInmuebles;
    }

    public void getInmuebles() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();

        Call<List<Inmueble>> callGetInmuebles = api.getInmuebles(token);

        callGetInmuebles.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    List<Inmueble> inmuebles = response.body();
                    if (inmuebles != null && inmuebles.isEmpty())
                        mErrorInmuebles.postValue("No tiene inmuebles registrados");
                    else
                        mInmuebles.postValue(response.body());
                }
                else {
                    //Toast.makeText(getApplication(), "No se pudieron cargar los inmuebles", Toast.LENGTH_LONG).show();
                    mErrorInmuebles.postValue("No se pudieron cargar los inmuebles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                //Toast.makeText(getApplication(), "Error al cargar los inmuebles", Toast.LENGTH_LONG).show();
                mErrorInmuebles.postValue("Error al cargar los inmuebles");
            }
        });
    }

}