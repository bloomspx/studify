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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studify.databinding.FragmentRoomListBinding;
import com.example.studify.viewmodel.UserViewModel;

import import androidx.recyclerview.widget.*;


public class RoomListFragment extends Fragment implements View.OnClickListener {
    private FragmentRoomListBinding binding;
    private UserViewModel UserViewModel;
    private NavController navController;
    RecyclerView recycleView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomListBinding.inflate(getLayoutInflater());
        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View view = inflater.inflate(R.fragment_room_list, container, false);
        String[] s = {"Hello", "World","yes"};
        recycleView = (RecyclerView) view.findViewById(R.id.roomsRecyclerView);
        RoomAdapter adapter = new RoomAdapter(getActivity(), s);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
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