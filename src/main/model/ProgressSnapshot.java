package model;

import java.time.LocalDateTime;

import org.json.JSONObject;

import persistence.Writable;

// Represents a snapshot of the completion percentage of a project at a specific time
public class ProgressSnapshot implements Writable {
    private int completionPercentage;
    private LocalDateTime time;

    // EFFECTS: creates a progress snapshot with the given completion percentage and time
    public ProgressSnapshot(int completionPercentage, LocalDateTime time) {
        this.completionPercentage = completionPercentage;
        this.time = time;
    }

    // EFFECTS: returns the completion percentage
    public int getCompletionPercentage() {
        return this.completionPercentage;
    }

    // EFFECTS: returns the time of the snapshot
    public LocalDateTime getTime() {
        return this.time;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time", time.toString());
        json.put("completion_percentage", completionPercentage);
        return json;
    }

}
