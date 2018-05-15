package com.xlg.library.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by terry on 2017/3/21.
 */
public class NumberFormat {

    private static DecimalFormat format;
    private static DecimalFormat formatDouble;

    private static DecimalFormat getFormat(){
        if(format == null){
            format = new DecimalFormat();
        }
        return format;
    }

    private static DecimalFormat getFormatDouble(){
        if(formatDouble == null){
            formatDouble = new DecimalFormat("###,###.##");
        }
        return formatDouble;
    }

//    public static String getDealerPrice(String p, boolean label){
//        String string = "--";
//        try {
//            p = p.replace("￥", "").replace(",", "");
//            int price = (int)Float.parseFloat(p);
//            if (!TextUtils.isEmpty(p) && price != 0) {
//                if(price >= 0 || !label) {
//                    string = (label ? "￥" : "") + format(price);
//                }else if(price < 0 && label){
//                    string = "-￥" +  format(price).substring(1);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            if(p != null)
//                return p;
//        }
//        return string;
//    }

//    public static String getDealerPrice(String p){
//        return getDealerPrice(p, true);
//    }

//    public static String getDealerDiscountPrice(String manPrice, String nokedPirce){
//        return getDealerPrice(getDealerIntDiscount(manPrice, nokedPirce) + "");
//    }

//    public static String getDealerIntDiscount(String manPrice, String nokedPirce){
//        try {
//            return (int)(Float.parseFloat(manPrice) - Float.parseFloat(nokedPirce)) + "";
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "";
//    }

    /**
     * 格式化千分位
     * @param number
     * @return
     */
    public static String format(double number){
        return getFormat().format(number);
    }


    /**
     * 格式化千分位
     * @param number
     * @return
     */
    public static String format(String number){//防止出现 number为123.00情况
        if(TextUtils.isEmpty(number)) return "";
        return format(Double.parseDouble(number));
    }


    /**
     * 去除￥ ，
     * @param  price
     * @return
     */
    public static double getDoublePrice(String price){
        if(TextUtils.isEmpty(price))return 0;
        price = price.replaceAll(",", "");
        price = price.replace("￥","");
        try {
            return Double.parseDouble(price);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }



    public static String getFormatDouble(String p){
        return getFormatDouble(p, true);
    }

    public static String getFormatDouble(String p, boolean label) {
        String string = "--";
        try {
            p = p.replace("￥", "").replace(",", "");
            double price = Double.parseDouble(p);
            if (!TextUtils.isEmpty(p)) {
                if(price >= 0 || !label) {
                    string = (label ? "￥" : "") + formatDouble(price);
                }else if(price < 0 && label){
                    string = "-￥" +  formatDouble(price).substring(1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            if(p != null)
                return p;
        }
        return string;
    }

    public static String formatDouble(double number){
        return getFormatDouble().format(number);
    }

}
