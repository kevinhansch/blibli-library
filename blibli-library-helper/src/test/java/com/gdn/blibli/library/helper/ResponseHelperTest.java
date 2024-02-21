package com.gdn.blibli.library.helper;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

public class ResponseHelperTest {

  @InjectMocks
  private ResponseHelper responseHelper;

  @BeforeEach
  public void setUp() throws Exception {
    initMocks(this);
  }
}
