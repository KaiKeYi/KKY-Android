package xyz.yikai.kky.model;

import xyz.yikai.kky.web.BaseItem;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:43
 * @Description:店铺model
 */
public class ShopItem extends BaseItem {

    public Shop data;

    @Override
    public Object getData() {
        return data;
    }

    public final class Shop {

        public String dealerno; //店铺编号
        public String shopname; //店铺名称
    }
}
