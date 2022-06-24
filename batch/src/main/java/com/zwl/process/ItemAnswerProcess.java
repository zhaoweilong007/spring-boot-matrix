package com.zwl.process;

import com.zwl.entity.Answer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/22 15:46
 **/
@Component
@Slf4j
public class ItemAnswerProcess implements ItemProcessor<Map<Integer, List<Answer>>, List<Answer>> {

    /**
     * 对answerId去重
     */
    Set<Long> answers = new HashSet<>();

    @Override
    public List<Answer> process(Map<Integer, List<Answer>> map) {
        log.info("ItemAnswerProcess item size:{}", map.size());
        return map.entrySet().stream().flatMap(entity -> entity.getValue().stream()).filter(answer -> {
            if (answers.contains(answer.getAnswerId())) {
                return false;
            }
            answers.add(answer.getAnswerId());
            return true;
        }).collect(Collectors.toList());
    }
}
