package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


/**
 * Unit tests for the RouteController class. This class contains tests to verify the functionality
 * of the RouteController class.
 */
@ContextConfiguration
@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(RouteController.class)
public class RouteControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @InjectMocks
  private RouteController testRouteController;


  @Mock
  private MyFileDatabase myFileDatabase;

  /**
   * Sets up the test environment before all test cases by initializing test data for the Department
   * and Course classes.
   *
   * @throws Exception if any error occurs during setup
   */
  @BeforeAll
  public static void setUpBeforeClass() throws Exception {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};

    //data for psyc dept
    psyc1001 = new Course("Patricia G Lindemann", "501 SCH", "1:10-2:25", 200);
    psyc1001.setEnrolledStudentCount(191);
    Course psyc1610 = new Course("Christopher Baldassano", "200 SCH", times[2], 45);
    psyc1610.setEnrolledStudentCount(42);
    Course psyc2235 = new Course("Katherine T Fox-Glassman", "501 SCH", times[0], 125);
    psyc2235.setEnrolledStudentCount(128);
    Course psyc2620 = new Course("Jeffrey M Cohen", "303 URIS", "1:10-3:40", 60);
    psyc2620.setEnrolledStudentCount(55);
    Course psyc3212 = new Course("Mayron Piccolo", "200 SCH", "2:10-4:00", 15);
    psyc3212.setEnrolledStudentCount(15);
    Course psyc3445 = new Course("Mariam Aly", "405 SCH", "2:10-4:00", 12);
    psyc3445.setEnrolledStudentCount(12);
    Course psyc4236 = new Course("Trenton Jerde", "405 SCH", "6:10-8:00", 18);
    psyc4236.setEnrolledStudentCount(17);
    Course psyc4493 = new Course("Jennifer Blaze", "200 SCH", "2:10-4:00", 15);
    psyc4493.setEnrolledStudentCount(9);

    Map<String, Course> courses = new HashMap<>();
    courses.put("1001", psyc1001);
    courses.put("1610", psyc1610);
    courses.put("2235", psyc2235);
    courses.put("2620", psyc2620);
    courses.put("3212", psyc3212);
    courses.put("3445", psyc3445);
    courses.put("4236", psyc4236);
    courses.put("4493", psyc4493);

    psyc = new Department("PSYC", courses, "Nim Tottenham", 437);
  }


  @Test
  public void indexTest() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(
            "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
                + "\n\n This can be done using the following format: \n\n http:127.0.0"
                + ".1:8080/endpoint?arg=value"));
  }

  @Test
  public void retrieveDepartmentSuccessTest() throws Exception {
    mockMvc.perform(get("/retrieveDept").param("deptCode", "PSYC"))
        .andExpect(status().isOk())
        .andExpect(content().string(psyc.toString()));
  }


  @Test
  public void retrieveDepartmentFailedTest() throws Exception {
    mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "UNKNOWN"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }


  @Test
  public void retrieveCourseSuccessTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001"))
        .andExpect(status().isOk())
        .andExpect(content().string(psyc1001.toString()));
  }

  @Test
  public void retrieveCourseDepartmentNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "Unknown")
            .param("courseCode", "1001"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void retrieveCourseCourseNotFoundTest() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "PSYC")
            .param("courseCode", "9000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void isCourseFullTrueTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "PSYC")
            .param("courseCode", "2235"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  public void isCourseFullFalseTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001"))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  public void isCourseFullCourseNotFoundTest() throws Exception {
    mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "PSYC")
            .param("courseCode", "6000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void getMajorCtFromDeptTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "PSYC"))
        .andExpect(status().isOk())
        .andExpect(content().string("There are: 437 majors in the department"));
  }

  @Test
  public void getMajorCtFromDeptNotFoundTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "UNKNOWN"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "PSYC"))
        .andExpect(status().isOk())
        .andExpect(content().string("Nim Tottenham is the department chair."));
  }

  @Test
  public void identifyDeptChairtDeptNotFoundTest() throws Exception {
    mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "UNKNOWN"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001"))
        .andExpect(status().isOk())
        .andExpect(content().string("501 SCH is where the course is located."));
  }

  @Test
  public void findCourseLocationNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "PSYC")
            .param("courseCode", "6000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001"))
        .andExpect(status().isOk())
        .andExpect(content().string("Patricia G Lindemann is the instructor for the course."));
  }

  @Test
  public void findCourseInstructorCourseNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "PSYC")
            .param("courseCode", "6000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001"))
        .andExpect(status().isOk())
        .andExpect(content().string("The course meets at: 1:10-2:25"));
  }

  @Test
  public void findCourseTimeNotFoundTest() throws Exception {
    mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "PSYC")
            .param("courseCode", "6000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void addMajorToDeptTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
            .param("deptCode", "PSYC"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully"));
  }

  @Test
  public void addMajorToDeptNotFoundTest() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
            .param("deptCode", "UNKNOWN"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void removeMajorFromDeptTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "PSYC"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  public void removeMajorFromDeptNotFoundTest() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
            .param("deptCode", "UNKNOWN"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  @Order(1)
  public void dropStudentSuccessTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001"))
        .andExpect(status().isOk())
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void dropStudentCourseNotFoundTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "PSYC")
            .param("courseCode", "6000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  @Order(2)
  public void setEnrollmentCountTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001")
            .param("count", "0"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void setEnrollmentCountNotFoundTest() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "PSYC")
            .param("courseCode", "6000")
            .param("count", "100"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  @Order(3)
  public void dropStudentFailedTest() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Student has not been dropped."));
  }

  @Test
  @Order(4)
  public void setEnrollmentCountBack() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001")
            .param("count", "191"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseTimeTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001")
            .param("time", "1:10-2:25"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseTeacherTest() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001")
            .param("teacher", "Patricia G Lindemann"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
            .param("deptCode", "PSYC")
            .param("courseCode", "1001")
            .param("location", "501 SCH"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  private static Department psyc;
  private static Course psyc1001;

}
