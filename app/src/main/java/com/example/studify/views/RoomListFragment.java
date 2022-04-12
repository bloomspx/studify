package com.example.studify.views;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.text.TextUtils;
import android.util.Log;
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
import com.example.studify.databinding.DialogJoinRoomBinding;
import com.example.studify.models.RoomModel;
import com.example.studify.viewmodel.UserViewModel;
import com.example.studify.viewmodel.RoomViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class RoomListFragment extends Fragment implements View.OnClickListener {
    private FragmentRoomListBinding binding;
    private UserViewModel UserViewModel;
    private NavController navController;
    private RoomViewModel RoomViewModel;
    private RoomModel room;
    private FirebaseAuth firebaseAuth;
    private com.google.firebase.firestore.FieldValue FieldValue;
    private FirebaseFirestore db;
    private static final String TAG = "RoomListFragment";
    private DocumentReference docRef;

    public RoomListFragment() {

        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentRoomListBinding.inflate(getLayoutInflater());

        UserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        RoomViewModel = new ViewModelProvider(this).get(com.example.studify.viewmodel.RoomViewModel.class);

        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.createRoomButton.setOnClickListener(this);
        binding.joinRoomButton.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void onClick(View view) {
        int id = view.getId();

        if (id == binding.createRoomButton.getId()) {
            Log.i(TAG, "Create Room Button Clicked");
            Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_taskListFragment);
        }
        if (id == binding.joinRoomButton.getId()) {
            db = FirebaseFirestore.getInstance();
            String roomID = binding.hashId.getText().toString().trim();
            if (TextUtils.isEmpty(roomID)) {
                Log.w(TAG, "Invalid ID enter");
                binding.hashId.setError("ID is required");

            } else {
                Log.i(TAG, "Join Room Activated");

                docRef = db.collection("rooms").document(roomID);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                db.collection("rooms").document(document.getId()).update("user_IDs", FieldValue.arrayUnion(firebaseAuth.getCurrentUser().getUid()));
                                db.collection("rooms").document(document.getId()).update("roomUserCount", FieldValue.increment(1));
                                Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_roomFragment);


                            } else {
                                binding.hashId.setError("Document does not exist");
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            binding.hashId.setError("Request Failed:" + task.getException());
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        }
    }
}








    /* Comments : This is a Dump Space for reduntant code


     */
     /*   else {
            final boolean[] roomexists = {false};
            System.out.println("Detects Join Room Button Click");
            AlertDialog.Builder myDialog = new AlertDialog.Builder(getContext());
            DialogJoinRoomBinding dialogBinding = DialogJoinRoomBinding.inflate(getLayoutInflater());
            myDialog.setView(dialogBinding.getRoot());

            final AlertDialog dialog = myDialog.create();
            dialog.setCancelable(false);

            dialog.show();

            dialogBinding.finalJoinRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("HERE SWEETIE");
                    db = FirebaseFirestore.getInstance();
                    System.out.println("**********Inside Join Room Button**********");
                    String roomID = dialogBinding.hashId.getText().toString().trim();
                    if (TextUtils.isEmpty(roomID)) {
                        dialogBinding.hashId.setError("ID is required");
                    } else {
                        System.out.println("**********Inside Join Room **********");

                        docRef = db.collection("rooms").document(roomID);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        db.collection("rooms").document(document.getId()).update("user_IDs", FieldValue.arrayUnion(firebaseAuth.getCurrentUser().getUid()));
                                        db.collection("rooms").document(document.getId()).update("roomUserCount", FieldValue.increment(1));
                                        Bundle bundle = new Bundle();
                                        bundle.putString("roomID", roomID);
                                        roomexists[0] = true;
                                        Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_roomFragment);



                                        // Navigation.findNavController(view).navigate(R.id.action_roomListFragment_to_roomFragment);

                                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    } else {
                                        dialogBinding.hashId.setError("Document does not exist");
                                        //Log.d(TAG, "No such document");
                                    }
                                } else {
                                    //dialogBinding.hashId.setError("Request Failed:" + task.getException());
                                    //dLog.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                    }
//                    dialog.show();

                }
            });


        }
        //@RequiresApi(api = Build.VERSION_CODES.P)
    //@Override
    /*public void onClick(View view) {
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
                    //Navigation.n
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
                //String roomID = binding.
                //joinRoom();
            }
        });

    }
}






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

      */


