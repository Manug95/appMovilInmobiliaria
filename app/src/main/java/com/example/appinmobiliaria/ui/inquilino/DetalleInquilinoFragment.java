package com.example.appinmobiliaria.ui.inquilino;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.databinding.FragmentDetalleInquilinoBinding;
import com.example.appinmobiliaria.modelos.Inquilino;

public class DetalleInquilinoFragment extends Fragment {
    private FragmentDetalleInquilinoBinding binding;
    private DetalleInquilinoViewModel viewModel;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        viewModel.getMInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                String nombreYApellido = inquilino.getNombre() + " " + inquilino.getApellido();
                binding.tvNombreYApellidoInquilino.setText(nombreYApellido);
                binding.tvDNIInquilino.setText(String.valueOf(inquilino.getDni()));
                binding.tvTelefonoInquilino.setText(String.valueOf(inquilino.getTelefono()));
                binding.tvEmailInquilino.setText(inquilino.getEmail());
            }
        });

        viewModel.recuperarInquilino(getArguments());

        return binding.getRoot();
    }

}