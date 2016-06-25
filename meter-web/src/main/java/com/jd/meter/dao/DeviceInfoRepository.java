package com.jd.meter.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jd.meter.entity.DeviceInfo;

@Repository
public interface DeviceInfoRepository extends CrudRepository<DeviceInfo, Long>, JpaSpecificationExecutor<DeviceInfo> {

	DeviceInfo findByCode(String code);
	
    @Modifying
    @Query("delete from DeviceInfo i where i.code=?1")
    void removeDeviceInfoByCode(String code);
}
