package com.sogang.capstone2.pianoplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        startActivity(new Intent(getApplicationContext(), CoverActivity.class));
    }
}
