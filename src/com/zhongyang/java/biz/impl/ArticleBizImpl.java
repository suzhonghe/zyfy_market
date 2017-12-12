package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.ArticleBiz;
import com.zhongyang.java.pojo.Article;
import com.zhongyang.java.pojo.CmsColumn;
import com.zhongyang.java.service.ArticleService;
import com.zhongyang.java.service.CmsColumnService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ArticleVO;

@Service
public class ArticleBizImpl implements ArticleBiz {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private CmsColumnService cmsColumnService;

	@Override
	public Page<ArticleVO> queryByParams(Page<ArticleVO> page) {
		try {
			CmsColumn col=null;
			if (page.getParams().get("colKey") == null) {
				page.getParams().put("columnId", null);
			} else {
				col = new CmsColumn();
				if ("PTGG".equals(page.getParams().get("colKey"))) {
					col.setName("平台公告");
				}
				if ("ZYDT".equals(page.getParams().get("colKey"))) {
					col.setName("左右动态");
				}
				if ("HYDT".equals(page.getParams().get("colKey"))) {
					col.setName("行业动态");
				}
				if ("MTBD".equals(page.getParams().get("colKey"))) {
					col.setName("媒体报道");
				}
			}
			if (col != null&&col.getName()!=null) {
				 col = cmsColumnService.queryByParams(col);
				page.getParams().put("columnId", col.getId());
			}
			//媒体报道里剔除平台公告
			if("".equals(page.getParams().get("colKey"))){
				col.setName("平台公告");
				col = cmsColumnService.queryByParams(col);
				page.getParams().put("PTGG", col.getId());
			}
			List<ArticleVO> articleVOs = new ArrayList<ArticleVO>();
			List<Article> articles = articleService.queryByParams(page);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (Article art : articles) {
					ArticleVO articleVO = new ArticleVO();
					articleVO.setTitle(art.getTitle());
					articleVO.setId(art.getId());
					articleVO.setColumnName(art.getColumnName());
					articleVO.setSubmitTime(sdf.format(art.getSubmitTime()));
					articleVOs.add(articleVO);
				}
			page.setResults(articleVOs);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询失败！！！！");
		}
	}

	@Override
	public Article getArticleById(String id) {
		Article art = articleService.getArticleById(id);
		
		List<Article> list = articleService.getBeforAndAfter(art.getColumnId(),art.getIndex() == 50 ? 1 : (Integer.valueOf(art.getIndex())-1),art.getIndex() == 50 ? 1 :  (Integer.valueOf(art.getIndex())+1));
		if(list !=null && list.size()>0){
			for(Article article : list){
				if(article.getIndex()<art.getIndex()){
					art.setPreid(article.getId());
					art.setPreTitle(article.getTitle());
				}else{
					art.setSurid(article.getId());
					art.setSurTitle(article.getTitle());
				}
			}
		}
		return art;
	}


}
