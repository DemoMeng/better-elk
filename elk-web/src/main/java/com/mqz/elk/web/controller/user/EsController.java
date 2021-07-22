package com.mqz.elk.web.controller.user;

import com.mqz.elk.common.model.dto.PoemKeywordCompletionDTO;
import com.mqz.elk.common.model.index.Poem;
import com.mqz.elk.common.model.index.PoemCompletion;
import com.mqz.elk.web.reponstory.PoemCompletionRepository;
import com.mqz.elk.web.reponstory.PoemRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private PoemCompletionRepository poemCompletionRepository;
    @Resource
    private ElasticsearchOperations elasticsearchOperations;


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
    public Object getAll(Pageable pageable){
        log.info("【better-elk】查询索引:poem的所有文档！");
        return poemRepository.findAll(pageable);
    }

    @GetMapping(value = "/count")
    @ApiOperation(value = "poem-统计所有文档")
    public Object count(){
        log.info("【better-elk】poem-统计所有文档");
        return "总共的文档数为：（记录）" + poemRepository.count();
    }


    @GetMapping(value = "/search")
    @ApiOperation(value = "poem-poemRepository搜索")
    public Object count(String keyword, Pageable pageable){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(keyword,"userRemark","content")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .withPageable(pageable)
                .build();
        log.info("【better-elk】poemRepository 关键字查找索引:poem的文档！");
        return poemRepository.search(searchQuery);
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


    @GetMapping(value = "/createPoemCompletion")
    @ApiOperation(value = "poem_completion-创建索引")
    public Object createPoemCompletion(){
        if(elasticsearchTemplate.indexExists(PoemCompletion.class)){
            log.info("【better-elk】poem_completion已经存在无需创建");
            return "poem_completion已经存在无需创建";
        }
        elasticsearchTemplate.createIndex(PoemCompletion.class);
        log.info("【better-elk】已创建索引:poem_completion");
        return "OK";
    }

    @GetMapping(value = "/initCompletionData")
    @ApiOperation(value = "poem_completion-初始化数据")
    public Object initData(){
        poemCompletionRepository.saveAll(PoemCompletion.poemList);
        log.info("【better-elk】已创建初始化数据，索引:poem_completion");
        return "OK";
    }


    /**
     * 关键字自动补全
     */
    @PostMapping(value = "/keywordCompletion2")
    @ApiOperation(value = "poem_completion-关键字自动补全2")
    public List<String> completion2(@RequestBody PoemKeywordCompletionDTO dto) {
        String keyword = "en_Sili";

        //构造搜索建议语句,搜索条件字段
        CompletionSuggestionBuilder completionSuggestionBuilder = SuggestBuilders.completionSuggestion(dto.getSearchField());
        //搜索关键字
        completionSuggestionBuilder.prefix(keyword);
        //去除重复
        completionSuggestionBuilder.skipDuplicates(true);
        //匹配数量
        completionSuggestionBuilder.size(dto.getSearchMaxCount());
        SearchRequestBuilder searchRequestBuilder =elasticsearchTemplate.getClient()
                .prepareSearch(dto.getIndex())
                .setTypes(dto.getType());
        //根据
        SearchResponse suggestResponse = searchRequestBuilder.suggest(new SuggestBuilder().addSuggestion("my-suggest",completionSuggestionBuilder)).get();
        CompletionSuggestion completionSuggestion = suggestResponse.getSuggest().getSuggestion("my-suggest");
        List<CompletionSuggestion.Entry.Option> options = completionSuggestion.getEntries().get(0).getOptions();
        List<String> suggestList = new ArrayList<>();
        options.forEach(item ->{ suggestList.add(item.getText().string()); });

        for (String string : suggestList) {
            System.err.println(string);
        }
        return suggestList;
    }

    /**
     * 关键字自动补全
     */
    @PostMapping(value = "/keywordCompletion")
    @ApiOperation(value = "poem_completion-关键字自动补全")
    public List<String> listSuggestCompletion(@RequestBody PoemKeywordCompletionDTO dto) {
        String keywordSuggest = "keyword_suggest";
        CompletionSuggestionBuilder suggestionBuilderDistrict = new CompletionSuggestionBuilder(dto.getSearchField())
                .prefix(dto.getSearchValue())
                .size(dto.getSearchMaxCount());
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion(keywordSuggest, suggestionBuilderDistrict);//添加suggest
        // 查询
        SearchResponse goodsNameSuggestResp = elasticsearchTemplate.suggest(suggestBuilder, PoemCompletion.class);
        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> goodsNameSuggest = goodsNameSuggestResp
                .getSuggest().getSuggestion("my-suggest");

        // 处理返回
        List<String> suggests = goodsNameSuggest.getEntries().stream().map(x -> x.getOptions().stream().map(y->y.getText().toString()).collect(Collectors.toList())).findFirst().get();
        // 输出内容
        log.info("【信息】关键字自动补全搜索结果：{}",suggests.toString());
        return suggests;
//        SearchRequestBuilder requestBuilder = elasticsearchTemplate
//                .getClient()
//                .prepareSearch(dto.getIndex())
//                .setTypes(dto.getType())
//                .suggest(suggestBuilder);
//        log.info("【信息】关键字自动补全请求：{}",requestBuilder.toString());
//        SearchResponse response = requestBuilder.get();
//        //suggest实体
//        Suggest suggest = response.getSuggest();
//        Set<String> suggestSet = new HashSet<>();
//        int maxSuggest = 0;
//        if (suggest != null) {
//            Suggest.Suggestion result = suggest.getSuggestion(keywordSuggest);
//            for (Object term : result.getEntries()) {
//                if (term instanceof CompletionSuggestion.Entry) {
//                    CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) term;
//                    if (!item.getOptions().isEmpty()) {
//                        for (CompletionSuggestion.Entry.Option option : item.getOptions()) {
//                            String tip = option.getText().toString();
//                            if (!suggestSet.contains(tip)) {
//                                suggestSet.add(tip);
//                                ++maxSuggest;
//                            }
//                        }
//                    }
//                }
//                //返回指定补全的条数
//                if (maxSuggest >= dto.getSearchMaxCount()) {
//                    break;
//                }
//            }
//        }
//        List<String> suggests = Arrays.asList(suggestSet.toArray(new String[]{}));
//        log.info("【信息】关键字自动补全搜索结果：{}",suggests.toString());
//        return suggests;
    }

    //TODO 高亮查询


}
