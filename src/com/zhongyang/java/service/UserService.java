package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.UserInfoVo;

public interface UserService {
	
	 public int getUserNumByMobile(User user);
	 
	 public User getUserByMobile(String mobile);
	 
	 public User getUserByLoginName(String LoginName);
	 
	 public int updateUser(User user);
	 
	 public int addUser(User user);
	
	 public int updateUserByMobile(User user);
	 
	 public User queryById(String id);
	 
	 public UserInfoVo getUserInfo(UserInfoVo userInfoVo);
	 
	 public List<Map> queryInviterInvest(Page<User> page)throws Exception;

	public List<Map> queryUserByUserId(Page page)throws Exception;
	
	public User queryUserByParams(User user);
	
	public Map<String,Object> queryByParams(User user);
}
