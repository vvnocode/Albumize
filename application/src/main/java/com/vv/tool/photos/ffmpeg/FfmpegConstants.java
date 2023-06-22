package com.vv.tool.photos.ffmpeg;

public interface FfmpegConstants {

    String FFMPEG_PATH = "../bin/ffmpeg/ffmpeg";

    String I = "-i";

    String VF = "-vf";

    /**
     * 压缩参数：正方形、200*200
     */
    String COMPRESS_SQUARE_SMALL = "crop=min(iw\\,ih):min(iw\\,ih),scale=200:200";

    String V_Q = "-q:v";
}
