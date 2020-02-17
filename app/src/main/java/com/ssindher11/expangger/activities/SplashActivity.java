package com.ssindher11.expangger.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssindher11.expangger.R;
import com.ssindher11.expangger.Utils;
import com.ssindher11.expangger.models.User;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar pb;

    private SharedPreferences sharedPreferences;

    public static final int RC_SIGN_IN = 1008;
    public static final String TAG = "Login Result";

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);

        MaterialButton loginBtn = findViewById(R.id.btn_login);
        pb = findViewById(R.id.pb_splash);

        if (sharedPreferences.contains("name")) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            loginBtn.setVisibility(View.VISIBLE);
            loginBtn.setOnClickListener(v -> signIn());
        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Sign in Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        pb.setVisibility(View.VISIBLE);

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            handleSignin(user);
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Utils.showErrorSnackbar(findViewById(android.R.id.content).getRootView(), "Authentication Failed!");
                    }

                    pb.setVisibility(View.GONE);
                });
    }

    private void handleSignin(FirebaseUser firebaseUser) {
        final DatabaseReference myRef = mDatabase.getReference().child("users").child(firebaseUser.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        //existing user
                        User user = dataSnapshot.getValue(User.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", firebaseUser.getDisplayName());
                        editor.putString("uid", firebaseUser.getUid());
                        editor.putString("email", firebaseUser.getEmail());
                        if (firebaseUser.getPhotoUrl() != null && firebaseUser.getPhotoUrl().toString().length() != 0) {
                            editor.putString("photo", firebaseUser.getPhotoUrl().toString());
                        }
                        editor.apply();
                        /*Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        i.putExtra("user", user);
                        i.putExtra("type", "old");*/
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finishAfterTransition();
                    } else {
                        //new user
                        mDatabase.getReference().child("users").child(firebaseUser.getUid()).setValue(new User(firebaseUser.getDisplayName())).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Utils.makeSnackbar(findViewById(android.R.id.content).getRootView(), "Welcome to Expanager");
                                /*User user = new User(firebaseUser.getDisplayName());
                                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                i.putExtra("user", user);
                                i.putExtra("type", "new");*/
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finishAfterTransition();
                            } else
                                Utils.showErrorSnackbar(findViewById(android.R.id.content).getRootView(), "Some error occurred!");
                        });
                    }
                } catch (Exception e) {
                    Utils.showErrorSnackbar(findViewById(android.R.id.content).getRootView(), "Some error occurred");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, databaseError.getDetails());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
    }
}
