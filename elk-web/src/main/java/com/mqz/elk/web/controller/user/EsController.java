package com.mqz.elk.web.controller.user;

import com.mqz.elk.common.model.index.Poem;
import com.mqz.elk.web.reponstory.PoemRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：
 * @Description
 * @About： https://github.com/DemoMeng
 */
@RestController
@RequestMapping(value = "/es/poem")
@Api(value = "ES-诗歌",tags = "ES-诗歌")
@Slf4j
public class EsController {

    @Resource
    private PoemRepository poemRepository;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping(value = "/create")
    @ApiOperation(value = "poem-创建索引")
    public Object create(){
        elasticsearchTemplate.createIndex(Poem.class);
        log.info("【better-elk】已创建索引:poem");
        return "OK";
    }


    @GetMapping(value = "/add")
    @ApiOperation(value = "poem-初始化document")
    public Object add(){
        delete();
        poemRepository.saveAll(Poem.poemList);
        log.info("【better-elk】已初始化索引:poem的所有文档！");
        return "OK";
    }

    @GetMapping(value = "/delete")
    @ApiOperation(value = "poem-删除所有document")
    public Object delete(){
        poemRepository.deleteAll();
        log.info("【better-elk】已删除索引:poem的所有文档！");
        return "OK";
    }

    @GetMapping(value = "/getAll")
    @ApiOperation(value = "poem-查询所有")
    public Object getAll(){
        log.info("【better-elk】查询索引:poem的所有文档！");
        return poemRepository.findAll();
    }

    @GetMapping(value = "/getByKeyword")
    @ApiOperation(value = "poem-关键字查找")
    public Object getByKeyword(String keyword, Pageable pageable){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(keyword,"userRemark","content")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .withPageable(pageable)
                .build();
        log.info("【better-elk】关键字查找索引:poem的文档！");
        return elasticsearchTemplate.queryForList(searchQuery, Poem.class);
    }

    //TODO 高亮查询


}
