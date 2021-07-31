# ELK 

- Elasticsearch: 分布式搜索和分析引擎，用于全文检索结构化检索及分析，基于Lucene开发 （源于创始人为妻子做一个菜谱功能）
- Logstash:实时数据传输管道，负责将数据从管道输入端传入管道的输出端，并且对这些数据可以进行筛选，清洗的操作
- Kibana:可视化操作平台，是用于Elasticsearch的使用，搜索查看索引的数据，地图、图表，kibana能够很轻易的展示高级数据分析与可视化




# Elasticsearch : 


#### 定义：

    · 分布式的 Restful 风格的搜索和数据分析引擎

#### 特点：
    
    查询：允许执行和合并多种类型的搜索 — 结构化、非结构化、地理位置、度量指标 — 搜索方式随心而变。
    分析：Elasticsearch 聚合让您能够从大处着眼，探索数据的趋势和模式。
    速度：很快，可以做到亿万级的数据，毫秒级返回。
    可扩展性：可以在笔记本电脑上运行，也可以在承载了 PB 级数据的成百上千台服务器上运行。
    弹性：运行在一个分布式的环境中，从设计之初就考虑到了这一点。
    灵活性：具备多个案例场景。支持数字、文本、地理位置、结构化、非结构化，所有的数据类型都欢迎。
    

#### 健康检测： localhost:9200/_cluster/health，返回：
  
      {
        "cluster_name": "docker-cluster",
        "status": "yellow",
        "timed_out": false,
        "number_of_nodes": 1,
        "number_of_data_nodes": 1,
        "active_primary_shards": 11,
        "active_shards": 11,
        "relocating_shards": 0,
        "initializing_shards": 0,
        "unassigned_shards": 4,
        "delayed_unassigned_shards": 0,
        "number_of_pending_tasks": 0,
        "number_of_in_flight_fetch": 0,
        "task_max_waiting_in_queue_millis": 0,
        "active_shards_percent_as_number": 73.33333333333333
      }     



#### 安装IK分词器：
    
      1. docker exec -it 容器id /bin/bash
      2. ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.13.2/elasticsearch-analysis-ik-7.13.2.z
      3. docker restart 容器id

#### 集群：
    -集群： 
        1. 一个运行中的 Elasticsearch 实例称为一个节点，而集群是由一个或者多个拥有相同 cluster.name 配置的节点组成，
           它们共同承担数据和负载的压力。当有节点加入集群中或者从集群中移除节点时，集群将会重新平均分布所有的数据。
        2. 主节点负责管理集群范围内增加、删除索引等操作！ 任何节点都可以选举为主节点，当只有一个es实例的时候

    - 集群健康： 
        1. GET /_cluster/health
            返回json信息： 

            {
                "cluster_name": "docker-cluster",
                "status": "yellow",
                "timed_out": false,
                "number_of_nodes": 1,
                "number_of_data_nodes": 1,
                "active_primary_shards": 25,
                "active_shards": 25,
                "relocating_shards": 0,
                "initializing_shards": 0,
                "unassigned_shards": 18,
                "delayed_unassigned_shards": 0,
                "number_of_pending_tasks": 0,
                "number_of_in_flight_fetch": 0,
                "task_max_waiting_in_queue_millis": 0,
                "active_shards_percent_as_number": 58.139534883720934
            }
            
            其中状态值status：
                green 所有的主分片和副本分片都正常运行。
                yellow 所有的主分片都正常运行，但不是所有的副本分片都正常运行。
                red 有主分片没能正常运行。

