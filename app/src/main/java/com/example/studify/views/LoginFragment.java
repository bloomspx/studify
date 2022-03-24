package com.example.studify.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.studify.R;
import com.example.studify.databinding.FragmentLoginBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private FragmentLoginBinding binding;
    private LoginViewModel LoginViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        LoginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.buttonLogin.setOnClickListener(this);
        binding.buttonRegister.setOnClickListener(this);
        binding.buttonForgetPassword.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.buttonRegister.getId()) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        } else if (id == binding.buttonLogin.getId()) {
            login();
        } else if (id == binding.buttonForgetPassword.getId()) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
        }
    }

    // Login Function - Firebase
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void login() {
        String email = binding.loginEmail.getText().toString().trim();
        String password = binding.loginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            binding.loginEmail.setError("Email is Required");
        } else if (TextUtils.isEmpty(password)) {
            binding.loginPassword.setError("Password is Required");
        } else {
            LoginViewModel.login(email, password);
        }
    }


}