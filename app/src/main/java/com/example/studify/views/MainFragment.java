package com.example.studify.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.studify.R;
import com.example.studify.databinding.FragmentMainBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.example.studify.viewmodel.MainActivityViewModel;

public class MainFragment extends Fragment{
    private FragmentMainBinding binding;
    private MainActivityViewModel MainActivityViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        MainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}
