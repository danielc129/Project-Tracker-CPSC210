package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjectTest {
    private Project project1;
    private LeafTask leaf1;
    private LeafTask leaf2;
    private LeafTask leaf3;
    private BranchTask branch1;
    private BranchTask branch2;

    @BeforeEach
    public void setup() {
        project1 = new Project("Test Project 1", "Test Description 1");
        leaf1 = new LeafTask("Leaf Task 1", "Test Description L1", new Date(1, 1, 2026), 10);
        leaf2 = new LeafTask("Leaf Task 2", "Test Description L2", new Date(2, 1, 2026), 5);
        leaf3 = new LeafTask("Leaf Task 3", "Test Description L3", new Date(3, 1, 2026), 10);
        ArrayList<Task> subtasks1 = new ArrayList<>();
        subtasks1.add(leaf2);
        subtasks1.add(leaf3);
        branch1 = new BranchTask("Branch Task 1", "Test Description B1", subtasks1);
        ArrayList<Task> subtasks2 = new ArrayList<>();
        subtasks2.add(branch1);
        branch2 = new BranchTask("Branch Task 2", "Test Description B2", subtasks2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Test Project 1", project1.getName());
        assertEquals("Test Description 1", project1.getDescription());
    }

    @Test
    public void testAddTask() {
        project1.addTask(leaf1);
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(leaf1);
        assertEquals(expectedResult, project1.getTasks());
        project1.addTask(branch1);
        expectedResult.add(branch1);
        assertEquals(expectedResult, project1.getTasks());
    }

    @Test
    public void testRemoveTask() {
        project1.addTask(leaf1);
        project1.addTask(branch1);
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(branch1);
        project1.removeTask(leaf1);
        assertEquals(expectedResult, project1.getTasks());
        expectedResult.remove(branch1);
        project1.removeTask(branch1);
        assertEquals(expectedResult, project1.getTasks());
    }

    @Test
    public void testGetCompletionPercentageNoTasks() {
        assertEquals(0, project1.getCompletionPercentage());
    }

    @Test
    public void testGetCompletionPercentageOneTaskIncomplete() {
        leaf1.setCompletion(false);
        project1.addTask(leaf1);
        assertEquals(0, project1.getCompletionPercentage());
    }
    
    @Test
    public void testGetCompletionPercentageOneTaskComplete() {
        leaf1.setCompletion(true);
        project1.addTask(leaf1);
        assertEquals(100, project1.getCompletionPercentage());
    }

    @Test
    public void testGetCompletionPercentageMixedTasks() {
        leaf2.setCompletion(true);
        project1.addTask(branch2);
        assertEquals((int) (100 * (5.0 / 15)), project1.getCompletionPercentage());
    }

    @Test
    public void testGetSortedSubtasksAlreadyInOrder() {
        LeafTask leafA = new LeafTask("", "", new Date(3, 1, 2025), 5);
        LeafTask leafB = new LeafTask("", "", new Date(2, 1, 2025), 5);
        LeafTask leafC = new LeafTask("", "", new Date(1, 1, 2025), 5);
        ArrayList<Task> inputTasks = new ArrayList<>();
        inputTasks.add(leafC);
        inputTasks.add(leafB);
        inputTasks.add(leafA);
        project1.addTask(leafC);
        project1.addTask(leafB);
        project1.addTask(leafA);
        assertEquals(inputTasks, project1.getSortedTasks());
    }

    @Test
    public void testGetSortedTasksAlreadyReverseOrder() {
        LeafTask leafA = new LeafTask("", "", new Date(3, 1, 2025), 5);
        LeafTask leafB = new LeafTask("", "", new Date(2, 1, 2025), 5);
        LeafTask leafC = new LeafTask("", "", new Date(1, 1, 2025), 5);
        project1.addTask(leafA);
        project1.addTask(leafB);
        project1.addTask(leafC);
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(leafC);
        expectedResult.add(leafB);
        expectedResult.add(leafA);
        assertEquals(expectedResult, project1.getSortedTasks());
    }

    @Test
    public void testGetSortedTasksMixedOrder() {
        LeafTask leafA = new LeafTask("", "", new Date(3, 1, 2025), 5);
        LeafTask leafB = new LeafTask("", "", new Date(2, 1, 2025), 5);
        LeafTask leafC = new LeafTask("", "", new Date(1, 1, 2025), 5);
        project1.addTask(leafC);
        project1.addTask(leafA);
        project1.addTask(leafB);
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(leafC);
        expectedResult.add(leafB);
        expectedResult.add(leafA);
        assertEquals(expectedResult, project1.getSortedTasks());
    }
}
