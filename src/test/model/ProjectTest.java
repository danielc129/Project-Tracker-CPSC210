package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
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
        assertTrue(project1.getTasks().isEmpty());
        assertTrue(project1.getProgressHistory().isEmpty());
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

    @Test
    public void testUpdateProgressHistory() {
        assertTrue(project1.getProgressHistory().isEmpty());
        project1.updateProgressHistory();
        LocalDateTime time1 = LocalDateTime.now();
        List<ProgressSnapshot> result1 = project1.getProgressHistory();
        assertEquals(1, result1.size());
        assertEquals(0, result1.get(0).getCompletionPercentage());
        assertTrue(Math.abs(result1.get(0).getTime().until(time1, ChronoUnit.SECONDS)) < 1);
        project1.updateProgressHistory();
        List<ProgressSnapshot> result2 = project1.getProgressHistory();
        assertEquals(1, result2.size());
        assertEquals(0, result2.get(0).getCompletionPercentage());
        assertTrue(Math.abs(result2.get(0).getTime().until(time1, ChronoUnit.SECONDS)) < 1);
        project1.addTask(leaf1);
        leaf1.setCompletion(true);
        assertEquals(100, project1.getCompletionPercentage());
        project1.updateProgressHistory();
        LocalDateTime time2 = LocalDateTime.now();
        List<ProgressSnapshot> result3 = project1.getProgressHistory();
        assertEquals(2, result3.size());
        assertEquals(0, result3.get(0).getCompletionPercentage());
        assertTrue(Math.abs(result3.get(0).getTime().until(time1, ChronoUnit.SECONDS)) < 1);
        assertEquals(100, result3.get(1).getCompletionPercentage());
        assertTrue(Math.abs(result3.get(1).getTime().until(time2, ChronoUnit.SECONDS)) < 1);
    }

    @Test
    public void testSetProgressHistory() {
        ProgressSnapshot snapshot1 = new ProgressSnapshot(30, LocalDateTime.of(2026, 2, 23, 20, 29, 0));
        ProgressSnapshot snapshot2 = new ProgressSnapshot(70, LocalDateTime.of(2026, 2, 22, 20, 29, 0));
        ArrayList<ProgressSnapshot> progressHistory = new ArrayList<>();
        progressHistory.add(snapshot1);
        progressHistory.add(snapshot2);
        project1.setProgressHistory(progressHistory);
        List<ProgressSnapshot> progressHistoryReturn = project1.getProgressHistory();
        assertEquals(2, progressHistoryReturn.size());
        assertEquals(30, progressHistoryReturn.get(0).getCompletionPercentage());
        assertEquals(snapshot1.getTime(), progressHistoryReturn.get(0).getTime());
        assertEquals(70, progressHistoryReturn.get(1).getCompletionPercentage());
        assertEquals(snapshot2.getTime(), progressHistoryReturn.get(1).getTime());
    }

    @Test
    public void testGetFlattenedTasks() {
        project1.addTask(leaf1);
        project1.addTask(branch2);
        List<LeafTask> result = project1.getFlattenedTasks();
        List<LeafTask> expectedResult = new ArrayList<>();
        expectedResult.add(leaf1);
        expectedResult.add(leaf2);
        expectedResult.add(leaf3);
        assertEquals(expectedResult, result);
    }
}
