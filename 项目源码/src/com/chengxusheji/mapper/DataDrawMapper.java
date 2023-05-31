package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.DataDraw;

public interface DataDrawMapper {
	/*添加资料图纸信息*/
	public void addDataDraw(DataDraw dataDraw) throws Exception;

	/*按照查询条件分页查询资料图纸记录*/
	public ArrayList<DataDraw> queryDataDraw(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有资料图纸记录*/
	public ArrayList<DataDraw> queryDataDrawList(@Param("where") String where) throws Exception;

	/*按照查询条件的资料图纸记录数*/
	public int queryDataDrawCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条资料图纸记录*/
	public DataDraw getDataDraw(int drawId) throws Exception;

	/*更新资料图纸记录*/
	public void updateDataDraw(DataDraw dataDraw) throws Exception;

	/*删除资料图纸记录*/
	public void deleteDataDraw(int drawId) throws Exception;

}
