package io.akessler.chronolog.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.akessler.chronolog.R; // shouldn't need this import for R...investigate.
import io.akessler.chronolog.model.TimeEntry;

/**
 * Created by Andy on 11/7/2018.
 */

public class TimeEntryAdapter extends RecyclerView.Adapter<TimeEntryAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }

    }

    private Context context;

    private List<TimeEntry> entries;

    public TimeEntryAdapter(Context context, List<TimeEntry> entries) {
        this.context = context;
        this.entries = entries;
    }

    // TODO R.layout.item_time_entry
    @Override
    public TimeEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_time_entry, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimeEntryAdapter.ViewHolder viewHolder, int position) {
        TimeEntry entry = entries.get(position);
        viewHolder.txtDescription.setText(entry.description);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

}
