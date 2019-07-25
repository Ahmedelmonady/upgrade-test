package com.example.testappwithoutfirebase;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.gameball.gameball.GameBallApp;
import com.gameball.gameball.model.request.Action;
import com.gameball.gameball.model.request.HoldPointBody;
import com.gameball.gameball.model.response.HoldPointsResponse;
import com.gameball.gameball.network.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText playerCategoryID;

    private GameBallApp gameBallApp;
    private EditText playerIDField;
    private Button registerBtn;
    private EditText challengeApiIdField;
    private EditText apiKeyField;
    private Button addChallengeIdBtn;
    private RecyclerView challengeApiIdsRecyclerview;
    private Button submitActionsBtn;
    private Button btnShowProfile;
    private Button changeLangBtn;
    private Button apiKeyBtn;

    private ChallengeApiIDAdapter adapter;
    private PopupMenu langPopupMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBallApp = GameBallApp.getInstance(MainActivity.this.getApplicationContext());
        adapter = new ChallengeApiIDAdapter(this, new ArrayList<String>());

        initView();
        prepView();
        changeLang(Locale.getDefault().getLanguage());


//        gameBallApp.generateOTP(new GenerateOTPBody("5sdfd2dvvd-9mnvhu25d6c3d"),
//                new Callback()
//                {
//                    @Override
//                    public void onSuccess(Object o)
//                    {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e)
//                    {
//
//                    }
//                });
    }

    private void daynamicLinkTest()
    {
        gameBallApp.isUserReferred(this, getIntent());
    }

    public void navigateToFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            String tag = fragment.getClass().getSimpleName();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);

            fragmentTransaction.replace(R.id.container, fragment, tag);

            fragmentTransaction.addToBackStack(tag);

            fragmentTransaction.commit();
        }
    }

    private void initView()
    {
        playerIDField = findViewById(R.id.player_id_filed);
        playerCategoryID = findViewById(R.id.playerCategoryID);
        registerBtn = findViewById(R.id.register_btn);
        challengeApiIdField = findViewById(R.id.challenge_api_id_field);
        addChallengeIdBtn = findViewById(R.id.add_challenge_id_btn);
        challengeApiIdsRecyclerview = findViewById(R.id.challenge_api_ids_recyclerview);
        submitActionsBtn = findViewById(R.id.submit_actions_btn);
        btnShowProfile = findViewById(R.id.btn_show_profile);
        changeLangBtn = findViewById(R.id.change_lang_btn);
        apiKeyField = findViewById(R.id.api_key_field);
        apiKeyBtn = findViewById(R.id.api_key_btn);
    }

    private void prepView()
    {
        challengeApiIdsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        challengeApiIdsRecyclerview.setNestedScrollingEnabled(false);
        challengeApiIdsRecyclerview.setAdapter(adapter);

        registerBtn.setOnClickListener(this);
        addChallengeIdBtn.setOnClickListener(this);
        submitActionsBtn.setOnClickListener(this);
        btnShowProfile.setOnClickListener(this);
        changeLangBtn.setOnClickListener(this);
        apiKeyBtn.setOnClickListener(this);


        langPopupMenu = new PopupMenu(this,changeLangBtn);
        langPopupMenu.getMenu().add("en");
        langPopupMenu.getMenu().add("ar");
        langPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                changeLang(item.getTitle().toString());
                recreate();
                return true;
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.register_btn:
                registerPlayer();
                break;
            case R.id.btn_show_profile:
                showProfile();
                break;
            case R.id.add_challenge_id_btn:
                challengeApiIdField.setError(null);
                if (!challengeApiIdField.getText().toString().isEmpty())
                    adapter.addChallengeApiId(challengeApiIdField.getText().toString());
                else
                    challengeApiIdField.setError("field cannot be empty");
                break;
            case R.id.submit_actions_btn:
//                if (adapter.getmData().size() > 0)
                    //Todo: addAction test method to be implemented

                    Action action = new Action();

                HashMap<String, Object> metaData = new HashMap<>();
                metaData.put("Amount", 1000);

                action.addEvent("Buy", metaData);

                gameBallApp.addAction(action);
                break;
            case R.id.change_lang_btn:
                langPopupMenu.show();
                break;
            case R.id.api_key_btn:
                if(!apiKeyField.getText().toString().trim().isEmpty())
                {
                    gameBallApp.init(apiKeyField.getText().toString(),R.mipmap.ic_launcher);
                }
                else
                    Toast.makeText(this,
                            "Api key cannot be empty",
                            Toast.LENGTH_LONG).show();
                break;

        }
    }

    private void holdRedeemPoints()
    {
        gameBallApp.holdPoints(new HoldPointBody(10, "08773", "5sdfd2dvvd-9mnvhu25d6c3d"),
                new Callback<HoldPointsResponse>()
                {
                    @Override
                    public void onSuccess(HoldPointsResponse holdPointsResponse)
                    {
                        Log.i("hold_response", holdPointsResponse.getHoldReference());
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.i("hold_response", e.getMessage());
                    }
                });
    }

    private void showProfile()
    {
        try
        {
            gameBallApp.showProfile(MainActivity.this);
        } catch (Exception e)
        {
            Toast.makeText(MainActivity.this, "enter player ID then hit register," +
                            "and then try to show profile"
                    , Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void registerPlayer()
    {
        if (!playerIDField.getText().toString().trim().isEmpty())
        {
            if (!playerCategoryID.getText().toString().trim().isEmpty())
                gameBallApp.registerPlayer(playerIDField.getText().toString().trim(),
                        Integer.parseInt(playerCategoryID.getText().toString().trim()));
            else
                gameBallApp.registerPlayer(playerIDField.getText().toString());
        } else
            Toast.makeText(MainActivity.this,
                    "playerID cannot be empty", Toast.LENGTH_SHORT).show();
    }

    private void changeLang(String lang)
    {
        changeLangBtn.setText(lang);

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

    }
}
