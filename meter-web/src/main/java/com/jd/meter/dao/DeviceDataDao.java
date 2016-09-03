package com.jd.meter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jd.meter.entity.DeviceData;

@Repository
public interface DeviceDataDao extends CrudRepository<DeviceData, Long>, JpaSpecificationExecutor<DeviceData> {

    @Modifying
    @Query("delete from DeviceData i where i.deviceId=?1")
    void removeDeviceDataByDeviceId(Long deviceId);
    

    @Modifying
    //@Query("select i from DeviceData i where i.syncSuccessTime is null limit 0, 100")
    @Query("select i from DeviceData i where i.syncSuccessTime is null")
    List<DeviceData> findWaitingSync(int length);
}
