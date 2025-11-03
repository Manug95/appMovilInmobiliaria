package com.example.appinmobiliaria.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import androidx.annotation.ColorInt;

import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.DialogMensajePersonalizadoBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Dialogo {
    private Context context;
    private DialogMensajePersonalizadoBinding dialogBinding;
    @ColorInt
    private int COLOR_ERROR;
    @ColorInt
    private int COLOR_EXITO;

    public Dialogo(Context context, LayoutInflater inflater) {
        this.context = context;
        this.dialogBinding = DialogMensajePersonalizadoBinding.inflate(inflater);
        COLOR_EXITO = context.getResources().getColor(R.color.success, null);
        COLOR_ERROR = context.getResources().getColor(R.color.error, null);
    }

    public void mostrarMensaje(String mensaje, DialogInterface.OnClickListener listener, boolean exito){
        dialogBinding.tvMensajeDialog.setText(mensaje);
        dialogBinding.tvMensajeDialog.setTextColor(exito ? COLOR_EXITO : COLOR_ERROR);

        if (listener == null) {
            listener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface di,int i){
                    di.dismiss();
                }
            };
        }

        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.titulo_dialog_mensaje)
                .setView(dialogBinding.getRoot())
                .setNeutralButton(R.string.dialog_ok, listener)
                .show();
    }

    public void mostrarMensaje(int mensaje, DialogInterface.OnClickListener listener, boolean exito) {
        dialogBinding.tvMensajeDialog.setText(mensaje);
        dialogBinding.tvMensajeDialog.setTextColor(exito ? COLOR_EXITO : COLOR_ERROR);

        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.titulo_dialog_mensaje)
                .setView(dialogBinding.getRoot())
                .setNeutralButton(R.string.dialog_ok, listener)
                .show();
    }

    public void mostrarPregunta(String pregunta, DialogInterface.OnClickListener listenerSi, DialogInterface.OnClickListener listenerNo){
        dialogBinding.tvMensajeDialog.setText(pregunta);

        if (listenerNo == null) {
            listenerNo = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface di,int i){
                    di.dismiss();
                }
            };
        }

        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.titulo_dialog_salir)
                .setView(dialogBinding.getRoot())
                .setPositiveButton(R.string.dialog_si, listenerSi)
                .setNegativeButton(R.string.dialog_no, listenerNo)
                .show();
    }

    public void mostrarPregunta(int pregunta, DialogInterface.OnClickListener listenerSi, DialogInterface.OnClickListener listenerNo){
        dialogBinding.tvMensajeDialog.setText(pregunta);

        if (listenerNo == null) {
            listenerNo = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface di,int i){
                    di.dismiss();
                }
            };
        }

        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.titulo_dialog_salir)
                .setView(dialogBinding.getRoot())
                .setPositiveButton(R.string.dialog_si, listenerSi)
                .setNegativeButton(R.string.dialog_no, listenerNo)
                .show();
    }
}
