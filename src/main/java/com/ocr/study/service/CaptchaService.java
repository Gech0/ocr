package com.ocr.study.service;


import com.alibaba.fastjson.JSONObject;
import com.ocr.study.config.Constants;
import com.ocr.study.tools.DownloadImage;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {
    public String getCaptchaCode () throws IOException {
        String code;
        updateCode();
        code = recognizeCode();
        while (code.length() != 6) {
            code = recognizeCode();
        }
        return code;
    }
    private void updateCode () {
        String picUrl = Constants.verificationCodeUrl;
        String folder = Constants.imagePath;
        String fileName = "verification_code.jpg";
        try {
            DownloadImage.download(picUrl,fileName, folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String recognizeCode () throws IOException {
        //用户名
        String username= "gecho";
        //密码
        String password= "1122330";
        //验证码类型(默认数英混合),1:纯数字, 2:纯英文，3:数英混合：可空
        String typeid="1";
        //备注字段: 可以不写
        String remark="输出计算结果";
        InputStream inputStream=null;
        //你需要识别的1:图片地址，2:也可以是一个文件
        //1:这是远程url的图片地址
//        String url = "http://gk.vecc.org.cn/kaptcha/kaptcha.jpg";
//        URL u = new URL(url);
//        inputStream=u.openStream();
        //2:这是本地文件
        File needRecoImage=new File("src/main/resources/image/verification_code.jpg");
        inputStream=new FileInputStream(needRecoImage);

        Map< String, String> data = new HashMap<>();
        data.put("username",username);
        data.put("password", password);
        data.put("typeid", typeid);
        data.put("remark", remark);

        String resultString = Jsoup.connect("http://api.ttshitu.com/create.json")
                .data(data).data("image","test.jpg",inputStream)
                .ignoreContentType(true)
                .post().text();
        JSONObject jsonObject = JSONObject.parseObject(resultString);
        if (jsonObject.getBoolean("success")) {
            String result=jsonObject.getJSONObject("data").getString("result");
//            System.out.println("识别成功结果为:"+result);
            return result;
        }else {
            System.out.println("识别失败原因为:"+jsonObject.getString("message"));
        }
        return "";
    }


}
