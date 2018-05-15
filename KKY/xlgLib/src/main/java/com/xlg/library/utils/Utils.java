package com.xlg.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.xlg.library.base.BaseApp;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("SimpleDateFormat")
public class Utils {
    private static Dialog loadingDialog;
    private static Toast mToast;
    private static Notification n = null;
    private static int notifyId;
    private static Notification.Builder builder;
    private static int cuurentProgress;
    /**
     * @param context
     */
    private static int i = 0;
    private static CountDownTimer countDownTimer;  //里面有handler,要canle,要不会内存泄露
    /**
     * 图片的存储路径
     */
    private static String imagePath = "";
    /**
     * 获取appsd路径
     */
    private static String appPath = "";
    /**
     * 缓存的存储路径
     */
    private static String cachePath = "";
    /**
     * 资产报告的存储路径
     */
    private static String assetPath = "";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat performanceSDF = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormatMounthDay = new SimpleDateFormat("MM-dd HH:mm");
    private static SimpleDateFormat simpleDateFormatMiuns = new SimpleDateFormat("HH:mm");

    //数字格式化
    public static DecimalFormat df = new DecimalFormat("#.##");
    public static DecimalFormat df_order = new DecimalFormat("###,###");
    //日期格式化
    public static SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf_minute = new SimpleDateFormat("HH:mm");

    /**
     * 关闭流
     *
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果，以字符串格式返回
     */
    public static String round(String v, int scale) {
        return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v          需要四舍五入的数字
     * @param scale      小数点后保留几位
     * @param round_mode 指定的舍入模式
     * @return 四舍五入后的结果，以字符串格式返回
     */
    public static String round(String v, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, round_mode).toString();
    }

    public static String round(double d, int scale) {
        return round(d + "", scale, BigDecimal.ROUND_HALF_DOWN);
    }

