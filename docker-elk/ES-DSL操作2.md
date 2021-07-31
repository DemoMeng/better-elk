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

PUT poem/test/5
{
"id": "5",
"userRemark": "索引中的version字段",
"content": "是乐观锁，保证数据并发情况下的准确性"
}

PUT poem/test/6
{
"id": "6",
"userRemark": "索引中的version字段-相同的id就是update文档",
"content": "是乐观锁，保证数据并发情况下的准确性-相同的id就是update文档"
}


#查询指定id的文档
GET poem/test/122?pretty

#查询指定id的文档并且返回指定部分
GET poem/test/1/_source





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



#返回集群中所有索引下的所有文档：
GET _search
{
"from": 0,
"size": 50

}

#获取索引的映射
GET poem/_mapping


#分析词条
GET /_analyze
{
"analyzer": "standard",
"text": "demoMeng is a handson gentleman!"
}







