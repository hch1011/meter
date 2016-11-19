package com.tj.meter.util;

import java.io.Serializable;
import java.net.InetAddress;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.tj.meter.exception.MeterException;

/**
 * ID生成器,64位
 */
public class SnowflakeIdGenerator implements IdentifierGenerator {
	private static SnowflakeIdGenerator instance;
	
    private long workerId;
    private final static long twepoch = 1361753741828L;
    private long sequence = 0L;
    private final static long workerIdBits = 8L;
    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
    private final static long sequenceBits = 5L;

    private final static long workerIdShift = sequenceBits;
    private final static long timestampLeftShift = sequenceBits + workerIdBits;
    public final static long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator() {
        try {
            this.workerId = getWorkerId();
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                		maxWorkerId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & sequenceMask;
            if (this.sequence == 0) {
                System.out.println("###########" + sequenceMask);
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(String.format(
                        "Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp
                                - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << workerIdShift)
                | (this.sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    private static long getWorkerId() throws Exception {
        byte[] ip = InetAddress.getLocalHost().getAddress();
        long id = (0x000000FF & (long) ip[ip.length - 1]);
        return id;
    }

    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return nextId();
    }

    public static void main(String[] args) {
        SnowflakeIdGenerator worker2 = new SnowflakeIdGenerator();
        for (int i = 0; i < 100; i++) {
            System.out.println(worker2.nextId());
            try {
				ObjectUtil.sleep(1000, true);
			} catch (MeterException e) {
				e.printStackTrace();
			}
        }
    }
    
    public static SnowflakeIdGenerator getInstance(){
    	if(instance == null){
    		instance = new SnowflakeIdGenerator();
    	} 
    	
    	return instance;
    }
}
