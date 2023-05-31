package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.SiteRun;

public interface SiteRunMapper {
	/*添加运行情况信息*/
	public void addSiteRun(SiteRun siteRun) throws Exception;

	/*按照查询条件分页查询运行情况记录*/
	public ArrayList<SiteRun> querySiteRun(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有运行情况记录*/
	public ArrayList<SiteRun> querySiteRunList(@Param("where") String where) throws Exception;

	/*按照查询条件的运行情况记录数*/
	public int querySiteRunCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条运行情况记录*/
	public SiteRun getSiteRun(int siteRunId) throws Exception;

	/*更新运行情况记录*/
	public void updateSiteRun(SiteRun siteRun) throws Exception;

	/*删除运行情况记录*/
	public void deleteSiteRun(int siteRunId) throws Exception;

}
