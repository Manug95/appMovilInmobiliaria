package com.example.appinmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinmobiliaria.modelos.Login;
import com.example.appinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mLoginCorrecto = new MutableLiveData<>();
    private final MutableLiveData<String> mLoginIncorrecto = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mEstaLogueado = new MutableLiveData<>();
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private ManejadorAcelerometro manejadorAcelerometro;

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
            mLoginIncorrecto.setValue("El E-mail o la contraseña esta(n) vacío(s)");
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<String> apiCall = api.login(new Login(email, password));

        apiCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    ApiClient.guardarToken(getApplication(), response.body());
                    mLoginCorrecto.setValue("¡Bienvenid@!");
                } else {
                    mLoginIncorrecto.setValue(ApiClient.obtenerMensajeError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mLoginIncorrecto.setValue("Ocurrió un error al hacer intentar iniciar sesión");
            }
        });
    }

    public void comprobarSesion() {
        String token = ApiClient.leerToken(getApplication());
        if (token != null) {
            mEstaLogueado.setValue(true);
        }
    }

    public void activarSensorAcelerometro(ManejadorAcelerometro.OnShakeListener listener) {
        sensorManager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (acelerometro != null) {
                manejadorAcelerometro = new ManejadorAcelerometro();
                manejadorAcelerometro.setOnShakeListener(listener);
                sensorManager.registerListener(manejadorAcelerometro, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    public void desactivarSensor() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(manejadorAcelerometro);
        }
    }

    public static class ManejadorAcelerometro implements SensorEventListener {

        private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
        private static final int SHAKE_SLOP_TIME_MS = 500;
        private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

        private OnShakeListener mListener;
        private long mShakeTimestamp;
        private int mShakeCount;

        public void setOnShakeListener(OnShakeListener listener) {
            this.mListener = listener;
        }

        public interface OnShakeListener {
            void onShake(int count);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (mListener != null) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float gX = x / SensorManager.GRAVITY_EARTH;
                float gY = y / SensorManager.GRAVITY_EARTH;
                float gZ = z / SensorManager.GRAVITY_EARTH;

                // gForce es aproximadamente 1 cuando está quieto.
                float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

                if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                    final long now = System.currentTimeMillis();
                    // Para ignorar las agitaciones demasiado seguidas
                    if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                        return;
                    }

                    // Reiniciar el contador si ha pasado mucho tiempo
                    if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                        mShakeCount = 0;
                    }

                    mShakeTimestamp = now;
                    mShakeCount++;

                    mListener.onShake(mShakeCount);
                }
            }
        }
    }
}
