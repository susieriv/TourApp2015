package com.kioube.tourapp.android.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kioube.tourapp.android.client.R;

/**
 * Created by pierrelouis on 07/05/2016.
 */
public class FacebookActivity extends Activity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    private Button button;

    // Passage des variables
    Intent returnIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnIntent = new Intent();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.connexion_activity);

        button = (Button) findViewById(R.id.button_back);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.v("facebook - FirstName", profile2.getFirstName());
                            Log.v("facebook - LastName", profile2.getLastName());
                            mProfileTracker.stopTracking();
                            returnIntent.putExtra("EXTRA_FIRST_NAME", profile2.getFirstName());
                            returnIntent.putExtra("EXTRA_LAST_NAME",profile2.getLastName());
                            returnIntent.putExtra("EXTRA_ID",profile2.getId());
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();

                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - FirstName", profile.getFirstName());
                    returnIntent.putExtra("EXTRA_FIRST_NAME", profile.getFirstName());
                    returnIntent.putExtra("EXTRA_LAST_NAME",profile.getLastName());
                    returnIntent.putExtra("EXTRA_ID",profile.getId());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
                Log.v("FacebookActivity", "cancel");
            }

            @Override
            public void onError(FacebookException e) {

                info.setText("Login attempt failed.");
                Log.v("FacebookActivity", e.getCause().toString());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
