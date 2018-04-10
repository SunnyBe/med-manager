package com.makarioworks.app.medmanager.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.makarioworks.app.medmanager.R;
import com.makarioworks.app.medmanager.ui.mainview.MainActivity;

/**
 * Created by ndu on 3/31/18.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    // Declare the Authentication object
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    // View objects
    private SignInButton signInButton;

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, ": On Start Called- Check initial User Login");
        // Check if user is signed in before creating the activity
        GoogleSignInAccount currentAccount = GoogleSignIn.getLastSignedInAccount(this);
        // Proceed if the current user is signed in
        if (currentAccount !=null){
            updateUI(currentAccount);
            Log.v(TAG, ": On Start Called- User Login found");
        }else {
            Log.v(TAG, ": On Start Called- User Login NOT found");
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        //signInButton = this.findViewById(R.id.sign_in_button);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build the Sign sign in client with the gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Listen for event on button
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "On activity Result: Requesting for login");

        // Result returned from launching the intent from GoogleSignInclient
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void updateUI(GoogleSignInAccount currentAccount) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra("user", currentAccount);
        startActivity(mainIntent);
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        // Set a new GoogleSignInAccount for the new user
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Update the UI
            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "SignInResult: Failed code= "+ e.getStatusCode());
            updateUI(null);
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button :
                signIn();
                break;
        }
    }
}
