package com.zwl.stream;

import com.zwl.model.AbstractMessage;

/**
 * 描述：stream 消息实体
 *
 * @author zwl
 * @since 2022/3/10 9:21
 **/
public abstract class AbstractStreamMessage extends AbstractMessage {


    /**
     * 获取streamKey
     *
     * @return streamKey
     */
    public abstract String getStreamKey();
}
