#添加id为1的一个文档
PUT poem/test/1
{
    "id": "demoData",
    "userRemark": "demoData",
    "content": "demoData"
}
PUT poem/test/2
{
    "id": "1",
    "userRemark": "你好es",
    "content": "我来了"
}
PUT poem/test/3
{
    "id": "3",
    "userRemark": "demoData ok ",
    "content": "demoData nice"
}
PUT poem/test/4
{
    "id": "4",
    "userRemark": "ok everyone",
    "content": "哈哈哈哈哈哈哈"
}



#查询索引的全部数据
GET poem/_search

#关键字查询 （query-string），这个查询是全匹配
GET poem/test/_search?q=userRemark:demoData
# 更多 query-string 的搜索： https://www.elastic.co/guide/cn/elasticsearch/guide/current/search-lite.html


# 请求体构建查询，模糊匹配，demoData 和 ok
GET poem/test/_search
{
    "query":{
        "match": {
            "userRemark": "demoData ok"
        }
    }
}

#配置短语，只匹配 demoData ok 的词组
GET poem/test/_search
{
    "query":{
        "match_phrase": {
            "userRemark": "demoData ok"
        }
    }
}

#高亮搜索，自定义高亮前缀和后缀标签
GET poem/test/_search
{
    "query":{
        "match_phrase": {
            "userRemark": "demoData ok"
        }
    },
    "highlight":{
        "pre_tags": "<b color='read'>",
        "post_tags":" </b>",
        "fields": {
            "userRemark": {}
        }
    }
}








