package com.tj.meter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tj.meter.entity.SystemAccount;
import com.tj.meter.service.SystemAccountService;

@Controller
public class AccountController extends BaseController{ 
	@Autowired
	SystemAccountService  systemAccountService;

	@RequestMapping(value = "/admin/account", method = RequestMethod.POST)
	@ResponseBody
	public Object createAccountByAdmin(
			HttpServletRequest request,
			HttpServletResponse response,
			SystemAccount account,
			Model model
	) {
		try {
			account = systemAccountService.createUserByAdmin(account);
			account.cleanPassword();
			return success(account);
		} catch (Exception e) {
			return fail(e);
		}
	}

	
	@RequestMapping(value = "/admin/account", method = RequestMethod.PUT)
	@ResponseBody
	public Object updateAccountByAdmin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody SystemAccount account,
			Model model
	) {
		try {
			account = systemAccountService.updateUserByAdmin(account);
			account.cleanPassword();
			return success(account);
		} catch (Exception e) {
			return fail(e);
		}
	}
	
	/**
	 * 所有用户
	 */
	@RequestMapping(value = "/admin/account/list", method = RequestMethod.GET)
	public String accountListByAdmin(
			HttpServletRequest request,
			HttpServletResponse response,
			@PageableDefault(page=0, size=100) Pageable pageable,
			Model model
	) {
		
		List<SystemAccount> accountList = systemAccountService.queryAllUser();
		//accountList.forEach(i -> i.cleanPassword());
		model.addAttribute("accountList", accountList);
		return "account/list";
	}
	
	@RequestMapping(value = "/admin/account/{loginName}/json", method = RequestMethod.GET)
	@ResponseBody
	public Object accountJson(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value="loginName", required = false) String loginName,
			Model model
	) {
		SystemAccount account = systemAccountService.queryByLoginName(loginName);
		account.cleanPassword();
		return success(account);
	}
 
} 
