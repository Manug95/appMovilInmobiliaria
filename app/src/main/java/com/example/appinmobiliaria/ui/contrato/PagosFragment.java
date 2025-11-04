package com.example.appinmobiliaria.ui.contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.databinding.FragmentPagosBinding;
import com.example.appinmobiliaria.modelos.Pago;
import com.example.appinmobiliaria.util.Dialogo;

import java.util.ArrayList;
import java.util.List;

public class PagosFragment extends Fragment {
    private FragmentPagosBinding binding;
    private PagosViewModel viewModel;
    private boolean estaCargandoPagos = false;
    private boolean noHayMasPagos = false;
    private int paginaActual = 1;
    private PagoAdapter pagoAdapter;

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

        configurarRecyclerView();

        viewModel.getMPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                pagoAdapter.setDatos(pagos);
            }
        });

        viewModel.getMNuevosPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> nuevosPagos) {
                pagoAdapter.agregarDatos(nuevosPagos);
                estaCargandoPagos = false;
                noHayMasPagos = nuevosPagos.isEmpty() || nuevosPagos.size() < 10;
            }
        });

        viewModel.getMErrorPagos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                muestraDialog(s, false);
            }
        });

        viewModel.traerPagos(getArguments());

        return binding.getRoot();
    }

    private void configurarRecyclerView() {
        pagoAdapter = new PagoAdapter(new ArrayList<Pago>(), getContext(), getLayoutInflater());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvPagos.setLayoutManager(linearLayoutManager);
        binding.rvPagos.setAdapter(pagoAdapter);

        binding.rvPagos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy <= 0) return;
                if (estaCargandoPagos || noHayMasPagos) return;

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int cantidadItemsVisibles = linearLayoutManager.getChildCount();
                int totalItems = linearLayoutManager.getItemCount();
                int posicionPrimerItemVisible = linearLayoutManager.findFirstVisibleItemPosition();

                if ((cantidadItemsVisibles + posicionPrimerItemVisible) >= totalItems && posicionPrimerItemVisible >= 0) {
                    estaCargandoPagos = true;
                    cargarMasDatos();
                }
            }
        });
    }

    private void cargarMasDatos() {
        viewModel.traerNuevosPagos(++paginaActual);
    }

    private void muestraDialog(String mensaje, boolean exito){
        Dialogo dialogo = new Dialogo(getContext(),getLayoutInflater());
        dialogo.mostrarMensaje(mensaje, null, exito);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}