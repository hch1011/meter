package com.tj.meter.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tj.meter.entity.SystemAccount;
import com.tj.meter.exception.MeterExceptionFactory;
import com.tj.meter.service.SystemAccountService;

@Service("meterRealm")
public class MeterRealm  extends AuthorizingRealm { 
	@Autowired
	SystemAccountService systemAccountService;
	 
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){  
        SystemAccount account = systemAccountService.queryByLoginName((String)super.getAvailablePrincipal(principals));
        if(account == null){
        	throw MeterExceptionFactory.applicationException("用户", null);
        }  
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();  
        simpleAuthorInfo.addRoles(account.getRoles());
        return simpleAuthorInfo;  
    }  
  
      
    /** 
     * 验证当前登录的Subject 
     * @see  经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;  
        SystemAccount account = systemAccountService.login(token.getUsername(), new String(token.getPassword()));
        this.setSession("loginedAccount",account);
        return new SimpleAuthenticationInfo(token.getUsername(), new String(token.getPassword()), account.getNickname());  
    }  
      
      
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see  比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession(); 
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }  

}
