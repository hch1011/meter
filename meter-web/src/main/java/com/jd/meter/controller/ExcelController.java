package com.jd.meter.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.util.ExcelTemplate;

/**
 * Created by Darker on 2016/5/6.
 */
@Controller
public class ExcelController extends BaseController{
	@RequestMapping(value = "/device/data/error/exporting", method = RequestMethod.GET)
	public String exporting(
			HttpServletRequest request,
	    	HttpServletResponse response,
	    	Long deviceId) throws IOException {
		OutputStream out = response.getOutputStream();

		ExcelTemplate template = new ExcelTemplate();
		template.readTemplateByClasspath("/excelTemplate/order-with-item2.xlsx");
		
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		
		// 红色字体
		XSSFFont ftRed = (XSSFFont)template.createFont();
		ftRed.setColor(HSSFColor.RED.index);
		// 黄色字体
		XSSFFont ftYello = (XSSFFont)template.createFont();
		ftRed.setColor(HSSFColor.RED.index);
		// 灰色字体
		XSSFFont ftHuise = (XSSFFont)template.createFont();
		ftRed.setColor(HSSFColor.RED.index);
		
 		int orderNum = 0;			//订单序号
		for (DeviceInfo item :  list) {
			template.createNewRow();
			template.createCell(orderNum);//序号
			template.createCell(item.getCode());// 订单ID
			template.createCell("");// 商品ID
			template.createCell("");// 商品名称
			template.createCell("");// 商品单价
			template.createCell("");// 成本价/京东协议价
			template.createCell("");// 数量
		}
		template.wirteToStream(out);
	    out.flush();
		return null;
    } 
} 
