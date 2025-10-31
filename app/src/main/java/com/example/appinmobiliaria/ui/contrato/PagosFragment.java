package com.example.appinmobiliaria.ui.contrato;

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
import com.example.appinmobiliaria.databinding.FragmentPagosBinding;
import com.example.appinmobiliaria.modelos.Contrato;
import com.example.appinmobiliaria.modelos.Pago;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class PagosFragment extends Fragment {
    private FragmentPagosBinding binding;
    private PagosViewModel viewModel;
    @ColorInt
    private int COLOR_ERROR;
    @ColorInt
    private int COLOR_EXITO;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        COLOR_EXITO = getResources().getColor(R.color.success, null);
        COLOR_ERROR = getResources().getColor(R.color.error, null);

        viewModel.getMPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter adapter = new PagoAdapter(pagos, getContext(), getLayoutInflater());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.rvPagos.setLayoutManager(glm);
                binding.rvPagos.setAdapter(adapter);
            }
        });

        viewModel.getMErrorPagos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, COLOR_ERROR);
            }
        });

        viewModel.traerPagos(getArguments());

        return binding.getRoot();
    }

    private void muestraDialog(String mensaje, @ColorInt int colorDelMensaje){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        DialogMensajePersonalizadoBinding dialogBinding = DialogMensajePersonalizadoBinding.inflate(inflater);

        dialogBinding.tvMensajeDialog.setText(mensaje);
        dialogBinding.tvMensajeDialog.setTextColor(colorDelMensaje);

        new MaterialAlertDialogBuilder(getContext())
                .setTitle(R.string.titulo_dialog_mensaje)
                .setView(dialogBinding.getRoot())
                .setNeutralButton(R.string.dialog_ok,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        di.dismiss();
                    }
                })
                .show();
    }

}