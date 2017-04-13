package com.kode.fractalsv2.activities;

import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kode.fractalsv2.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Camera camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupCamera(640, 480, 30, null);
    }

    public void setupCamera(int wid, int hei, double fps, Camera.PreviewCallback cb) {
        camera = Camera.open();
        Camera.Parameters p = camera.getParameters();
        List<Camera.Size> supportedSizes = p.getSupportedPreviewSizes();
        Camera.Size procSize_=supportedSizes.get(supportedSizes.size() / 2);
        List<int[]> supportedFrameRate = p.getSupportedPreviewFpsRange();
        double diff = Math.abs(supportedSizes.get(0).width * supportedSizes.get(0).height - wid * hei);
        int targetIndex = 0;
        for (int i = 1; i < supportedSizes.size(); i++) {
            double newDiff = Math.abs(supportedSizes.get(i).width * supportedSizes.get(i).height - wid * hei);
            if (newDiff < diff) {
                diff = newDiff;
                targetIndex = i;
            }
        }
        procSize_.width = supportedSizes.get(targetIndex).width;
        procSize_.height = supportedSizes.get(targetIndex).height;
        diff = Math.abs(supportedFrameRate.get(0)[0] * supportedFrameRate.get(0)[1] - fps * fps * 1000 * 1000);
        targetIndex = 0;

        for (int i = 1; i < supportedFrameRate.size(); i++) {
            double newDiff = Math.abs(supportedFrameRate.get(i)[0] * supportedFrameRate.get(i)[1] - fps * fps * 1000 * 1000);
            if (newDiff < diff) {
                diff = newDiff;
                targetIndex = i;
            }
        }

        int targetMaxFrameRate = supportedFrameRate.get(targetIndex)[0];
        int targetMinFrameRate = supportedFrameRate.get(targetIndex)[1];
        p = camera.getParameters();
        p.setPreviewSize(procSize_.width, procSize_.height);
        p.setPreviewFpsRange(targetMaxFrameRate, targetMinFrameRate);
        camera.setParameters(p);
        PixelFormat pixelFormat = new PixelFormat();
        PixelFormat.getPixelFormatInfo(ImageFormat.NV21, pixelFormat);
        int bufSize = procSize_.width * procSize_.height * pixelFormat.bitsPerPixel / 8;
        camera.addCallbackBuffer(new byte[bufSize]);
        camera.setPreviewCallbackWithBuffer(cb);
    }
}
