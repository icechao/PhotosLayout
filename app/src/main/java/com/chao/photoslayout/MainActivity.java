package com.chao.photoslayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleView();
    }

    private void handleView() {
        PhotosLayout photosLayout = (PhotosLayout) findViewById(R.id.photos_layout);
        photosLayout.setOnViewChangerListener(new PhotosLayout.OnViewChangerListener() {
            @Override
            public void viewChanger(View removeView, View newView) {
                Toast.makeText(MainActivity.this, "View已经切换", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
