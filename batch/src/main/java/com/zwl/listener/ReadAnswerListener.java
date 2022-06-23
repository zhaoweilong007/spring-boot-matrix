package com.zwl.listener;

import com.zwl.entity.Answer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/22 15:41
 **/
@Component
@Slf4j
public class ReadAnswerListener implements ItemReadListener<Answer> {
    @Override
    public void beforeRead() {
        log.info("ReadAnswerListener beforeRead.....");
    }

    @Override
    public void afterRead(Answer item) {
        log.info("ReadAnswerListener afterRead...... ");
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("ReadAnswerListener onReadError.....");
    }
}
