package com.mqz.elk.web.controller.user;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 *  ???????????? ?? Copyright 2012<br>
 *
 * @Author??? ?????????
 * @Date???
 * @Description
 * @About??? https://github.com/DemoMeng
 */
@RestController
@RequestMapping(value = "/es/poem")
@Api(value = "ES-??????",tags = "ES-??????")
//@Slf4j
public class EsController {

    private final static Logger log = LoggerFactory.getLogger(EsController.class);

    @Resource
    private PoemRepository poemRepository;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private PoemCompletionRepository poemCompletionRepository;
    @Resource
    private ElasticsearchOperations elasticsearchOperations;


    @ApiOperationSupport(order = 1)
    @GetMapping(value = "/create")
    @ApiOperation(value = "poem-????????????")
    public Object create(){
        elasticsearchTemplate.createIndex(Poem.class);
        log.info("???better-elk??????????????????:poem");
        return "OK";
    }
    @ApiOperationSupport(order = 2)
    @GetMapping(value = "/add")
    @ApiOperation(value = "poem-?????????document")
    public Object add(){
        delete();
        poemRepository.saveAll(Poem.poemList);
        log.info("???better-elk?????????????????????:poem??????????????????");
        return "OK";
    }
    @ApiOperationSupport(order = 3)
    @GetMapping(value = "/delete")
    @ApiOperation(value = "poem-????????????document")
    public Object delete(){
        poemRepository.deleteAll();
        log.info("???better-elk??????????????????:poem??????????????????");
        return "OK";
    }
    @ApiOperationSupport(order = 4)
    @GetMapping(value = "/getAll")
    @ApiOperation(value = "poem-????????????")
    public Object getAll(Pageable pageable){
        log.info("???better-elk???????????????:poem??????????????????");
        return poemRepository.findAll(pageable);
    }
    @ApiOperationSupport(order = 5)
    @GetMapping(value = "/count")
    @ApiOperation(value = "poem-??????????????????")
    public Object count(){
        log.info("???better-elk???poem-??????????????????");
        return "????????????????????????????????????" + poemRepository.count();
    }

    @ApiOperationSupport(order = 6)
    @GetMapping(value = "/search")
    @ApiOperation(value = "poem-poemRepository??????")
    public Object count(String keyword, Pageable pageable){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(keyword,"userRemark","content")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .withPageable(pageable)
                .build();
        log.info("???better-elk???poemRepository ?????????????????????:poem????????????");
        return poemRepository.search(searchQuery);
    }

    @ApiOperationSupport(order = 7)
    @GetMapping(value = "/getByKeyword")
    @ApiOperation(value = "poem-???????????????")
    public Object getByKeyword(String keyword, Pageable pageable){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(keyword,"userRemark","content")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .withPageable(pageable)
                .build();
        log.info("???better-elk????????????????????????:poem????????????");
        return elasticsearchTemplate.queryForList(searchQuery, Poem.class);
    }

    @ApiOperationSupport(order = 8)
    @GetMapping(value = "/createPoemCompletion")
    @ApiOperation(value = "poem_completion-????????????")
    public Object createPoemCompletion(){
        if(elasticsearchTemplate.indexExists(PoemCompletion.class)){
            log.info("???better-elk???poem_completion????????????????????????");
            return "poem_completion????????????????????????";
        }
        elasticsearchTemplate.createIndex(PoemCompletion.class);
        log.info("???better-elk??????????????????:poem_completion");
        return "OK";
    }
    @ApiOperationSupport(order = 9)
    @GetMapping(value = "/initCompletionData")
    @ApiOperation(value = "poem_completion-???????????????")
    public Object initData(){
        poemCompletionRepository.saveAll(PoemCompletion.poemList);
        log.info("???better-elk????????????????????????????????????:poem_completion");
        return "OK";
    }


    /**
     * ?????????????????????
     */
    @ApiOperationSupport(order = 10)
    @PostMapping(value = "/keywordCompletion2")
    @ApiOperation(value = "poem_completion-?????????????????????2")
    public List<String> completion2(@RequestBody PoemKeywordCompletionDTO dto) {
        //????????????????????????,??????????????????
        CompletionSuggestionBuilder completionSuggestionBuilder = SuggestBuilders.completionSuggestion(dto.getSearchField());
        //???????????????
        completionSuggestionBuilder.prefix(dto.getSearchValue());
        //????????????
        completionSuggestionBuilder.skipDuplicates(true);
        //????????????
        completionSuggestionBuilder.size(dto.getSearchMaxCount());
        SearchRequestBuilder searchRequestBuilder =elasticsearchTemplate.getClient()
                .prepareSearch(dto.getIndex())
                .setTypes(dto.getType());
        //??????
        SearchResponse suggestResponse = searchRequestBuilder.suggest(new SuggestBuilder().addSuggestion("search-suggest",completionSuggestionBuilder)).get();
        CompletionSuggestion completionSuggestion = suggestResponse.getSuggest().getSuggestion("search-suggest");
        List<CompletionSuggestion.Entry.Option> options = completionSuggestion.getEntries().get(0).getOptions();
        List<String> suggestList = new ArrayList<>();
        options.forEach(item ->{ suggestList.add(item.getText().string()); });

        for (String string : suggestList) {
            System.err.println(string);
        }
        return suggestList;
    }

