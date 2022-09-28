package com.example.video_0927.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * //保存到SP
 *  SharedPreferences sp = getSharedPreferences("onClick", MODE_PRIVATE);
 *  sp.edit().putString("SoundCode","测点代码").apply();//apply才会写入到xml配置文件里面
 */

/**
 *  //获取到SP中的数据
 *   SharedPreferences sp1 = getSharedPreferences("onClick", MODE_PRIVATE);
 *                 //如果SoundCode,获取的值是空的,则会弹出后面的默认值
 *                 String obtain = sp1.getString("SoundCode", "默认值");

 */
public class SharedPreferencesUtil {

    private static SharedPreferences preference;

    /**
     * 要初始化
     * 可以在Application里初始化
     * @param context
     */
    public static void init(Context context){
        preference=context.getSharedPreferences("videoPractice",Context.MODE_PRIVATE);

    }

    public static void save(String key,String value){
        preference.edit()
                .putString(key,value)
                .apply();
    }

    public static String get(String key){
        return preference.getString(key,null);
    }

}
