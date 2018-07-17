package app.shao.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Description:
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/7/17
 */
public class ToastUtil {

    private static Toast mToast;

    public static void show(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
