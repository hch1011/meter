package com.jd.meter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jd.meter.entity.DeviceInfo;

@Repository
public interface DeviceInfoDao extends CrudRepository<DeviceInfo, Long>, JpaSpecificationExecutor<DeviceInfo> {

	//@Query("select i from DeviceInfo i inner join DeviceType t on t.type = i.type where t.monitorPageFlag = 1 and i.monitorPageFlag = 1 order by i.type, i.monitorPageSort")
	@Query("select i from DeviceInfo i , DeviceType t where t.type = i.type and t.monitorPageFlag = 1 and i.monitorPageFlag = 1 order by i.type, i.monitorPageSort")
	List<DeviceInfo> findMonitorInfo();
	
	List<DeviceInfo> findByType(Long type);
	
	List<DeviceInfo> findAll(); 
	
    @Modifying
    @Query("delete from DeviceInfo i where i.code=?1")
    void removeDeviceInfoByCode(String code);
}
