package com.tj.meter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.collect.Sets;

@Entity
@Table(name = "system_account")
public class SystemAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String ROLE_SYSTEM_ADMIN = "systemAdmin" ;//系统管理员
	public static String ROLE_MANAGER = "manager" ;//普通管理员
	public static String ROLE_USER = "user" ;//普通用户

	public static Integer STATUS_VALID = 1;//有效
	public static Integer STATUS_LOCK = 9;//锁定
	
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "login_name")
	private String loginName;
	@Column(name = "salt")
	private String salt;
	@Column(name = "password")
	private String password;
	@Column(name = "nickname")
	private String nickname;
	@Column(name = "real_name")
	private String realName;
	@Column(name = "role")
	private String role;
	@Column(name = "status")
	private Integer status;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}	
	public Set<String> getRoles(){
		if(ROLE_USER.equals(role)){
			return Sets.newHashSet(ROLE_USER);
		}
		if(ROLE_MANAGER.equals(role)){
			return Sets.newHashSet(ROLE_MANAGER, ROLE_USER);
		}
		
		if(ROLE_SYSTEM_ADMIN.equals(role)){
			return Sets.newHashSet(ROLE_SYSTEM_ADMIN, ROLE_MANAGER, ROLE_USER);
		}
		return Sets.newHashSet();
	}
	
	public void cleanPassword(){
		password = null;
		salt = null;
	}
	
//	public String getStatusText(){
//		if(STATUS_LOCK.equals(status)){
//			return "用户被锁定";
//		}
//		if(STATUS_VALID.equals(status)){
//			return "正常用户";
//		}
//		return "无效用户";
//		
//	}
//	public String getRoleText(){
//		if(ROLE_USER.equals(role)){
//			return "普通用户";
//		}
//		if(ROLE_MANAGER.equals(role)){
//			return "管理员";
//		}
//		if(ROLE_SYSTEM_ADMIN.equals(role)){
//			return "超级管理员";
//		}
//		return "无效角色";
//	}
}