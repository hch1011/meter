package com.tj.meter.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.meter.dao.SystemAccountDao;
import com.tj.meter.entity.SystemAccount;
import com.tj.meter.exception.MeterExceptionFactory;
import com.tj.meter.util.IdGenerator;
import com.tj.meter.util.MD5Utils;

@Service("systemAccountService")
public class SystemAccountService {
	public static final int saltLen = 10;
	
	@Autowired
	SystemAccountDao systemAccountDao;
	
	public SystemAccount createUser(SystemAccount accountNew){
		if(StringUtils.isBlank(accountNew.getPassword())){
			throw MeterExceptionFactory.applicationException("密码不能空", null);
		}
		if(systemAccountDao.findByLoginName(accountNew.getLoginName()) != null){
			throw MeterExceptionFactory.applicationException("用户名已存在", null);
		}
		accountNew.setId(null);
		accountNew.setSalt("a"+IdGenerator.randomStr(saltLen));
		accountNew.setPassword(signPassword(accountNew.getSalt(), accountNew.getPassword()));
		accountNew.setCreateTime(new Date());
		accountNew.setUpdateTime(new Date());
		systemAccountDao.save(accountNew);
		
		return accountNew;
	}
	
	/*
	 * 登录成功返回Account对象
	 */
	public  SystemAccount login(String loginName, String password){
		SystemAccount account = systemAccountDao.findByLoginName(loginName);
		if(account == null){
			throw MeterExceptionFactory.applicationException("用户名或密码不正确", null);
		}
		
		if(signPassword(account.getSalt(),password).equals(account.getPassword())){
			if(password.equals(account.getPassword())){
				changePassword(loginName, null, password, false);
			}
			return account;
		}
		
		throw MeterExceptionFactory.applicationException("用户名或密码不正确", null);
	}
	
	public  boolean changePassword(String loginName, String oldPassword, String newPassword, boolean checkOldPassword){
		SystemAccount accountDb = systemAccountDao.findByLoginName(loginName);
		if(accountDb == null){
			return false;
		}
		if(checkOldPassword && !signPassword(accountDb.getSalt(),oldPassword).equals(accountDb.getPassword())){
			throw MeterExceptionFactory.applicationException("原始密码不正确", null);
		}
		 
		accountDb.setSalt("a"+IdGenerator.randomStr(saltLen));
		accountDb.setPassword(signPassword(accountDb.getSalt(), accountDb.getPassword()));
		accountDb.setUpdateTime(new Date());
		systemAccountDao.save(accountDb);
		return true;
	}

	public  SystemAccount queryByLoginName(String loginName){
		return systemAccountDao.findByLoginName(loginName);
	}
	
	//如果salt=null或0，返回原始密码
	String signPassword(String salt, String password){
		if(StringUtils.isBlank(salt) || "0".equals(salt)){
			if(password == null){
				return "";
			}
			return password;
		}
		
		return MD5Utils.md5("hjt-" + salt + "-hc-" + password + 2016);
	}
}
