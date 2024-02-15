package com.gdn.blibli.library.repository.impl;

import com.gdn.blibli.library.repository.BookRepository;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest(properties = "spring.data.mongodb.port=30001")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookRepositoryImplTest {

  private MongoServer server;

  @Autowired
  private BookRepository repository;

  @BeforeAll
  public void setUp() {
    server = new MongoServer(new MemoryBackend());
    server.bind("localhost", 30001);
  }

  @AfterEach
  public void tearEach() {
    this.repository.deleteAll().block();
  }

  @AfterAll
  public void tearDown() {
    server.shutdown();
  }
}
