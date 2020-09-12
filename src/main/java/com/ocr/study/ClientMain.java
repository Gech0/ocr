package com.ocr.study;

import com.ocr.study.service.CaptchaService;
import com.ocr.study.service.WeeklyImageDownLoadService;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        //获取图片
        new WeeklyImageDownLoadService().down();

        //图片处理(分割)

        //图片识别

        //保存解析结果到本地

        //下载验证码
        //识别验证码
        String captcha = new CaptchaService().getCaptchaCode();

        //爬取信息
        //返回结果

    }
}
