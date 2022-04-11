package com.example.studify.views;

import android.content.res.Resources;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

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
        binding.joinRoomButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == binding.createRoomButton.getId()) {
           // RoomViewModel.createRoom(room.getTasks_Lists());
            Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_taskListFragment);
        }
        if (id == binding.joinRoomButton.getId()) {
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;
            System.out.println("yes");

            View popupView = getLayoutInflater().inflate(R.layout.join_room_popup, null);
            PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            //close the popup window when cross is clicked
            ImageButton close = (ImageButton) popupView.findViewById(R.id.closeButton);
            Button join = (Button) popupView.findViewById(R.id.finalJoinRoom);
            close.setOnClickListener(new View.OnClickListener() {

                public void onClick(View popupView) {
                    popupWindow.dismiss();
                }
            });
            join.setOnClickListener(new View.OnClickListener() {

                public void onClick(View popupView) {
                    popupWindow.dismiss();

                    EditText taskName = (EditText) popupWindow.getContentView().findViewById(R.id.hashId);
                    String hashID = taskName.getText().toString();
                    //TODO implement join room()
                }
            });
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

    //private void joinRoom() {
        //String roomID = binding.hashId.getText().toString().trim();
        //if (TextUtils.isEmpty(roomID)) {
            //binding.hashId.setError("Email is Required");
       // }
       //else {
            //System.out.println("Hey There Doofus - 1");
            //RoomViewModel.joinRoom(roomID);
        //}
    //}



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
                //joinRoom();
            }
        });
        //binding.testButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_profileFragment);
            //}
       // });
    }
}