    public static boolean formatInput(String text, int start, EditText editText) {
//        if (TextUtils.isEmpty(text)) {
//            return true;
//        }
//        if (text.equals("0")) {
//            editText.setText("");
//            return true;
//        }
        if (text.equals(".")) {
            editText.setText("");
            return true;
        }
        if (text.length() > start) {
            StringBuilder sb = new StringBuilder(text);
            char c = text.charAt(start);
            if (c == '.' && Utils.getCharCount(text, '.') > 1) {
                sb.replace(start, start, "");
                editText.setText(sb.toString());
                editText.setSelection(editText.getText().length());
                return true;
            }

//            if (start == 0 && c == '0') {
//                text = text.substring(1, text.length());
//                editText.setText(text);
//                editText.setSelection(editText.getText().length());
//                return true;
//            }
        }
        if (text.contains(".")) {

            String[] strs = text.split("\\.");
            if (strs.length > 1) {
                String str = strs[1];
                if (str.length() > 2) {
                    text = text.substring(0, text.length() - 1);
                    editText.setText(text);
                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断是否为全数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static String parseDate(String dateStr, String oriFormat, String toFormat) {
        if (!TextUtils.isEmpty(dateStr)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(oriFormat);
                Date date = sdf.parse(dateStr);
                sdf = new SimpleDateFormat(toFormat);
                return sdf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";

    }

    public static long parse2ms(String dateStr, String oriFormat) {
        if (!TextUtils.isEmpty(dateStr)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(oriFormat);
                Date date = sdf.parse(dateStr);
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;

    }

    /**
     * 全角转半角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("!", "！").replaceAll(":", "：");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 清空文件夹
     */
    public static void clearDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        f.delete();
                    } else {
                        clearDir(f);
                    }
                }
            }
        }
    }

    public static int getCharCount(String txt, char c) {
        int count = 0;
        for (int i = 0; i < txt.length(); i++) {
            char s = txt.charAt(i);
            if (s == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * @param money
     * @return 三位一个 "，" 的显示方式
     */
    public static String formatMoney(String money) {
        if (TextUtils.isEmpty(money)) {
            return "0";
        }
        money = money.replaceAll(",", ""); // 去掉所有逗号
        java.text.DecimalFormat df = new java.text.DecimalFormat("##,###,###.##");
        return df.format(Double.parseDouble(money));
    }

    /**
     * @param context
     * @return if true have Internet or false
     */
    public static boolean isConnectInternet(Context context) {
        if (context == null) {
            context = BaseApp.getAppContext();
        }
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

//		if (CheckApnUtil.checkNetworkType(context) == CheckApnUtil.TYPE_CT_WAP) {
//			return true;
//		}

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }

        return false;
    }

    /**
     * @param context
     * @param despStr void
     * @描述: 调用系统的发送数据
     * @异常
     */
    public static void onSendText(final Context context, final String despStr) {
        if (null == context || TextUtils.isEmpty(despStr)) return;

        Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
        intent.setType("text/plain"); // 分享发送的数据类型
        intent.putExtra(Intent.EXTRA_TEXT, despStr); // 分享的内容
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "选择分享"));// 目标应用选择对话框的标题
    }

    /**
     * 获取渠道号
     */
    public static String getChannelID() {
        String channels = "";
        try {
            Context ctx = BaseApp.getAppContext();
            ApplicationInfo appInfo = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
            int chan = appInfo.metaData.getInt("RENTALCAR_CHANNLE");
            if (chan == 1) {
                channels = "1001";
            } else {
                channels = chan + "";
            }
        } catch (PackageManager.NameNotFoundException e) {
        }

        if (TextUtils.isEmpty(channels)) {
            channels = "1001";
        }
        return channels;

    }

    /**
     * 是否符合正则
     */
    public static boolean regexMatcher(String content, String matcher, int minLen, int maxLen) {
        if (TextUtils.isEmpty(content)) {
            return true;
        }
        int len = 0;
        try {
            len = content.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(matcher)) {
            return len >= minLen && len <= maxLen;
        }

        if (len >= minLen && len <= maxLen) ;
        else return false;

        Pattern p = Pattern.compile(matcher);
        Matcher m = p.matcher(content);
        return m.matches();
    }

    /**
     * 验证str 字符长度
     *
     * @param content
     * @param minLen
     * @param maxLen
     * @return
     */
    public static boolean patternStrLength(String content, int minLen, int maxLen) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        int len = 0;
        try {
            len = content.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return len >= minLen && len <= maxLen;
    }

    /**
     * 实名验证
     *
     * @param realName 只能输入中文，最少2个中文，最多10个中文（6—30个字符）
     * @return
     */
    public static boolean realNamePattern(String realName) {
        if (TextUtils.isEmpty(realName)) {
            return true;
        }
        int len = 0;
        try {
            len = realName.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (len >= 6 && len <= 30) ;
        else return false;
        Pattern pattern = Pattern.compile("^[\u4E00-\u9fa5]+$");
        return pattern.matcher(realName).matches();
    }

    /**
     * 当前网络是否是wifi
     *
     * @param context
     * @return
     */

    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }


    /**
     * 获取文件权限
     *
     * @param dir
     */
    public static void getChmod(File dir) {
        String[] command = {"chmod", "777", dir.getPath()};

        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * sd卡是否可用
     *
     * @return
     */
    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && readSDCard() > 10;
    }

    /**
     * SD卡可用的空间，单位为mb
     */
    public static long readSDCard() {
        long AvailableRoom = 0;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            // long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            AvailableRoom = availCount * blockSize / 1024 / 1024;
        }
        return AvailableRoom;
    }

    /**
     * 获取次文件夹 的剩余容量
     *
     * @param dir
     * @return
     */
    public static long getFileCapacity(File dir) {
        long AvailableRoom = 0;
        StatFs sf = new StatFs(dir.getPath());
        long blockSize = sf.getBlockSize();
        // long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();
        AvailableRoom = availCount * blockSize / 1024 / 1024;
        return AvailableRoom;
    }


    public static boolean judgeHasVK() {
        if (Display.getScreenWidth() == Display.SCREEN_XXLARGE) {
            return Display.getScreenHeight() < 1900;
        } else if (Display.getScreenWidth() == Display.SCREEN_XXXLARGE) {
            return Display.getScreenHeight() < 2400;
        } else if (Display.getScreenWidth() == Display.SCREEN_XLARGE) {
            return Display.getScreenHeight() < 1200;
        }
        return false;
    }

