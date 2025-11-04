package com.example.appinmobiliaria.ui.contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.example.appinmobiliaria.modelos.Contrato;
import com.example.appinmobiliaria.util.Dialogo;

import java.time.format.DateTimeFormatter;

public class DetalleContratoFragment extends Fragment {
    private FragmentDetalleContratoBinding binding;
    private DetalleContratoViewModel viewModel;
    private Contrato contratoActual;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        viewModel.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                contratoActual = contrato;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                binding.tvNroContrato.setText(String.valueOf(contrato.getId()));
                binding.tvDireccionInmueble.setText(contrato.getInmueble().getDireccion());
                binding.tvInquilino.setText(contrato.getInquilino().getNombreCompleto());
                binding.tvMontoMensual.setText(String.valueOf(contrato.getMontoMensual()));
                binding.tvFechaInicio.setText(contrato.getFechaInicio().format(formatter));
                binding.tvFechaFin.setText(contrato.getFechaFin().format(formatter));
            }
        });

        viewModel.getMErrorContrato().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, false);
            }
        });

        binding.btnVerPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contrato", contratoActual);
                Navigation
                        .findNavController((Activity) getContext(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.pagosFragment, bundle);
            }
        });

        viewModel.recibirContrato(getArguments());

        return binding.getRoot();
    }

    private void muestraDialog(String mensaje, boolean exito){
        Dialogo dialogo = new Dialogo(getContext(),getLayoutInflater());
        dialogo.mostrarMensaje(mensaje, null, exito);
    }

}