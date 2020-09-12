package com.ocr.study.tools;

import com.ocr.study.config.Constants;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageCutUtil {

    /**
     * 分割图像
     *
     * @param image     传入的图片对象
     * @param rows      垂直方向上需要裁剪出的图片数量 - 行
     * @param cols      水平方向上需要裁剪出的图片数量 - 列
     * @param x         开始裁剪位置的X坐标
     * @param y         开始裁剪位置的Y坐标
     * @param width     每次裁剪的图片宽度
     * @param height    每次裁剪的图片高度
     * @param changeX   每次需要改变的X坐标数量
     * @param changeY   每次需要改变的Y坐标数量
     * @param component 容器对象，目的是用来创建裁剪后的每个图片对象
     * @return 裁剪完并加载到内存后的二维图片数组
     */
    public static Image[][] cutImage(Image image, int rows, int cols, int x,
                                     int y, int width, int height, int changeX, int changeY,
                                     Component component) {
        Image[][] images = new Image[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ImageFilter filter = new CropImageFilter(x + j * changeX, y + i
                        * changeY, width, height);
                images[i][j] = component.createImage(new FilteredImageSource(
                        image.getSource(), filter));
            }
        }

        return images;
    }



    public void cutImage(String filePath, int x, int y, int w, int h)
            throws Exception {
        ImageInputStream iis = ImageIO.createImageInputStream(new FileInputStream(filePath));
        @SuppressWarnings("rawtypes")
        Iterator it = ImageIO.getImageReaders(iis);
        ImageReader imagereader = (ImageReader) it.next();
        imagereader.setInput(iis);
        ImageReadParam par = imagereader.getDefaultReadParam();
        par.setSourceRegion(new Rectangle(x, y, w, h));
        BufferedImage bi = imagereader.read(0, par);
        String newPath = Constants.firstCutPath + filePath.substring(filePath.length() - 10);
        System.out.println("start");
        ImageIO.write(bi, "png", new File(newPath));
        System.out.println("done");
    }


    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    public void cutAllImage (String imagePath) throws Exception {
        List<String> imageList = getAllFile(imagePath, false);
        for (String path: imageList) {
            cutImage(path, 228,24, 243, 2496);
        }
    }

    public static void main(String[] args) throws Exception {
       new ImageCutUtil().cutAllImage("src/main/resources/image/07-09-2020");
    }

}