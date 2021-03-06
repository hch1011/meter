package com.tj.meter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tj.meter.entity.DeviceInfo;
import com.tj.meter.entity.DeviceType;

@Repository
public interface SystemPropertyDao extends CrudRepository<DeviceType, Long>, JpaSpecificationExecutor<DeviceInfo> {
	 
	List<DeviceType> findByMonitorPageFlagOrderByMonitorPageSort(int monitorPageFlag);
	
	List<DeviceType> findAll();
	 
    @Modifying
    @Query("delete from DeviceInfo i where i.code=?1")
    void removeDeviceInfoByCode(String code);
}
