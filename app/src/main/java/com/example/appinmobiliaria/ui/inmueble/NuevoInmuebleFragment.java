package com.example.appinmobiliaria.ui.inmueble;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.appinmobiliaria.databinding.DialogMensajePersonalizadoBinding;
import com.example.appinmobiliaria.util.Dialogo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.FragmentNuevoInmuebleBinding;
import com.example.appinmobiliaria.modelos.TipoInmueble;

import java.util.List;

public class NuevoInmuebleFragment extends Fragment {
    private FragmentNuevoInmuebleBinding binding;
    private NuevoInmuebleViewModel viewModel;
    @ColorInt
    private int COLOR_ERROR;
    @ColorInt
    private int COLOR_EXITO;
    private ActivityResultLauncher<Intent> arl;
    private Intent intentActivityGaleriaFotos;

    public static NuevoInmuebleFragment newInstance() {
        return new NuevoInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentNuevoInmuebleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        //COLOR_EXITO = getResources().getColor(R.color.success, null);
        //COLOR_ERROR = getResources().getColor(R.color.error, null);

        viewModel.getMURIFoto().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFotoNueva.setImageURI(uri);
            }
        });

        viewModel.getMMensajeExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, true);
            }
        });

        viewModel.getMMensajeError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, false);
            }
        });

        viewModel.getMErrorCalle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorCalle.setText(s);
            }
        });

        viewModel.getMErrorCantidadAmbientes().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorCantidadAmbientes.setText(s);
            }
        });

        viewModel.getMErrorPrecio().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorPrecio.setText(s);
            }
        });

        viewModel.getMErrorNroCalle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorNroCalle.setText(s);
            }
        });

        viewModel.getMErrorTipoInmueble().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorTipoInmueble.setText(s);
            }
        });

        viewModel.getMErrorUso().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorUso.setText(s);
            }
        });

        viewModel.getMErrorFoto().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorFoto.setText(s);
            }
        });

        viewModel.getMTiposInmueble().observe(getViewLifecycleOwner(), new Observer<List<TipoInmueble>>() {
            @Override
            public void onChanged(List<TipoInmueble> tiposInmueble) {
                ArrayAdapter<TipoInmueble> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, tiposInmueble);
                binding.etTipoInmueble.setAdapter(adapter);
            }
        });

        binding.btnGuardarNuevoInmeuble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiearMensajesdeError();

                String calle = binding.etCalle.getText().toString();
                String nro = binding.etNroCalle.getText().toString();
                String tipoInmueble = binding.etTipoInmueble.getText().toString();
                String uso = binding.etUso.getText().toString();
                String cantAmbientes = binding.etCantidadAmbientes.getText().toString();
                String precio = binding.etPrecio.getText().toString();

                viewModel.guardarInmueble(calle, nro, tipoInmueble, uso, cantAmbientes, precio);
            }
        });

        binding.btnCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intentActivityGaleriaFotos);
            }
        });

        viewModel.getTipoInmuebles();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, new String[]{"COMERCIAL", "RESIDENCIAL"});
        binding.etUso.setAdapter(adapter);
        abrirGaleria();

        return binding.getRoot();
    }

    private void abrirGaleria() {
        intentActivityGaleriaFotos = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                viewModel.recibirFoto(result);
            }
        });
    }

    private void muestraDialog(String mensaje, boolean exito){
        Dialogo dialogo = new Dialogo(getContext(),getLayoutInflater());
        dialogo.mostrarMensaje(mensaje, null, exito);
    }

    private void limpiearMensajesdeError() {
        binding.tvErrorCalle.setText("");
        binding.tvErrorNroCalle.setText("");
        binding.tvErrorTipoInmueble.setText("");
        binding.tvErrorUso.setText("");
        binding.tvErrorCantidadAmbientes.setText("");
        binding.tvErrorPrecio.setText("");
        binding.tvErrorFoto.setText("");
    }

}