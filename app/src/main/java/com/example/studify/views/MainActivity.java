package com.example.studify.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.studify.R;
import com.example.studify.databinding.ActivityMainBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.example.studify.viewmodel.MainActivityViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SUCCESSFUL";
    private ActivityMainBinding binding;
    private MainActivityViewModel MainActivityViewModel;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Creates link for navigation between NavBar and Fragment
        NavHostFragment navHostFragment = ((NavHostFragment) (getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment)));
        NavigationUI.setupWithNavController(binding.bottomNavView, navHostFragment.getNavController());

        // LiveData Observer - If registered, redirect to main page
        MainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        MainActivityViewModel.getLoggedOutLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                    startActivity(intent);
                    Log.i("SUCCESS", "MainActivityData has been Changed");
                } else {
                    Log.i("FAIL", "Not Logged Out");
                }
            }
        });
    }

}




    // TODO: exit app with back button
