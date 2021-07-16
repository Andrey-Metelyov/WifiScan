package ru.grfc.wifiscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.grfc.wifiscan.adapter.ItemAdapter;
import ru.grfc.wifiscan.data.Datasource;
import ru.grfc.wifiscan.model.APInfo;

public class MainActivity extends AppCompatActivity {
    private WifiManager wifiManager;

    final private Datasource list = new Datasource();
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button refreshButton = findViewById(R.id.button);
//        refreshButton.setOnClickListener(view -> startScan());

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(), "Resutls received", Toast.LENGTH_SHORT).show();
                showScanResults();
//                getApplicationContext().unregisterReceiver(this);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);

//        List<APInfo> dataset = list.loadList();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new ItemAdapter(this, list);
        recyclerView.setAdapter(adapter);

        startScan();
    }

    private void startScan() {
        boolean success = wifiManager.startScan();
        if (!success) {
            Toast.makeText(getApplicationContext(), "Scan start failure...", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Scan started", Toast.LENGTH_SHORT).show();
        }
    }

    private String getResults(List<ScanResult> results) {
//        StringBuilder message = new StringBuilder();
        for (ScanResult el : results) {
            long actualTime = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(SystemClock.elapsedRealtimeNanos() - el.timestamp, TimeUnit.NANOSECONDS);
//            System.out.println("currentTimeMillis: " + System.currentTimeMillis());
//            System.out.println("actualTime:        " + actualTime);
//            System.out.println("timestamp:         " + el.timestamp);
//            String time = formatDateTime(actualTime);
//            String line = "BSSID: " + el.BSSID +
//                    " SSID: " + el.SSID +
//                    " level: " + el.level +
//                    " frequency: " + el.frequency +
//                    " timestamp: " + time;
//            message.append(line).append("\n");
            list.update(el.BSSID, new APInfo(el.BSSID, el.SSID, el.level, System.currentTimeMillis()));
        }
        return list.toString();
    }

    private String formatDateTime(long timestamp) {
        Date dateTime = new Date(timestamp);
        return DateFormat.format("yyyy-MM-dd kk:mm:ss", dateTime).toString();
    }

    private void showScanResults() {
        List<ScanResult> results = wifiManager.getScanResults();
        String message = "Last result: " + formatDateTime(System.currentTimeMillis()) +
                "\n" +
                getResults(results);
        System.out.println(message);
        Toast.makeText(getApplicationContext(), "count: " + adapter.getItemCount() + " list: " + list.size(), Toast.LENGTH_SHORT).show();

        adapter.notifyDataSetChanged();
//        final TextView text = findViewById(R.id.text);
//        text.setText(message);
    }
}
