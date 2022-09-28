package com.example.video_0927.util;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ToastUtil {

    private static Toast sToast;

    public static void show(Context context,String text){
        if (sToast!=null){
            //取消正在显示的，不然需要等当前的toast显示完了才会显示后面的
            sToast.cancel();
        }
        sToast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        sToast.show();
    }

    public static void show(Fragment fragment,String text){
        show(fragment.getContext(),text);
    }
}
