package com.jd.meter.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jd.meter.entity.DeviceData;

@Repository
public interface DeviceDataDao extends CrudRepository<DeviceData, Long>, JpaSpecificationExecutor<DeviceData> {

	DeviceData findByDeviceId(Long deviceId);
	
    @Modifying
    @Query("delete from DeviceData i where i.deviceId=?1")
    void removeDeviceDataByDeviceId(Long deviceId);
}
