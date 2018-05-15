package xyz.yikai.kky.model;

import xyz.yikai.kky.web.BaseItem;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:53
 * @Description:日期model
 */
public class DateItem extends BaseItem {

    public Date data;

    @Override
    public Object getData() {
        return data;
    }

    public final class Date {

        public String week; //5
        public String date; //2018-04-13
        public String date_name; //4月13日
        public String week_name; //今天
    }
}
