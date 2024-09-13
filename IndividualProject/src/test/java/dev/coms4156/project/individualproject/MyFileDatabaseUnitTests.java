package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.*;
import java.util.HashMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyFileDatabaseUnitTests {

  @BeforeAll
  public static void setUp() throws IOException {
    tempFile = File.createTempFile("testfile", ".txt");
    tempFile.deleteOnExit();
    myFileDatabase = new MyFileDatabase(1, tempFile.getAbsolutePath());
    departmentMapping = new HashMap<>();
    HashMap<String, Course> coursesMapping = new HashMap<>();
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
      HashMap<String, Department> savedMapping = (HashMap<String, Department>) in.readObject();
      assertEquals(departmentMapping, savedMapping);
    }
  }

  @Test
  @Order(3)
  public void deSerializeObjectFromFileTest(){
    HashMap<String, Department> deserializedMapping = myFileDatabase.deSerializeObjectFromFile();
    assertEquals(departmentMapping, deserializedMapping);
  }

  @Test
  @Order(4)
  public void toStringTest() {
    String result = myFileDatabase.toString();
    assertEquals("For the COMS department: \n" + department.toString(), result);
  }

  private static HashMap<String, Department> departmentMapping;
  private static Department department;
  private static MyFileDatabase myFileDatabase;
  private static File tempFile;
}

