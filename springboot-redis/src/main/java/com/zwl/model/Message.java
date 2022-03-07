package com.zwl.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.connection.stream.RecordId;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/4 18:08
 **/
@Data
@Builder
public class Message {

    Long senderId;
    Long receiverId;
    String content;

}
