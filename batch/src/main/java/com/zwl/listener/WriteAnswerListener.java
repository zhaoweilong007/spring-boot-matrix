package com.zwl.listener;

import com.zwl.entity.Answer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/22 15:41
 **/
@Component
@Slf4j
public class WriteAnswerListener implements ItemWriteListener<Answer> {

    @Override
    public void beforeWrite(List<? extends Answer> items) {
        log.info("WriteAnswerListener beforeWrite...");
    }

    @Override
    public void afterWrite(List<? extends Answer> items) {
        log.info("WriteAnswerListener afterWrite...");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Answer> items) {
        log.error("WriteAnswerListener onWriteError...");
    }
}
