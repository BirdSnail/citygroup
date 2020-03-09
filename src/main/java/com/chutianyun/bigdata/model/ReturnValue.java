package com.chutianyun.bigdata.model;

import lombok.Data;
import lombok.Getter;

import java.nio.file.Path;

/**
 * @author BirdSnail
 * @date 2020/3/9
 */
@Getter
public class ReturnValue {

    private int count;

    private Path currentPath;

    public ReturnValue(int count, Path currentPath) {
        this.count = count;
        this.currentPath = currentPath;
    }

    @Override
    public String toString() {
        return "{文件:" + currentPath.toAbsolutePath().toString() + ", 申请人数:" + count + "}";
    }
}
