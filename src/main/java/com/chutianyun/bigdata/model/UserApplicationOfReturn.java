package com.chutianyun.bigdata.model;

import lombok.Data;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
@Data
public class UserApplicationOfReturn {

    /**
     * 申请文件信息
     */
    private ApplicationFileInfo applicationFileInfo;

    /**
     * 申请返岗人信息
     */
    private ApplicationUser user;

    public UserApplicationOfReturn(ApplicationFileInfo applicationFileInfo, ApplicationUser user) {
        this.applicationFileInfo = applicationFileInfo;
        this.user = user;
    }
}
