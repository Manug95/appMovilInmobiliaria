package com.example.appinmobiliaria.ui.login;

import static android.Manifest.permission.CALL_PHONE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appinmobiliaria.BuildConfig;
import com.example.appinmobiliaria.MainActivity;
import com.example.appinmobiliaria.databinding.ActivityLoginBinding;
import com.example.appinmobiliaria.util.Dialogo;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);

        solicitarPermisos();

        viewModel.comprobarSesion();

        viewModel.getMLoginCorrecto().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                iniciarMainActivity();
                Toast.makeText(getApplication(), s, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        viewModel.getMLoginIncorrecto().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoginIncorrecto.setText(s);
            }
        });

        viewModel.getmEstaLogueado().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                iniciarMainActivity();
                finish();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvLoginIncorrecto.setText("");
                String email = binding.etMailLogin.getText().toString();
                String password = binding.etPasswordLogin.getText().toString();
                viewModel.login(email, password);
            }
        });

        setContentView(binding.getRoot());
    }

    private void iniciarMainActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void mostrarDialogoDeLlamada() {
        Dialogo dialogo = new Dialogo(this, getLayoutInflater());
        dialogo.mostrarPregunta("¿Desea llamar a la inmobiliaria?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    iniciarLlamada();
                    dialog.dismiss();
                } else {
                    solicitarPermisos();
                }

            }
        }, null);
    }

    public void solicitarPermisos(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{CALL_PHONE},2000);
        }
    }

    @Override
    protected void onResume() {
        viewModel.activarSensorAcelerometro(new LoginActivityViewModel.ManejadorAcelerometro.OnShakeListener() {
            @Override
            public void onShake(int count) {
                mostrarDialogoDeLlamada();
            }
        });
        super.onResume();
    }

    @Override
    protected void onPause() {
        viewModel.desactivarSensor();
        super.onPause();
    }

    private void iniciarLlamada() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + BuildConfig.TELEFONO));
        startActivity(intent);
    }
}