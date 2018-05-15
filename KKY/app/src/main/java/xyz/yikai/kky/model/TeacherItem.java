package xyz.yikai.kky.model;

import xyz.yikai.kky.web.BaseItem;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:59
 * @Description:教师model
 */
public class TeacherItem extends BaseItem {

    public Teacher data;

    @Override
    public Object getData() {
        return data;
    }

    public final class Teacher {

        public String tid; //93
        public String name; //芳芳
        public String can_order; //1
        public String ordered; //0
    }
}
