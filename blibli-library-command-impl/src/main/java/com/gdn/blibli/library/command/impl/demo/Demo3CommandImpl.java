package com.gdn.blibli.library.command.impl.demo;

import com.gdn.blibli.library.command.demo.Demo3Command;
import com.gdn.blibli.library.enums.ErrorCodes;
import com.gdn.blibli.library.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
public class Demo3CommandImpl implements Demo3Command {

  @Override
  public Mono<Boolean> execute(String request) {

    Mono<Integer> mono1 = Mono.just(1);
    Flux<Integer> flux1 = Flux.just(1, 2, 3, 4, 5);
    Flux<Integer> flux2 = Flux.range(1, 10);
//
//    mono1.map(value -> 3).subscribe();
//    flux1.map(value -> value + 5);

//    mono1.flatMap(value -> Mono.just(value + 1));
//    mono1.map(value -> Flux.just(value + 1, value + 2));
//    flux1.flatMap(Mono::just).subscribe();
//
//    mono1.filter(value -> value == 2).subscribe();

    flux2.filter(value -> value % 2 == 0)
        .concatWith(Flux.error(new BusinessException(ErrorCodes.DATA_NOT_FOUND)))
        .doOnNext(System.out::println)
        .doOnError(e -> System.out.println("error! error: " + e.getMessage()))
        .doOnComplete(() -> System.out.println("Success sampai trakhir")).subscribe();

//    flux1.filter(value -> value % 2 == 0)
//        .concatWith(Flux.error(new BusinessException(ErrorCodes.DATA_NOT_FOUND)))
//        .concatWith(Flux.just(999, 1000))
//            .doOnNext(System.out::println)
//        .doOnComplete(() -> System.out.println("completed"))
//        .doOnError(e -> System.out.println("error! " + e.getMessage()))
//                .subscribe();

//    flux1.window(3).map(innerFlux -> innerFlux.doOnNext(System.out::println).subscribe()).subscribe();

    return Mono.just(Boolean.TRUE);
  }
}
