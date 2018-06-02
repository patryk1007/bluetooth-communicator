package com.patryk1007.bluetoothtestapp;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DevicesListAdapter extends RecyclerView.Adapter<DevicesListAdapter.ViewHolder> {

    private List<BluetoothDevice> devices = new ArrayList<>();

    public DevicesListAdapter() {
    }

    public void addDevice(BluetoothDevice device) {
        if (device != null && device.getAddress() != null && !isDeviceOnList(device)) {
            devices.add(device);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new ViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BluetoothDevice device = devices.get(position);
        String getDeviceName = device.getName() != null ? device.getName() : device.getAddress();
        holder.deviceName.setText(getDeviceName);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView deviceName;

        ViewHolder(View mainView) {
            super(mainView);
            deviceName = mainView.findViewById(R.id.device_name);
        }
    }

    private boolean isDeviceOnList(BluetoothDevice newDevice) {
        String newDeviceAddress = newDevice.getAddress();
        for (BluetoothDevice device : devices) {
            if (device.getAddress().equalsIgnoreCase(newDeviceAddress)) {
                return true;
            }
        }
        return false;
    }

}
