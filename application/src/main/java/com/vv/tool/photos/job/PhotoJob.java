package com.vv.tool.photos.job;

import com.vv.tool.photos.cache.ScanCache;
import com.vv.tool.photos.config.PropertiesConfig;
import com.vv.tool.photos.ffmpeg.FfmpegConstants;
import com.vv.tool.photos.utils.CommandUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 14:16
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class PhotoJob implements Runnable {

    private Path path;

    private ScanCache scanCache;

    private PropertiesConfig propertiesConfig;

    @Override
    public void run() {
        String absolutePath = path.toAbsolutePath().toString();
        String savePath = propertiesConfig.getThumbnailOut() + File.separator + new Random().nextInt(128);
        String outPhotoPath = savePath + File.separator + System.currentTimeMillis() + ".jpg";
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdir();
        }

        //压缩图片
        List<String> command = new ArrayList<>(16);
        //ffmpeg位置暂时写死
        command.add(FfmpegConstants.FFMPEG_PATH);
        command.add(FfmpegConstants.I);
        command.add(absolutePath);
        command.add(FfmpegConstants.VF);
        command.add(FfmpegConstants.COMPRESS_SQUARE_SMALL);
        command.add(FfmpegConstants.V_Q);
        command.add("1");
        command.add(outPhotoPath);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            CommandUtils.process(command, stringBuilder::append);
        } catch (IOException e) {
            log.error("处理Path = {} 出错", absolutePath, e);
            return;
        }
        log.debug("返回结果：{}", stringBuilder);

        //校验文件是否生成
        File outFile = new File(outPhotoPath);
        if (!outFile.exists()) {
            log.error("文件 {} 未生成", outPhotoPath);
            return;
        }
        //记录下已压缩图片数量
        scanCache.getCountJob().incrementAndGet();

        //todo 存储文件信息


    }
}
