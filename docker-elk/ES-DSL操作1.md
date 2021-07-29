#删除文档
DELETE simple_system_user/user/mengqizhang

#删除索引
DELETE simple_system_user


#创建tpye=user,文档=mengqizhang
POST simple_system_user/user/mengqizhang
{
    "id": 1,
    "loginName": "mengqizhang",
    "password": "123123",
    "salt": "撒打算2撒打算的",
    "name": "蒙奇长",
    "userType": 1,
    "area": "12",
    "loginIp": "192.168.33.23",
    "enable": 1,
    "loginFlag": 1,
    "createBy": 1,
    "updateBy": 1,
    "remarks": "测试es",
    "delFlag": 1
}

#获取文档=mengqizhang
GET simple_system_user/user/mengqizhang


#创建tpye=user,文档=lixinghua
POST simple_system_user/user/lixinghua
{
    "id": 1,
    "loginName": "lixinghua",
    "password": "111啊啊啊啊啊啊啊",
    "salt": "水电费水电费的身份重新出现",
    "name": "理性化",
    "userType": 2,
    "area": "12",
    "loginIp": "99.168.33.23",
    "enable": 1,
    "loginFlag": 1,
    "createBy": 1,
    "updateBy": 1,
    "remarks": "撒打算的都是对的",
    "delFlag": 1
}

#获取文档=lixinghua
GET simple_system_user/user/lixinghua


#创建tpye=user,文档=zhangsan
POST simple_system_user/user/zhangsan
{
    "id": 1,
    "loginName": "zhangsan",
    "password": "123123啊啊啊啊啊",
    "salt": "123123啊啊啊啊滴滴答答滴滴答答滴滴答答滴滴答答的",
    "name": "张三",
    "userType": 1,
    "area": "12",
    "loginIp": "172.168.33.23",
    "enable": 1,
    "loginFlag": 1,
    "createBy": 1,
    "updateBy": 1,
    "remarks": "水电费水电费桑多瓦尔为",
    "delFlag": 1
}

#获取文档=zhangsan
GET simple_system_user/user/zhangsan


#搜索simple_system_user索引下的user类型所有的数据
#带JSON条件搜索， query ，sort：排序
GET simple_system_user/user/_search
{
    "query": {
        "match": {
            "name": "蒙"
        }
    },
    "sort": [
        {
        "delFlag": "desc"
        }
    ]
}
#搜索索引 simple_system_user 所有的数据
GET simple_system_user/_search
{
    "from":0,
    "size":200
}


GET simple_system_user/_search
{
    "query": {
        "bool": {
          "must":[
            {
              "match": {
                "name":"蒙"
              }
            } 
          ],
          "should": [
            {
              "match": {
                "name": "理"
              }
            }
          ]
        } 
    }
}

# must：　　表示一定要满足；
# should：　　表示可以满足也可以不满足；
# must_not：　　表示不能满足该条件；
# minimum_should_match:1 ：表示最小匹配度，可以设置为百分之百，设置了这个值的时候就必须满足should里面的设置了，
#　　　　另外注意这边should里面同一字段设置的多个值，意思是当这个值等于X或者等于Y都成立，务必注意格式。


#搜索索引 simple_system_user 所有的数据
GET simple_system_user/_search
{
    "from":0,
    "size":45
}




#高亮搜索，需要加入highlight参数进行查询
GET simple_system_user/user/_search
{
    "query": {
        "match_phrase": {
        "remarks": "李清照"
        }
    },
    "highlight": {
        "fields": {
            "remarks": {}
        }
    }
}

#高亮搜索，需要加入highlight参数进行查询，自定义高亮显示的关键字标签
GET simple_system_user/user/_search
{
"query": {
"match": {
"remarks": "李清照"
}
},
"highlight": {
"pre_tags": "<b color='read'>",
"post_tags":" </b>",
"fields": {
"remarks": {}
}
}
}

GET info-2021.07.19/_search
{

}


#创建索引
PUT d_l_location


# 索引： 类似mysql的database
# 类型： 类似mysql的table
# 文档： 类似mysql的记录
# 

#创建文档，并且插入数据
POST d_l_location/mydoc/d1
{
"location": "52.374081,4.912350",
"geohash": "u173zy3j4h3dsdk",
"name": "NEMO Science Museum"
}

POST d_l_location/mydoc/d2
{
"location": "52.369219,4.901618",
"geohash": "u173zt90zczr95s",
"name": "Museum Het Rembrandthuis"
}
POST d_l_location/mydoc/d3
{
"location": "52.371667,4.914722",
"geohash": "u173zvfz1666db0",
"name": "Nederlands Scheepvaartmuseum"
}
POST d_l_location/mydoc/d4
{
"location": "51.222900,4.405200",
"geohash": "u155khzg08ehqm9",
"name": "Letterenhuis"
}
POST d_l_location/mydoc/d5
{
"location": "48.861111,2.336389",
"geohash": "u09tvnvk9g3bm03",
"name": "Musée du Louvre"
}
POST d_l_location/mydoc/d6
{
"location": "48.860000,2.327000",
"geohash": "u09tuywqph0uyu8",
"name": "Musée d'Orsay"
}


GET info-2021.07.20/_doc/_search
{
"query": {
"match": {
"message": "【信息】"
}
}

}



PUT d_l_location2
DELETE d_l_location2

POST d_l_location2/mydoc/1
{

        "geo" : {
          "properties" : {
            "coordinates" : {
              "type" : "geo_point"
            },
            "dest" : {
              "type" : "keyword"
            },
            "src" : {
              "type" : "keyword"
            },
            "srcdest" : {
              "type" : "keyword"
            }
          }
        }



}

DELETE %%{appname}-2021.07.21

put poem

GET poem/_mapping


# 设置mapper 和
# 定义成completion的字段无法应用highlight返回，这里和原来索引一样
PUT /poem_completion
{
"mappings" : {
"properties" : {
"userRemark" : {
"type" :    "completion"
},
"id" : {
"type" :   "text"
}      
}
}
}

DELETE poem_completion

#查看索引的映射情况
GET poem_completion/_mapping

GET poem_completion/_search
{

}




DELETE poem_completion
# 需要配置es的ik分词器插件
PUT /poem_completion
{
"mappings" : {
"properties" : {
"userRemark" : {
"type" :    "text"
},
"id" : {
"type" :   "text"
},
"content" : {
"type" :   "text"
},
"completionKey" : {
"type" :   "completion"
}
}
}
}














