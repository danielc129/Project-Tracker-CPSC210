package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class BranchTaskTest {
    private BranchTask branch1;
    private BranchTask branch3;
    private BranchTask branch2;
    private BranchTask branch4;
    private BranchTask branch5;
    private BranchTask branch6;
    private LeafTask leaf1;
    private LeafTask leaf2;
    private LeafTask leaf3;
    private LeafTask leaf5;
    private LeafTask leaf6;
    private LeafTask leaf4;
    private LeafTask leaf7;
    private LeafTask leaf8;
    private LeafTask leaf9;
    private LeafTask leaf10;
    private LeafTask leaf11;
    

    @BeforeEach
    public void setup() {
        leaf1 = new LeafTask("Leaf Task 1", "Test Description L1", new Date(1, 1, 2026), 10);
        leaf2 = new LeafTask("Leaf Task 2", "Test Description L2", new Date(5, 1, 2026), 5);
        leaf3 = new LeafTask("Leaf Task 3", "Test Description L3", new Date(3, 1, 2026), 20);
        ArrayList<Task> subtaskList1 = new ArrayList<>();
        subtaskList1.add(leaf1);
        subtaskList1.add(leaf2);
        subtaskList1.add(leaf3);
        branch1 = new BranchTask("Branch Task 1", "Test Description B1", subtaskList1);

        leaf4 = new LeafTask("Leaf Task 6", "Test Description L6", new Date(4, 11, 2025), 30);
        ArrayList<Task> subtaskList2 = new ArrayList<>();
        subtaskList2.add(leaf4);
        branch2 = new BranchTask("Branch Task 3", "Test Description B3", subtaskList2);

        leaf5 = new LeafTask("Leaf Task 4", "Test Description L4", new Date(5, 3, 2025), 25);
        leaf6 = new LeafTask("Leaf Task 5", "Test Description L5", new Date(6, 2, 2025), 3);
        ArrayList<Task> subtaskList3 = new ArrayList<>();
        subtaskList3.add(branch2);
        subtaskList3.add(leaf5);
        subtaskList3.add(leaf6);
        branch3 = new BranchTask("Branch Task 2", "Test Description B2", subtaskList3);

        leaf7 = new LeafTask("Leaf Task 7", "Test Description L7", new Date(7, 6, 2025), 50);
        
        leaf8 = new LeafTask("Leaf Task 8", "Test Description L8", new Date(7, 1, 2025), 10);
        ArrayList<Task> subtaskList4 = new ArrayList<>();
        subtaskList4.add(leaf8);
        branch4 = new BranchTask("Branch Task 4", "Test Description B4", subtaskList4);

        leaf9 = new LeafTask("Leaf Task 9", "Test Description L9", new Date(8, 3, 2025), 10);
        leaf10 = new LeafTask("Leaf Task 10", "Test Description L10", new Date(9, 4, 2025), 20);
        ArrayList<Task> subtaskList5 = new ArrayList<>();
        subtaskList5.add(leaf9);
        subtaskList5.add(leaf10);
        branch5 = new BranchTask("Branch Task 5", "Test Description B5", subtaskList5);

        leaf11 = new LeafTask("Leaf Task 11", "Test Description L11", new Date(10, 5, 2025), 15);
        ArrayList<Task> subtaskList6 = new ArrayList<>();
        subtaskList6.add(branch5);
        subtaskList6.add(leaf11);
        branch6 = new BranchTask("Branch Task 6", "Test Description B6", subtaskList6);
    }

    @Test
    public void testConstructor() {
        assertEquals("Branch Task 2", branch3.getName());
        assertEquals("Test Description B2", branch3.getDescription());
        ArrayList<Task> expectedSubtasks = new ArrayList<>();
        expectedSubtasks.add(branch2);
        expectedSubtasks.add(leaf5);
        expectedSubtasks.add(leaf6);
        assertEquals(expectedSubtasks, branch3.getSubtasks());
        ArrayList<Task> expectedSubtasks2 = new ArrayList<>();
        expectedSubtasks2.add(leaf4);
        assertEquals(expectedSubtasks2, branch2.getSubtasks());
    }

    @Test
    public void testAddSubtaskLeaf() {
        ArrayList<Task> expectedSubtasks = new ArrayList<>();
        expectedSubtasks.add(leaf1);
        expectedSubtasks.add(leaf2);
        expectedSubtasks.add(leaf3);
        expectedSubtasks.add(leaf7);
        branch1.addSubtask(leaf7);
        assertEquals(expectedSubtasks, branch1.getSubtasks());

        ArrayList<Task> expectedSubtasks2 = new ArrayList<>();
        expectedSubtasks2.add(branch2);
        expectedSubtasks2.add(leaf5);
        expectedSubtasks2.add(leaf6);
        expectedSubtasks2.add(leaf7);
        branch3.addSubtask(leaf7);
        assertEquals(expectedSubtasks2, branch3.getSubtasks());
    }

    @Test
    public void testAddSubtaskBranch() {
        ArrayList<Task> expectedSubtasks = new ArrayList<>();
        expectedSubtasks.add(leaf1);
        expectedSubtasks.add(leaf2);
        expectedSubtasks.add(leaf3);
        expectedSubtasks.add(branch3);
        branch1.addSubtask(branch3);
        assertEquals(expectedSubtasks, branch1.getSubtasks());

        ArrayList<Task> expectedSubtasks2 = new ArrayList<>();
        expectedSubtasks2.add(branch2);
        expectedSubtasks2.add(leaf5);
        expectedSubtasks2.add(leaf6);
        expectedSubtasks2.add(branch1);
        branch3.addSubtask(branch1);
        assertEquals(expectedSubtasks2, branch3.getSubtasks());
    }

    @Test
    public void testAddSubtaskMultiple() {
        ArrayList<Task> expectedSubtasks = new ArrayList<>();
        expectedSubtasks.add(leaf1);
        expectedSubtasks.add(leaf2);
        expectedSubtasks.add(leaf3);
        expectedSubtasks.add(leaf7);
        branch1.addSubtask(leaf7);
        assertEquals(expectedSubtasks, branch1.getSubtasks());
        expectedSubtasks.add(branch3);
        branch1.addSubtask(branch3);
        assertEquals(expectedSubtasks, branch1.getSubtasks());
    }

    @Test
    public void testRemoveSubtask() {
        branch1.addSubtask(leaf7);
        branch1.addSubtask(branch3);
        ArrayList<Task> expectedSubtasks = new ArrayList<>();
        expectedSubtasks.add(leaf1);
        expectedSubtasks.add(leaf2);
        expectedSubtasks.add(leaf3);
        expectedSubtasks.add(branch3);
        branch1.removeSubtask(leaf7);
        assertEquals(expectedSubtasks, branch1.getSubtasks());
        expectedSubtasks.remove(branch3);
        branch1.removeSubtask(branch3);
        assertEquals(expectedSubtasks, branch1.getSubtasks());
    }

    @Test
    public void testGetDueDateSingleSubtask() {
        Date result = branch4.getDueDate();
        assertEquals(7, result.getDay());
        assertEquals(1, result.getMonth());
        assertEquals(2025, result.getYear());
    }

    @Test
    public void testGetDueDateLeafSubtasksOnly() {
        Date result = branch1.getDueDate();
        assertEquals(5, result.getDay());
        assertEquals(1, result.getMonth());
        assertEquals(2026, result.getYear());
    }

    @Test
    public void testGetDueDateMixedSubtasks() {
        Date result = branch3.getDueDate();
        assertEquals(4, result.getDay());
        assertEquals(11, result.getMonth());
        assertEquals(2025, result.getYear());
    }

    @Test
    public void testGetWeightLeafSubtasksOnly() {
        assertEquals(35, branch1.getWeight());
    }

    @Test
    public void testGetWeightMixedSubtasks() {
        assertEquals(58, branch3.getWeight());
    }

    @Test
    public void testGetCompletionPercentageOneSubtaskIncomplete() {
        assertFalse(leaf8.isCompleted());
        assertEquals(0, branch4.getCompletionPercentage());
    }

    @Test
    public void testGetCompletionPercentageOneSubtaskComplete() {
        leaf8.setCompletion(true);
        assertTrue(leaf8.isCompleted());
        assertEquals(100, branch4.getCompletionPercentage());
    }

    @Test
    public void testGetCompletionPercentageLeafSubtasksOnly() {
        assertFalse(leaf1.isCompleted());
        assertFalse(leaf2.isCompleted());
        assertFalse(leaf3.isCompleted());
        assertEquals(0, branch1.getCompletionPercentage());
        leaf3.setCompletion(true);
        assertEquals((int) (100 * (20.0 / 35)), branch1.getCompletionPercentage());
        leaf1.setCompletion(true);
        assertEquals((int) (100 * (30.0 / 35)), branch1.getCompletionPercentage());
        leaf2.setCompletion(true);
        assertEquals(100, branch1.getCompletionPercentage());
    }

    @Test
    public void testGetCompletionPercentageMixedSubtasks() {
        assertEquals(0, branch6.getCompletionPercentage());
        leaf9.setCompletion(true);
        assertEquals((int) (100 * (10.0 / 45)), branch6.getCompletionPercentage());
        leaf11.setCompletion(true);
        assertEquals((int) (100 * (25.0 / 45)), branch6.getCompletionPercentage());
        leaf10.setCompletion(true);
        assertEquals(100, branch6.getCompletionPercentage());
    }

    @Test
    public void testGetStringFormatLeafSubtasksOnly() {
        leaf2.setCompletion(true);
        leaf3.setCompletion(true);
    }

    @Test
    public void testGetStringFormatMixedSubtasks() {
        leaf5.setCompletion(true);
        String expected = "[ ] Branch Task 2: Test Description B2 (Due: November 4, 2025 | Progress: 43%)" +
                          "\n    [ ] Leaf Task 5: Test Description L5 (Due: February 6, 2025 | Weight: 3)" +
                          "\n    [X] Leaf Task 4: Test Description L4 (Due: March 5, 2025 | Weight: 25)" +
                          "\n    [ ] Branch Task 3: Test Description B3 (Due: November 4, 2025 | Progress: 0%)" +
                          "\n        [ ] Leaf Task 6: Test Description L6 (Due: November 4, 2025 | Weight: 30)";
        assertEquals(expected, branch3.getStringFormat());
    }

    @Test
    public void testGetStringFormatFullyCompleted() {
        leaf1.setCompletion(true);
        leaf2.setCompletion(true);
        leaf3.setCompletion(true);
        String expected = "[X] Branch Task 1: Test Description B1 (Due: January 5, 2026 | Progress: 100%)" +
                          "\n    [X] Leaf Task 1: Test Description L1 (Due: January 1, 2026 | Weight: 10)" +
                          "\n    [X] Leaf Task 3: Test Description L3 (Due: January 3, 2026 | Weight: 20)" +
                          "\n    [X] Leaf Task 2: Test Description L2 (Due: January 5, 2026 | Weight: 5)";
        assertEquals(expected, branch1.getStringFormat());
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
        BranchTask branchTask = new BranchTask("", "", inputTasks);
        assertEquals(inputTasks, branchTask.getSortedSubtasks());
    }

    @Test
    public void testGetSortedSubtasksReverseOrder() {
        LeafTask leafA = new LeafTask("", "", new Date(3, 1, 2025), 5);
        LeafTask leafB = new LeafTask("", "", new Date(2, 1, 2025), 5);
        LeafTask leafC = new LeafTask("", "", new Date(1, 1, 2025), 5);
        ArrayList<Task> inputTasks = new ArrayList<>();
        inputTasks.add(leafA);
        inputTasks.add(leafB);
        inputTasks.add(leafC);
        BranchTask branchTask = new BranchTask("", "", inputTasks);
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(leafC);
        expectedResult.add(leafB);
        expectedResult.add(leafA);
        assertEquals(expectedResult, branchTask.getSortedSubtasks());
    }

    @Test
    public void testGetSortedSubtasksMixedOrder() {
        LeafTask leafA = new LeafTask("", "", new Date(3, 1, 2025), 5);
        LeafTask leafB = new LeafTask("", "", new Date(2, 1, 2025), 5);
        LeafTask leafC = new LeafTask("", "", new Date(1, 1, 2025), 5);
        ArrayList<Task> inputTasks = new ArrayList<>();
        inputTasks.add(leafC);
        inputTasks.add(leafA);
        inputTasks.add(leafB);
        BranchTask branchTask = new BranchTask("", "", inputTasks);
        ArrayList<Task> expectedResult = new ArrayList<>();
        expectedResult.add(leafC);
        expectedResult.add(leafB);
        expectedResult.add(leafA);
        assertEquals(expectedResult, branchTask.getSortedSubtasks());
    }

    @Test
    public void testSetCompletionTrue() {
        branch3.setCompletion(true);
        assertTrue(branch3.isCompleted());
    }

    @Test
    public void testSetCompletionFalse() {
        branch3.setCompletion(true);
        branch3.setCompletion(false);
        assertFalse(branch3.isCompleted());
    }

    @Test
    public void testGetFlattenedSubtasks() {
        List<LeafTask> result = branch3.getFlattenedSubtasks();
        List<LeafTask> expectedResult = new ArrayList<>();
        expectedResult.add(leaf4);
        expectedResult.add(leaf5);
        expectedResult.add(leaf6);
        assertEquals(expectedResult, result);
    }
}
