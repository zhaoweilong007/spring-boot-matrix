package com.zwl;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhaoWeiLong
 * @since 2021/12/1
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(count = 2,ports = {9092,9093})
public class StartKafka {

  @Test
  public void contextLoads()throws IOException {
    System.in.read();
  }

}
