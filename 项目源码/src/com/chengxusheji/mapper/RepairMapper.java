﻿package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Repair;

public interface RepairMapper {
	/*添加校准检修信息*/
	public void addRepair(Repair repair) throws Exception;

	/*按照查询条件分页查询校准检修记录*/
	public ArrayList<Repair> queryRepair(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有校准检修记录*/
	public ArrayList<Repair> queryRepairList(@Param("where") String where) throws Exception;

	/*按照查询条件的校准检修记录数*/
	public int queryRepairCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条校准检修记录*/
	public Repair getRepair(int repairId) throws Exception;

	/*更新校准检修记录*/
	public void updateRepair(Repair repair) throws Exception;

	/*删除校准检修记录*/
	public void deleteRepair(int repairId) throws Exception;

}
