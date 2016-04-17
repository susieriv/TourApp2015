package com.kioube.tourapp.android.client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kioube.tourapp.android.client.R;
import com.kioube.tourapp.android.client.ui.domain.ActionItem;

import java.util.List;

/**
 * @author pierrelouis on 15/04/2016.
 */
public class AuthentificationFragment extends FragmentBase {

    /* --- Constants --- */

    private static final String LOG_TAG = AuthentificationFragment.class.getSimpleName();

	/* --- Fields --- */

    private View view;
    private TextView info;
    private List<ActionItem> actionItemList;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>(){

           @Override
            public void onSuccess(LoginResult loginResult) {
               AccessToken accessToken = loginResult.getAccessToken();
               Profile profile = Profile.getCurrentProfile();
               if(profile != null){
                   info.setText("Welcome "+profile.getName());
               }

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }

    };

    /*
     * (non-Javadoc)
     *
     * @see android.app.Fragment#getView()
     */
    @Override
    public View getView() {
        return super.getView() != null ? super.getView() : this.view;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager=CallbackManager.Factory.create();
    }


    /*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_authentification, container, false);
        return view;
    }


    /*
 * (non-Javadoc)
 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
 */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        //loginButton.setFragment(this);
        loginButton.setFragment(new NativeFragmentWrapper(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    /*
     * (non-Javadoc)
     *
     * @see android.app.Fragment#onDestroyView()
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
