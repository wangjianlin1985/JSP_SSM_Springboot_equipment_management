package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.SiteRun;

import com.chengxusheji.mapper.SiteRunMapper;
@Service
public class SiteRunService {

	@Resource SiteRunMapper siteRunMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加运行情况记录*/
    public void addSiteRun(SiteRun siteRun) throws Exception {
    	siteRunMapper.addSiteRun(siteRun);
    }

    /*按照查询条件分页查询运行情况记录*/
    public ArrayList<SiteRun> querySiteRun(String ssdw,String zddh,String zdlb,String zdmc,String gzlx,String gzsj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_siteRun.ssdw like '%" + ssdw + "%'";
    	if(!zddh.equals("")) where = where + " and t_siteRun.zddh like '%" + zddh + "%'";
    	if(!zdlb.equals("")) where = where + " and t_siteRun.zdlb like '%" + zdlb + "%'";
    	if(!zdmc.equals("")) where = where + " and t_siteRun.zdmc like '%" + zdmc + "%'";
    	if(!gzlx.equals("")) where = where + " and t_siteRun.gzlx like '%" + gzlx + "%'";
    	if(!gzsj.equals("")) where = where + " and t_siteRun.gzsj like '%" + gzsj + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return siteRunMapper.querySiteRun(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<SiteRun> querySiteRun(String ssdw,String zddh,String zdlb,String zdmc,String gzlx,String gzsj) throws Exception  { 
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_siteRun.ssdw like '%" + ssdw + "%'";
    	if(!zddh.equals("")) where = where + " and t_siteRun.zddh like '%" + zddh + "%'";
    	if(!zdlb.equals("")) where = where + " and t_siteRun.zdlb like '%" + zdlb + "%'";
    	if(!zdmc.equals("")) where = where + " and t_siteRun.zdmc like '%" + zdmc + "%'";
    	if(!gzlx.equals("")) where = where + " and t_siteRun.gzlx like '%" + gzlx + "%'";
    	if(!gzsj.equals("")) where = where + " and t_siteRun.gzsj like '%" + gzsj + "%'";
    	return siteRunMapper.querySiteRunList(where);
    }

    /*查询所有运行情况记录*/
    public ArrayList<SiteRun> queryAllSiteRun()  throws Exception {
        return siteRunMapper.querySiteRunList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String ssdw,String zddh,String zdlb,String zdmc,String gzlx,String gzsj) throws Exception {
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_siteRun.ssdw like '%" + ssdw + "%'";
    	if(!zddh.equals("")) where = where + " and t_siteRun.zddh like '%" + zddh + "%'";
    	if(!zdlb.equals("")) where = where + " and t_siteRun.zdlb like '%" + zdlb + "%'";
    	if(!zdmc.equals("")) where = where + " and t_siteRun.zdmc like '%" + zdmc + "%'";
    	if(!gzlx.equals("")) where = where + " and t_siteRun.gzlx like '%" + gzlx + "%'";
    	if(!gzsj.equals("")) where = where + " and t_siteRun.gzsj like '%" + gzsj + "%'";
        recordNumber = siteRunMapper.querySiteRunCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取运行情况记录*/
    public SiteRun getSiteRun(int siteRunId) throws Exception  {
        SiteRun siteRun = siteRunMapper.getSiteRun(siteRunId);
        return siteRun;
    }

    /*更新运行情况记录*/
    public void updateSiteRun(SiteRun siteRun) throws Exception {
        siteRunMapper.updateSiteRun(siteRun);
    }

    /*删除一条运行情况记录*/
    public void deleteSiteRun (int siteRunId) throws Exception {
        siteRunMapper.deleteSiteRun(siteRunId);
    }

    /*删除多条运行情况信息*/
    public int deleteSiteRuns (String siteRunIds) throws Exception {
    	String _siteRunIds[] = siteRunIds.split(",");
    	for(String _siteRunId: _siteRunIds) {
    		siteRunMapper.deleteSiteRun(Integer.parseInt(_siteRunId));
    	}
    	return _siteRunIds.length;
    }
}
