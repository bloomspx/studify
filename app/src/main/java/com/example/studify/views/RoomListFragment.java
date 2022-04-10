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

import com.example.studify.R;
import com.example.studify.databinding.FragmentRoomListBinding;
import com.example.studify.models.RoomModel;
import com.example.studify.viewmodel.UserViewModel;
import com.example.studify.viewmodel.RoomViewModel;


public class RoomListFragment extends Fragment implements View.OnClickListener {
    private FragmentRoomListBinding binding;
    private UserViewModel UserViewModel;
    private NavController navController;
    private RoomViewModel RoomViewModel;
    private RoomModel room;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomListBinding.inflate(getLayoutInflater());
        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        RoomViewModel = new ViewModelProvider(this).get(com.example.studify.viewmodel.RoomViewModel.class);

        //room = new ViewModelProvider(this).get(room)

        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.createRoomButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == binding.createRoomButton.getId()) {
           // RoomViewModel.createRoom(room.getTasks_Lists());
            Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_taskListFragment);
        }
//        else if(id == binding.joinRoomButton.getId())
//        {
//            System.out.println("HERE***********");
//        }
//        else if(id == binding.joinRoomButton.getId())
//        {
//            System.out.println("HEY");
//            //System.out.println("HEREERERE");
//            joinRoom();
//        }
    }
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

        binding.createRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_taskListFragment);
            }
        });
        binding.joinRoomButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                System.out.println("meow");
                joinRoom();
            }
        });
        binding.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_profileFragment);
            }
        });
    }
}