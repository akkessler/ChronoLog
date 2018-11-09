package io.akessler.chronolog.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.akessler.chronolog.R; // shouldn't need this import for R...investigate.
import io.akessler.chronolog.model.TimeEntry;

/**
 * Created by Andy on 11/7/2018.
 */

public class TimeEntryAdapter extends RecyclerView.Adapter<TimeEntryAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtDescription;
        public TextView txtProject;
        public TextView txtDuration;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtProject = itemView.findViewById(R.id.txtProject);
            txtDuration = itemView.findViewById(R.id.txtDuration);
        }

        public void bind(TimeEntry entry) {
            txtDescription.setText(entry.description);
            txtProject.setText("Sample Project");

            long duration = entry.endTime - entry.startTime;
            // what about days? should you really be timing for days though?
            String durationText = new SimpleDateFormat("hh:mm:ss").format(new Date(duration));
            txtDuration.setText(durationText);

            final String key = entry.key;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ENTRY_CLICK", "Key: " + key);
                }
            });
        }


    }

    private List<TimeEntry> entries;

    public TimeEntryAdapter(List<TimeEntry> entries) {
        this.entries = entries;
    }

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
        viewHolder.bind(entry);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

}
