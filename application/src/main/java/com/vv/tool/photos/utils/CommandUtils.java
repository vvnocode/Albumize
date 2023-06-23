package com.vv.tool.photos.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author vv
 * @description:
 * @date 2023/6/20 19:44
 */
@Slf4j
public class CommandUtils {
    /**
     * 执行命令
     *
     * @param command     命令
     * @param commandInfo 输出信息
     * @throws IOException IOException
     */
    public static void process(List<String> command, Consumer<String> commandInfo) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream inputStream = null;
        try {
            inputStream = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (commandInfo != null) {
                    commandInfo.accept(line);
                }
            }
            //听说进程未结束时调用exitValue将抛出异常。所以等待一下。
            process.waitFor();
            process.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

}