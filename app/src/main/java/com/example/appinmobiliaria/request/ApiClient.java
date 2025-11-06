package com.example.appinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.appinmobiliaria.BuildConfig;
import com.example.appinmobiliaria.modelos.CambiarContrasenia;
import com.example.appinmobiliaria.modelos.Contrato;
import com.example.appinmobiliaria.modelos.EditarDisponible;
import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.modelos.Login;
import com.example.appinmobiliaria.modelos.Pago;
import com.example.appinmobiliaria.modelos.Propietario;
import com.example.appinmobiliaria.modelos.TipoInmueble;
import com.example.appinmobiliaria.util.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {
    public static final String URL_BASE_PRUEBA = BuildConfig.API_URL_BASE_PROFE;
    public static final String URL_BASE = BuildConfig.API_URL_BASE;

    public static InmobiliariaService getInmobiliariaService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(InmobiliariaService.class);
    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", "Bearer " + token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    public static  void borrarToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }

    public static String obtenerMensajeError(ResponseBody responseBody) {
        String mensajeError;
        try {
            String errorJson = responseBody.string();
            JSONObject errorObj = new JSONObject(errorJson);
            //Log.d("json", errorObj.toString());
            if (errorObj.has("mensaje")) {
                mensajeError = errorObj.getString("mensaje");
            } else {
                mensajeError = "no hay mensaje explicativo reconocible";
            }
        } catch (Exception e) {
            mensajeError = "Error";
        }
        return mensajeError;
    }

    public interface InmobiliariaService {
        @POST("Propietario/login")
        Call<String> login(@Body Login login);

        @PATCH("Propietario/clave")
        Call<Void> cambiarContrasenia(@Header("Authorization") String token, @Body CambiarContrasenia cambiarContrasenia);

        @GET("Propietario")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        @PUT("Propietario")
        Call<Propietario> putPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        @GET("Inmueble")
        Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);

        @GET("Inmuebles/alquilados")
        Call<List<Inmueble>> getInmueblesAlquilados(@Header("Authorization") String token);

        @PATCH("Inmueble/{id}/disponible")
        Call<Void> cambiarDisponible(@Header("Authorization") String token, @Path("id") int id, @Body EditarDisponible editarDisponible);

        @Multipart
        @POST("Inmueble")
        Call<Inmueble> crearInmueble(@Header("Authorization") String token,
                                     @Part MultipartBody.Part foto,
                                     @Part("inmueble") RequestBody inmuebleBody);

        @GET("TipoInmueble")
        Call<List<TipoInmueble>> getTiposInmuebles(@Header("Authorization") String token);

        @GET("Contrato/vigentes")
        Call<List<Contrato>> getContratosVigentes(@Header("Authorization") String token);

        @GET("Pago")
        Call<List<Pago>> getPagos(@Header("Authorization") String token, @Query(("c")) int idContrato, @Query("offset") int offset);
    }
}