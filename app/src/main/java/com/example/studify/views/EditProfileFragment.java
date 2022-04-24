package com.example.studify.views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.studify.R;
import com.example.studify.databinding.FragmentEditProfileBinding;
import com.example.studify.models.UserProfileModel;
import com.example.studify.viewmodel.UserViewModel;

public class EditProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentEditProfileBinding binding;
    private UserViewModel UserViewModel;
    private ActivityResultLauncher<Intent> MainActivityResultLauncher;
    private Uri img;
    private String TAG = "EDIT_PROFILE";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater());
        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.buttonUpdateProfile.setOnClickListener(this);
        binding.buttonDelete.setOnClickListener(this);
        binding.buttonChangePic.setOnClickListener(this);
        binding.changeProfileImage.setOnClickListener(this);
        binding.backButtonProfile.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.buttonDelete.getId()) {
            UserViewModel.deleteProfile();
        } else if (id == binding.backButtonProfile.getId()) {
            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_profileFragment);
        } else if (id == binding.changeProfileImage.getId() || id == binding.buttonChangePic.getId()) {
            // Load Images from External Phone Storage
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            MainActivityResultLauncher.launch(intent);
        } else if (id == binding.buttonUpdateProfile.getId()) {
            String name = String.valueOf(binding.changeUsername.getText()).trim();
            if (img == null && TextUtils.isEmpty(name)) {

            } else if (img == null) {
                UserViewModel.updateProfile(new UserProfileModel.Builder()
                        .setName(name)
                        .setImg(null)
                        .build());
            } else if (TextUtils.isEmpty(name)) {
                UserViewModel.updateProfile(new UserProfileModel.Builder()
                        .setName(null)
                        .setImg(img.toString())
                        .build());
            } else {
                UserViewModel.updateProfile(new UserProfileModel.Builder()
                        .setImg(img.toString())
                        .setName(name)
                        .build());
            }
            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_profileFragment);
        }
    }


        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            // Redirects if Logout is Successful
            UserViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean loggedOut) {
                    if (loggedOut) {
                        Intent i = new Intent(getActivity(), AuthActivity.class);
                        startActivity(i);
                    }
                }
            });

            // LiveData Observer for UserProfileModel
            UserViewModel.getUserProfileLiveData().observe(getViewLifecycleOwner(), new Observer<UserProfileModel>() {
                @Override
                public void onChanged(UserProfileModel userProfileModel) {
                    if (userProfileModel != null) {
                        binding.changeUsername.setHint(userProfileModel.getName());
                        if (userProfileModel.getImg() != null) {
                            updateProfilePicture(Uri.parse(userProfileModel.getImg()));
                        }
                        Log.d(TAG, "onChanged: userProfileModel is not empty, fields updated ");
                    }
                }
            });

            // ActivityResultLauncher - Manages Opening Images and Switching Profile Picture
            MainActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result ->  {
                        if(result.getResultCode() == Activity.RESULT_OK) { // no need for request code
                            Intent data = result.getData();
                            img = data.getData();
                            updateProfilePicture(img);
                        }
                    });
        }


    // Updates Profile Picture with Glide
    private void updateProfilePicture(Uri img) {
        if (img != null) {
            Glide.with(this)
                    .load(img)
                    .into(binding.changeProfileImage);
        }
    }
}