#### 索引，类型，文档，分片，副本：
    
    1 索引： = mysql数据库
       倒排索引： =mysql的索引，提升检索的速度
    2 类型： = mysql表
    3 文档： = mysql中的记录
    4 分片： 把数据分成多个在不同节点上
            a 一个分片是一个Lucene实例
            b Elasticsearch 是利用分片将数据分发到集群内各处的，分片是数据的容器，文档保存在分片内，分片又被分配到集群内的各个节点里。
            c 主分片 和 副分片，通过接口查询es的健康状态，其中的 "unassigned_shards"代表副分片未分配的数量（在同一个节点上既保存原始数据又保存副本是没有意义的）
    5 副本： 索引的备份机制



    倒排索引：
        例如，假设我们有两个文档，每个文档的 content 域包含如下内容：
            The quick brown fox jumped over the lazy dog
            Quick brown foxes leap over lazy dogs in summer
        为了创建倒排索引，我们首先将每个文档的 content 域拆分成单独的 词（我们称它为 词条 或 tokens ），
        创建一个包含所有不重复词条的排序列表，然后列出每个词条出现在哪个文档。结果如下所示：
            Term      Doc_1  Doc_2
            -------------------------
            Quick   |       |  X
            The     |   X   |
            brown   |   X   |  X
            dog     |   X   |
            dogs    |       |  X
            fox     |   X   |
            foxes   |       |  X
            in      |       |  X
            jumped  |   X   |
            lazy    |   X   |  X
            leap    |       |  X
            over    |   X   |  X
            quick   |   X   |
            summer  |       |  X
            the     |   X   |
            ------------------------

        现在，如果我们想搜索 quick brown ，我们只需要查找包含每个词条的文档：
            
            Term      Doc_1  Doc_2
            -------------------------
            brown   |   X   |  X
            quick   |   X   |
            ------------------------
            Total   |   2   |  1
        两个文档都匹配，但是第一个文档比第二个匹配度更高。如果我们使用仅计算匹配词条数量的简单 相似性算法 ，那么，我们可以说，对于我们查询的相关性来讲，第一个文档比第二个文档更佳。

    



    文档的如何存储的：
        
        1. 当索引一个文档的时候，文档会存到主分片中。ES如何知道这个文档是在哪个分片里，依据的公式：
            分片  = hash(默认文档的id) % 主分片的数量
            shard = hash(routing) % number_of_primary_shards
                
                - routing 是一个可变值，默认是文档的 _id ，也可以设置成一个自定义的值。
                - routing 通过 hash 函数生成一个数字，然后这个数字再除以 number_of_primary_shards （主分片的数量）后得到 余数 。
                - 这个分布在 0到 number_of_primary_shards-1 之间的余数，就是我们所寻求的文档所在分片的位置。
                这就解释了为什么我们要在创建索引的时候就确定好主分片的数量 并且永远不会改变这个数量：因为如果数量变化了，那么所有之前路由的值都会无效，文档也再也找不到了。
            

    
    文档的操作：

        1.新增一个文档
            PUT poem/test/1
            {
                "id": "1",
                "userRemark": "索引中的version字段-相同的id就是update文档",
                "content": "是乐观锁，保证数据并发情况下的准确性-相同的id就是update文档"
            }
            解析： 1是文档的id，若id=1该文档不在索引内，则是新建，返回如下result=created，并且有个一version字段，
                  在Elasticsearch中每个文档都有一个版本号。当每次对文档进行修改时（包括删除），_version 的值会递增。在处理冲突中，
                  我们讨论了怎样使用 _version号码确保你的应用程序中的一部分修改不会覆盖另一部分所做的修改。
    
            {
                "_index" : "poem",
                "_type" : "test",
                "_id" : "6",
                "_version" : 1,
                "result" : "created",
                "_shards" : {
                "total" : 2,
                "successful" : 1,
                "failed" : 0
                },
                "_seq_no" : 1,
                "_primary_term" : 1
            }

        2.查询指定id的文档
          GET poem/test/1?pretty
          返回：
                {
                    "_index" : "poem",
                    "_type" : "test",
                    "_id" : "1",
                    "_version" : 1,
                    "_seq_no" : 0,
                    "_primary_term" : 1,
                    "found" : true,
                    "_source" : {
                    "id" : "demoData",
                    "userRemark" : "demoData",
                    "content" : "demoData"
                    }
                }
            
          #查询指定id的文档并且返回指定部分
          GET poem/test/1/_source
          返回：
                {
                    "id" : "demoData",
                    "userRemark" : "demoData",
                    "content" : "demoData"
                }



    





# logstash 


#### 同行

    filebeat ： 
            1. golang，轻量，消耗内存资源较小，
            2. 分布式服务可以考虑日志传送使用filebeat传送到logstash再转发Redis、mq，或者直接到Es

    logtash：
            1. filter功能，过滤筛选日志格式


#### 定义

 - Logstash 主要用于收集服务器日志，它是一个开源数据收集引擎，具有实时管道(pipeline)功能。Logstash 可以动态地将来自不同数据源的数据统一起来，并将数据标准化到您所选择的目的地。

#### 结构

 - 输入： 数据（包含但不限于日志）往往都是以不同的形式、格式存储在不同的系统中，而 Logstash 支持从多种数据源中收集数据（File、Syslog、MySQL、消息中间件等等）。
 - 过滤器： 实时解析和转换数据，识别已命名的字段以构建结构，并将它们转换成通用格式。   
 - 输出：Elasticsearch 并非存储的唯一选择，Logstash 提供很多输出选择。

#### 常用数据涞源：
  - file ： 从一个文件读取（*.log）
  - tcp : 
  
