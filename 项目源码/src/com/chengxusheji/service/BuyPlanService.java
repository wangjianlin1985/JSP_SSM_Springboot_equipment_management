package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DeviceType;
import com.chengxusheji.po.BuyPlan;

import com.chengxusheji.mapper.BuyPlanMapper;
@Service
public class BuyPlanService {

	@Resource BuyPlanMapper buyPlanMapper;
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

    /*添加购置计划记录*/
    public void addBuyPlan(BuyPlan buyPlan) throws Exception {
    	buyPlanMapper.addBuyPlan(buyPlan);
    }

    /*按照查询条件分页查询购置计划记录*/
    public ArrayList<BuyPlan> queryBuyPlan(DeviceType deviceTypeObj,String deviceName,String sydw,String jhsj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != deviceTypeObj && deviceTypeObj.getTypeId()!= null && deviceTypeObj.getTypeId()!= 0)  where += " and t_buyPlan.deviceTypeObj=" + deviceTypeObj.getTypeId();
    	if(!deviceName.equals("")) where = where + " and t_buyPlan.deviceName like '%" + deviceName + "%'";
    	if(!sydw.equals("")) where = where + " and t_buyPlan.sydw like '%" + sydw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_buyPlan.jhsj like '%" + jhsj + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return buyPlanMapper.queryBuyPlan(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<BuyPlan> queryBuyPlan(DeviceType deviceTypeObj,String deviceName,String sydw,String jhsj) throws Exception  { 
     	String where = "where 1=1";
    	if(null != deviceTypeObj && deviceTypeObj.getTypeId()!= null && deviceTypeObj.getTypeId()!= 0)  where += " and t_buyPlan.deviceTypeObj=" + deviceTypeObj.getTypeId();
    	if(!deviceName.equals("")) where = where + " and t_buyPlan.deviceName like '%" + deviceName + "%'";
    	if(!sydw.equals("")) where = where + " and t_buyPlan.sydw like '%" + sydw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_buyPlan.jhsj like '%" + jhsj + "%'";
    	return buyPlanMapper.queryBuyPlanList(where);
    }

    /*查询所有购置计划记录*/
    public ArrayList<BuyPlan> queryAllBuyPlan()  throws Exception {
        return buyPlanMapper.queryBuyPlanList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(DeviceType deviceTypeObj,String deviceName,String sydw,String jhsj) throws Exception {
     	String where = "where 1=1";
    	if(null != deviceTypeObj && deviceTypeObj.getTypeId()!= null && deviceTypeObj.getTypeId()!= 0)  where += " and t_buyPlan.deviceTypeObj=" + deviceTypeObj.getTypeId();
    	if(!deviceName.equals("")) where = where + " and t_buyPlan.deviceName like '%" + deviceName + "%'";
    	if(!sydw.equals("")) where = where + " and t_buyPlan.sydw like '%" + sydw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_buyPlan.jhsj like '%" + jhsj + "%'";
        recordNumber = buyPlanMapper.queryBuyPlanCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取购置计划记录*/
    public BuyPlan getBuyPlan(int planId) throws Exception  {
        BuyPlan buyPlan = buyPlanMapper.getBuyPlan(planId);
        return buyPlan;
    }

    /*更新购置计划记录*/
    public void updateBuyPlan(BuyPlan buyPlan) throws Exception {
        buyPlanMapper.updateBuyPlan(buyPlan);
    }

    /*删除一条购置计划记录*/
    public void deleteBuyPlan (int planId) throws Exception {
        buyPlanMapper.deleteBuyPlan(planId);
    }

    /*删除多条购置计划信息*/
    public int deleteBuyPlans (String planIds) throws Exception {
    	String _planIds[] = planIds.split(",");
    	for(String _planId: _planIds) {
    		buyPlanMapper.deleteBuyPlan(Integer.parseInt(_planId));
    	}
    	return _planIds.length;
    }
}
