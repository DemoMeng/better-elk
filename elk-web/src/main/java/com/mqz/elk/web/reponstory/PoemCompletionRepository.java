package com.mqz.elk.web.reponstory;

import com.mqz.elk.common.model.index.PoemCompletion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：
 * @Description
 * @About： https://github.com/DemoMeng
 */
public interface PoemCompletionRepository extends ElasticsearchRepository<PoemCompletion,String> {
}
