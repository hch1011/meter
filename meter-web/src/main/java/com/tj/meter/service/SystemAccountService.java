package com.tj.meter.service;

import static com.tj.meter.exception.MeterExceptionFactory.exceptionIfBlank;
import static com.tj.meter.exception.MeterExceptionFactory.exceptionIfTrue;

import java.util.Date;
import java.util.List;

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
	


	public List<SystemAccount> queryAllUser() {
		return systemAccountDao.findAll();
	}
	/**
	 * 管理员创建用户
	 * @param accountNew
	 * @return
	 */
	public SystemAccount createUserByAdmin(SystemAccount accountNew){
		exceptionIfBlank(accountNew.getLoginName(), "请输入登录名");
		exceptionIfBlank(accountNew.getPassword(), "密码不能空");
		exceptionIfBlank(accountNew.getRole() , "请选择角色"); 
		if(systemAccountDao.findByLoginName(accountNew.getLoginName()) != null){
			throw MeterExceptionFactory.applicationException("用户名已存在", null);
		}
		
		accountNew.setId(null);
		accountNew.setSalt("a"+IdGenerator.randomStr(saltLen));
		accountNew.setPassword(signPassword(accountNew.getSalt(), accountNew.getPassword()));
		accountNew.setCreateTime(new Date());
		accountNew.setUpdateTime(new Date());
		if(StringUtils.isBlank(accountNew.getRole())){
			accountNew.setRole(SystemAccount.ROLE_USER);
		}
		if(accountNew.getStatus() == null){
			accountNew.setStatus(SystemAccount.STATUS_VALID);
		}
		systemAccountDao.save(accountNew);
		return accountNew;
	}
	

	public void deleteAccount(String loginName){
		SystemAccount account = systemAccountDao.findByLoginName(loginName);
		if(account != null){
			exceptionIfTrue(SystemAccount.ROLE_ADMIN.equals(account.getRole()), "系统管理员不能被删除");
			systemAccountDao.delete(account.getId());
		}
	}

	/**
	 * 管理员根据登录名更新状态，密码等
	 * @param accountNew
	 * @return
	 */
	public SystemAccount updateUserByAdmin(SystemAccount accountNew){
		exceptionIfBlank(accountNew.getLoginName(), "登录名不能空");
		SystemAccount accountDb = systemAccountDao.findByLoginName(accountNew.getLoginName());
		
		if(accountDb == null){
			throw MeterExceptionFactory.applicationException("用户存在", null);
		}
		boolean update = false;
		//更新密码
		if(StringUtils.isNotBlank(accountNew.getPassword())){
			accountDb.setSalt("a" + IdGenerator.randomStr(saltLen));
			accountDb.setPassword(signPassword(accountDb.getSalt(), accountDb.getPassword()));
			update = true;
		}
		if(StringUtils.isNotBlank(accountNew.getNickname())){
			accountDb.setNickname(accountNew.getNickname());
			update = true;
		}
		if(StringUtils.isNotBlank(accountNew.getRealName())){
			accountDb.setRealName(accountNew.getRealName());
			update = true;
		}
		if(StringUtils.isNotBlank(accountNew.getRole())){
			accountDb.setRole(accountNew.getRole());
			update = true;
		}
		if(accountNew.getStatus() == null){
			accountDb.setStatus(accountNew.getStatus());
			update = true;
		} 
		if(update){
			accountDb.setUpdateTime(new Date());
			systemAccountDao.save(accountDb);
		}
		return accountDb;
	}
	
	/*
	 * 登录成功返回Account对象
	 */
	public  SystemAccount login(String loginName, String password){
		SystemAccount account = systemAccountDao.findByLoginName(loginName);
		if(account == null){
			throw MeterExceptionFactory.applicationException("用户名或密码不正确", null);
		}
		
		if(SystemAccount.STATUS_LOCK.equals(account.getStatus())){
			throw MeterExceptionFactory.applicationException("用户被锁定，请联系相关人员", null);
		}
		
		if(!SystemAccount.STATUS_VALID.equals(account.getStatus())){
			throw MeterExceptionFactory.applicationException("用户状态不对，请联系相关人员", null);
		}
		
		if(signPassword(account.getSalt(),password).equals(account.getPassword())){
			//如果是明文密码，需要加密密码
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
