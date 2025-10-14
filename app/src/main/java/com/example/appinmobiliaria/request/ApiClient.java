package com.example.appinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.appinmobiliaria.modelos.CambiarContrasenia;
import com.example.appinmobiliaria.modelos.Login;
import com.example.appinmobiliaria.modelos.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {
    private static final String URL_BASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

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

    public interface InmobiliariaService {
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> login(@Field("Usuario") String email, @Field("Clave")String password);

        @POST("todavia no tengo el path")
        Call<String> login(@Body Login login);

        @PATCH("todavia no tengo el path")
        Call<String> cambiarContrasenia(@Header("Authorization") String token, @Body CambiarContrasenia cambiarContrasenia);

        @GET("api/Propietarios")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> putPropietario(@Header("Authorization") String token, @Body Propietario propietario);
    }
}