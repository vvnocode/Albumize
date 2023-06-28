package com.vv.tool.photos.spring;

import com.vv.tool.photos.config.PropertiesConfig;
import com.vv.tool.photos.es.element.ESElementService;
import com.vv.tool.photos.ffmpeg.FfmpegConstants;
import com.vv.tool.photos.job.ScanJob;
import com.vv.tool.photos.utils.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private ThreadPoolTaskExecutor scanExecutor;

    @Autowired
    private ThreadPoolTaskExecutor compressExecutor;
    @Autowired
    private ESElementService esElementService;


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
    public void testScanJob() {
        scanExecutor.execute(new ScanJob(propertiesConfig, compressExecutor, esElementService));
        while (true) {

        }
    }
}
