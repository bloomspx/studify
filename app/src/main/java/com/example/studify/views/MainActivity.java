package com.example.studify.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.studify.R;
import com.example.studify.databinding.ActivityMainBinding;
import com.example.studify.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SUCCESSFUL";
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private NavController navController;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, binding.getRoot().getId());
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        addDynamicFragment();
        setBottomView();

    }

    // INPROGRESS - Implements Dynamic Fragment Container Switching
    public void addDynamicFragment() {
        Fragment newFragment = new MainFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment).commit();
        Log.d(TAG, "onStart: ");
    }

    // INPROGRESS - NAVBAR
    public void setBottomView() {
        binding.bottomNavView.setOnItemSelectedListener( item -> {
          switch (item.getItemId()) {
              case R.id.mainFragment:
                  // TODO: check if MainActivity is currently displayed?
                  transaction.replace(R.id.nav_host_fragment, MainFragment.class, null);
                  // transaction.addToBackStack(null);
                  break;
              case R.id.roomListFragment:
                  transaction.replace(R.id.nav_host_fragment, RoomListFragment.class, null);
                  // transaction.addToBackStack(null);
                  break;
              case R.id.profileFragment:
                  transaction.replace(R.id.nav_host_fragment, ProfileFragment.class, null);
                  // transaction.addToBackStack(null);
                  break;
          }
            return true;
        });
    }



//    // Display Menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.bottom_navigation_menu, menu);
//        return true;
//    }


    // TODO: exit app with back button

}