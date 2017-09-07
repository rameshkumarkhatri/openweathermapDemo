package com.ramesh.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {
    @BindView(R.id.pb)
    ProgressBar progressBar;
    @BindView(R.id.btn_as_guest)
    Button btnGuest;

    @BindView(R.id.fullscreen_content)
     View mContentView;
     CallbackManager callbackManager;
     LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initViews();
        fbFunctionality();
        FrontEngine.printKeyHash(this);
    }

    @OnClick(R.id.btn_as_guest)
    public void guestClick(View view) {
        navigateAfterDelay(0);
    }

    /**
     * This method will initialize the views
     */
    private void initViews() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    /**
     * This method will start main activity after specific milisecond seconds delay.
     *
     * @param delay
     */
    private void navigateAfterDelay(int delay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }, delay);
    }

    /**
     * This function will check for Facebook access token whether it exists and not expired then it will get detail from facebook and proceed further
     */
    public void fbFunctionality() {


        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btn_fb);
        loginButton.setReadPermissions("email");

        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            getUserDetails();

            return;
        }


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                if (loginResult != null) {

                    getUserDetails();
                }
            }

            @Override
            public void onCancel() {
                // App code

                Toast.makeText(Splash.this, "Facebook authenticatoin canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(Splash.this, "Facebook request exception occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This method will get data from Facebook Graph API and saves in user model, which will be use later
     */
    public void getUserDetails() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
        btnGuest.setVisibility(View.GONE);
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.v("LoginActivity Response ", response.toString());
                        FrontEngine.user = new User();

                        if (object != null) {

                            try {
                                if (object.has("name"))
                                    FrontEngine.user.setFullname(object.getString("name"));
                                if (object.has("email"))
                                    FrontEngine.user.setEmail(object.getString("email"));
                                if (object.has("id"))
                                    FrontEngine.user.setFBId(object.getString("id"));
                                if (object.has("picture")) {
                                    JSONObject jsonObject = object.getJSONObject("picture");
                                    if (jsonObject.has("data")) {
                                        JSONObject data = jsonObject.getJSONObject("data");
                                        if (data.has("url")) {
                                            FrontEngine.user.setImageURL(data.getString("url"));
                                        }
                                    }

                                }
                                FrontEngine.user.setFbAccessToken(AccessToken.getCurrentAccessToken().getToken());

                                Toast.makeText(Splash.this, "Facebook authenticated successfully", Toast.LENGTH_SHORT).show();


                                navigateAfterDelay(0);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Splash.this, "Facebook request exception occured", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                        }


                    }
                });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,picture.type(large),about,link,email");
        request.setParameters(parameters);
        request.executeAsync();


    }

    /**
     * this method will call the facebook callback manager to get the profile and authenticate
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
