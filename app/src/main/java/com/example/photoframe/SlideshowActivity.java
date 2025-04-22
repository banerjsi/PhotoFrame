package com.example.photoframe;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SlideshowActivity extends Activity {

    private ImageView imageView;
    private TextView tvFilename;
    private Handler handler = new Handler();
    private List<File> imageFiles = new ArrayList<>();
    private int currentIndex = 0;
    private int intervalMillis = 5000;
    private boolean paused = false;
    private Button btnPrev, btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        // Keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        imageView = findViewById(R.id.imageView);
        tvFilename = findViewById(R.id.tvFilename);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);


        String folderPath = getIntent().getStringExtra("folderPath");
        int intervalSeconds = getIntent().getIntExtra("intervalSeconds", 5);
        intervalMillis = intervalSeconds * 1000;

        File folder = new File(folderPath);
        scanDirectoryForImages(folder);

        if (!imageFiles.isEmpty()) {
            showImage(currentIndex);
            handler.postDelayed(slideshowRunnable, intervalMillis);
        } else {
            Toast.makeText(this, "No images found in selected folder.", Toast.LENGTH_LONG).show();
            finish();
        }

// Tap to pause/resume
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused = !paused;
                if (paused) {
                    handler.removeCallbacks(slideshowRunnable);
                    tvFilename.setText(imageFiles.get(currentIndex).getName());
                    tvFilename.setVisibility(View.VISIBLE);
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                } else {
                    tvFilename.setVisibility(View.GONE);
                    btnPrev.setVisibility(View.GONE);
                    btnNext.setVisibility(View.GONE);
                    handler.postDelayed(slideshowRunnable, intervalMillis);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % imageFiles.size();
                showImage(currentIndex);
                tvFilename.setText(imageFiles.get(currentIndex).getName());
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex - 1 + imageFiles.size()) % imageFiles.size();
                showImage(currentIndex);
                tvFilename.setText(imageFiles.get(currentIndex).getName());
            }
        });

    }

    private void scanDirectoryForImages(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) {
                    imageFiles.add(file);
                }
            }
        }
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".bmp") || name.endsWith(".gif");
    }

    private Runnable slideshowRunnable = new Runnable() {
        @Override
        public void run() {
            if (!paused) {
                currentIndex = (currentIndex + 1) % imageFiles.size();
                showImage(currentIndex);
                handler.postDelayed(this, intervalMillis);
            }
        }
    };
    private void showImage(int index) {
        if (imageFiles == null || imageFiles.isEmpty()) return;
        File imageFile = imageFiles.get(index);
        String path = imageFile.getAbsolutePath();
        // Step 1: Decode image dimensions only
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // Step 2: Calculate sample size (downscaling factor)
        options.inSampleSize = calculateInSampleSize(options, 640, 400); // target width/height
        options.inJustDecodeBounds = false;
        // Step 3: Decode the bitmap with sample size
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(slideshowRunnable);
    }
}

