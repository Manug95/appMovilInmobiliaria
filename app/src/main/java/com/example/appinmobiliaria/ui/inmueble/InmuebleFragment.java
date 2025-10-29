package com.example.appinmobiliaria.ui.inmueble;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
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
import com.example.appinmobiliaria.databinding.FragmentInmuebleBinding;
import com.example.appinmobiliaria.modelos.Inmueble;

import java.util.List;

public class InmuebleFragment extends Fragment {

    private InmuebleViewModel viewModel;
    private FragmentInmuebleBinding binding;

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

}