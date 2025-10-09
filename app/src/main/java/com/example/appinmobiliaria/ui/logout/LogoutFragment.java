package com.example.appinmobiliaria.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.request.ApiClient;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        // TODO: Use the ViewModel
    }

    private void muestraDialog(){
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.titulo_dialog_salir)
                .setMessage(R.string.pregunta_dialog_salir)
                .setPositiveButton(R.string.dialog_si,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        ApiClient.borrarToken(getContext());
                        getActivity().finish();
                    }
                })

                .setNegativeButton(R.string.dialog_no,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        //No hace nada
                    }
                }).show();
    }

}