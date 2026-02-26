package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.ProjectList;

public class JsonWriterTest {

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
            assertEquals(reader1.readFile("./data/testReaderEmptyProject.json"), reader2.readFile("./data/testWriterEmptyProject.json"));

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
            assertEquals(reader1.readFile("./data/testReaderEmptyProjectList.json"), reader2.readFile("./data/testWriterEmptyProjectList.json"));

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
            assertEquals(reader1.readFile("./data/testReaderGeneralProject.json"), reader2.readFile("./data/testWriterGeneralProject.json"));

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
            assertEquals(reader1.readFile("./data/testReaderMultipleProject.json"), reader2.readFile("./data/testWriterMultipleProject.json"));

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
            assertEquals(reader1.readFile("./data/testReaderNestedBranch.json"), reader2.readFile("./data/testWriterNestedBranch.json"));

        } catch (IOException e) {
            fail("Unexpected IOException");
        }   
    }

}
