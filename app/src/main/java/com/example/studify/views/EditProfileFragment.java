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
import com.example.studify.databinding.FragmentEditProfileBinding;
import com.example.studify.databinding.FragmentProfileBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.example.studify.viewmodel.MainActivityViewModel;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentEditProfileBinding binding;
    private MainActivityViewModel MainActivityViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater());
        MainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.buttonChangePic.setOnClickListener(this);
        binding.buttonChangeEmail.setOnClickListener(this);
        binding.buttonChangePassword.setOnClickListener(this);
        binding.buttonDelete.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.buttonChangeEmail.getId()) {
            String email = binding.changeEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                binding.changeEmail.setError("Email is Required!");
            } else {
                MainActivityViewModel.changeEmail(email);
                MainActivityViewModel.logOut();
            }
        } else if (id == binding.buttonChangePassword.getId()) {
            String password = binding.changePassword.getText().toString().trim();
            if (TextUtils.isEmpty(password)) {
                binding.changePassword.setError("Email is Required!");
            } else {
                MainActivityViewModel.changePassword(password);
                MainActivityViewModel.logOut();
            }
        } else if (id == binding.buttonDelete.getId()) {
            MainActivityViewModel.deleteProfile();
            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_authActivity);
        } else if (id == binding.buttonChangePic.getId()) {
            //TODO: implement update profile pic feature
            // MainActivityViewModel.changeProfilePic();
        }
    }

    // Redirects if Logout is Successful
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // LiveData Observer
        MainActivityViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut) {
                    Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_authActivity);
                }
            }
        });
    }
}
