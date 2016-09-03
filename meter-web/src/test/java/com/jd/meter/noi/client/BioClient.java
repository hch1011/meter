package com.jd.meter.noi.client;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.alibaba.druid.support.json.JSONUtils;
import com.jd.meter.sync.pkg.PingPackage;

import ch.qos.logback.core.encoder.ByteArrayUtil;

public class BioClient {
	public static void main(String[] args) throws Exception {
		System.out.println("main start");
 		Socket socket = new Socket("192.168.1.108", 9101);
 		OutputStream out = socket.getOutputStream();
 		InputStream in = socket.getInputStream();
 		PingPackage ping = new PingPackage();
 		//out.write("abc".getBytes());
 		
 		//System.out.println(ByteArrayUtil.toHexString(ping.buildByteArray()));
 		byte[] pkg = ping.toByteArray();
		System.out.println("write:"+ByteArrayUtil.toHexString(pkg));
 		out.write(pkg);
 		System.out.println("writed");
 		out.flush();
 		
 		byte[] b = new byte[1024];
		in.read(b );
		System.out.println("read:"+ByteArrayUtil.toHexString(b));
 		close(out);
 		close(in);
 		close(socket);


		System.out.println("main end");
	}
	
	public static void close(Closeable o){
		 try {
			o.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
}

