package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.DeviceType;

public interface DeviceTypeMapper {
	/*添加设备类型信息*/
	public void addDeviceType(DeviceType deviceType) throws Exception;

	/*按照查询条件分页查询设备类型记录*/
	public ArrayList<DeviceType> queryDeviceType(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有设备类型记录*/
	public ArrayList<DeviceType> queryDeviceTypeList(@Param("where") String where) throws Exception;

	/*按照查询条件的设备类型记录数*/
	public int queryDeviceTypeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条设备类型记录*/
	public DeviceType getDeviceType(int typeId) throws Exception;

	/*更新设备类型记录*/
	public void updateDeviceType(DeviceType deviceType) throws Exception;

	/*删除设备类型记录*/
	public void deleteDeviceType(int typeId) throws Exception;

}
