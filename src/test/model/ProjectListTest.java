package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class ProjectListTest {
    private ProjectList projectList1;
    private Project project1;
    private Project project2;

    @BeforeEach
    public void setup() {
        projectList1 = new ProjectList();
        project1 = new Project("Test Project 1", "Test Description 1");
        project2 = new Project("Test Project 2", "Test Description 2");
    }

    @Test
    public void testConstructor() {
        assertTrue(projectList1.getProjects().isEmpty());
    }

    @Test
    public void testAddProject() {
        projectList1.addProject(project1);
        ArrayList<Project> expectedResult = new ArrayList<>();
        expectedResult.add(project1);
        assertEquals(projectList1.getProjects(), expectedResult);
        projectList1.addProject(project2);
        expectedResult.add(project2);
        assertEquals(projectList1.getProjects(), expectedResult);
    }

    @Test
    public void testRemoveProject() {
        projectList1.addProject(project1);
        projectList1.addProject(project2);
        projectList1.removeProject(project1);
        ArrayList<Project> expectedResult = new ArrayList<>();
        expectedResult.add(project2);
        assertEquals(projectList1.getProjects(), expectedResult);
        projectList1.removeProject(project2);
        assertTrue(projectList1.getProjects().isEmpty());
    }

}
