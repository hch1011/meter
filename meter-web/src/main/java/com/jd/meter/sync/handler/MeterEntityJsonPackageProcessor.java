package com.jd.meter.sync.handler;

import com.alibaba.fastjson.JSON;
import com.jd.meter.entity.DeviceData;
import com.jd.meter.service.DeviceService;
import com.jd.meter.sync.pkg.PackageBasic;
import com.jd.meter.sync.pkg.SyncResponsePackage;
import com.jd.meter.sync.pkg.PackageType;
import com.jd.meter.sync.pkg.PackageUtil;

import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 业务处理类
 */
@Component
public class MeterEntityJsonPackageProcessor extends AbstractPackageProcessor {
	@Autowired
	DeviceService deviceService;
	@Override
	public int matchPackageType() {
		return PackageType.data_entity_json;
	}

	@Override
	public void process(ChannelHandlerContext ctx, PackageBasic msg) {
		
		String str = new String(msg.getBody(),Charset.forName("utf-8"));
		
		DeviceData deviceData = JSON.parseObject(str, DeviceData.class);
		deviceService.receiveSyncDeviceData(deviceData);
		SyncResponsePackage pkg = PackageUtil.buildSyncResponsePackage(0,deviceData.getId().toString(),null);
		ctx.channel().writeAndFlush(pkg);
	}
}
