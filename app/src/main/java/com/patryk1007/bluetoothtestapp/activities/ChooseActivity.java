package com.patryk1007.bluetoothtestapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.patryk1007.bluetoothtestapp.R;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        initButtons();
    }

    private void initButtons() {
        findViewById(R.id.choose_start_client_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseActivity.this, ScanActivity.class));
            }
        });
        findViewById(R.id.choose_start_server_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseActivity.this, ServerActivity.class));
            }
        });
    }
}
