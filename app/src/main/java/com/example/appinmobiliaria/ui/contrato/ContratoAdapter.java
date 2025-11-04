package com.example.appinmobiliaria.ui.contrato;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.ItemInmuebleBinding;
import com.example.appinmobiliaria.modelos.Contrato;
import com.example.appinmobiliaria.modelos.Inmueble;
import com.example.appinmobiliaria.request.ApiClient;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder>{
    private List<Contrato> listaContratos;
    private Context context;
    private LayoutInflater layoutInflater;
    private ContratoAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Contrato contrato);
    }

    public ContratoAdapter(List<Contrato> listaContratos, Context context, LayoutInflater layoutInflater, ContratoAdapter.OnItemClickListener listener) {
        this.listaContratos = listaContratos;
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.listener = listener;
    }

    public ContratoAdapter(List<Contrato> listaInmuebles, Context context, LayoutInflater layoutInflater) {
        this.listaContratos = listaInmuebles;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ContratoAdapter.ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInmuebleBinding binding = ItemInmuebleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContratoAdapter.ContratoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ContratoViewHolder holder, int position) {
        Contrato contratoActual = listaContratos.get(position);
        holder.bind(contratoActual);
    }

    @Override
    public int getItemCount() {
        return listaContratos.size();
    }

    public class ContratoViewHolder extends RecyclerView.ViewHolder{
        private final ItemInmuebleBinding binding;

        public ContratoViewHolder(@NonNull ItemInmuebleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Contrato contrato) {
            Inmueble inmueble = contrato.getInmueble();
            String direccion = inmueble.getCalle() + " " + inmueble.getNroCalle();
            binding.tvDireccion.setText(direccion);
            binding.tvPrecio.setText("$" + inmueble.getPrecio());
            Glide.with(binding.getRoot())
                    .load(ApiClient.URL_BASE + inmueble.getFoto())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.ivFotoInmueble);

            binding.itemInmueble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(contrato);
                }
            });

        }
    }
}
