package xyz.yikai.kky.model;

import xyz.yikai.kky.web.BaseItem;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:34
 * @Description:用户model
 */
public class UserItem extends BaseItem {

    public User data;

    @Override
    public Object getData() {
        return data;
    }

    public final class User {

        public String cid; //课程ID
        public String mobile; //手机号
        public String password; //密码
        public String vip; //VIP号
        public String jinzhan; //状态(已办卡)
        public String bbname; //宋梦婷
        public String parent; //宋梦婷
        public String dizhi; //地址

        public String nickname; //昵称
        public String birthday; //生日
        public String sex; //性别
        public String baby_number; //宝宝编号5

        public String shopname; //店铺名称
        public String shopphone; //店铺手机号
        public String shopaddress; //店铺地址
        public String token; //token
        public String dealerno; //店铺编号
    }
}
