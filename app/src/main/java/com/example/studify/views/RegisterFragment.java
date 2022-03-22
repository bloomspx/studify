package com.example.studify.views;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.studify.R;
import com.example.studify.databinding.FragmentLoginBinding;
import com.example.studify.databinding.FragmentRegisterBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    private FragmentRegisterBinding binding;
    private LoginViewModel LoginViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        LoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.signUpButton.setOnClickListener(this);
        binding.backButtonSignUp.setOnClickListener(this);
        binding.signUpGoogleButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.signUpButton.getId()) {
            register();
        } else if (id == binding.backButtonSignUp.getId()) {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        }
    }

    // Register Function - Firebase
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void register() {
        String name = binding.registerUsername.getText().toString();
        String email = binding.registerEmail.getText().toString();
        String password = binding.registerPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            binding.registerEmail.setError("Email is Required");
        } else if (TextUtils.isEmpty(password)) {
            binding.registerPassword.setError("Password is Required");
        } else if (TextUtils.isEmpty(name)) {
            binding.registerUsername.setError("Username is Required");
        } else {
            LoginViewModel.register(name, email, password);
        }
    }

    // Redirects if Registration is Successful
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // LiveData Observer - If registered, redirect to main page
        LoginViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_navgraph);
                }
            }
        });
    }
}