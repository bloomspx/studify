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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.studify.R;
import com.example.studify.databinding.FragmentEditProfileBinding;
import com.example.studify.models.UserProfile;
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
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.buttonDelete.getId()) {
            UserViewModel.deleteProfile();
        } else if (id == binding.changeProfileImage.getId() || id == binding.buttonChangePic.getId()) {
            // Load Images from External Phone Storage
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            MainActivityResultLauncher.launch(intent);
        } else if (id == binding.buttonUpdateProfile.getId()) {
            String name = String.valueOf(binding.changeUsername.getText()).trim();
            if (img == null) {
                Toast.makeText(getContext(), "Image is Required", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(name)) {
                binding.changeUsername.setError("Name is Required");
                return;
            }
            UserViewModel.updateProfile(new UserProfile.Builder()
                    .setImg(img.toString())
                    .setName(name)
                    .build());
            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_profileFragment);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // // Redirects if Logout is Successful
        UserViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut) {
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
                    binding.changeUsername.setHint(userProfile.getName());
                    if (userProfile.getImg() != null) {
                        updateProfilePicture(Uri.parse(userProfile.getImg()));
                    }
                    Log.d(TAG, "onChanged: userProfile is not empty, fields updated ");
                }
            }
        });

        // TODO: check if this is appropriate - Manages Opening Images and Switching Profile Picture
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