package com.design.oa.utils;

import java.util.UUID;

/**
 * @Description: 生成 UUId
 * @Author: caomt
 * @Date Create In 18:02 2018/12/13
 * @Modified By:
 */
public class UUIdUtil {

    public static String getUUID32() {

        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
//  return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

}
