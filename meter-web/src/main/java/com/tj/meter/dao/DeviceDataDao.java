package com.tj.meter.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tj.meter.entity.DeviceData;

@Repository
public interface DeviceDataDao extends CrudRepository<DeviceData, Long>, JpaSpecificationExecutor<DeviceData> {

    @Modifying
    //@Query("select i from DeviceData i where i.deviceId=:deviceId and i.snapTime>=:fromDate and i.snapTime<=:toDate and i.snapData >= 0")
    @Query(value ="select * from device_data i where i.device_id=:deviceId and i.snap_time>=:fromDate and i.snap_time<=:toDate and i.snap_data >= 0 limit 0,200 ",
    nativeQuery = true)
    List<DeviceData> findByRange(
    		@Param("deviceId") Long deviceId, 
    		@Param("fromDate")Date fromDate, 
    		@Param("toDate") Date toDate
    		);
    

    @Modifying
    @Query("delete from DeviceData i where i.deviceId=?1")
    void removeDeviceDataByDeviceId(Long deviceId);

    @Modifying
    //@Query("select i from DeviceData i where i.syncSuccessTime is null limit 0, 100")
    @Query("select i from DeviceData i where i.syncSuccessTime is null")
    List<DeviceData> findWaitingSync(int length);
}
