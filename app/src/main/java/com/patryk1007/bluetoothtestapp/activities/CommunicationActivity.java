package com.patryk1007.bluetoothtestapp.activities;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.patryk1007.bluetoothtestapp.R;
import com.patryk1007.bluetoothtestapp.adapters.LogAdapter;
import com.patryk1007.bluetoothtestapp.bluetooth.ConnectionCallback;

public class CommunicationActivity extends AppCompatActivity {

    protected RecyclerView logList;
    protected View sendButton;
    protected EditText messageInput;

    protected LogAdapter logAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        initViews();
        initLogList();
    }

    protected void addLog(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (logAdapter != null) {
                    logAdapter.addLog(message);
                }
            }
        });
    }

    private void initViews() {
        logList = findViewById(R.id.communication_log_view);
        messageInput = findViewById(R.id.communication_message_input);
        sendButton = findViewById(R.id.communication_send_message_button);
    }

    private void initLogList() {
        logAdapter = new LogAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        logList.setLayoutManager(layoutManager);
        logList.setAdapter(logAdapter);
    }

    protected ConnectionCallback connectionCallback = new ConnectionCallback() {
        @Override
        public void onConnecting() {
            addLog(getString(R.string.communication_view_connecting));
        }

        @Override
        public void onWaitingForDevice() {
            addLog(getString(R.string.communication_view_waiting_for_devices));
        }

        @Override
        public void onConnected(BluetoothDevice device) {
            addLog(getString(R.string.communication_view_connected));
        }

        @Override
        public void onDisconnected() {
            addLog(getString(R.string.communication_view_disconnected));
        }

        @Override
        public void onError(String errorMessage) {
            String message = String.format(getString(R.string.communication_view_error), errorMessage);
            addLog(message);
        }
    };
}
