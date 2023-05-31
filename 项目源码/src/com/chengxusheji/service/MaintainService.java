package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Maintain;

import com.chengxusheji.mapper.MaintainMapper;
@Service
public class MaintainService {

	@Resource MaintainMapper maintainMapper;
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

    /*添加维护情况记录*/
    public void addMaintain(Maintain maintain) throws Exception {
    	maintainMapper.addMaintain(maintain);
    }

    /*按照查询条件分页查询维护情况记录*/
    public ArrayList<Maintain> queryMaintain(String ssdw,String jhsj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_maintain.ssdw like '%" + ssdw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_maintain.jhsj like '%" + jhsj + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return maintainMapper.queryMaintain(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Maintain> queryMaintain(String ssdw,String jhsj) throws Exception  { 
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_maintain.ssdw like '%" + ssdw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_maintain.jhsj like '%" + jhsj + "%'";
    	return maintainMapper.queryMaintainList(where);
    }

    /*查询所有维护情况记录*/
    public ArrayList<Maintain> queryAllMaintain()  throws Exception {
        return maintainMapper.queryMaintainList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String ssdw,String jhsj) throws Exception {
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_maintain.ssdw like '%" + ssdw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_maintain.jhsj like '%" + jhsj + "%'";
        recordNumber = maintainMapper.queryMaintainCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取维护情况记录*/
    public Maintain getMaintain(int maintainId) throws Exception  {
        Maintain maintain = maintainMapper.getMaintain(maintainId);
        return maintain;
    }

    /*更新维护情况记录*/
    public void updateMaintain(Maintain maintain) throws Exception {
        maintainMapper.updateMaintain(maintain);
    }

    /*删除一条维护情况记录*/
    public void deleteMaintain (int maintainId) throws Exception {
        maintainMapper.deleteMaintain(maintainId);
    }

    /*删除多条维护情况信息*/
    public int deleteMaintains (String maintainIds) throws Exception {
    	String _maintainIds[] = maintainIds.split(",");
    	for(String _maintainId: _maintainIds) {
    		maintainMapper.deleteMaintain(Integer.parseInt(_maintainId));
    	}
    	return _maintainIds.length;
    }
}
