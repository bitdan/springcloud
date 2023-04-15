CREATE TABLE `gzjj_wrj`.T_WF_WRJ_TEMP
(
    `sbbh`   varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '设备编号',
    `zqmj`   varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '执勤民警',
    `clfl`   int NULL DEFAULT NULL COMMENT '车辆分类: 不为空时，必须为3-公安机牌证机动车；4-武警机牌证机动车；5-部队牌证机动车；6-农机牌证机动车',
    `hpzl`   int NULL DEFAULT NULL COMMENT '号牌种类',
    `hphm`   varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '号牌号码',
    `xzqh`   varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '违法地行政区划',
    `wfdd`   varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '违法地点',
    `lddm`   varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '路段代码',
    `ddms`   int NULL DEFAULT NULL COMMENT '地点米数',
    `wfdz`   varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '违法地址:  中文描述',
    `wfsj`   datetime NULL DEFAULT NULL COMMENT '违法时间: 不为空时，格式如：\r\nYYYY-MM-DD hh24:mi:ss\r\n',
    `wfsj1`  datetime NULL DEFAULT NULL COMMENT '违法时间_1, 区间测速抓拍的交通违法行为可采集，可为空',
    `wfxw`   varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '违法行为, 4位或5位代码',
    `scz`    varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '实测值',
    `bzz`    varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标准值',
    `zpwjm`  varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图片文件名',
    `zpstr1` mediumtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '照片1, Base64编码，如果图片以地址链接方式提供，可为空',
    `zpstr2` mediumtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '照片2, Base64编码',
    `zpstr3` mediumtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '照片3, Base64编码',
    `wfspdz` varchar(1228) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '违法视频地址, 与综合平台保持一致',
    `fxjg`   varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '违法发现机关, 违法发现机关，如果不传，默认取设备管理部门',
    `zpurl1` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '照片链接_1',
    `zpurl2` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '照片链接_2',
    `zpurl3` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '照片链接_3'
        PRIMARY KEY (`device_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '无人机违法数据' ROW_FORMAT = Dynamic;