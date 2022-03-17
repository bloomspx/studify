package com.example.studify.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studify.databinding.ActivityAuthBinding;
import com.example.studify.viewmodel.LoginViewModel;

public class AuthActivity extends AppCompatActivity {
    private ActivityAuthBinding binding;
    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
