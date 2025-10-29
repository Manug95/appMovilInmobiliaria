package com.example.appinmobiliaria.ui.inquilino;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.FragmentDetalleInquilinoBinding;

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



        return inflater.inflate(R.layout.fragment_detalle_inquilino, container, false);
    }

}