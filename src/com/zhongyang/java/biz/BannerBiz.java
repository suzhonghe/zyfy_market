package com.zhongyang.java.biz;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.BannerPhoto;

public interface BannerBiz {
	
	public List<BannerPhoto> queryBannerPhotos(String type);

	public Map<String, Object> getAppVersion();

}
