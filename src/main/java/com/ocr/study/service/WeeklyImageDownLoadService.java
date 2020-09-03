package com.ocr.study.service;


import com.ocr.study.config.Constants;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.ocr.study.service.DownloadService.download;

@Service
public class WeeklyImageDownLoadService implements PageProcessor {

    public static void down() {
        Spider.create(new WeeklyImageDownLoadService())
                .addUrl(Constants.weekUrl)
                .addPipeline(new ConsolePipeline())
                .thread(5) //设置多线程
                .run();
    }


    private Site site = Site.me()
            .setCharset("utf8") //设置编码
            .setTimeOut(10000)  //设置超时时间，单位是ms
            .setRetrySleepTime(1000) //设置重试的间隔时间
            .setSleepTime(3);   //设置


    public void process(Page page) {
        page.addTargetRequests(page.getHtml().css("div#pages a").links().regex(".*tzggxxgk.*").all());
        page.putField(page.getUrl().get(), page.getHtml().css("img").regex("/dbfile.*jpg"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        for (Map.Entry<String, Object> entry : page.getResultItems().getAll().entrySet()) {
            String picUrl = Constants.baseUrl + entry.getValue();
            String webUrl = entry.getKey();
            String picName = webUrl.substring(33, webUrl.indexOf("jhtml") - 1);
            String folder = Constants.folder + formatter.format(new Date());
            try {
                download(picUrl, picName + ".jpg", folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Site getSite() {
        return site;
    }


}