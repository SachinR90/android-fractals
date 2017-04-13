package com.kode.fractalsv2.activities;

import android.R.color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.kode.fractalsv2.R;
import com.kode.fractalsv2.components.FractalImageView;

public class DrawingActivity extends AppCompatActivity {
    FractalImageView imageView;
    Button           button;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        FractalImageView image = (FractalImageView) findViewById(R.id.myFractal);
        image.setBackgroundColor(color.white);
        button = (Button) findViewById(R.id.button1);
        image.setButton1(button);
    }
}
