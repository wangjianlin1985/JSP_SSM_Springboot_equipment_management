package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.SiteBase;

import com.chengxusheji.mapper.SiteBaseMapper;
@Service
public class SiteBaseService {

	@Resource SiteBaseMapper siteBaseMapper;
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

    /*添加站点基本信息记录*/
    public void addSiteBase(SiteBase siteBase) throws Exception {
    	siteBaseMapper.addSiteBase(siteBase);
    }

    /*按照查询条件分页查询站点基本信息记录*/
    public ArrayList<SiteBase> querySiteBase(String ssdw,String gsd,String zddh,String zdmc,String zdlb,String txfs,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_siteBase.ssdw like '%" + ssdw + "%'";
    	if(!gsd.equals("")) where = where + " and t_siteBase.gsd like '%" + gsd + "%'";
    	if(!zddh.equals("")) where = where + " and t_siteBase.zddh like '%" + zddh + "%'";
    	if(!zdmc.equals("")) where = where + " and t_siteBase.zdmc like '%" + zdmc + "%'";
    	if(!zdlb.equals("")) where = where + " and t_siteBase.zdlb like '%" + zdlb + "%'";
    	if(!txfs.equals("")) where = where + " and t_siteBase.txfs like '%" + txfs + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return siteBaseMapper.querySiteBase(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<SiteBase> querySiteBase(String ssdw,String gsd,String zddh,String zdmc,String zdlb,String txfs) throws Exception  { 
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_siteBase.ssdw like '%" + ssdw + "%'";
    	if(!gsd.equals("")) where = where + " and t_siteBase.gsd like '%" + gsd + "%'";
    	if(!zddh.equals("")) where = where + " and t_siteBase.zddh like '%" + zddh + "%'";
    	if(!zdmc.equals("")) where = where + " and t_siteBase.zdmc like '%" + zdmc + "%'";
    	if(!zdlb.equals("")) where = where + " and t_siteBase.zdlb like '%" + zdlb + "%'";
    	if(!txfs.equals("")) where = where + " and t_siteBase.txfs like '%" + txfs + "%'";
    	return siteBaseMapper.querySiteBaseList(where);
    }

    /*查询所有站点基本信息记录*/
    public ArrayList<SiteBase> queryAllSiteBase()  throws Exception {
        return siteBaseMapper.querySiteBaseList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String ssdw,String gsd,String zddh,String zdmc,String zdlb,String txfs) throws Exception {
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_siteBase.ssdw like '%" + ssdw + "%'";
    	if(!gsd.equals("")) where = where + " and t_siteBase.gsd like '%" + gsd + "%'";
    	if(!zddh.equals("")) where = where + " and t_siteBase.zddh like '%" + zddh + "%'";
    	if(!zdmc.equals("")) where = where + " and t_siteBase.zdmc like '%" + zdmc + "%'";
    	if(!zdlb.equals("")) where = where + " and t_siteBase.zdlb like '%" + zdlb + "%'";
    	if(!txfs.equals("")) where = where + " and t_siteBase.txfs like '%" + txfs + "%'";
        recordNumber = siteBaseMapper.querySiteBaseCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取站点基本信息记录*/
    public SiteBase getSiteBase(int siteBaseId) throws Exception  {
        SiteBase siteBase = siteBaseMapper.getSiteBase(siteBaseId);
        return siteBase;
    }

    /*更新站点基本信息记录*/
    public void updateSiteBase(SiteBase siteBase) throws Exception {
        siteBaseMapper.updateSiteBase(siteBase);
    }

    /*删除一条站点基本信息记录*/
    public void deleteSiteBase (int siteBaseId) throws Exception {
        siteBaseMapper.deleteSiteBase(siteBaseId);
    }

    /*删除多条站点基本信息信息*/
    public int deleteSiteBases (String siteBaseIds) throws Exception {
    	String _siteBaseIds[] = siteBaseIds.split(",");
    	for(String _siteBaseId: _siteBaseIds) {
    		siteBaseMapper.deleteSiteBase(Integer.parseInt(_siteBaseId));
    	}
    	return _siteBaseIds.length;
    }
}
