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

### 接口

- 扫描
  http://localhost:8080/task/scan
- 分页查询
  parentId为空时查询顶层
  http://localhost:8080/pic/l?pageSize=10&pageNo=1&parentId=
- 访问图片
  type=2缩略图，否则原图
  http://localhost:8080/pic/p?id=giixDIkBKWARSOetOkRD&type=2