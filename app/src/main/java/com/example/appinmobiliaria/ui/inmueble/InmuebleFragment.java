package com.example.appinmobiliaria.ui.inmueble;

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
import com.example.appinmobiliaria.databinding.FragmentInmuebleBinding;
import com.example.appinmobiliaria.modelos.Inmueble;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class InmuebleFragment extends Fragment {

    private InmuebleViewModel viewModel;
    private FragmentInmuebleBinding binding;
    @ColorInt
    private int COLOR_ERROR;
    @ColorInt
    private int COLOR_EXITO;

    public static InmuebleFragment newInstance() {
        return new InmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(InmuebleViewModel.class);
        COLOR_EXITO = getResources().getColor(R.color.success, null);
        COLOR_ERROR = getResources().getColor(R.color.error, null);

        viewModel.getMInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                //InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getContext(), getLayoutInflater());
                InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getContext(), getLayoutInflater(), new InmuebleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Inmueble inmueble) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("inmueble", inmueble);
                        Navigation
                                .findNavController((Activity) getContext(), R.id.nav_host_fragment_content_main)
                                .navigate(R.id.detalleInmuebleFragment, bundle);
                    }
                });
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                binding.rvInmuebles.setLayoutManager(glm);
                binding.rvInmuebles.setAdapter(adapter);
            }
        });

        viewModel.getMErrorInmeubles().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, COLOR_ERROR);
            }
        });

        binding.btnAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation
                        .findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.nuevoInmuebleFragment);
            }
        });

        viewModel.getInmuebles();

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
                }).show();
    }

}