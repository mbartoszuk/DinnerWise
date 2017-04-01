package com.bartoszuk.dinnerwise.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.login.LoginActivity;
import com.bartoszuk.dinnerwise.activity.secondarymenu.AboutActivity;
import com.bartoszuk.dinnerwise.activity.secondarymenu.FeedbackActivity;
import com.bartoszuk.dinnerwise.activity.secondarymenu.PreferencesActivity;
import com.bartoszuk.dinnerwise.activity.secondarymenu.SettingsActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Maria Bartoszuk on 22/03/2017.
 */
public abstract class LogOut extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Collect the email address of the user logging in.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    // Sign out the user and move the app to the login screen.
    public void signOutLoggedUser(MenuItem item) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent intent = new Intent(LogOut.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
    }

    // Implement Preferences opening here because of the inheritance of other activities from LogOut.
    public void openPreferencesActivity(MenuItem item) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    // Implement Settings opening here because of the inheritance of other activities from LogOut.
    public void openSettingsActivity(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    // Implement About opening here because of the inheritance of other activities from LogOut.
    public void openAboutActivity(MenuItem item) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    // Implement Feedback opening here because of the inheritance of other activities from LogOut.
    public void openFeedbackActivity(MenuItem item) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // TODO: Display error message?
    }
}
