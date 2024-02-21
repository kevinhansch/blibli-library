package com.gdn.blibli.library.service.impl;

import com.gdn.blibli.library.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

  @Override
  public Boolean demo1() throws InterruptedException {
    for (int i = 0; i < 10; i++) {
      System.out.println("SERVICE DEMO 1 Logged for number " + i);
      Thread.sleep(1000);
    }
    return Boolean.TRUE;
  }

  @Override
  public Boolean demo2() throws InterruptedException {
    for (int i = 0; i < 10; i++) {
      System.out.println("SERVICE DEMO 2 Logged for number " + i);
      Thread.sleep(1000);
    }
    return Boolean.TRUE;
  }
}
