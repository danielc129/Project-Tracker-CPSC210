package model;

import java.time.LocalDateTime;

// Represents a snapshot of the completion percentage of a project at a specific time
public class ProgressSnapshot {

    // EFFECTS: creates a progress snapshot with the given completion percentage and time
    public ProgressSnapshot(int completionPercentage, LocalDateTime time) {

    }

    // EFFECTS: returns the completion percentage
    public int getCompletionPercentage() {
        return -1;
    }

    // EFFECTS: returns the time of the snapshot
    public LocalDateTime getTime() {
        return null;
    }
    
}
