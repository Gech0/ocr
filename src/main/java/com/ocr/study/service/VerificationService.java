package com.ocr.study.service;


import com.ocr.study.config.Constants;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VerificationService {
    private static void getVerificationCode () {
        String picUrl = Constants.verificationCodeUrl;
        String folder = Constants.folder;
        String fileName = "verification_code.jpg";
        try {
            DownloadService.download(picUrl,fileName, folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
