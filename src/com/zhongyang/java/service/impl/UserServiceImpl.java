package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UserDao;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.UserInfoVo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public int getUserNumByMobile(User user) {
		return userDao.getCountModelByMobile(user);
	}

	@Override
	public User getUserByMobile(String mobile) {
		User user=new User();
		user.setMobile(mobile);
		return userDao.getUserByParam(user);
	}
	
	@Override
	public User queryUserByParams(User user) {
		return userDao.selectUserByParams(user);
	}

	@Override
	public User getUserByLoginName(String LoginName) {
		User user=new User();
		user.setLoginname(LoginName);
		return userDao.getUserByParam(user);
	}

	@Override
	public int addUser(User user) {	
		return userDao.addUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public int updateUserByMobile(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public User queryById(String id) {
		User user=new User();
		user.setId(id);
		return userDao.getUserByParam(user);
	}

	@Override
	public UserInfoVo getUserInfo(UserInfoVo userInfoVo) {
		
		return userDao.getUserInfo(userInfoVo);
	}

	@Override
	public List<Map> queryInviterInvest(Page page) throws Exception{
		return userDao.selectInviterInvest(page);
	}

	@Override
	public List<Map> queryUserByUserId(Page page) throws Exception {
		return userDao.selectUserByUserId(page);
	}

	@Override
	public Map<String, Object> queryByParams(User user) {
		return userDao.selectByParams(user);
	}
	
}
