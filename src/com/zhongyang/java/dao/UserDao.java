package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.UserInfoVo;

public interface UserDao {	
	/**
	 * 通过手机号码 查询用户是否存在
	 * @param mobile 手机号码
	 * @return 存在为1 不存在为0
	 */
	public int getCountModelByMobile(User user);
	
	public List<User> getUser(User user);
	
	public User getUserByParam(User user);
		
	public int addUser(User user);
	
	public int updateUser(User user);
	
	public int updateUserByMobile(User user);
	
	public UserInfoVo getUserInfo(UserInfoVo userInfoVo);
	
	public List<Map> selectInviterInvest(Page page)throws Exception;

	public List<Map> selectUserByUserId(Page page)throws Exception;
	
	public User selectUserByParams(User user);
	
	public Map<String,Object> selectByParams(User user);
}
