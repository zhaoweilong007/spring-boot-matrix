package com.zwl.delayed;

import com.zwl.model.SMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/24 10:44
 **/
@Component
@Slf4j
public class OrderConfirmTimeOutHandler implements DelayedServiceHandler<SMessage> {

    @Override
    public void execute(SMessage sMessage) {
        log.info("OrderConfirmTimeOutHandler execute....");
        log.info("OrderConfirmTimeOutHandler message:{}", sMessage);
    }
}
