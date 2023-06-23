package com.vv.tool.photos.spring;

import com.vv.tool.photos.cache.ScanCache;
import com.vv.tool.photos.config.PropertiesConfig;
import com.vv.tool.photos.ffmpeg.FfmpegConstants;
import com.vv.tool.photos.file.PhFileVisitor;
import com.vv.tool.photos.utils.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 15:05
 */
@Slf4j
@SpringBootTest
public class TestScan {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private ScanCache scanCache;

    @Autowired
    private ThreadPoolTaskExecutor scanExecutor;

    /**
     * 测试压缩
     */
    @Test
    public void testCompress() throws IOException {
        String outPath = "../data/a2.jpg";
        File outFile = new File(outPath);
        if (outFile.exists()) {
            outFile.delete();
        }
        List<String> command = new ArrayList<>(10);
        command.add(FfmpegConstants.FFMPEG_PATH);
        command.add(FfmpegConstants.I);
        command.add("../data/test.jpg");
        command.add(FfmpegConstants.VF);
        command.add(FfmpegConstants.COMPRESS_SQUARE_SMALL);
        command.add(FfmpegConstants.V_Q);
        command.add("1");
        command.add(outPath);
        StringBuilder stringBuilder = new StringBuilder();
        CommandUtils.process(command, stringBuilder::append);
        log.debug("返回结果：{}", stringBuilder);

    }

    /**
     * 测试扫描文件夹
     */
    @Test
    public void testScan() {
        long l1 = System.currentTimeMillis();
        String path = "/Users/vv/Downloads/图片压缩测试/原图";
        log.debug("path = {}", path);

        String glob = "glob:**/*.{jpg,png,jpeg}";
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);

        AtomicBoolean finish = new AtomicBoolean(false);

        scanExecutor.execute(() -> {
            try {
                Files.walkFileTree(new File(path).toPath(),
                        new PhFileVisitor(scanCache, pathMatcher, scanExecutor, propertiesConfig));
                log.debug("扫描 {} 完成", path);
            } catch (IOException e) {
                log.error("扫描 {} 失败", path);
            } finally {
                finish.set(true);
            }
        });


        while (!finish.get() || scanCache.getCountJob().get() != scanCache.getCount().get()) {

        }
        long l = scanCache.getCount().get();
        long m = scanCache.getCountJob().get();
        log.debug("总共{}文件，完成{}文件，耗时{}秒", l, m, (System.currentTimeMillis() - l1) / 1000.0);

    }
}
