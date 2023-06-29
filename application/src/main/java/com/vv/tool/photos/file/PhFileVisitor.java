package com.vv.tool.photos.file;

import com.vv.tool.photos.cache.ScanCache;
import com.vv.tool.photos.config.PropertiesConfig;
import com.vv.tool.photos.es.element.ESElementService;
import com.vv.tool.photos.es.element.Element;
import com.vv.tool.photos.job.PhotoJob;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 15:02
 */
@Slf4j
@NoArgsConstructor
public class PhFileVisitor implements FileVisitor<Path> {

    private PathMatcher pathMatcher;

    private ThreadPoolTaskExecutor scanExecutor;

    private PropertiesConfig propertiesConfig;

    private ESElementService esElementService;

    public PhFileVisitor(PathMatcher pathMatcher, ThreadPoolTaskExecutor scanExecutor, PropertiesConfig propertiesConfig, ESElementService esElementService) {
        this.pathMatcher = pathMatcher;
        this.scanExecutor = scanExecutor;
        this.propertiesConfig = propertiesConfig;
        this.esElementService = esElementService;
    }

    /**
     * Invoked for a directory before entries in the directory are visited.
     */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        //todo 保存文件夹信息
        String fileName = dir.getFileName().toString();
        File file1 = dir.toFile();
        String parentPath = file1.getParentFile().toString();
        String parentAbsolutePath = file1.getParentFile().getAbsolutePath();
        String absolutePath = dir.toAbsolutePath().toString();

        String parentId = ScanCache.getId(parentAbsolutePath);
        Element element = new Element();
        element.setFileName(fileName);
        element.setFileType(2);
        element.setFileParentPath(parentPath);
        element.setFileCreateTime(new Date());
        element.setFileAbsolutePath(absolutePath);
        element.setParentId(parentId);
        Element save = esElementService.Save(element);

        String id = save.getId();
        ScanCache.putId(absolutePath, id);

        return FileVisitResult.CONTINUE;
    }

    /**
     * Invoked for a file in a directory.
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        if (pathMatcher.matches(file)) {
//            log.debug("符合条件的文件: {}", file);
            PhotoJob job = new PhotoJob(file, propertiesConfig, esElementService);
            scanExecutor.execute(job);
            //这里记录下扫描的数量

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
