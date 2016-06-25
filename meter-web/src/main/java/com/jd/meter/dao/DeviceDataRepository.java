package com.jd.meter.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jd.meter.entity.DeviceData;

public interface DeviceDataRepository extends CrudRepository<DeviceData, Long>, JpaSpecificationExecutor<DeviceData> {

	DeviceData findByDeviceId(Long deviceId);
	
    @Modifying
    @Query("delete from DeviceData i where i.deviceId=?1")
    void removeDeviceDataByDeviceId(Long deviceId);
}
