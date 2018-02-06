package com.codedao.menuonline.Guest;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codedao.menuonline.R;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class QRCode extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener {
    private QRCodeReaderView mQRCodeView;
    private CheckBox mCBFlash;
    private TextView mTvResult;
    private ImageButton mBtnRevert;
    boolean flag = true;
    private PointsOverlayView mPointsOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        getSupportActionBar().hide();
        initView();
        mQRCodeView.setOnQRCodeReadListener(this);
        mQRCodeView.setAutofocusInterval(2000L);
        mCBFlash.setOnClickListener(this);
        mBtnRevert.setOnClickListener(this);
    }

    private void initView() {
        mQRCodeView = findViewById(R.id.qrdecoderview);
        mCBFlash = findViewById(R.id.cbFlashlight);
        mBtnRevert = findViewById(R.id.btnRevert);
        mTvResult = findViewById(R.id.result);
        mPointsOverlayView = findViewById(R.id.overlay);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        mPointsOverlayView.setmPoints(points);
        if (mTvResult.getVisibility() == View.GONE) {
            mTvResult.setVisibility(View.VISIBLE);
            mTvResult.setText(text);
            mQRCodeView.stopCamera();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRCodeView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        if (mTvResult.getVisibility() == View.VISIBLE) {
            mTvResult.setVisibility(View.GONE);
            mQRCodeView.startCamera();
            mPointsOverlayView.setmPoints(null);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cbFlashlight:
                mQRCodeView.setTorchEnabled(mCBFlash.isChecked());
                break;
//            case R.id.mBtnRevert:
//                if (flag) {
//                    mQRCodeView.setFrontCamera();
//                    flag = false;
//                } else {
//                    mQRCodeView.setBackCamera();
//                    flag = true;
//                }
        }
    }
}
