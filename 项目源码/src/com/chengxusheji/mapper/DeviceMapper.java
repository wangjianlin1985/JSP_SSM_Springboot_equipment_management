package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Device;

public interface DeviceMapper {
	/*添加仪器设备信息*/
	public void addDevice(Device device) throws Exception;

	/*按照查询条件分页查询仪器设备记录*/
	public ArrayList<Device> queryDevice(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有仪器设备记录*/
	public ArrayList<Device> queryDeviceList(@Param("where") String where) throws Exception;

	/*按照查询条件的仪器设备记录数*/
	public int queryDeviceCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条仪器设备记录*/
	public Device getDevice(int deviceId) throws Exception;

	/*更新仪器设备记录*/
	public void updateDevice(Device device) throws Exception;

	/*删除仪器设备记录*/
	public void deleteDevice(int deviceId) throws Exception;

}
