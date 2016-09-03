package com.jd.meter.sync.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import com.jd.meter.entity.DeviceData;
import com.jd.meter.exception.MeterExceptionFactory;
import com.jd.meter.sync.pkg.PackageUtil;
import com.jd.meter.sync.pkg.SyncResponsePackage;
import com.jd.meter.util.ObjectUtil;

public class BioClient{
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    
    public BioClient(Socket socket) throws SocketException{
    	this.socket = socket;
    	socket.setSoTimeout(20*1000);
    }
    
    public SyncResponsePackage write(DeviceData deviceData) {
    	try {
    		if(out == null){
        		out = socket.getOutputStream();
        	}
        	if(in == null){
        		in = socket.getInputStream();
        	}
		
        	PackageUtil.writePackage(socket.getOutputStream(), PackageUtil.encodePackage(deviceData));
        	SyncResponsePackage pkg = PackageUtil.readSyncResponse(in);
        	return pkg;
    	} catch (Exception e) {
			throw MeterExceptionFactory.applicationException("write DeviceData error", e);
		}
	}
    
    public void close(){
    	ObjectUtil.close(in, true);
    	ObjectUtil.close(out, true);
    	ObjectUtil.close(socket, true);
    }

	
}
