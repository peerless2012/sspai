package com.peerless2012.sspai.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Toast;
import com.peerless2012.sspai.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/7/8 11:33
 * @Version V1.0
 * @Description : 微信分享工具类
 * Fuck,在开发平台上填写签名
 * 1 必须是md5值（用keytool获取到的有md5，sha1，sha256）
 * 2 md5不能有冒号分割（建议小写或者用官方提供的获取签名的工具来获取）
 */
public class ShareUtils {

    /**
     * 收藏
     */
    public static final String TAG_FAVORITE = "TAG_FAVORITE";
    /**
     * 会话
     */
    public static final String TAG_SESSION = "TAG_SESSION";
    /**
     * 朋友圈
     */
    public static final String TAG_TIMELINE = "TAG_TIMELINE";

    /**
     * 未知，理论上不会用到
     */
    public static final String TAG_UNKNOWN = "TAG_UNKNOWN";

    @IntDef({SendMessageToWX.Req.WXSceneSession
            ,SendMessageToWX.Req.WXSceneTimeline
            ,SendMessageToWX.Req.WXSceneFavorite})
    public @interface Scene{}

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    private static final String APP_ID = "wxcf4964dc7b79bbd3";

    private static final String TAG = "ShareUtils";

    // IWXAPI 是第三方app和微信通信的openapi接口
    private static IWXAPI api;

    public static void install(Context context){
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context.getApplicationContext(),APP_ID, false);
        api.registerApp(APP_ID);
    }


    public static IWXAPI getApi(){
        return api;
    }

    public static void share(){
        // 初始化WXTextObject对象
        WXTextObject textObject = new WXTextObject();
        textObject.text = "文本";

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = textObject;
        mediaMessage.description = "描述";

        // 构造请求
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = mediaMessage;

        //调用接口发送
        boolean sendReq = api.sendReq(req);
        Log.i(TAG, "share: sendReq = "+sendReq);
    }

    public static void shareWeb(Context context,String title,String desc,String url,@Scene int scene){
        if (!checkForSupport(context)) return;
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = title;
        msg.description = desc;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        // 如果分享到朋友圈需要校验是否支持
        if (scene == SendMessageToWX.Req.WXSceneTimeline && !checkTimeLine(context)) return;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(scene);
        req.message = msg;
        req.scene = scene;
        boolean sendReq = api.sendReq(req);
        Log.i(TAG, "shareWeb: sendReq = "+sendReq);
    }

    private static String buildTransaction(@Scene int scene) {
        String tag = null;
        if (scene == SendMessageToWX.Req.WXSceneSession){
            tag = TAG_SESSION;
        }else if (scene == SendMessageToWX.Req.WXSceneTimeline){
            tag = TAG_TIMELINE;
        }else if (scene == SendMessageToWX.Req.WXSceneFavorite){
            tag = TAG_FAVORITE;
        }else {
            // can't reach
            tag = TAG_UNKNOWN;
        }
        return tag + System.currentTimeMillis();
    }

    /**
     * 检测是否支持分享到朋友圈
     * @return
     */
    public static boolean checkTimeLine(Context context){
        int wxSdkVersion = api.getWXAppSupportAPI();
        boolean supportTimeLine = wxSdkVersion >= TIMELINE_SUPPORTED_VERSION;
        if (!supportTimeLine) Toast.makeText(context,"微信版本过低，请升级微信后分享",Toast.LENGTH_LONG).show();
        return supportTimeLine;
    }

    /**
     * 检测微信客户端是否可用
     *
     * @return
     */
    private static boolean checkForSupport(final Context context) {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context,"尚未检测到微信，请安装微信后分享",Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }
}
