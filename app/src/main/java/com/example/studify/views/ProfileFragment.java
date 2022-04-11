package com.example.studify.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.studify.R;
import com.example.studify.databinding.FragmentProfileBinding;
import com.example.studify.models.UserProfile;
import com.example.studify.viewmodel.UserViewModel;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentProfileBinding binding;
    private UserViewModel UserViewModel;
    private String TAG = "PROFILE";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        System.out.println("In Profile Fragment");
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
            UserViewModel.logOut();
            Log.i("SUCCESS", "Logged Out");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // LiveData Observer for LogOut - if successful, navigate back to AuthActivity
        UserViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut) {
                    Toast.makeText(getContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), AuthActivity.class);
                    startActivity(i);
                }
            }
        });

        // LiveData Observer for UserProfile
        UserViewModel.getUserProfileLiveData().observe(getViewLifecycleOwner(), new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                if (userProfile != null) {
                    binding.username.setText(userProfile.getName());
                    updateProfilePicture(userProfile);
                    Log.d(TAG, "onChanged: userProfile is not empty, fields updated ");
                }
            }
        });

    }

    private void updateProfilePicture(UserProfile userProfile) {
        String img = userProfile.getImg();
        if (img != null) {
            Glide.with(this)
                    .load(Uri.parse(img))
                    .into(binding.profileImage);
        }
    }
}