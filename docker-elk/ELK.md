# ELK 

- Elasticsearch: 分布式搜索和分析引擎，用于全文检索结构化检索及分析，基于Lucene开发 （源于创始人为妻子做一个菜谱功能）
- Logstash:实时数据传输管道，负责将数据从管道输入端传入管道的输出端，并且对这些数据可以进行筛选，清洗的操作
- Kibana:可视化操作平台，是用于Elasticsearch的使用，搜索查看索引的数据，地图、图表，kibana能够很轻易的展示高级数据分析与可视化




## Elasticsearch : 

- 健康检测： localhost:9200/_cluster/health，返回：
  
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



- 安装IK分词器：

  1. docker exec -it 容器id /bin/bash
  2. ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.13.2/elasticsearch-analysis-ik-7.13.2.z
  3. docker restart 容器id
  