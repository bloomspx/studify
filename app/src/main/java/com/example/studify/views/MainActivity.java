package com.example.studify.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.studify.R;
import com.example.studify.databinding.ActivityMainBinding;
import com.example.studify.databinding.FragmentLoginBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.example.studify.viewmodel.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}