    /**
     * ViewHolder
     *
     * @param convertView
     * @param id
     */
    public static <T extends View> T obtainView(View convertView, int id) {
        SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
        if (holder == null) {
            holder = new SparseArray<View>();
            convertView.setTag(holder);
        }
        View childView = holder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            holder.put(id, childView);
        }
        return (T) childView;
    }


    /**
     * 隐藏输入键盘
     *
     * @param activity
     */
    public static void hidenKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 将毫秒数换算成x天x时x分x秒x毫秒
     *
     * @param ms
     * @return
     */
    public static String formatTime(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strDay + "天" + strHour + "小时" + strMinute + "分钟";
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 判断是否是同一天
     *
     * @param day1
     * @param day2
     * @return
     */

    public static boolean isSameDay(Date day1, Date day2) {
        String ds1 = simpleDateFormat.format(day1);
        String ds2 = simpleDateFormat.format(day2);
        return ds1.equals(ds2);
    }

    /**
     * 判断是否是Email地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否是身份证号码
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        String str = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    /**
     * 手机验证码是否正确
     */
    public static boolean phoneVerifiPattern(String password) {
        Pattern pattern = Pattern.compile("^\\d{6}$");
        return pattern.matcher(password).matches();
    }

    /**
     * 验证手机后四位
     */
    public static boolean phoneFourPattern(String password) {
        Pattern pattern = Pattern.compile("^\\d{4}$");
        return pattern.matcher(password).matches();
    }

    /**
     * 验证密码
     */
    public static boolean passwordPattern(String password) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]{6,16}$");
        return pattern.matcher(password).matches();
    }

    /**
     * 检测手机号是否合法
     */
    public static boolean checkPhoneNum(String phoneNum) {
        String pattern = "1[3-8][0-9]{9}";
        return phoneNum.matches(pattern);
    }

    /**
     * 验证账号
     *
     * @param account 字母、数字、下划线，4-20位，首字母不能是下划线
     * @return
     */
    public static boolean acccountPattern(String account) {
        if (TextUtils.isEmpty(account)) {
            return false;
        }
        String rex = "^[a-zA-Z0-9][a-zA-Z0-9_]{3,19}$";
        Pattern patternOne = Pattern.compile(rex);
        boolean one = patternOne.matcher(account).matches();
        return one;
    }

    private static int chineseNums(String str) {
        byte b[] = str.getBytes();
        int byteLength = b.length;
        int strLength = str.length();
        return (byteLength - strLength) / 2;
    }

    /**
     * 银行卡位数验证 13 到19
     *
     * @param bankNum
     * @return
     */
//    public static boolean bankPattern(String bankNum) {
//        Pattern pattern = null;
//        pattern = Pattern.compile("^\\d{13,19}$");
//        return pattern.matcher(bankNum).matches();
//    }

    /**
     * 验证登陆账号
     *
     * @param account ^[^!#$%^&*?~\\s+]{4,20}$
     * @return
     */
