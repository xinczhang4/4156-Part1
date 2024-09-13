package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.openMocks;


import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.mockito.plugins.MockMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


/**
 * Unit tests for the Course class. This class contains tests to verify the functionality of the
 * Course class.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(OrderAnnotation.class)
public class RouteControllerUnitTests {

  @InjectMocks
  private RouteController testRouteController;
  private IndividualProjectApplication individualProjectApplication;

  @Mock
  private MyFileDatabase myFileDatabase;

  @BeforeAll
  public static void setUpBeforeClass(){
    departmentMapping = new HashMap<>();
    coursesMapping = new HashMap<>();
    course = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    course.setEnrolledStudentCount(125);
    coursesMapping.put("3251", course);
    department = new Department("COMS", coursesMapping, "Luca Carloni", 2700);
    departmentMapping.put("COMS", department);
  }


  @BeforeEach
  public void setUp() {
    openMocks(this);
  }

  @Test
  public void indexTest() {
    String s = "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
        + "\n\n This can be done using the following format: \n\n http:127.0.0"
        + ".1:8080/endpoint?arg=value";
    assertEquals(s, testRouteController.index());
  }

  @Test
  public void retrieveDepartmentSuccessTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(department.toString(), response.getBody());
  }


  @Test
  public void retrieveDepartmentFailedTest() {
    ResponseEntity<?> response = testRouteController.retrieveDepartment("UNKNOWN");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }


  @Test
  public void retrieveCourseSuccessTest() {
    String deptCode = "COMS";
    int courseCode = 3251;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = testRouteController.retrieveCourse(deptCode, courseCode);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(course.toString(), response.getBody());
  }

  @Test
  public void retrieveCourseDepartmentNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = testRouteController.retrieveCourse("UNKNOWN", 3251);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void retrieveCourseCourseNotFoundTest() {
    String deptCode = "COMS";
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
//    when(myFileDatabase.getDepartmentMapping().get(deptCode).getCourseSelection()).thenReturn(coursesMapping);

    ResponseEntity<?> response = testRouteController.retrieveCourse(deptCode, 3157);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void isCourseFullTrueTest() {
    String deptCode = "COMS";
    int courseCode = 3251;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = testRouteController.isCourseFull(deptCode, courseCode);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((boolean) response.getBody());
  }

  @Test
  public void isCourseFullFalseTest() {
    String deptCode = "COMS";
    int courseCode = 3251;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    departmentMapping.get(deptCode).getCourseSelection().get("3251").setEnrolledStudentCount(0);

    ResponseEntity<?> response = testRouteController.isCourseFull(deptCode, courseCode);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse((boolean) response.getBody());
  }

  @Test
  public void isCourseFullCourseNotFoundTest() {
    String deptCode = "COMS";
    int courseCode = 3157;
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    ResponseEntity<?> response = testRouteController.isCourseFull(deptCode, courseCode);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void getMajorCtFromDeptTest() {
    String deptCode = "COMS";
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept(deptCode);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("There are: 2700 majors in the department", response.getBody());
  }

  @Test
  public void getMajorCtFromDeptNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("UNKNOWN");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void identifyDeptChairTest() {
    String deptCode = "COMS";
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.identifyDeptChair(deptCode);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Luca Carloni is the department chair.", response.getBody());

  }

  @Test
  public void identifyDeptChairtDeptNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.identifyDeptChair("UNKNOWN");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void findCourseLocationTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("402 CHANDLER is where the course is located.", response.getBody());
  }

  @Test
  public void findCourseLocationNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 3157);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void findCourseTimeTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("The course meets at: 1:10-2:25", response.getBody());
  }

  @Test
  public void findCourseTimeNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 3157);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void addMajorToDeptTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.addMajorToDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated successfully", response.getBody());
  }

  @Test
  public void addMajorToDeptNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.addMajorToDept("UNKNOWN");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void removeMajorFromDeptTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", response.getBody());
  }

  @Test
  public void removeMajorFromDeptNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.removeMajorFromDept("UNKNOWN");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Department Not Found", response.getBody());
  }

  @Test
  public void dropStudentSuccessTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    departmentMapping.get("COMS").getCourseSelection().get("3251").setEnrolledStudentCount(100);
    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 3251);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Student has been dropped.", response.getBody());
  }

  @Test
  public void dropStudentFailedTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    departmentMapping.get("COMS").getCourseSelection().get("3251").setEnrolledStudentCount(0);
    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 3251);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Student has not been dropped.", response.getBody());
  }

  @Test
  public void dropStudentCourseNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.dropStudent("COMS", 3157);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void setEnrollmentCountTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 3251, 150);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void setEnrollmentCountNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.setEnrollmentCount("COMS", 3157, 150);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void changeCourseTimeTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 3251, "11:10-12:55");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void changeCourseTimeNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.changeCourseTime("COMS", 3157, "11:10-12:55");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void changeCourseTeacherTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 3251, "Jae Lee");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void changeCourseTeacherNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.changeCourseTeacher("COMS", 3157, "Jae Lee");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void changeCourseLocationTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 3251, "417 IAB");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void changeCourseLocationNotFoundTest() {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    ResponseEntity<?> response = testRouteController.changeCourseLocation("COMS", 3157, "417 IAB");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void retrieveDepartmentExceptionTest() {
    when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<?> response = testRouteController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }

  @Test
  public void retrieveCourseExceptionTest() {
    when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<?> response = testRouteController.retrieveCourse("COMS", 3251);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }

  @Test
  public void isCourseFullExceptionTest() {
    when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<?> response = testRouteController.isCourseFull("COMS", 3251);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }

  @Test
  public void getMajorCtFromDeptExceptionTest() {
    when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<?> response = testRouteController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }

  @Test
  public void identifyDeptChairExceptionTest() {
    when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<?> response = testRouteController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }

  @Test
  public void findCourseLocationExceptionTest() {
    when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<?> response = testRouteController.findCourseLocation("COMS", 3251);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }

  @Test
  public void findCourseInstructorExceptionTest() {
    when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<?> response = testRouteController.findCourseInstructor("COMS", 3251);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }

  @Test
  public void findCourseTimeExceptionTest() {
    when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException("Database error"));
    ResponseEntity<?> response = testRouteController.findCourseTime("COMS", 3251);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An Error has occurred", response.getBody());
  }


  private static HashMap<String, Department> departmentMapping;
  private static HashMap<String, Course> coursesMapping;
  private static Department department;
  private static Course course;

}
