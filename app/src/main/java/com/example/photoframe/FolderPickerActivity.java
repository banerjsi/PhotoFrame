package com.example.photoframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderPickerActivity extends Activity {

    private File currentDir;
    private List<File> directories = new ArrayList<>();
    private ListView listView;
    private Button btnSelectFolder;
    private TextView tvCurrentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up layout manually (no XML)
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        tvCurrentPath = new TextView(this);
        layout.addView(tvCurrentPath);

        listView = new ListView(this);
        layout.addView(listView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0, 1.0f)); // weight = 1 for listView to fill space

        btnSelectFolder = new Button(this);
        btnSelectFolder.setText("Select This Folder");
        layout.addView(btnSelectFolder);

        setContentView(layout);

        currentDir = Environment.getExternalStorageDirectory(); // Start from SD root
        showFolders(currentDir);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            File selected = directories.get(position);
            if (selected.isDirectory()) {
                showFolders(selected);
            }
        });

        btnSelectFolder.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("selectedFolder", currentDir.getAbsolutePath());
            setResult(RESULT_OK, result);
            finish();
        });
    }

    private void showFolders(File dir) {
        File[] files = dir.listFiles();
        directories.clear();
        List<String> folderNames = new ArrayList<>();

        if (dir.getParentFile() != null) {
            directories.add(dir.getParentFile());
            folderNames.add(".. (Up)");
        }

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.canRead()) {
                    directories.add(file);
                    folderNames.add(file.getName());
                }
            }
        }

        tvCurrentPath.setText("Path: " + dir.getAbsolutePath());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, folderNames);
        listView.setAdapter(adapter);
        currentDir = dir;
    }
}


