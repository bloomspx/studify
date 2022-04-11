package com.example.studify.views;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.studify.R;
import com.example.studify.databinding.FragmentForgotPasswordBinding;
import com.example.studify.viewmodel.LoginViewModel;

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener{
    private FragmentForgotPasswordBinding binding;
    private LoginViewModel LoginViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(getLayoutInflater());
        LoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.buttonResetPassword.setOnClickListener(this);
        binding.buttonBack.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.buttonResetPassword.getId()) {
            reset_password();
            Navigation.findNavController(view).navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
        } else if (id == binding.buttonBack.getId()) {
            Navigation.findNavController(view).navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void reset_password() {
        String email = binding.registerEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            binding.registerEmail.setError("Email is Required!");
        } else {
            LoginViewModel.resetPassword(email);
        }
    }

}