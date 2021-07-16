package ru.grfc.wifiscan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.grfc.wifiscan.R;
import ru.grfc.wifiscan.data.Datasource;
import ru.grfc.wifiscan.model.APInfo;

public class ItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private Datasource dataset;

    public ItemAdapter(Context context, Datasource dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View adapterLayout = LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(adapterLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        List<APInfo> list = dataset.loadList();
        APInfo item = list.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        APInfo element = list.get(position);
        String text = element.SSID + " " + element.BSSID;
        itemViewHolder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textView;

        public ItemViewHolder(@NonNull @NotNull View view) {
            super(view);
            textView = view.findViewById(R.id.item_title);
        }
    }
}
