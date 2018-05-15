package xyz.yikai.kky.module.test;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xlg.library.helper.AppHelper;
import com.xlg.library.utils.CommonUtil;
import com.xlg.library.utils.LogUtil;
import com.xlg.library.utils.ToastUtil;
import com.xlg.library.utils.Utils;

import java.util.ArrayList;

import xyz.yikai.kky.config.CacheConfig;

/**
 * @Author: Jason
 * @Time: 2018/4/24 15:37
 * @Description:测试工具类
 */
public class TestActivity extends ListActivity {

    private ArrayList<String> titles;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titles = new ArrayList<>();
        titles.add("=======测试页面=======");
        titles.add("切换到线上环境");//1
        titles.add("切换到测试环境");//2
        titles.add("判断字符串是否为空&打印调试日志&弹出Toast");//3
        titles.add("加载框");//4
        titles.add("提示框");//5
        titles.add("获取当前Activity");//6
        titles.add("接口请求Post");//7

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles));
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 1:
                CacheConfig.setIsTest(true);
                AppHelper.getInstance().exitAppClearData();
                break;
            case 2:
                CacheConfig.setIsTest(false);
                AppHelper.getInstance().exitAppClearData();
                break;
            case 3:
                String str = "";
                if (Utils.isEmpty(str)) { //判断字符串是否为空
                    LogUtil.i("字符串为空"); //打印调试日志
                    ToastUtil.getInstance().showToast("字符串为空"); //弹出Toast
                }
                break;
            case 4:

                break;
            case 5:

                break;
            case 6:
                CommonUtil.getCurrentActivity().finish(); //关闭当前页面
                break;
        }
    }
}
