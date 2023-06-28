package com.vv.tool.photos.job;

import com.vv.tool.photos.config.PropertiesConfig;
import com.vv.tool.photos.es.element.ESElementService;
import com.vv.tool.photos.file.PhFileVisitor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;

@Slf4j
@NoArgsConstructor

public class ScanJob implements Runnable {

    private PropertiesConfig propertiesConfig;

    private ThreadPoolTaskExecutor scanExecutor;

    private ESElementService esElementService;

    public ScanJob(PropertiesConfig propertiesConfig, ThreadPoolTaskExecutor scanExecutor, ESElementService esElementService) {
        this.propertiesConfig = propertiesConfig;
        this.scanExecutor = scanExecutor;
        this.esElementService = esElementService;
    }

    @Override
    public void run() {
        scan();
    }

    public void scan() {
        long l1 = System.currentTimeMillis();
        String path = propertiesConfig.getPicturePath();

        String glob = "glob:**/*.{jpg,png,jpeg}";
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);

        scanExecutor.execute(() -> {
            try {
                Files.walkFileTree(new File(path).toPath(),
                        new PhFileVisitor(pathMatcher, scanExecutor, propertiesConfig, esElementService));
                log.debug("扫描 {} 完成", path);
            } catch (IOException e) {
                log.error("扫描 {} 失败", path);
            }
        });

        log.debug("扫描耗时{}秒", (System.currentTimeMillis() - l1) / 1000.0);

    }
}
