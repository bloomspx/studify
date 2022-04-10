package com.example.studify.views;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.example.studify.databinding.FragmentRoomListBinding;
import com.example.studify.viewmodel.UserViewModel;

import com.example.studify.R;


public class RoomListFragment extends Fragment implements View.OnClickListener {
    private FragmentRoomListBinding binding;
    private UserViewModel UserViewModel;
    private NavController navController;
    RecyclerView recycleView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        String[] s = {"Hello", "World","yes"};
        recycleView = binding.roomsRecyclerView;
        RoomAdapter adapter = new RoomAdapter(getActivity(), s);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        return view;

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
        if (id == binding.joinRoomButton.getId()) {
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;

            View popupView = getLayoutInflater().inflate(R.layout.join_room_popup, null);
            PopupWindow popupWindow = new PopupWindow(popupView, width, height+400, true);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            //close the popup window when cross is clicked
            ImageButton close = (ImageButton) popupView.findViewById(R.id.closeButton);
            close.setOnClickListener(new View.OnClickListener() {

                public void onClick(View popupView) {
                    popupWindow.dismiss();
                }
            });
        }



        if (id == binding.createRoomButton.getId()) {
            //TODO: Fill in with new CreateRoom fragment
        }

    }

    // Navigator Instantiation
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}