package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the MyFileDatabase class. This class contains tests to verify the functionality of
 * the MyFileDatabase class.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyFileDatabaseUnitTests {

  /**
   * Sets up the testing environment by creating a temporary file, initializing the MyFileDatabase,
   * and preparing a Department with a mapping of courses. This method is run before all test
   * methods in the class, and it creates the temporary file and department mapping available for
   * the tests.
   *
   * @throws IOException if an I/O error occurs while creating the temporary file.
   */
  @BeforeAll
  public static void setUp() throws IOException {
    tempFile = File.createTempFile("testfile", ".txt");
    tempFile.deleteOnExit();
    myFileDatabase = new MyFileDatabase(1, tempFile.getAbsolutePath());
    departmentMapping = new HashMap<>();
    Map<String, Course> coursesMapping = new HashMap<>();
    department = new Department("COMS", coursesMapping, "Luca Carloni", 2700);
    departmentMapping.put("COMS", department);
  }

  @Test
  @Order(1)
  public void setMappingTest() {
    myFileDatabase.setMapping(departmentMapping);
    assertEquals(departmentMapping, myFileDatabase.getDepartmentMapping());
  }


  @Test
  @Order(2)
  public void saveContentsToFileTest() throws IOException, ClassNotFoundException {
    myFileDatabase.saveContentsToFile();
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(tempFile))) {
      Map<String, Department> savedMapping = (HashMap<String, Department>) in.readObject();
      assertEquals(departmentMapping, savedMapping);
    }
  }

  @Test
  @Order(3)
  public void deSerializeObjectFromFileTest() {
    Map<String, Department> deserializedMapping = myFileDatabase.deSerializeObjectFromFile();
    assertEquals(departmentMapping, deserializedMapping);
  }

  @Test
  @Order(4)
  public void toStringTest() {
    String result = myFileDatabase.toString();
    assertEquals("For the COMS department: \n" + department.toString(), result);
  }

  private static Map<String, Department> departmentMapping;
  private static Department department;
  private static MyFileDatabase myFileDatabase;
  private static File tempFile;
}

