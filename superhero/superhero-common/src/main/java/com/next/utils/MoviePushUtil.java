package com.next.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.util.ArrayList;
import java.util.List;

public class MoviePushUtil {
    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
//    private static String appId = "MuPAEm7fmi8S3OFLVwYkr5";
//    private static String appKey = "xqCpWGlMWY9X8YSRPv7eJ7";
//    private static String masterSecret = "DuYy91mAx7Ac2yGcpRc7o8";
//    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    private static String appId = "TAZjzn3GMkAWlCthzUjWv7";
    private static String appKey = "v8FmArEoVq5CC5MOebL434";
    private static String masterSecret = "fhMFmWcKHr7uYQomzaRyv";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void doPush(String title, String text) {
        IGtPush iGtPush = new IGtPush(url, appKey, masterSecret);

        NotificationTemplate template = getNotificationTemplate(appId, appKey, title, text);

        List<String> appIds = new ArrayList<>();
        appIds.add(appId);

        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult iPushResult = iGtPush.pushMessageToApp(message);
        System.out.println(iPushResult.getResponse().toString());
    }

    public static void main(String[] args) {
        IGtPush iGtPush = new IGtPush(url, appKey, masterSecret);

        NotificationTemplate template = getNotificationTemplateTest(appId, appKey);

        List<String> appIds = new ArrayList<>();
        appIds.add(appId);

        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult iPushResult = iGtPush.pushMessageToApp(message);
        System.out.println(iPushResult.getResponse().toString());
    }

    private static NotificationTemplate getNotificationTemplate(String appId, String appKey, String title, String text) {
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        style.setText("这是text内容");
        style.setText("这是title标题");

        template.setStyle(style);

        return template;
    }

    private static NotificationTemplate getNotificationTemplateTest(String appId, String appKey) {
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        style.setText("这是text内容");
        style.setText("这是title标题");

        template.setStyle(style);

        return template;
    }
}
