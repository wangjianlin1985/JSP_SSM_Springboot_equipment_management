package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.BuyPlan;

public interface BuyPlanMapper {
	/*添加购置计划信息*/
	public void addBuyPlan(BuyPlan buyPlan) throws Exception;

	/*按照查询条件分页查询购置计划记录*/
	public ArrayList<BuyPlan> queryBuyPlan(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有购置计划记录*/
	public ArrayList<BuyPlan> queryBuyPlanList(@Param("where") String where) throws Exception;

	/*按照查询条件的购置计划记录数*/
	public int queryBuyPlanCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条购置计划记录*/
	public BuyPlan getBuyPlan(int planId) throws Exception;

	/*更新购置计划记录*/
	public void updateBuyPlan(BuyPlan buyPlan) throws Exception;

	/*删除购置计划记录*/
	public void deleteBuyPlan(int planId) throws Exception;

}
