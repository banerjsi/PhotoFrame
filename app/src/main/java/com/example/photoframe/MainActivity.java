package com.example.photoframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int REQUEST_SELECT_FOLDER = 1;

    private TextView tvSelectedPath;
    private EditText editInterval;
    private String selectedFolderPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Suppress keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        Button btnBrowse = findViewById(R.id.btnBrowse);
        Button btnPlay = findViewById(R.id.btnPlay);
        editInterval = findViewById(R.id.editInterval);
        tvSelectedPath = findViewById(R.id.tvSelectedPath);

        // Suppress keyboard
        editInterval.clearFocus();

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
                String intervalStr = editInterval.getText().toString().trim();
                if (selectedFolderPath != null && !intervalStr.isEmpty()) {
                    int interval = Integer.parseInt(intervalStr);
                    Intent intent = new Intent(MainActivity.this, SlideshowActivity.class);
                    intent.putExtra("folderPath", selectedFolderPath);
                    intent.putExtra("intervalSeconds", interval);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please select folder and interval", Toast.LENGTH_SHORT).show();
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