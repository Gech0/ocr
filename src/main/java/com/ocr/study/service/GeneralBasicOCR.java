package com.ocr.study.service;
import com.alibaba.fastjson.JSON;
import com.ocr.study.config.Constants;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.ocr.v20181119.OcrClient;

import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;

import java.util.HashMap;
import java.util.Map;

public class GeneralBasicOCR {
    public static void main(String[] args) {
        ocr("");
    }
    public static void ocr(String imageUrl) {
        try{

            Credential cred = new Credential(Constants.secretId, Constants.secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);

            Map<String,String> params = new HashMap<>();
            //String params = "{\"ImageUrl\":\"image/verification_code.jpg\"}";
            params.put("ImageUrl",imageUrl);
            GeneralBasicOCRRequest req = GeneralBasicOCRRequest.fromJsonString(JSON.toJSONString(params), GeneralBasicOCRRequest.class);

            GeneralBasicOCRResponse resp = client.GeneralBasicOCR(req);

            System.out.println(GeneralBasicOCRResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

    }
}