    /**
     * ?????????????????????
     */
    @ApiOperationSupport(order = 11)
    @PostMapping(value = "/keywordCompletion")
    @ApiOperation(value = "poem_completion-?????????????????????")
    public List<String> listSuggestCompletion(@RequestBody PoemKeywordCompletionDTO dto) {
        String keywordSuggest = "keyword_suggest";
        CompletionSuggestionBuilder suggestionBuilderDistrict = new CompletionSuggestionBuilder(dto.getSearchField())
                .prefix(dto.getSearchValue())
                .size(dto.getSearchMaxCount());
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion(keywordSuggest, suggestionBuilderDistrict);//??????suggest
        // ??????
        SearchResponse goodsNameSuggestResp = elasticsearchTemplate.suggest(suggestBuilder, PoemCompletion.class);
        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> goodsNameSuggest = goodsNameSuggestResp
                .getSuggest().getSuggestion("my-suggest");

        // ????????????
        List<String> suggests = goodsNameSuggest.getEntries().stream().map(x -> x.getOptions().stream().map(y->y.getText().toString()).collect(Collectors.toList())).findFirst().get();
        // ????????????
        log.info("????????????????????????????????????????????????{}",suggests.toString());
        return suggests;
//        SearchRequestBuilder requestBuilder = elasticsearchTemplate
//                .getClient()
//                .prepareSearch(dto.getIndex())
//                .setTypes(dto.getType())
//                .suggest(suggestBuilder);
//        log.info("??????????????????????????????????????????{}",requestBuilder.toString());
//        SearchResponse response = requestBuilder.get();
//        //suggest??????
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
//                //???????????????????????????
//                if (maxSuggest >= dto.getSearchMaxCount()) {
//                    break;
//                }
//            }
//        }
//        List<String> suggests = Arrays.asList(suggestSet.toArray(new String[]{}));
//        log.info("????????????????????????????????????????????????{}",suggests.toString());
//        return suggests;
    }

    /**
     * es????????????
     */
    @ApiOperationSupport(order = 12)
    @GetMapping(value = "/highLight")
    @ApiOperation(value = "es????????????")
    public Object highLight(String content, @PageableDefault Pageable pageable) {
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(multiMatchQuery(content,"name","remarks").type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
//                .withPageable(pageable)
//                .build();
//        Client client = elasticsearchTemplate.getClient();
        // ????????????
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        // ??????????????? , ????????????
        searchQuery.withIndices("poem");
        // ???????????????boost??????????????????????????????????????????
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.multiMatchQuery(content, "content").boost(3))
                .should(QueryBuilders.multiMatchQuery(content, "userRemark").boost(2));
        searchQuery.withQuery(queryBuilder);
        // ????????????????????????????????????????????????
        List<String> highlightFields = new ArrayList<String>();
        highlightFields.add("content");
        highlightFields.add("userRemark");
        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[highlightFields.size()];
        for (int x = 0; x < highlightFields.size(); x++) {
            fields[x] = new HighlightBuilder.Field(highlightFields.get(x))
                    .preTags("<b color='red'>")
                    .postTags("</b>");
        }
        searchQuery.withHighlightFields(fields);
        // ????????????
        // ????????????
        searchQuery.withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        Page<Poem> page = elasticsearchTemplate.queryForPage(searchQuery.build(), Poem.class, new SearchResultMapper() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                // ????????????????????????
                List<Poem> list = new ArrayList<>();
                SearchHits hits = response.getHits();
                for (SearchHit searchHit : hits) {
                    log.info("searchHit : {}",searchHit);
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    Poem data = new Poem();
                    // ????????????
                    //data.setId(new Double(searchHit.getId()).longValue());
                    data.setContent(String.valueOf(searchHit.getSourceAsMap().get("content")));
                    data.setUserRemark(String.valueOf(searchHit.getSourceAsMap().get("userRemark")));
                    // ????????????
//                    data.setKeyword(String.valueOf(searchHit.getSourceAsMap().get("keyword")));
//                    data.setPassCity(String.valueOf(searchHit.getSourceAsMap().get("passCity")));
                    // ????????????set?????????????????????????????????
                    try {
                        for (String field : highlightFields) {
                            HighlightField highlightField = searchHit.getHighlightFields().get(field);
                            log.info("highlightField: {}",highlightField);
                            if (highlightField != null) {
                                String setMethodName = parSetName(field);
                                Class<? extends Poem> poemClazz = data.getClass();
                                Method setMethod = poemClazz.getMethod(setMethodName, String.class);
                                String highlightStr = highlightField.fragments()[0].toString();
                                // ???????????????
                                if ("remarks".equals(field) && highlightStr.length() > 50) {
                                    //
                                }
                                setMethod.invoke(data, highlightStr);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.add(data);
                }
                if (list.size() > 0) {
                    AggregatedPage<T> result = new AggregatedPageImpl<T>((List<T>) list, pageable,
                            response.getHits().getTotalHits());
                    return result;
                }
                return null;
            }
        });
        return page;
    }

    private static String parSetName(String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }




}
