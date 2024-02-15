package com.gdn.blibli.library.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import brave.Tracer;
import brave.propagation.ExtraFieldPropagation;
import org.springframework.data.domain.ReactiveAuditorAware;
import reactor.core.publisher.Mono;

public class ReactiveAuditorAwareConfiguration implements ReactiveAuditorAware<String> {

  @Autowired
  private Tracer tracer;

  @Override
  public Mono<String> getCurrentAuditor() {
    String username = Optional.ofNullable(tracer.currentSpan())
        .map(currentSpan -> ExtraFieldPropagation.get(currentSpan.context(), "username"))
        .orElse("blibli-library");
    return Mono.just(username);
  }

}
