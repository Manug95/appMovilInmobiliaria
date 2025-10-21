package com.example.appinmobiliaria.ui.inmueble;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.appinmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.example.appinmobiliaria.databinding.FragmentInmuebleBinding;
import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel viewModel;
    private FragmentDetalleInmuebleBinding binding;

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

        viewModel.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.tvIdInmueble.setText(String.valueOf(inmueble.getId()));
                binding.tvDireccionI.setText(inmueble.getDireccion());
                binding.tvUsoI.setText(inmueble.getUso());
                binding.tvAmbientesI.setText(String.valueOf(inmueble.getCantidadAmbientes()));
                binding.tvLatitudI.setText(String.valueOf(inmueble.getLatitud()));
                binding.tvLongitudI.setText(String.valueOf(inmueble.getLongitud()));
                binding.tvValorI.setText(String.valueOf(inmueble.getValor()));
                binding.checkDisponible.setChecked(inmueble.isDisponible());
                Glide.with(binding.getRoot())
                        .load(ApiClient.URL_BASE + inmueble.getImagen())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.imgInmuebleD);
            }
        });

        viewModel.getMErroActualizacion().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.checkDisponible.setChecked(aBoolean);
            }
        });

        binding.checkDisponible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                viewModel.cambiarDisponibilidad(isChecked);
            }
        });

        viewModel.recuperarInmueble(getArguments());

        return binding.getRoot();
    }

}