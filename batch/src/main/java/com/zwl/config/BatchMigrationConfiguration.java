package com.zwl.config;

import com.alibaba.fastjson2.TypeReference;
import com.zwl.dao.AnswerDao;
import com.zwl.entity.Answer;
import com.zwl.listener.ReadAnswerListener;
import com.zwl.listener.WriteAnswerListener;
import com.zwl.process.ItemAnswerProcess;
import com.zwl.read.FastJsonItemRead;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述：任务配置
 *
 * @author zwl
 * @since 2022/6/22 14:21
 **/
@Configuration
public class BatchMigrationConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private AnswerDao answerDao;

    @Bean
    public Job answerMigrationJob(@Qualifier("answerMigrationStep") Step answerMigrationStep) {
        return this.jobBuilderFactory.get("answerMigrationJob")
                .start(answerMigrationStep)
                .build();
    }

    @Bean
    public Step answerMigrationStep(MultiResourceItemReader<Map<Integer, List<Answer>>> multiResourceItemReader,
                                    ItemWriter<List<Answer>> repositoryItemWriter,
                                    ReadAnswerListener readAnswerListener,
                                    WriteAnswerListener writeAnswerListener,
                                    ItemAnswerProcess itemAnswerProcess) {
        return this.stepBuilderFactory.get("answerMigrationStep")
                .<Map<Integer, List<Answer>>, List<Answer>>chunk(2)
                .reader(multiResourceItemReader)
                .listener(readAnswerListener)
                .processor(itemAnswerProcess)
                .writer(repositoryItemWriter)
                .listener(writeAnswerListener)
                //开启多线程处理
                //.taskExecutor(new SimpleAsyncTaskExecutor("spring-batch-answer-migration"))
                .build();
    }

    @Bean
    public MultiResourceItemReader<Map<Integer, List<Answer>>> multiResourceItemReader() throws Exception {
        MultiResourceItemReader<Map<Integer, List<Answer>>> multiResourceItemReader = new MultiResourceItemReader<>();
        multiResourceItemReader.setResources(selectAllResource("C:\\Users\\admin\\ideaProject\\zhihuCrawler\\data\\answerJson"));
        multiResourceItemReader.setName("multiResourceItemReader");
        JsonItemReader<Map<Integer, List<Answer>>> jsonItemReader = new JsonItemReader<>();
        jsonItemReader.setJsonObjectReader(new FastJsonItemRead<Map<Integer, List<Answer>>>(new TypeReference<Map<Integer, List<Answer>>>() {
        }.getType()));
        multiResourceItemReader.setDelegate(jsonItemReader);
        return multiResourceItemReader;
    }

    private Resource[] selectAllResource(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.isDirectory()) {
            throw new Exception("filePath not be directory");
        }
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("json");
            }
        });
        return Stream.of(files).map(FileSystemResource::new).toArray(Resource[]::new);
    }


    @Bean
    public ItemWriter<List<Answer>> repositoryItemWriter() {
        return new ItemWriter<List<Answer>>() {
            @Override
            public void write(List<? extends List<Answer>> items) throws Exception {
                List<Answer> answerList = items.stream().flatMap(answers -> answers.stream()).collect(Collectors.toList());
                answerDao.saveAllAndFlush(answerList);
            }
        };
    }


}
