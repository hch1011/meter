package com.tj.meter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tj.meter.entity.DeviceInfo;
import com.tj.meter.entity.DeviceType;
import com.tj.meter.entity.SystemAccount;

@Repository
public interface SystemAccountDao extends CrudRepository<SystemAccount, Integer>, JpaSpecificationExecutor<SystemAccount> {
	 
	SystemAccount findByLoginName(String loginName);
	
	List<SystemAccount> findAll();
	 
    @Modifying
    @Query("delete from DeviceInfo i where i.code=?1")
    void removeDeviceInfoByCode(String code);
}
