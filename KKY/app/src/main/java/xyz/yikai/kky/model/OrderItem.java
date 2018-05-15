package xyz.yikai.kky.model;

import xyz.yikai.kky.web.BaseItem;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:49
 * @Description:订单model
 */
public class OrderItem extends BaseItem {

    public Order data;

    @Override
    public Object getData() {
        return data;
    }

    public final class Order {

        public String tid;
        public String course; //课程编号
        public String course_name; //课程名称
        public String classroom; //班级编号
        public String classroom_name; //班级名称
        public String teacher; //教师
        public String icon; //课程图片https://yy.kaikeyi.cn/Public/images/course/1.png
        public String day; //2018-04-02
        public String time_start; //10:30
        public String day_name; //4月2日
        public String status; //预约中
        public String week; //周一
        public String usetime;
        public String updatetime;
    }
}
