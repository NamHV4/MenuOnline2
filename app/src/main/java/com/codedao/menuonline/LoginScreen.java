package com.codedao.menuonline;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codedao.menuonline.Guest.MainScreenGuest;
import com.codedao.menuonline.Host.MainScreenHost;

public class LoginScreen extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button mBtnGo, mBtnGuest;
    private CardView cv;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();
        setListener();

    }


    private void initView() {

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        mBtnGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        mFab = findViewById(R.id.fab);
        mBtnGuest =findViewById(R.id.btnGuest);
    }

    private void setListener() {
        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginScreen.this);
                Intent i2 = new Intent(LoginScreen.this, MainScreenHost.class);

                startActivity(i2, oc2.toBundle());
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginScreen.this, mFab, mFab.getTransitionName());
                startActivity(new Intent(LoginScreen.this, RegisterActivity.class), options.toBundle());
            }
        });
        mBtnGuest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc3=ActivityOptionsCompat.makeSceneTransitionAnimation(LoginScreen.this);
                startActivity(new Intent(LoginScreen.this,MainScreenGuest.class),oc3.toBundle());
            }
        });
    }
}
