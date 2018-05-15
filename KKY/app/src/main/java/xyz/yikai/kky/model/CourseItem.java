package xyz.yikai.kky.model;

import xyz.yikai.kky.web.BaseItem;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:47
 * @Description:课程model
 */
public class CourseItem extends BaseItem {

    public Course data;

    @Override
    public Object getData() {
        return data;
    }

    public final class Course {

        public String icon; //图片
        public String course; //课程编号
        public String course_name; //课程名称
        public String classroom; //班级编号
        public String classroom_name; //班级名称
        public String teachers; //教师s
        public String yy_cnt; //预约成功人次
    }
}
