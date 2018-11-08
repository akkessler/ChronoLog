package io.akessler.chronolog.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andy on 11/7/2018.
 */

@IgnoreExtraProperties
public class TimeEntry {

    public String description;
    public List<String> tags;
    public long startTime;
    public long endTime;
//    public long projectId;

    public String key;

    public TimeEntry() {
        // Need default constructor for calls to DataSnapshot.getValue(TimeEntry.class)
    }

    public TimeEntry(String description, long startTime, long endTime, String[] tags) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = tags != null ? Arrays.asList(tags) : new ArrayList<String>();
    }

    public TimeEntry(String description, long startTime, String[] tags) {
        this(description, startTime, -1, tags);
    }

    public TimeEntry(String description, long startTime, long endTime) {
        this(description, startTime, endTime, null);
    }

    public TimeEntry(String description, long startTime) {
        this(description, startTime, -1, null);
    }

}
