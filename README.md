# Albumize

一个私有相册

## 开发

- 需要下载ffmpeg到bin目录

### es

- 安装
    ```shell
    docker network create itmentu-net
    docker run -d \
        --name elasticsearch7 \
        -e "ES_JAVA_OPTS=-Xms1024m -Xmx4096m" \
        -e "discovery.type=single-node" \
        -v /mnt/user/appdata/elasticsearch/7/data:/usr/share/elasticsearch/data \
        -v /mnt/user/appdata/elasticsearch/7/plugins:/usr/share/elasticsearch/plugins \
        --privileged \
        --network itmentu-net \
        -p 9200:9200 \
        -p 9300:9300 \
    elasticsearch:7.17.10
    ```

- 创建索引
  PUT /element
    ```
    {
        "mappings": {
            "properties": {
                "FILE_TYPE": {
                    "type": "integer"
                },
                "FILE_NAME": {
                    "type": "keyword"
                },
                "FILE_PARENT_PATH": {
                    "type": "keyword"
                },
                "FILE_ABSOLUTE_PATH": {
                    "type": "keyword"
                },
                "FILE_SIZE": {
                    "type": "long"
                },
                "FILE_CREATE_TIME": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                },
                "THUMBNAIL_ABSOLUTE_PATH": {
                    "type": "text"
                },
                "THUMBNAIL_SIZE": {
                    "type": "long"
                }
            }
        }
    }
    ```