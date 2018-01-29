package com.codedao.menuonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mButtonCustomer;
    Button mButtonBussi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonBussi = findViewById(R.id.btnBussi);
        mButtonCustomer = findViewById(R.id.btnCustomer);
        mButtonBussi.setOnClickListener(this);
        mButtonCustomer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBussi:
                break;
            case R.id.btnCustomer:
                break;
            default:
                break;
        }
    }
}
