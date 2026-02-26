package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.BranchTask;
import model.LeafTask;
import model.ProgressSnapshot;
import model.Project;
import model.ProjectList;

// ATTRIBUTION: based on JsonSerializationDemo
@ExcludeFromJacocoGeneratedReport
public class JsonReaderTest {

    @Test
    public void testReaderNonexistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ProjectList projectList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyProjectList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProjectList.json");
        try {
            ProjectList projectList = reader.read();
            assertTrue(projectList.getProjects().isEmpty());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderEmptyProject() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProject.json");
        try {
            ProjectList projectList = reader.read();
            assertEquals(1, projectList.getProjects().size());
            Project project = projectList.getProjects().get(0);
            assertEquals("Project 1", project.getName());
            assertEquals("Project 1 Description", project.getDescription());
            assertTrue(project.getTasks().isEmpty());
            assertTrue(project.getProgressHistory().isEmpty());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderGeneralProject() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralProject.json");
        try {
            ProjectList projectList = reader.read();
            assertEquals(1, projectList.getProjects().size());
            Project project = projectList.getProjects().get(0);
            assertEquals("Project 1", project.getName());
            assertEquals("Project 1 Description", project.getDescription());
            ProgressSnapshot progressHistory1 = project.getProgressHistory().get(0);
            assertEquals(progressHistory1.getTime(), LocalDateTime.of(2026, 2, 21, 16, 14, 20));
            assertEquals(progressHistory1.getCompletionPercentage(), 40);
            ProgressSnapshot progressHistory2 = project.getProgressHistory().get(1);
            assertEquals(progressHistory2.getTime(), LocalDateTime.of(2026, 2, 20, 12, 14, 20));
            assertEquals(progressHistory2.getCompletionPercentage(), 50);
            BranchTask task1 = (BranchTask) project.getTasks().get(0);
            assertEquals("Task 1", task1.getName());
            assertEquals("Task 1 Description", task1.getDescription());
            LeafTask task2 = (LeafTask) task1.getSubtasks().get(0);
            assertEquals("Task 2", task2.getName());
            assertEquals("Task 2 Description", task2.getDescription());
            assertEquals(21, task2.getDueDate().getDay());
            assertEquals(2, task2.getDueDate().getMonth());
            assertEquals(2026, task2.getDueDate().getYear());
            assertEquals(10, task2.getWeight());
            assertTrue(task2.isCompleted());
            LeafTask task3 = (LeafTask) project.getTasks().get(1);
            assertEquals("Task 3", task3.getName());
            assertEquals("Task 3 Description", task3.getDescription());
            assertEquals(20, task3.getDueDate().getDay());
            assertEquals(2, task3.getDueDate().getMonth());
            assertEquals(2026, task3.getDueDate().getYear());
            assertEquals(20, task3.getWeight());
            assertFalse(task3.isCompleted());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderMultipleProject() {
        JsonReader reader = new JsonReader("./data/testReaderMultipleProject.json");
        try {
            ProjectList projectList = reader.read();
            assertEquals(2, projectList.getProjects().size());
            Project project1 = projectList.getProjects().get(0);
            assertEquals("Project 1", project1.getName());
            assertEquals("Project 1 Description", project1.getDescription());
            LeafTask task3 = (LeafTask) project1.getTasks().get(0);
            assertEquals("Task 3", task3.getName());
            assertEquals("Task 3 Description", task3.getDescription());
            assertEquals(20, task3.getDueDate().getDay());
            assertEquals(2, task3.getDueDate().getMonth());
            assertEquals(2026, task3.getDueDate().getYear());
            assertEquals(20, task3.getWeight());
            assertFalse(task3.isCompleted());
            ProgressSnapshot progressHistory1 = project1.getProgressHistory().get(0);
            assertEquals(progressHistory1.getTime(), LocalDateTime.of(2026, 2, 21, 16, 14, 20));
            assertEquals(progressHistory1.getCompletionPercentage(), 40);
            ProgressSnapshot progressHistory2 = project1.getProgressHistory().get(1);
            assertEquals(progressHistory2.getTime(), LocalDateTime.of(2026, 2, 20, 12, 14, 20));
            assertEquals(progressHistory2.getCompletionPercentage(), 50);
            Project project2 = projectList.getProjects().get(1);
            LeafTask task1 = (LeafTask) project2.getTasks().get(0);
            assertEquals("Task 1", task1.getName());
            assertEquals("Task 1 Description", task1.getDescription());
            assertEquals(15, task1.getDueDate().getDay());
            assertEquals(1, task1.getDueDate().getMonth());
            assertEquals(2026, task1.getDueDate().getYear());
            assertEquals(30, task1.getWeight());
            assertTrue(task1.isCompleted());
            ProgressSnapshot progressHistory3 = project2.getProgressHistory().get(0);
            assertEquals(progressHistory3.getTime(), LocalDateTime.of(2026, 2, 21, 15, 14, 20));
            assertEquals(progressHistory3.getCompletionPercentage(), 70);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderNestedBranch() {
        JsonReader reader = new JsonReader("./data/testReaderNestedBranch.json");
        try {
            ProjectList projectList = reader.read();
            assertEquals(1, projectList.getProjects().size());
            Project project = projectList.getProjects().get(0);
            assertEquals("Project 1", project.getName());
            assertEquals("Project 1 Description", project.getDescription());
            ProgressSnapshot progressHistory1 = project.getProgressHistory().get(0);
            assertEquals(progressHistory1.getTime(), LocalDateTime.of(2026, 2, 21, 16, 14, 20));
            assertEquals(progressHistory1.getCompletionPercentage(), 40);
            ProgressSnapshot progressHistory2 = project.getProgressHistory().get(1);
            assertEquals(progressHistory2.getTime(), LocalDateTime.of(2026, 2, 20, 12, 14, 20));
            assertEquals(progressHistory2.getCompletionPercentage(), 50);
            BranchTask task1 = (BranchTask) project.getTasks().get(0);
            assertEquals("Task 1", task1.getName());
            assertEquals("Task 1 Description", task1.getDescription());
            BranchTask task2 = (BranchTask) task1.getSubtasks().get(0);
            assertEquals("Task 2", task2.getName());
            assertEquals("Task 2 Description", task2.getDescription());
            LeafTask task3 = (LeafTask) task2.getSubtasks().get(0);
            assertEquals("Task 3", task3.getName());
            assertEquals("Task 3 Description", task3.getDescription());
            assertEquals(20, task3.getDueDate().getDay());
            assertEquals(2, task3.getDueDate().getMonth());
            assertEquals(2026, task3.getDueDate().getYear());
            assertEquals(20, task3.getWeight());
            assertFalse(task3.isCompleted());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }
    
}