//    public static boolean loginAcccountPattern(String account) {
//        if (TextUtils.isEmpty(account)) {
//            return false;
//        }
//        String rexOne = "^[^!#$%^&*?~\\s+]{0,20}$";
//        Pattern patternOne = Pattern.compile(rexOne);
//        int num = chineseNums(account);
//        int len = account.getBytes().length - 3 * num + 2 * num;
//        boolean one = patternOne.matcher(account).matches() && len <= 20 && len >= 4;
//        return one;
//    }

    /**
     * 将金额转成汉字金额
     *
     * @param money
     */
    public static String digitUppercase(double money) {
        String fraction[] = {"角", "分"};
        // String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"
        // };
        // String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };
        String digit[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String unit[][] = {{"元", "万", "亿"}, {"", "十", "百", "千"}};

        String head = money < 0 ? "负" : "";
        money = Math.abs(money);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int) (Math.floor(money * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(money);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && money > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }


    public static String formatTime(Date date) {
        return simpleDateFormat.format(date);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 时间戳转年月日
     *
     * @param date   秒值
     * @param format 格式 如，yyyy-MM-dd
     * @return
     */
    public static String formatTimeMiunsDate(long date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(date * 1000));
    }

    public static String formatDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            try {
                performanceSDF.applyPattern("yyyyMMdd");
                Date parseDate = performanceSDF.parse(date);
                performanceSDF.applyPattern("MM.dd");
                return performanceSDF.format(parseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String formatTimeMounthDay(long time) {
        return simpleDateFormatMounthDay.format(time);
    }


    public static String formatTimeMiuns(long time) {
        return simpleDateFormatMiuns.format(time);
    }

    public static Date lastWeek(String dateStr, int isNext) {
        Date date = null;
        if (TextUtils.isEmpty(dateStr)) {
            return new Date();
        }
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return new Date();
        }
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date)) + isNext * 6;
        if (day < 1) {
            month -= 1;
            if (month == 0) {
                year -= 1;
                month = 12;
            }
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                day = 30 + day;
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                day = 31 + day;
            } else if (month == 2) {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) day = 29 + day;
                else day = 28 + day;
            }
        }
        String y = year + "";
        String m = "";
        String d = "";
        if (month < 10) m = "0" + month;
        else m = month + "";
        if (day < 10) d = "0" + day;
        else d = day + "";
        String str = y + m + d;
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static Date nextWeek(Date date) {
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date)) + 6;
        if (day < 1) {
            month -= 1;
            if (month == 0) {
                year -= 1;
                month = 12;
            }
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                day = 30 + day;
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                day = 31 + day;
            } else if (month == 2) {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) day = 29 + day;
                else day = 28 + day;
            }
        }
        String y = year + "";
        String m = "";
        String d = "";
        if (month < 10) m = "0" + month;
        else m = month + "";
        if (day < 10) d = "0" + day;
        else d = day + "";
        String str = y + m + d;
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String getMondayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int today = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_WEEK, -today + Calendar.MONDAY);
        LogUtil.i(simpleDateFormat.format(c.getTime()));
        return simpleDateFormat.format(c.getTime());
    }

    public static Date whichDay(Date date, int i) {
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date)) + i;
        while (day < 0) {
            if (day < 1) {
                month -= 1;
                if (month == 0) {
                    year -= 1;
                    month = 12;
                }
                if (month == 4 || month == 6 || month == 9 || month == 11) {
                    day = 30 + day;
                } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    day = 31 + day;
                } else if (month == 2) {
                    if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) day = 29 + day;
                    else day = 28 + day;
                }
            }
        }
        String y = year + "";
        String m = "";
        String d = "";
        if (month < 10) m = "0" + month;
        else m = month + "";
        if (day < 10) d = "0" + day;
        else d = day + "";
        String str = y + m + d;
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }


    /**
     * 获取是上午还是下午
     *
     * @return
     */
    public static String getAMorPM(Context context) {
        String nowStr = "";
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (5 < hour && hour < 12) {
            nowStr = "上午好";
        } else if (12 <= hour && hour < 18) {
            nowStr = "下午好";
        } else {
            nowStr = "晚上好";
        }
        return nowStr;
    }

    /**
     * 传入时间获取上下午
     *
     * @param context
     * @param time
     * @return
     */
    public static String getAMorPM(Context context, long time) {
        String nowStr = "";
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (5 < hour && hour < 12) {
            nowStr = "上午好";
        } else if (12 <= hour && hour < 18) {
            nowStr = "下午好";
        } else {
            nowStr = "晚上好";
        }
        return nowStr;
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }


    /**
     * 是否有sd卡
     *
     * @return
     */
    public static boolean getHasSdCard() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) && readSDCard() > 10;
    }


    /**
     * 清除通知
     */
    public static void clearNotification(Context context) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) context.getSystemService(ns);
        try {
            mNotificationManager.cancel(123456789);
            cuurentProgress = 0;
            builder = null;
            n = null;
        } catch (Exception e) {
            mNotificationManager.cancel(123456789);
            cuurentProgress = 0;
            builder = null;
            n = null;
        }
    }

    /**
     * Bean --> Map
     * 利用Introspector和PropertyDescriptor 将Bean --> Map
     *
     * @param javaBean
     * @return
     */
    public static Map<String, String> toMap(Object javaBean) {
        Map<String, String> result = new HashMap<>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception e) {
            }
        }

        return result;
    }


    /**
     * 没有数据or 网络加载请求失败页面
     */
    private static PopupWindow mNetErrorPop;


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转 long
     *
     * @param str
     * @return
     */
    public static long getTime(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static boolean isEmpty(String str) {
        if (str == null
                || str.length() == 0
                || str.equals("null")
                || str.equals("(null)")
                || str.equals("<null>"))
            return true;
        else
            return false;
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     * @return true 为模拟器
     * @引用文章：http://blog.csdn.net/tianshuai4317618/article/details/78834683
     */
    public static Boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (null == sensor8) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description:  dp转px
     * @Author:  Jason
     * @Time:  2018/3/14 15:34
     */
    public static int calculateDpToPx(Context context, int dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (int) (dp * scale + 0.5f);
    }
}
