package ru.grfc.wifiscan.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.grfc.wifiscan.model.APInfo;

public class Datasource {
    HashMap<String, APInfo> map = new HashMap<>();

    public List<APInfo> loadList() {
        return new ArrayList<APInfo>(map.values());
    }

    public void update(String BSSID, APInfo el) {
        if (map.containsKey(BSSID)) {
            APInfo ap = map.get(BSSID);
            if (ap.maxLevel < el.maxLevel) {
                map.put(BSSID, el);
            }
        } else {
            map.put(BSSID, el);
        }
    }

    public int size() {
        return map.size();
    }
}
