package ru.grfc.wifiscan.model;

import android.text.format.DateFormat;

import org.jetbrains.annotations.NotNull;

public class APInfo {
    public APInfo(String BSSID, String SSID, int maxLevel, long timestamp) {
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.maxLevel = maxLevel;
        this.timestamp = timestamp;
    }

    @Override
    public @NotNull String toString() {
        return "SSID='" + SSID + '\'' +
                ", maxLevel=" + maxLevel +
                ", timestamp=" + DateFormat.format("yyyy-MM-dd kk:mm:ss", timestamp) + '\n';
    }

    public String BSSID;
    public String SSID;
    public int maxLevel;
    public long timestamp;
}
