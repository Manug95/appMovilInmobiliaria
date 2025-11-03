package com.example.appinmobiliaria.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.MainActivity;
import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.request.ApiClient;
import com.example.appinmobiliaria.ui.login.LoginActivity;
import com.example.appinmobiliaria.util.Dialogo;

public class LogoutFragment extends Fragment {

    private LogoutViewModel mViewModel;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        muestraDialog();
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    private void muestraDialog(){
        Dialogo dialogo = new Dialogo(getContext(),getLayoutInflater());
        dialogo.mostrarPregunta(R.string.pregunta_dialog_salir, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface di,int i){
                ApiClient.borrarToken(getContext());
                //getActivity().finish();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        },
                null);
        /*new AlertDialog.Builder(getContext())
                .setTitle(R.string.titulo_dialog_salir)
                .setMessage(R.string.pregunta_dialog_salir)
                .setPositiveButton(R.string.dialog_si,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        ApiClient.borrarToken(getContext());
                        //getActivity().finish();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })

                .setNegativeButton(R.string.dialog_no,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        //No hace nada
                    }
                }).show();
         */
    }

}