package com.codedao.menuonline;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;

public class MainScreen extends AppCompatActivity {
    int flag=0;
    ImageView imgTable;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTransformEffect();
        getSupportActionBar().hide();
        initView();

        imgTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag+=1;
                imgTable.setImageResource((flag%2==0)?R.drawable.full_table:R.drawable.emty_table);
            }
        });
    }

    private void initView() {
        imgTable=findViewById(R.id.imgTable);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getTransformEffect() {
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
    }
}
