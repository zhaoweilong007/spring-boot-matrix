package com.zwl.delayed;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/24 10:38
 **/
@Getter
@RequiredArgsConstructor
public enum DelayTaskEnum {

    /**
     * 延迟任务枚举
     */
    ORDER_PAY_TIME_OUT("ORDER_PAY_TIME_OUT", "订单支付超时", OrderPayTimeOutHandler.class),
    ORDER_CONFIRM_TIME_OUT("ORDER_CONFIRM_TIME_OUT", "订单确认超时", OrderConfirmTimeOutHandler.class);

    private final String name;

    private final String desc;

    private final Class<? extends DelayedServiceHandler> beanCLass;
}
