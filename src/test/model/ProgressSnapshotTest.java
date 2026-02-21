package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProgressSnapshotTest {
    private ProgressSnapshot snapshot1;

    @BeforeEach
    public void setup() {
        snapshot1 = new ProgressSnapshot(30, LocalDateTime.of(2026, 2, 21, 8, 13));
    }

    @Test
    public void testConstructor() {
        assertEquals(30, snapshot1.getCompletionPercentage());
        assertEquals(LocalDateTime.of(2026, 2, 21, 8, 13), snapshot1.getTime());
    }
}
