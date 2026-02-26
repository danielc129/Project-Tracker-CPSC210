package persistence;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.ProjectList;

@ExcludeFromJacocoGeneratedReport
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/illegal:</filename.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyProject() {
        try {
            JsonReader reader1 = new JsonReader("./data/testReaderEmptyProject.json");
            ProjectList projectList = reader1.read();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyProject.json");
            writer.open();
            writer.write(projectList);
            writer.close();

            JsonReader reader2 = new JsonReader("./data/testWriterEmptyProject.json");
            ProjectList readProjectList = reader2.read();
            checkEmptyProject(readProjectList);
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
        
    }

    @Test
    public void testWriterEmptyProjectList() {
        try {
            JsonReader reader1 = new JsonReader("./data/testReaderEmptyProjectList.json");
            ProjectList projectList = reader1.read();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyProjectList.json");
            writer.open();
            writer.write(projectList);
            writer.close();

            JsonReader reader2 = new JsonReader("./data/testWriterEmptyProjectList.json");
            ProjectList readProjectList = reader2.read();
            checkEmptyProjectList(readProjectList);

        } catch (IOException e) {
            fail("Unexpected IOException");
        }   
    }

    @Test
    public void testWriterGeneralProject() {
        try {
            JsonReader reader1 = new JsonReader("./data/testReaderGeneralProject.json");
            ProjectList projectList = reader1.read();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralProject.json");
            writer.open();
            writer.write(projectList);
            writer.close();

            JsonReader reader2 = new JsonReader("./data/testWriterGeneralProject.json");
            ProjectList readProjectList = reader2.read();
            checkGeneralProject(readProjectList);

        } catch (IOException e) {
            fail("Unexpected IOException");
        }   
    }

    @Test
    public void testWriterMultipleProject() {
        try {
            JsonReader reader1 = new JsonReader("./data/testReaderMultipleProject.json");
            ProjectList projectList = reader1.read();
            JsonWriter writer = new JsonWriter("./data/testWriterMultipleProject.json");
            writer.open();
            writer.write(projectList);
            writer.close();

            JsonReader reader2 = new JsonReader("./data/testWriterMultipleProject.json");
            ProjectList readProjectList = reader2.read();
            checkMultipleProject(readProjectList);

        } catch (IOException e) {
            fail("Unexpected IOException");
        }   
    }

    @Test
    public void testWriterNestedBranch() {
        try {
            JsonReader reader1 = new JsonReader("./data/testReaderNestedBranch.json");
            ProjectList projectList = reader1.read();
            JsonWriter writer = new JsonWriter("./data/testWriterNestedBranch.json");
            writer.open();
            writer.write(projectList);
            writer.close();

            JsonReader reader2 = new JsonReader("./data/testWriterNestedBranch.json");
            ProjectList readProjectList = reader2.read();
            checkNestedBranch(readProjectList);

        } catch (IOException e) {
            fail("Unexpected IOException");
        }   
    }

}
