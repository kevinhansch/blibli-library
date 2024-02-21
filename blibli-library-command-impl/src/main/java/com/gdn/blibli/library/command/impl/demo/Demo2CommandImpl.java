package com.gdn.blibli.library.command.impl.demo;

import com.gdn.blibli.library.command.demo.Demo2Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class Demo2CommandImpl implements Demo2Command {

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
      System.out.println("DEMO 2 Logged number " + i);
      Thread.sleep(1000);
    }
    return Boolean.TRUE;
  }
}
