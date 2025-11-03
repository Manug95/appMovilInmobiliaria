package com.example.appinmobiliaria.ui.inquilino;

import androidx.annotation.ColorInt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.DialogMensajePersonalizadoBinding;
import com.example.appinmobiliaria.databinding.FragmentInquilinoBinding;
import com.example.appinmobiliaria.modelos.Contrato;
import com.example.appinmobiliaria.ui.contrato.ContratoAdapter;
import com.example.appinmobiliaria.util.Dialogo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class InquilinoFragment extends Fragment {
    private FragmentInquilinoBinding binding;
    private InquilinoViewModel viewModel;
    @ColorInt
    private int COLOR_ERROR;
    @ColorInt
    private int COLOR_EXITO;

    public static InquilinoFragment newInstance() {
        return new InquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(InquilinoViewModel.class);
        //COLOR_EXITO = getResources().getColor(R.color.success, null);
        //COLOR_ERROR = getResources().getColor(R.color.error, null);

        viewModel.getMContratos().observe(getViewLifecycleOwner(), new Observer<List<Contrato>>() {
            @Override
            public void onChanged(List<Contrato> contratos) {
                //InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getContext(), getLayoutInflater());
                ContratoAdapter adapter = new ContratoAdapter(contratos, getContext(), getLayoutInflater(), new ContratoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Contrato contrato) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contrato", contrato);
                        Navigation
                                .findNavController((Activity) getContext(), R.id.nav_host_fragment_content_main)
                                .navigate(R.id.detalleInquilinoFragment, bundle);
                    }
                });
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                binding.rvInmueblesAlquilados.setLayoutManager(glm);
                binding.rvInmueblesAlquilados.setAdapter(adapter);
            }
        });

        viewModel.getMErrorContratos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, false);
            }
        });

        return binding.getRoot();
    }

    private void muestraDialog(String mensaje, boolean exito){
        Dialogo dialogo = new Dialogo(getContext(),getLayoutInflater());
        dialogo.mostrarMensaje(mensaje, null, exito);
    }

}