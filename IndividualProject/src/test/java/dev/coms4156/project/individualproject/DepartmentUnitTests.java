package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit tests for the Course class. This class contains tests to verify the functionality of the
 * Course class.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentUnitTests {

  @BeforeAll
  public static void setUpForTesting() {
//    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
//    coms1004.setEnrolledStudentCount(249);
//    Course coms3134 = new Course("Brian Borowski", "309 HAV", "4:10-5:25", 250);
//    coms3134.setEnrolledStudentCount(242);
//    courses.put("1004", coms1004);
//    courses.put("3134", coms3134);
    coms3157 = new Course("Jae Lee", "301 URIS", "10:10-11:25", 400);
    coms3157.setEnrolledStudentCount(311);
    courses = new HashMap<>();
    testcourses = new HashMap<>();
//    testcourses.put("1004", coms1004);
//    testcourses.put("3134", coms3134);
    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }


  @Test
  @Order(1)
  public void getNumberofMajorsTest() {
    assertEquals(2700, testDepartment.getNumberOfMajors());
  }

  @Test
  @Order(2)
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  @Order(3)
  public void getCourseSelectionTest() {
    assertEquals(testcourses, testDepartment.getCourseSelection());
  }

  @Test
  @Order(4)
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(2701, testDepartment.getNumberOfMajors());
  }

  @Test
  @Order(5)
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(2700, testDepartment.getNumberOfMajors());
  }

  @Test
  @Order(6)
  public void dropPersonFromCourseWhenZeroTest() {
    testDepartment = new Department("COMS", courses, "Luca Carloni", 0);
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
  }


  @Test
  @Order(7)
  public void addCourseTest() {
    testDepartment.addCourse("3157", coms3157);
    testcourses.put("3157", coms3157);
    assertEquals(testcourses, testDepartment.getCourseSelection());
  }

  @Test
  @Order(8)
  public void toStringTest() {
    assertEquals("COMS 3157: \nInstructor: Jae Lee; Location: 301 URIS; Time: 10:10-11:25\n", testDepartment.toString());
  }

  @Test
  @Order(8)
  public void createCourseTest() {
    testDepartment.createCourse("3251", "Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    testcourses.put("3251", coms3251);
    assertEquals(testcourses, testDepartment.getCourseSelection());
  }

  public static Department testDepartment;
  public static HashMap<String, Course> courses;
  public static HashMap<String, Course> testcourses;
  public static Course coms3157;
}
