package com.example.appinmobiliaria.ui.contrato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinmobiliaria.databinding.ItemPagoBinding;
import com.example.appinmobiliaria.modelos.Pago;

import java.text.DateFormat;
import java.util.List;

import java.time.format.DateTimeFormatter;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {
    private List<Pago> listaPagos;
    private Context context;
    private LayoutInflater layoutInflater;

    public PagoAdapter(List<Pago> listaPagos, Context context, LayoutInflater layoutInflater) {
        this.listaPagos = listaPagos;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPagoBinding binding = ItemPagoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PagoAdapter.PagoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago pago = listaPagos.get(position);
        holder.bind(pago);
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class  PagoViewHolder extends RecyclerView.ViewHolder {
        private ItemPagoBinding binding;
        private DateTimeFormatter formatter;

        public PagoViewHolder(ItemPagoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        public void bind(Pago pago) {
            binding.tvNroPago.setText(String.valueOf(pago.getId()));
            binding.tvFechaPago.setText(pago.getFecha().format(formatter));
            binding.tvImportePago.setText(String.valueOf(pago.getImporte()));
            binding.tvDetallePago.setText(pago.getDetalle() == null ? "Sin Detalle" : pago.getDetalle());
        }
    }

}
