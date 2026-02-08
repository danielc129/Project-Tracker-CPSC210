package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.exceptions.IllegalMonthException;

public class LeafTaskTest {
    private LeafTask task1;

    @BeforeEach
    public void setup() throws IllegalMonthException {
        task1 = new LeafTask("Test Name 1", "Test Description 1", new Date(15, 2, 2026), 20);
    }

    @Test
    public void testConstructor() {
        assertEquals("Test Name 1", task1.getName());
        assertEquals("Test Description 1", task1.getDescription());
        assertEquals(15, task1.getDueDate().getDay());
        assertEquals(2, task1.getDueDate().getMonth());
        assertEquals(2026, task1.getDueDate().getYear());
        assertEquals(20, task1.getWeight());
        assertFalse(task1.isCompleted());
    }

    @Test
    public void testSetDueDate() throws IllegalMonthException {
        task1.setDueDate(new Date(13, 3, 2025));
        assertEquals(13, task1.getDueDate().getDay());
        assertEquals(3, task1.getDueDate().getMonth());
        assertEquals(2025, task1.getDueDate().getYear());

        task1.setDueDate(new Date(6, 4, 2024));
        assertEquals(6, task1.getDueDate().getDay());
        assertEquals(4, task1.getDueDate().getMonth());
        assertEquals(2024, task1.getDueDate().getYear());
    }

    @Test
    public void testSetWeight() {
        task1.setWeight(1);
        assertEquals(1, task1.getWeight());
        task1.setWeight(30);
        assertEquals(30, task1.getWeight());
    }

    @Test
    public void testSetCompletion() {
        task1.setCompletion(true);
        assertTrue(task1.isCompleted());
        task1.setCompletion(false);
        assertFalse(task1.isCompleted());
    }
     
}
