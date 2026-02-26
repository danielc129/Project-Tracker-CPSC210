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
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonexistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ProjectList projectList = reader.read();
            fail("IOException expected\n" + projectList.toString());
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyProjectList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProjectList.json");
        try {
            ProjectList projectList = reader.read();
            checkEmptyProjectList(projectList);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderEmptyProject() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProject.json");
        try {
            ProjectList projectList = reader.read();
            checkEmptyProject(projectList);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderGeneralProject() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralProject.json");
        try {
            ProjectList projectList = reader.read();
            checkGeneralProject(projectList);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderMultipleProject() {
        JsonReader reader = new JsonReader("./data/testReaderMultipleProject.json");
        try {
            ProjectList projectList = reader.read();
            checkMultipleProject(projectList);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderNestedBranch() {
        JsonReader reader = new JsonReader("./data/testReaderNestedBranch.json");
        try {
            ProjectList projectList = reader.read();
            checkNestedBranch(projectList);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReadFile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProjectList.json");
        try {
            String readResult = reader.readFile("./data/testReaderEmptyProjectList.json");
            String expected = "{    \"projects\": []}";
            assertEquals(expected, readResult);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }
    
}
