package com.org.androidtools.calender1.entity;

import java.io.Serializable;

/**
 * Created by 28751 on 2019/1/31.
 */

public class ReportNumberEntity implements Serializable {
    public String date;//	string	日期 格式 yyyy-MM-dd
    public Integer count;//	int	报告数量


    public ReportNumberEntity(String date, Integer count) {
        this.date = date;
        this.count = count;
    }
}
