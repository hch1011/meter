package com.tj.meter.util;

import org.springframework.stereotype.Component;

import com.tj.meter.exception.MeterExceptionFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by hujintao on 2016/8/13.
 */
@Component
public class ImageUtils { 
    /**
     * 图片切割
     *
     * @param imagePath 原图地址
     * @param x         目标切片坐标 X轴起点
     * @param y         目标切片坐标 Y轴起点
     * @param w         目标切片 宽度
     * @param h         目标切片 高度
     */
    public static void cutImage(String sourceImagePath, String targetImagePath, int x, int y, int w, int h) throws IOException {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(sourceImagePath));
            int srcWidth = bi.getWidth();      // 源图宽度
            int srcHeight = bi.getHeight();    // 源图高度

            //若原图大小大于切片大小，则进行切割
            if (srcWidth >= w && srcHeight >= h) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);

                cropFilter = new CropImageFilter(x, y, w, h);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "jpg", new File(targetImagePath));
            }
        } catch (IOException e) {
            throw MeterExceptionFactory.applicationException("裁剪图片出错", e);
        }
    }
 
    /**
     * 图片伸缩，不破坏图片
     * 
     * @param srcFile 原图片路径
     * @param dstFile 目标图片路径
     * @param dstWidth 目标宽度
     * @param dstHeight 目标高度
     * @date 2013-11-1
     */
    public static void scale(String srcFile, String dstFile, int dstWidth, int dstHeight) {

    	try {
    		ImageInputStream iis = ImageIO.createImageInputStream(new File(srcFile));

    		Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);

    		ImageReader reader = (ImageReader) iterator.next();

    		reader.setInput(iis, true);

    		BufferedImage source = reader.read(0);

    		BufferedImage tag = new BufferedImage(dstWidth, dstHeight, source.getType());
    		tag.getGraphics().drawImage(source, 0, 0, dstWidth, dstHeight, null);
    		File file = new File(dstFile);
    		ImageIO.write(tag, reader.getFormatName(), file);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
