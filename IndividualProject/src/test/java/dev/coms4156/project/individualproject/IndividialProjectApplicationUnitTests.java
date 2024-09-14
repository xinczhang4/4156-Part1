package dev.coms4156.project.individualproject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the IndividualProjectApplication class. This class contains tests to verify the
 * functionality of the application's setup and termination behavior.
 */
@SpringBootTest
@ContextConfiguration
public class IndividialProjectApplicationUnitTests {

  private IndividualProjectApplication app;

  /**
   * Sets up the test environment before each test case by initializing Mockito annotations and
   * creating a new instance of IndividualProjectApplication.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    app = new IndividualProjectApplication();
  }

  @Test
  void testSetUpMethod() {
    String[] args = {"setup", "Unknown"};
    app.main(args);
    // No need for assertions; just check for no exceptions in the main method
  }

  @Test
  void testOnTermination() {
    MyFileDatabase mockDatabase;
    mockDatabase = mock(MyFileDatabase.class);
    app.myFileDatabase = mockDatabase;
    app.onTermination();
    // Make sure saveContentsToFile was called
    verify(mockDatabase, times(1)).saveContentsToFile();
  }


}

