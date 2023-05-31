package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.SiteBase;

public interface SiteBaseMapper {
	/*添加站点基本信息信息*/
	public void addSiteBase(SiteBase siteBase) throws Exception;

	/*按照查询条件分页查询站点基本信息记录*/
	public ArrayList<SiteBase> querySiteBase(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有站点基本信息记录*/
	public ArrayList<SiteBase> querySiteBaseList(@Param("where") String where) throws Exception;

	/*按照查询条件的站点基本信息记录数*/
	public int querySiteBaseCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条站点基本信息记录*/
	public SiteBase getSiteBase(int siteBaseId) throws Exception;

	/*更新站点基本信息记录*/
	public void updateSiteBase(SiteBase siteBase) throws Exception;

	/*删除站点基本信息记录*/
	public void deleteSiteBase(int siteBaseId) throws Exception;

}
