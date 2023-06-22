package com.vv.tool.photos.file;

import com.vv.tool.photos.cache.ScanCache;
import com.vv.tool.photos.config.PropertiesConfig;
import com.vv.tool.photos.job.PhotoJob;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @description:
 * @author vv
 * @date 2023/6/20 15:02
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class PhFileVisitor implements FileVisitor<Path> {

    private ScanCache scanCache;

    private PathMatcher pathMatcher;

    private ThreadPoolTaskExecutor scanExecutor;

    private PropertiesConfig propertiesConfig;

    /**
     * Invoked for a directory before entries in the directory are visited.
     */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    /**
     * Invoked for a file in a directory.
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if (pathMatcher.matches(file)) {
//            log.debug("符合条件的文件: {}", file);
            PhotoJob job = new PhotoJob(file, scanCache, propertiesConfig);
            scanExecutor.execute(job);
            //这里记录下扫描的数量
            scanCache.getCount().incrementAndGet();
        }

        return FileVisitResult.CONTINUE;

    }

    /**
     * 扫描文件失败
     */
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        log.error("扫描文件 {} 失败", file.toFile().getAbsolutePath(), exc);
        return FileVisitResult.CONTINUE;
    }

    /**
     * 完成文件夹扫描后
     */
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
