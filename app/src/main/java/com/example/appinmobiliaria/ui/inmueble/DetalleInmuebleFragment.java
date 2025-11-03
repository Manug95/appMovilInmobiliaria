package com.example.appinmobiliaria.ui.inmueble;

import androidx.annotation.ColorInt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.DialogMensajePersonalizadoBinding;
import com.example.appinmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.example.appinmobiliaria.databinding.FragmentInmuebleBinding;
import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.request.ApiClient;
import com.example.appinmobiliaria.util.Dialogo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel viewModel;
    private FragmentDetalleInmuebleBinding binding;
    @ColorInt
    private int COLOR_ERROR;
    @ColorInt
    private int COLOR_EXITO;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        //COLOR_EXITO = getResources().getColor(R.color.success, null);
        //COLOR_ERROR = getResources().getColor(R.color.error, null);

        viewModel.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                String direccion = inmueble.getCalle() + " " + inmueble.getNroCalle();
                binding.tvDireccion.setText(direccion);
                binding.tvUso.setText(inmueble.getUso());
                binding.tvTipoInmueble.setText(inmueble.getTipoInmueble());
                binding.tvAmbientes.setText(String.valueOf(inmueble.getCantidadAmbientes()));
                binding.tvLatitud.setText(String.valueOf(inmueble.getLatitud()));
                binding.tvLongitud.setText(String.valueOf(inmueble.getLongitud()));
                binding.tvPrecio.setText(String.valueOf(inmueble.getPrecio()));
                binding.checkDisponible.setChecked(inmueble.isDisponible());
                Glide.with(binding.getRoot())
                        .load(ApiClient.URL_BASE + inmueble.getFoto())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.ivFoto);
            }
        });

        viewModel.getMErroActualizacion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, false);
            }
        });

        viewModel.getMEstadoDisponible().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.checkDisponible.setChecked(aBoolean);
            }
        });

        viewModel.getMEXitoCambioDisponibilidad().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, true);
            }
        });

        /*binding.checkDisponible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                viewModel.cambiarDisponibilidad(isChecked);
            }
        });*/
        binding.checkDisponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.cambiarDisponibilidad(binding.checkDisponible.isChecked());
            }
        });

        viewModel.recuperarInmueble(getArguments());

        return binding.getRoot();
    }

    private void muestraDialog(String mensaje, boolean exito){
        Dialogo dialogo = new Dialogo(getContext(),getLayoutInflater());
        dialogo.mostrarMensaje(mensaje, null, exito);
    }

}