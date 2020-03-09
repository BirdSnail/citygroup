package com.chutianyun.bigdata.model;

import lombok.Data;

import java.io.File;
import java.nio.file.Path;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
@Data
public class ApplicationFileInfo {

    /**
     * 入库时间
     */
//    private String RKSJ;

    /**
     * 入库文件
     */
    private String RKWJ;

    /**
     * 健康证明
     */
//    private String HEALTH_INFO;

    /**
     * 文件目录
     */
    private String WJML;

    /**
     * 文件子目录
     */
    private String WJZML;

    private Path currentPath;

    private static String splitter = File.separator.replace("\\", "\\\\");

    public ApplicationFileInfo(String RKWJ, String WJML, String WJZML, Path currentPath) {
        this.RKWJ = RKWJ;
        this.WJML = WJML;
        this.WJZML = WJZML;
        this.currentPath = currentPath;
    }

    public boolean isXiangYang() {
        return WJML.startsWith("襄阳市");
    }


    public boolean isYiChang() {
        return WJML.startsWith("宜昌市");
    }

    public static ApplicationFileInfo of(Path currentFile, String entry) {
        String fileName = currentFile.getFileName().toString();

        String[] dirs = currentFile.toAbsolutePath().toString().replace(entry, "")
                .split(splitter);
        int index = 0;
        if (dirs[0].isEmpty()) {
            index++;
        }
        String rkwj = fileName;
        String wjml = dirs[index++];

        if (dirs.length < 3) {
            return new ApplicationFileInfo(rkwj, wjml, fileName,currentFile);
        } else {
            return new ApplicationFileInfo(rkwj, wjml, dirs[index], currentFile);
        }
    }

}
