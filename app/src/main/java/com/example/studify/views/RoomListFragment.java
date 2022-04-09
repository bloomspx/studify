package com.example.studify.views;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studify.databinding.FragmentRoomListBinding;
import com.example.studify.viewmodel.LoginViewModel;
import com.example.studify.viewmodel.RoomViewModel;
import com.example.studify.viewmodel.UserViewModel;

public class RoomListFragment extends Fragment implements View.OnClickListener {
    private FragmentRoomListBinding binding;
    private UserViewModel UserViewModel;
    private NavController navController;
    private RoomViewModel RoomViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomListBinding.inflate(getLayoutInflater());
        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        RoomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.createRoomButton.setOnClickListener(this);
        binding.joinRoomButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.createRoomButton.getId()) {
<<<<<<< Updated upstream
            //TODO: Fill in with new CreateRoom fragment
=======
            System.out.println("*****************Hey I'm Here");
            RoomViewModel.createRoom();
            Navigation.findNavController(view).navigate(R.id.action_rooListFragment_to_taskListFragment);
>>>>>>> Stashed changes
        }
        else if(id == binding.joinRoomButton.getId())
        {
            joinRoom();
        }
    }

    // Login Function - Firebase
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void joinRoom() {
        String roomID = binding.hashId.getText().toString().trim();
        if (TextUtils.isEmpty(roomID)) {
            binding.hashId.setError("Email is Required");
        }
        else {
            System.out.println("Hey There Doofus - 1");
            RoomViewModel.joinRoom(roomID);
        }
    }

    // Navigator Instantiation
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}