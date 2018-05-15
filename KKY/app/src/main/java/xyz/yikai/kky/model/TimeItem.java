package xyz.yikai.kky.model;

import xyz.yikai.kky.web.BaseItem;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:57
 * @Description:时间model
 */
public class TimeItem extends BaseItem {

    public Time data;

    @Override
    public Object getData() {
        return data;
    }

    public final class Time {

        public String time_start; //09:30
        public String can_order; //6
        public String ordered; //0
    }
}
