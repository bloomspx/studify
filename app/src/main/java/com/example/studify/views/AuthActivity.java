package com.example.studify.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.studify.databinding.ActivityAuthBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    private ActivityAuthBinding binding;
    private LoginViewModel LoginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // LiveData Observer - If registered, redirect to main page
        LoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        LoginViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null) {
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.i("FAIL", "null" );
                }
            }
        });


    }

}
