package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit tests for the Course class. This class contains tests to verify the functionality of the
 * Course class.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  @Order(1)
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }


  @Test
  @Order(2)
  public void initialEnrollmentTest() {
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }


  @Test
  @Order(3)
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(0);
    assertTrue(testCourse.enrollStudent());
    assertEquals(1, testCourse.getEnrolledStudentCount());
  }

  @Test
  @Order(4)
  public void enrollStudentWhenFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
    assertEquals(250, testCourse.getEnrolledStudentCount());
  }

  @Test
  @Order(5)
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(100);
    assertTrue(testCourse.dropStudent());
    assertEquals(99, testCourse.getEnrolledStudentCount());
  }

  @Test
  @Order(6)
  public void dropStudentWhenZeroTest() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  @Order(7)
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Alice Wang");
    assertEquals("Alice Wang", testCourse.getInstructorName());
  }

  @Test
  @Order(8)
  public void reassignLocationTest() {
    testCourse.reassignLocation("633 Mudd");
    assertEquals("633 Mudd", testCourse.getCourseLocation());
  }

  @Test
  @Order(9)
  public void reassignTimeTest() {
    testCourse.reassignTime("1:10-2:25");
    assertEquals("1:10-2:25", testCourse.getCourseTimeSlot());
  }

  @Test
  @Order(10)
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(251);
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  @Order(10)
  public void isCourseNotFullTest() {
    testCourse.setEnrolledStudentCount(20);
    assertFalse(testCourse.isCourseFull());
  }

  @Test
  @Order(11)
  public void testEqualsHashCodeConsistency() {
    Course course1 = new Course("Instructor A", "Room 101", "1:10-2:25", 30);
    Course course2 = new Course("Instructor A", "Room 101", "1:10-2:25", 30);
    assertEquals(course1, course2);
    assertEquals(course1.hashCode(), course2.hashCode());
  }


  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;
}

