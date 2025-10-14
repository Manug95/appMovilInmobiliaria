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

import com.example.appinmobiliaria.databinding.FragmentPerfilBinding;
import com.example.appinmobiliaria.modelos.Propietario;

public class PerfilFragment extends Fragment {
    private PerfilViewModel viewModel;
    private FragmentPerfilBinding binding;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        //resetearMensajeErrorGlobal();

        viewModel.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etEmail.setText(propietario.getEmail());
            }
        });

        viewModel.getMEstadoEditar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etDni.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
                binding.etEmail.setEnabled(aBoolean);
            }
        });

        viewModel.getMTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnAccion.setText(s);
            }
        });

        viewModel.getMMensajeError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensajeError.setText(s);
            }
        });

        viewModel.getMErrorDni().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorDni.setText(s);
            }
        });

        viewModel.getMErrorNombre().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorNombre.setText(s);
            }
        });

        viewModel.getMErrorApellido().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorApellido.setText(s);
            }
        });

        viewModel.getMErrorTelefono().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorTelefono.setText(s);
            }
        });

        viewModel.getMErrorEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorEmail.setText(s);
            }
        });

        binding.btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetearMensajesError();

                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String dni = binding.etDni.getText().toString();
                String telefono = binding.etTelefono.getText().toString();
                String email = binding.etEmail.getText().toString();

                viewModel.editarPropietario(binding.btnAccion.getText().toString(), nombre, apellido, dni, telefono, email);
            }
        });

        viewModel.obtenerPropietario();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void resetearMensajeErrorGlobal() {
        binding.tvMensajeError.setText("");
    }

    private void resetearMensajesError() {
        resetearMensajeErrorGlobal();
        binding.tvErrorDni.setText("");
        binding.tvErrorNombre.setText("");
        binding.tvErrorApellido.setText("");
        binding.tvErrorTelefono.setText("");
        binding.tvErrorEmail.setText("");
    }
}