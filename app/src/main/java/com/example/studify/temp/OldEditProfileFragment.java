//package com.example.studify.temp;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.navigation.Navigation;
//
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.example.studify.databinding.FragmentEditProfileBinding;
//import com.example.studify.models.UserProfile;
//import com.example.studify.viewmodel.UserViewModel;
//import com.example.studify.views.AuthActivity;
//import com.google.firebase.auth.FirebaseUser;
//
//public class OldEditProfileFragment extends Fragment implements View.OnClickListener {
//    private FragmentEditProfileBinding binding;
//    private UserViewModel UserViewModel;
//
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentEditProfileBinding.inflate(getLayoutInflater());
//        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//
//        return binding.getRoot();
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        binding.buttonUpdateProfile.setOnClickListener(this);
//        binding.buttonUpdateCredentials.setOnClickListener(this);
//        binding.buttonDelete.setOnClickListener(this);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.P)
//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        if (id == binding.buttonUpdateCredentials.getId()) {
//            String email = binding.changeEmail.getText().toString().trim();
//            String password = binding.changePassword.getText().toString().trim();
//            String cfmPassword = binding.changeConfirmpassword.getText().toString().trim();
//            if (TextUtils.isEmpty(email)) {
//                binding.changeEmail.setError("Email is Required!");
//                return;
//            } else if (TextUtils.isEmpty(password)) {
//                binding.changePassword.setError("Password is Required!");
//                return;
//            } else if (TextUtils.isEmpty(cfmPassword)) {
//                binding.changeConfirmpassword.setError("Confirm Password is Required!");
//                return;
//            } else if (!cfmPassword.equals(password)) {
//                binding.changePassword.setError("Please reconfirm your password, your password is not the same");
//                return;
//            } else {
//                // kiv: update profile
//                UserProfile user = new UserProfile.Builder()
//                        .setEmail(email)
//                        .setPassword(password)
//                        .build();
//                UserViewModel.updateProfile(user);
//                UserViewModel.logOut();
//            }
//        } else if (id == binding.buttonDelete.getId()) {
//            UserViewModel.deleteProfile();
//        } else if (id == binding.buttonUpdateProfile.getId()) {
//
//        }
//    }
//
//    // Redirects if Logout is Successful
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        // LiveData Observer
//        UserViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean loggedOut) {
//                if (loggedOut) {
//                    Intent i = new Intent(getActivity(), AuthActivity.class);
//                    startActivity(i);
//                }
//            }
//        });
//    }
//}
