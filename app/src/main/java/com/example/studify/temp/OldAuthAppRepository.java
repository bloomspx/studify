//package com.example.studify.temp;
//
//import android.app.Application;
//import android.net.Uri;
//import android.os.Build;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.studify.models.UserProfile;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserProfileChangeRequest;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class OldAuthAppRepository {
//    private Application application;
//    private FirebaseAuth firebaseAuth;
//    private FirebaseUser user;
//    private FirebaseFirestore firebaseFirestore;
//    private MutableLiveData<FirebaseUser> userLiveData;
//    private MutableLiveData<Boolean> loggedOutLiveData;
//    private String userID;
//    private String TAG = "AUTHENTICATION";
//
//
//    public OldAuthAppRepository(Application application) {
//        this.application = application;
//
//        // Authenticate User
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        userLiveData = new MutableLiveData<>();
//        loggedOutLiveData = new MutableLiveData<>();
//
//        // Checks if User is logged in
//        if (firebaseAuth.getCurrentUser() != null) {
//            userLiveData.postValue(firebaseAuth.getCurrentUser());
//            loggedOutLiveData.postValue(false);
//        }
//    }
//
//    // kiv: Check if getMainExecutor is needed
//    @RequiresApi(api = Build.VERSION_CODES.P)
//    public void register(UserProfile user) {
//        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())    // Creates Account
//                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName(user.getName())
//                                    .setPhotoUri(Uri.parse("android.resource://com.example.studify/drawable/ic_default_profile"))
//                                    .build();
//                            firebaseAuth.getCurrentUser().updateProfile(profileUpdates);
//                            addUser(user);
//                            userLiveData.postValue(firebaseAuth.getCurrentUser());
//                        } else {
//                            Toast.makeText(application, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    // Adds user data to Firebase
//    public void addUser(UserProfile userData) {
//        userID = firebaseAuth.getCurrentUser().getUid();
//        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
//        Map<String, Object> user = new HashMap<>();
//        user.put("username", userData.getName());
//        user.put("email",userData.getEmail());
//        user.put("password", userData.getPassword());
//        user.put("photoUri", firebaseAuth.getCurrentUser().getPhotoUrl());
//        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Log.d(TAG, "onSuccess: userProfile is created for " + userID);
//            }
//        });
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.P)
//    public void login(String email, String password) {
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            userLiveData.postValue(firebaseAuth.getCurrentUser());
//                        } else {
//                            Toast.makeText(application, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.P)
//    public void resetPassword(String email) {
//        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(application, "Reset Password Email has been sent!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(application, "Reset Password Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    public void logOut () {
//        firebaseAuth.signOut();
//        loggedOutLiveData.postValue(true);
//    }
//
//    public void changeEmail(String email) {
//        user = firebaseAuth.getInstance().getCurrentUser();
//        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(application, "Email Address Updated.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(application, "Email Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    public void changePassword(String password) {
//        user = firebaseAuth.getInstance().getCurrentUser();
//        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(application, "Password Updated.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(application, "Password Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    public void deleteProfile() {
//        user = firebaseAuth.getInstance().getCurrentUser();
//        // kiv: delete from database
//        firebaseFirestore.collection("users").document(user.getUid())
//                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Log.d(TAG, "DocumentSnapshot successfully deleted!");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.w(TAG, "Error deleting document", e);
//            }
//        });
//        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(application, "Account Deleted.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(application, "Account Deletion Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//
//
//    // Getter for MutableLiveData to access via ViewModels
//    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
//        return userLiveData;
//    }
//
//    public MutableLiveData<Boolean> getLoggedOutLiveData() {
//        return loggedOutLiveData;
//    }
//
//}
//
