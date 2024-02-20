package com.gdn.blibli.library.repository;

import com.gdn.blibli.library.entity.Member;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MemberRepository extends ReactiveMongoRepository<Member, String> {
}
