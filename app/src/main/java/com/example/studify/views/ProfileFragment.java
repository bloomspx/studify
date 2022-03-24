package com.example.studify.views;

import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.studify.R;
import com.example.studify.databinding.FragmentForgotPasswordBinding;
import com.example.studify.databinding.FragmentLoginBinding;
import com.example.studify.databinding.FragmentProfileBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.example.studify.viewmodel.MainActivityViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private FragmentProfileBinding binding;
    private MainActivityViewModel MainActivityViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        MainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.buttonEditProfile.setOnClickListener(this);
        binding.buttonLogOut.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.buttonEditProfile.getId()) {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment);
        } else if (id == binding.buttonLogOut.getId()) {
            MainActivityViewModel.logOut();
            Log.i("SUCCESS", "Logged Out");
        }
    }

//     Redirects if Logout is Successful
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // LiveData Observer - if succesful, navigate back to AuthActivity
        MainActivityViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut) {
                    Toast.makeText(getContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), AuthActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    // TODO: Implement Display Username and Profile Picture

}