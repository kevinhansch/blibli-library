package com.gdn.blibli.library.command.impl.demo;

import com.gdn.blibli.library.command.demo.Demo1Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class Demo1CommandImpl implements Demo1Command {

  @Override
  public Mono<Boolean> execute(String request) {
    try {
      return Mono.just(this.printLog());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private Boolean printLog() throws InterruptedException {
    for (int i = 0; i < 10; i++) {
      System.out.println("DEMO 1 Logged number " + i);
      Thread.sleep(1000);
    }
    return Boolean.TRUE;
  }
}
