package com.mqz.elk.web.reponstory;

import com.mqz.elk.common.model.index.Poem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 青网科技集团 版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/7/21 4:45 下午
 * @Description
 * @About： https://github.com/DemoMeng
 */
public interface PoemRepository extends ElasticsearchRepository<Poem,String> {

}
