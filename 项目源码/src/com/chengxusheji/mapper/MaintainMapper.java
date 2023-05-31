package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Maintain;

public interface MaintainMapper {
	/*添加维护情况信息*/
	public void addMaintain(Maintain maintain) throws Exception;

	/*按照查询条件分页查询维护情况记录*/
	public ArrayList<Maintain> queryMaintain(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有维护情况记录*/
	public ArrayList<Maintain> queryMaintainList(@Param("where") String where) throws Exception;

	/*按照查询条件的维护情况记录数*/
	public int queryMaintainCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条维护情况记录*/
	public Maintain getMaintain(int maintainId) throws Exception;

	/*更新维护情况记录*/
	public void updateMaintain(Maintain maintain) throws Exception;

	/*删除维护情况记录*/
	public void deleteMaintain(int maintainId) throws Exception;

}
