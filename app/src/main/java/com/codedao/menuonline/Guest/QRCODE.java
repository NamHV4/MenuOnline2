package com.codedao.menuonline.Guest;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codedao.menuonline.R;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class QRCODE extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener {
    QRCodeReaderView qrCodeReaderView;
    CheckBox cbFlashlight;
    TextView result;
    ImageButton btnRevert;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        getSupportActionBar().hide();
        initView();
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        cbFlashlight.setOnClickListener(this);
        btnRevert.setOnClickListener(this);
    }

    private void initView() {
        qrCodeReaderView = findViewById(R.id.qrdecoderview);
        cbFlashlight = findViewById(R.id.cbFlashlight);
        btnRevert = findViewById(R.id.btnRevert);
        result = findViewById(R.id.result);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        if (result.getVisibility() == View.GONE) {
            result.setVisibility(View.VISIBLE);
            result.setText(text);
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        if (result.getVisibility() == View.VISIBLE) {
            result.setVisibility(View.GONE);
            qrCodeReaderView.startCamera();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cbFlashlight:
                qrCodeReaderView.setTorchEnabled(cbFlashlight.isChecked());
                break;
            case R.id.btnRevert:
                if (flag) {
                    qrCodeReaderView.setFrontCamera();
                    flag = false;
                } else {
                    qrCodeReaderView.setBackCamera();
                    flag = true;
                }
        }
    }
}
