package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration
public class IndividialProjectApplicationUnitTests {

  @Mock
  private MyFileDatabase mockDatabase;

  private IndividualProjectApplication app;

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
    mockDatabase = mock(MyFileDatabase.class);
    app.myFileDatabase = mockDatabase;
    app.onTermination();
    // Make sure saveContentsToFile was called
    verify(mockDatabase, times(1)).saveContentsToFile();
  }


}

