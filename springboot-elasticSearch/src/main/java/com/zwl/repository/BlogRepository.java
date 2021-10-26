package com.zwl.repository;

import com.zwl.model.BlogModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/26
 **/
public interface BlogRepository extends ElasticsearchRepository<BlogModel, String> {

}
