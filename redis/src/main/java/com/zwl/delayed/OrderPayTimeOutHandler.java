package com.zwl.delayed;

import com.zwl.model.LMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/24 10:41
 **/
@Component
@Slf4j
public class OrderPayTimeOutHandler implements DelayedServiceHandler<LMessage> {


    @Override
    public void execute(LMessage lMessage) {
        log.info("OrderPayTimeOutHandler execute....");
        log.info("OrderPayTimeOutHandler message:{}", lMessage);

    }
}
