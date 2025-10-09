package com.example.appinmobiliaria.ui.login;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.appinmobiliaria.MainActivity;
import com.example.appinmobiliaria.R;
import com.example.appinmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etMailLogin.getText().toString();
                String password = binding.etPasswordLogin.getText().toString();
                viewModel.login(email, password);
            }
        });

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

        viewModel.comprobarSesion();

        setContentView(binding.getRoot());
    }

    private void iniciarMainActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}