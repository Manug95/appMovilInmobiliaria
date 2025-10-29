package com.example.appinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.appinmobiliaria.modelos.CambiarContrasenia;
import com.example.appinmobiliaria.modelos.EditarDisponible;
import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.modelos.Login;
import com.example.appinmobiliaria.modelos.Propietario;
import com.example.appinmobiliaria.modelos.TipoInmueble;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public class ApiClient {
    public static final String URL_BASE_PRUEBA = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    public static final String URL_BASE = "http://192.168.0.21:5113/";

    public static InmobiliariaService getInmobiliariaService() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
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
            if (errorObj.has("error")) {
                mensajeError = errorObj.getString("error");
            } else {
                mensajeError = "no tiene el campo error";
            }
        } catch (Exception e) {
            mensajeError = "Error";
        }
        return mensajeError;
    }

    public interface InmobiliariaService {
        //endpoints de la API de prueba
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> login(@Field("Usuario") String email, @Field("Clave")String password);

        @GET("api/Propietarios")
        Call<Propietario> getPerfil(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> actualizarPerfil(@Header("Authorization") String token, @Body Propietario propietario);

        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<String> cambiarContrasenia(@Header("Authorization") String token, @Field("currentPassword") String claveActual, @Field("newPassword") String claveNueva);

        @GET("api/Inmuebles")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

        /*@GET("api/Inmuebles/GetContratoVigente")
        Call<List<Inmueble>> getInmueblesAlquilados(@Header("Authorization") String token);*/

        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> CargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble") RequestBody inmuebleBody);

        //endpoints que va a tener mi API
        @POST("Propietario/login")
        Call<String> login(@Body Login login);

        @PATCH("Propietario/cambiarContraseña")
        Call<String> cambiarContrasenia(@Header("Authorization") String token, @Body CambiarContrasenia cambiarContrasenia);

        @GET("Propietario")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        @PUT("Propietario")
        Call<Propietario> putPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        @GET("Inmueble")
        Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);

        @GET("Inmuebles/alquilados")
        Call<List<Inmueble>> getInmueblesAlquilados(@Header("Authorization") String token);

        @PATCH("Inmueble/disponible")
        Call<Void> cambiarDisponible(@Header("Authorization") String token, @Body EditarDisponible editarDisponible);

        @POST("Inmueble")
        Call<Inmueble> crearInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);
        @Multipart
        @POST("Inmueble")
        Call<Inmueble> crearInmueble(@Header("Authorization") String token,
                                     @Part MultipartBody.Part foto,
                                     @Part("inmueble") RequestBody inmuebleBody);

        @GET("TipoInmueble")
        Call<List<TipoInmueble>> getTiposInmuebles(@Header("Authorization") String token);
    }
}