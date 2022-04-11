package com.example.studify.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.studify.databinding.FragmentMainBinding;
import com.example.studify.viewmodel.UserViewModel;

public class MainFragment extends Fragment{
    private FragmentMainBinding binding;
    private UserViewModel UserViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}
