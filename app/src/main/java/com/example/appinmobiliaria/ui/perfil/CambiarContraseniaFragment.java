package com.example.appinmobiliaria.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.databinding.FragmentCambiarContraseniaBinding;
import com.example.appinmobiliaria.util.Dialogo;

public class CambiarContraseniaFragment extends Fragment {
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
        binding = FragmentCambiarContraseniaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CambiarContraseniaViewModel.class);

        viewModel.getMContraseniaCambiada().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, true);
            }
        });

        viewModel.getMErrorCambiarContrasenia().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, false);
            }
        });

        viewModel.getMErrorNuevaContrasenia().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorContraseniaNueva.setText(s);
            }
        });

        viewModel.getMErrorContraseniaActual().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorContraseniaActual.setText(s);
            }
        });

        viewModel.getMErrorRepetirContrasenia().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorContraseniaNuevaRepetida.setText(s);
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

    private void muestraDialog(String mensaje, boolean exito){
        Dialogo dialogo = new Dialogo(getContext(), getLayoutInflater());
        dialogo.mostrarMensaje(mensaje, null, exito);
    }

    private void resetearMensajesError() {
        binding.tvErrorContraseniaActual.setText("");
        binding.tvErrorContraseniaNueva.setText("");
        binding.tvErrorContraseniaNuevaRepetida.setText("");
    }

}