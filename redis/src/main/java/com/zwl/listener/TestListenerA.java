package com.zwl.listener;

import com.zwl.model.LMessage;
import com.zwl.pubsub.AbstractChannelListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/10 15:18
 **/
@Component
@Slf4j
public class TestListenerA extends AbstractChannelListener<LMessage> {

    @Override
    public Topic getTopic() {
        return channelTopic();
    }

    @Override
    public void onMessage(LMessage message) {
        log.info("TestListenerA receiver message:{}", message);
    }


}
