package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DeviceType;
import com.chengxusheji.po.Device;

import com.chengxusheji.mapper.DeviceMapper;
@Service
public class DeviceService {

	@Resource DeviceMapper deviceMapper;
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

    /*添加仪器设备记录*/
    public void addDevice(Device device) throws Exception {
    	deviceMapper.addDevice(device);
    }

    /*按照查询条件分页查询仪器设备记录*/
    public ArrayList<Device> queryDevice(DeviceType deviceTypeObj,String deviceName,String deviceState,String useDate,String ssdw,String jhsj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != deviceTypeObj && deviceTypeObj.getTypeId()!= null && deviceTypeObj.getTypeId()!= 0)  where += " and t_device.deviceTypeObj=" + deviceTypeObj.getTypeId();
    	if(!deviceName.equals("")) where = where + " and t_device.deviceName like '%" + deviceName + "%'";
    	if(!deviceState.equals("")) where = where + " and t_device.deviceState like '%" + deviceState + "%'";
    	if(!useDate.equals("")) where = where + " and t_device.useDate like '%" + useDate + "%'";
    	if(!ssdw.equals("")) where = where + " and t_device.ssdw like '%" + ssdw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_device.jhsj like '%" + jhsj + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return deviceMapper.queryDevice(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Device> queryDevice(DeviceType deviceTypeObj,String deviceName,String deviceState,String useDate,String ssdw,String jhsj) throws Exception  { 
     	String where = "where 1=1";
    	if(null != deviceTypeObj && deviceTypeObj.getTypeId()!= null && deviceTypeObj.getTypeId()!= 0)  where += " and t_device.deviceTypeObj=" + deviceTypeObj.getTypeId();
    	if(!deviceName.equals("")) where = where + " and t_device.deviceName like '%" + deviceName + "%'";
    	if(!deviceState.equals("")) where = where + " and t_device.deviceState like '%" + deviceState + "%'";
    	if(!useDate.equals("")) where = where + " and t_device.useDate like '%" + useDate + "%'";
    	if(!ssdw.equals("")) where = where + " and t_device.ssdw like '%" + ssdw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_device.jhsj like '%" + jhsj + "%'";
    	return deviceMapper.queryDeviceList(where);
    }

    /*查询所有仪器设备记录*/
    public ArrayList<Device> queryAllDevice()  throws Exception {
        return deviceMapper.queryDeviceList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(DeviceType deviceTypeObj,String deviceName,String deviceState,String useDate,String ssdw,String jhsj) throws Exception {
     	String where = "where 1=1";
    	if(null != deviceTypeObj && deviceTypeObj.getTypeId()!= null && deviceTypeObj.getTypeId()!= 0)  where += " and t_device.deviceTypeObj=" + deviceTypeObj.getTypeId();
    	if(!deviceName.equals("")) where = where + " and t_device.deviceName like '%" + deviceName + "%'";
    	if(!deviceState.equals("")) where = where + " and t_device.deviceState like '%" + deviceState + "%'";
    	if(!useDate.equals("")) where = where + " and t_device.useDate like '%" + useDate + "%'";
    	if(!ssdw.equals("")) where = where + " and t_device.ssdw like '%" + ssdw + "%'";
    	if(!jhsj.equals("")) where = where + " and t_device.jhsj like '%" + jhsj + "%'";
        recordNumber = deviceMapper.queryDeviceCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取仪器设备记录*/
    public Device getDevice(int deviceId) throws Exception  {
        Device device = deviceMapper.getDevice(deviceId);
        return device;
    }

    /*更新仪器设备记录*/
    public void updateDevice(Device device) throws Exception {
        deviceMapper.updateDevice(device);
    }

    /*删除一条仪器设备记录*/
    public void deleteDevice (int deviceId) throws Exception {
        deviceMapper.deleteDevice(deviceId);
    }

    /*删除多条仪器设备信息*/
    public int deleteDevices (String deviceIds) throws Exception {
    	String _deviceIds[] = deviceIds.split(",");
    	for(String _deviceId: _deviceIds) {
    		deviceMapper.deleteDevice(Integer.parseInt(_deviceId));
    	}
    	return _deviceIds.length;
    }
}
