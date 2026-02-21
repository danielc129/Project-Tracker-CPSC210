package model;

import java.time.LocalDateTime;

// Represents a snapshot of the completion percentage of a project at a specific time
public class ProgressSnapshot {
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

}
