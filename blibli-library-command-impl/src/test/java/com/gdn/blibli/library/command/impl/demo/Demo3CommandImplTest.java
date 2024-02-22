package com.gdn.blibli.library.command.impl.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class Demo3CommandImplTest {

  @InjectMocks
  private Demo3CommandImpl commandImpl;

  @BeforeEach
  public void setUp() {
    initMocks(this);
  }

  @AfterEach
  public void tearDown() {
  }

  @Test
  public void createBookCommandTest() {
    StepVerifier.create(this.commandImpl.execute("test"))
        .assertNext(Assertions::assertTrue).expectComplete().verify();
  }
}
