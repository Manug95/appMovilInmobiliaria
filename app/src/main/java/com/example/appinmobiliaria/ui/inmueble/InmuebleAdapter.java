package com.example.appinmobiliaria.ui.inmueble;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.ItemInmuebleBinding;
import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder>{
    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater layoutInflater;

    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater layoutInflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInmuebleBinding binding = ItemInmuebleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InmuebleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        Inmueble inmuebleActual = listaInmuebles.get(position);
        holder.bind(inmuebleActual);
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public static class InmuebleViewHolder extends RecyclerView.ViewHolder{
        private final ItemInmuebleBinding binding;

        public InmuebleViewHolder(@NonNull ItemInmuebleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Inmueble inmueble) {
            binding.tvDireccion.setText(inmueble.getDireccion());
            //binding.tvPrecio.setText("$" + inmueble.getPrecio());
            binding.tvPrecio.setText("$" + inmueble.getValor());
            Glide.with(binding.getRoot())
                    .load(ApiClient.URL_BASE + inmueble.getImagen())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.ivFotoInmueble);

            binding.itemInmueble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("inmueble", inmueble);
                    Navigation
                            .findNavController((Activity) v.getContext(), R.id.nav_host_fragment_content_main)
                            .navigate(R.id.detalleInmuebleFragment, bundle);
                }
            });
        }
    }
}
