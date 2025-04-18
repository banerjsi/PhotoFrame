package com.example.photoframe;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlideshowActivity extends Activity {

    private ImageView imageView;
    private Handler handler;
    private Runnable slideshowRunnable;
    private List<File> imageFiles = new ArrayList<>();
    private int intervalMillis = 5000; // default 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen setup
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);

        handler = new Handler();

        loadImagesFromSdCard();

        if (imageFiles.isEmpty()) {
            showMessage("No photos found on SD card.");
            return;
        }

        startSlideshow();
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void loadImagesFromSdCard() {
//        File sdCard = Environment.getExternalStorageDirectory(); // `/mnt/sdcard`
        File sdCard = new File("/Removable/MicroSD"); // `/mnt/sdcard`
        if (sdCard == null || !sdCard.exists()) {
            showMessage("SD card not available.");
            return;
        }

        scanDirectoryForImages(sdCard);
        Collections.shuffle(imageFiles); // random order
    }

    private void scanDirectoryForImages(File dir) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectoryForImages(file); // recursive
            } else if (file.getName().matches("(?i).*\\.(jpg|jpeg|png|bmp|gif)$")) {
                imageFiles.add(file);
            }
        }
    }

    private void startSlideshow() {
        slideshowRunnable = new Runnable() {
            int index = 0;
            @Override
            public void run() {
                if (index >= imageFiles.size()) index = 0;

                Bitmap bitmap = BitmapFactory.decodeFile(imageFiles.get(index).getAbsolutePath());
                imageView.setImageBitmap(bitmap);
                index++;

                handler.postDelayed(this, intervalMillis);
            }
        };
        handler.post(slideshowRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(slideshowRunnable);
    }
}

