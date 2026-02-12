package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UtilitiesTest {
    private Utilities utilities;
    private LeafTask leafA;
    private LeafTask leafB;
    private LeafTask leafC;
    private Task actualLeaf;
    private Task actualBranch;

    @BeforeEach
    public void setup() {
        utilities = new Utilities();
        leafA = new LeafTask("", "", new Date(3, 1, 2025), 5);
        leafB = new LeafTask("", "", new Date(2, 1, 2025), 5);
        leafC = new LeafTask("", "", new Date(1, 1, 2025), 5);
        actualLeaf = new LeafTask("", "", new Date(4, 1, 2025), 10);
        ArrayList<Task> subtasksForBranch = new ArrayList<>();
        subtasksForBranch.add(actualLeaf);
        actualBranch = new BranchTask("", "", subtasksForBranch);
    }

    @Test
    public void testSortTasksAlreadyInOrder() {
        ArrayList<Task> inputTasks = new ArrayList<>();
        inputTasks.add(leafC);
        inputTasks.add(leafB);
        inputTasks.add(leafA);
        assertEquals(inputTasks, utilities.sortTasks(inputTasks));
    }

    @Test
    public void testSortTasksReverseOrder() {
        ArrayList<Task> inputTasks = new ArrayList<>();
        inputTasks.add(leafA);
        inputTasks.add(leafB);
        inputTasks.add(leafC);
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(leafC);
        expectedResult.add(leafB);
        expectedResult.add(leafA);
        assertEquals(expectedResult, utilities.sortTasks(inputTasks));
    }

    @Test
    public void testSortTasksMixedOrder() {
        ArrayList<Task> inputTasks = new ArrayList<>();
        inputTasks.add(leafC);
        inputTasks.add(leafA);
        inputTasks.add(leafB);
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(leafC);
        expectedResult.add(leafB);
        expectedResult.add(leafA);
        assertEquals(expectedResult, utilities.sortTasks(inputTasks));
    }

    @Test
    public void testIsLeafTask() {
        assertTrue(utilities.isLeafTask(actualLeaf));
        assertFalse(utilities.isLeafTask(actualBranch));
    }
}