#### 数据源案例： 
  - file： 
        `
    
        input
            file {
                # 日志文件地址
                path => ["/var/log/*.log", "/var/log/message"]
                # 默认一小时
                close_older => 3600
                # 设置新行分隔符，默认为“ \ n”。
                delimiter => "\n"
                # 此参数配合stat_interval，此值用来发现新文件，最终频率为discover_interval × stat_interval。默认15
                discover_interval =>
                # 默认为1秒
                stat_interval => "1 second"
                # 忽略压缩包
                exclude => exclude => "*.gz"
                # 默认为false
                exit_after_read => false
                # 默认为4611686018427387903，是为了保证在读取下一个文件前保证当前文件已经读取完毕
                file_chunk_count => 4611686018427387903
                # 默认为32kb
                file_chunk_size => 32768
                # 默认值为delete，可选值：delete，log，log_and_delete
                file_completed_action => delete
                # 将完全读取的文件路径附加到哪个文件，此内容没有默认值
                file_completed_log_path => "/usr/local/log2/completed.log"
                # 默认值last_modified，可设置内容：last_modified, path
                file_sort_by => last_modified
                # 默认值asc，可设置的值asc, desc
                file_sort_direction =>
                # 设置了忽略1000秒之前修改的文件，此内容没有默认值
                ignore_older => 1000
                # 设置最多打开文件量，此值没有默认值，但是存在一个内部限制4095
                max_open_files => 4095
                # 设置了输入模式为tail
                # tail模式下，start_position 和close_older参数将被忽略。start_position始终从头开始读取文件，close_older到达EOF时文件自动关闭
                # read模式下需要设置ignore_older 、file_completed_action 、file_completed_log_path 参数
                mode => tail
                # 默认值2周
                sincedb_clean_after => "2 weeks"
                # 此为默认值，此值为文件路径而不是目录路径
                sincedb_path => path.data>/plugins/inputs/file
                # 默认值15秒
                sincedb_write_interval => "15 seconds"
                # 默认值"end"，可选值beginning，end。如果启动logstash的时候需要读取旧数据需要设置为beginning
                start_position => "end"
                # 下面是公共配置
                # 设置了type为system
                type => "system"
                # 默认line
                codec => "json"
                # 默认值为true
                enable_metric => false
                # 指定此数据输入id为input1
                id => input1
                # 添加了键位key值为value的数据到时间
                add_field => {
                "key" => "value"
                }
            }
        }
  

  - tcp:
      `
    
        input {
            tcp {
                # 主机地址
                host => "192.168.0.2"
                # 此时需要监听客户端
                mode => "server"
                # 要监听的端口
                port => 8081
                # 默认值为false
                tcp_keep_alive => false
                # 默认值为true
                dns_reverse_lookup_enabled => true
                # 下面是公共配置
                # 设置了type为system
                type => "system"
                # 默认line
                codec => "json"
                # 默认值为true
                enable_metric => false
                # 指定此数据输入id为input1
                id => input1
                # 添加了键位key值为value的数据到时间
                add_field => {
                "key" => "value"
                }
            }
        }


  - rabbitMq:
    ````
        input {
          rabbitmq {
            # 队列的主机
            host => "192.168.1.2"
            # 默认为guest
            password => "guest"
            # 消息服务器端口，默认为5672
            port => 5672
            # 默认为""
            queue => ""
            # 默认值为true
            ack => true
            # 默认值为{}
            arguments => { "x-ha-policy" => "all" }
            # 默认值为false
            auto_delete => false
            # 默认值为true
            automatic_recovery => true
            # 默认值为1秒
            connect_retry_interval => 1
            # 没有默认值，超时时间为无限
            connection_timeout => 1000
            # 默认值为false
            durable => false
            # 队列的交换器信息
            exchange => "log.exchange"
            # 队列的交换器信息
            exchange_type => "direct"
            # 默认值为false
            exclusive => false
            # 没有默认值，但是不指定的时候未60秒，秒为单位
            heartbeat => 60
            # 默认值为logstash，路由键
            key => logstash
            # 默认值为false，启动此功能保存元数据会影响性能
            metadata_enabled => false
            # 默认值为false，当设置true的时候表明为被动队列，则在消息服务器上，此队列已经存在
            passive => false
            # 默认为256
            prefetch_count => 256
            # 下面是公共配置
            # 设置了type为system
            type => "system" 
            # 默认line
            codec => "json"
            # 默认值为true
            enable_metric => false
            # 指定此数据输入id为input1
            id => input1
            # 添加了键位key值为value的数据到时间
            add_field => {
              "key" => "value"
            }
          }
        }


- redis: 
    ````
    input {
      redis {
      # 默认值为 125
      batch_count => 125
      # 没有默认值，但其可选内容list，channel，pattern_channel
      data_type => list
      # 默认值为 0
      db => 0
      # 默认值为 "127.0.0.1"
      host => "127.0.0.1"
      # 指定channel，没有默认值
      key => "channel"
      # redis的用户密码
      password => "password"
      # redis服务器端口，默认值为 6379
      port => 6379
      # 默认不开启SSL
      ssl => false
      # 初始超时为1秒
      timeout => 1
      }
    }
  

  
  
  
# Kibana ： Elasticsearch可视化web数据仪表台，数据分析面板


        