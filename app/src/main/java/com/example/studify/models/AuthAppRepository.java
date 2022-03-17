package com.example.studify.models;

import android.app.Application;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthAppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    public AuthAppRepository(Application application) {
        this.application = application;

        // Authenticate User
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        loggedOutLiveData = new MutableLiveData<>();

        // Checks if User is logged in
        if (firebaseAuth.getCurrentUser() == null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }
    // TODO: Check if getMainExecutor is needed
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void resetPassword(String email) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(application, "Reset Password Email has been sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(application, "Reset Password Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logOut () {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    public void changeEmail(String email) {
       user = firebaseAuth.getInstance().getCurrentUser();
       user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(application, "Email Address Updated.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(application, "Email Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void changePassword(String password) {
        user = firebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(application, "Password Updated.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(application, "Password Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteProfile() {
        user = firebaseAuth.getInstance().getCurrentUser();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(application, "Account Deleted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(application, "Account Deletion Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Getter for MutableLiveData to access via ViewModels
    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

}
