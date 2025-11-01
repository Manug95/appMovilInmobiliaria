package com.example.appinmobiliaria.ui.login;

import static android.Manifest.permission.CALL_PHONE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appinmobiliaria.MainActivity;
import com.example.appinmobiliaria.databinding.ActivityLoginBinding;

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
        new AlertDialog.Builder(this)
            .setTitle("Llamada de emergencia")
            .setMessage("¿Desea llamar a la inmobiliaria?")
            .setPositiveButton("Llamar", (dialog, which) -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "2664553344"));
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
                // Comprobar si tenemos permiso para llamar
                /*if (checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "2664553344"));
                    startActivity(intent);
                } else {
                    // Si no tenemos permiso, lo solicitamos. La llamada se hará en onRequestPermissionsResult
                    requestPermissions(new String[]{CALL_PHONE}, 2000);
                }*/
            })
            .setNegativeButton("Cancelar", null)
            .show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "2664553344"));
                //startActivity(intent);
                //Toast.makeText(this, "Permiso concedido. Realizando llamada...", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "Permiso de llamada denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}