package com.example.appinmobiliaria.ui.perfil;

import androidx.annotation.ColorInt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.FragmentCambiarContraseniaBinding;

public class CambiarContraseniaFragment extends Fragment {
    @ColorInt
    private int COLOR_ERROR;
    @ColorInt
    private int COLOR_EXITO;
    private CambiarContraseniaViewModel viewModel;
    private FragmentCambiarContraseniaBinding binding;

    public static CambiarContraseniaFragment newInstance() {
        return new CambiarContraseniaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        COLOR_EXITO = getResources().getColor(R.color.success, null);
        COLOR_ERROR = getResources().getColor(R.color.error, null);
        binding = FragmentCambiarContraseniaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CambiarContraseniaViewModel.class);

        viewModel.getMContraseniaCambiada().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensajeRespuesta.setText(s);
                binding.tvMensajeRespuesta.setTextColor(COLOR_EXITO);
            }
        });

        viewModel.getMErrorCambiarContrasenia().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensajeRespuesta.setText(s);
                binding.tvMensajeRespuesta.setTextColor(COLOR_ERROR);
            }
        });

        viewModel.getMErrorNuevaContrasenia().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorContraseniaNueva.setText(s);
                binding.tvErrorContraseniaNueva.setTextColor(COLOR_ERROR);
            }
        });

        viewModel.getMErrorContraseniaActual().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorContraseniaActual.setText(s);
                binding.tvErrorContraseniaActual.setTextColor(COLOR_ERROR);
            }
        });

        viewModel.getMErrorRepetirContrasenia().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorContraseniaNuevaRepetida.setText(s);
                binding.tvErrorContraseniaNuevaRepetida.setTextColor(COLOR_ERROR);
            }
        });

        binding.btnCambiarContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetearMensajesError();

                String contraseniaActual = binding.etContraseniaActual.getText().toString();
                String nuevaContrasenia = binding.etNuevaContrasenia.getText().toString();
                String repetirContrasenia = binding.etNuevaContraseniaRepetida.getText().toString();

                viewModel.cambiarContrasenia(contraseniaActual, nuevaContrasenia, repetirContrasenia);
            }
        });

        return binding.getRoot();
    }

    private void resetearMensajesError() {
        binding.tvErrorContraseniaActual.setText("");
        binding.tvErrorContraseniaNueva.setText("");
        binding.tvErrorContraseniaNuevaRepetida.setText("");
        binding.tvMensajeRespuesta.setText("");
    }

}