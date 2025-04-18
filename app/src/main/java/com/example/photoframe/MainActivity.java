package com.example.photoframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final int REQUEST_SELECT_FOLDER = 1;

    private TextView tvSelectedPath;
    private String selectedFolderPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBrowse = findViewById(R.id.btnBrowse);
        Button btnPlay = findViewById(R.id.btnPlay);
        tvSelectedPath = findViewById(R.id.tvSelectedPath);

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FolderPickerActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_FOLDER);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFolderPath != null) {
                    Intent intent = new Intent(MainActivity.this, SlideshowActivity.class);
                    intent.putExtra("folderPath", selectedFolderPath);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_FOLDER && resultCode == RESULT_OK) {
            selectedFolderPath = data.getStringExtra("selectedFolder");
            tvSelectedPath.setText("Selected: " + selectedFolderPath);
            findViewById(R.id.btnPlay).setEnabled(true);
        }
    }
}


