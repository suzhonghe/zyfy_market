package com.zhongyang.java.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.BannerBiz;
import com.zhongyang.java.pojo.BannerPhoto;
import com.zhongyang.java.service.BannerPhotoService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;

@Service
public class BannerBizImpl implements BannerBiz{
	
	private static Logger logger=Logger.getLogger(BannerBizImpl.class);
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		ip=(String) sysMap.get("ZYCFMARKET_IP");
		ZYCFMARKET_DOMAIN_NAME=(String) sysMap.get("ZYCFMARKET_DOMAIN_NAME");
	}
	
	private static String ip;
	
	private static String ZYCFMARKET_DOMAIN_NAME;
	
	@Autowired
	private BannerPhotoService bannerPhotoService;
	
	@Override
	public List<BannerPhoto> queryBannerPhotos(String type) {
		try {
			List<BannerPhoto> bannerPhotos=new ArrayList<BannerPhoto>();
			BannerPhoto bannerPhoto=new BannerPhoto();
			bannerPhoto.setType(type);
			List<BannerPhoto> queryAllPhotos = bannerPhotoService.queryByParams(bannerPhoto);
			int i=0;
			for (BannerPhoto photo : queryAllPhotos) {
				BannerPhoto bp=new BannerPhoto();
				i++;
				if(type.equals("news")&&i>3||i>5){
					break;
				}
				if(type.equals("advertisement")&&i>2||i>5){
					break;
				}
				bp.setJumpAddress(photo.getJumpAddress());
				bp.setPathAddress(ZYCFMARKET_DOMAIN_NAME+photo.getPathAddress());
				bp.setSerialNumber(photo.getSerialNumber());
				bannerPhotos.add(bp);
			}
			return bannerPhotos;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,e.getMessage());
		}
	}


	@Override
	public Map<String, Object> getAppVersion()
	{
		Map<String, Object> map= new HashMap<>();
		Map<String, Object> sysMap = SystemPro.getProperties();
		String zycfurlIp = (String) sysMap.get("ZYCFMARKET_APPIP");
		String zycfappversion = (String) sysMap.get("ZYCFAPPVERSION");
		String zycfappurl = (String) sysMap.get("ZYCFAPPURL");
		map.put("zycfurlIp", zycfurlIp+zycfappurl);
		map.put("zycfappversion", zycfappversion);
		return map;

     }
}
