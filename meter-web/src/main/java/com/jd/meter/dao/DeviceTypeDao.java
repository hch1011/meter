package com.jd.meter.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jd.meter.entity.DeviceInfo;
import com.jd.meter.entity.DeviceType;

@Repository
public interface DeviceTypeDao extends CrudRepository<DeviceType, Long>, JpaSpecificationExecutor<DeviceInfo> {

	//DeviceType findByCode(String code);
	
    @Modifying
    @Query("delete from DeviceInfo i where i.code=?1")
    void removeDeviceInfoByCode(String code);
}
