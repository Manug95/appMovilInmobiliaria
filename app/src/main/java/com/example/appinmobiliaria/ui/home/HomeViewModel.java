package com.example.appinmobiliaria.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<MapaActual> mMapaActual = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaActual> getMutableMapaActual() {
        return mMapaActual;
    }

    public void cargarMapa() {
        MapaActual mapaActual = new MapaActual();
        mMapaActual.setValue(mapaActual);
    }

    public class MapaActual implements OnMapReadyCallback {
        private final LatLng inmobiliaria = new LatLng(-33.150720, -66.306864);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MarkerOptions marcadorInmobiliaria = new MarkerOptions();
            marcadorInmobiliaria.position(inmobiliaria);
            marcadorInmobiliaria.title("San Luis");

            googleMap.addMarker(marcadorInmobiliaria);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(inmobiliaria)
                    .zoom(20)
                    .bearing(45)
                    .tilt(15)
                    .build();

            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

            googleMap.animateCamera(cameraUpdate);
        }
    }
}