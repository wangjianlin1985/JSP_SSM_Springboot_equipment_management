package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DeviceType;

import com.chengxusheji.mapper.DeviceTypeMapper;
@Service
public class DeviceTypeService {

	@Resource DeviceTypeMapper deviceTypeMapper;
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

    /*添加设备类型记录*/
    public void addDeviceType(DeviceType deviceType) throws Exception {
    	deviceTypeMapper.addDeviceType(deviceType);
    }

    /*按照查询条件分页查询设备类型记录*/
    public ArrayList<DeviceType> queryDeviceType(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return deviceTypeMapper.queryDeviceType(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<DeviceType> queryDeviceType() throws Exception  { 
     	String where = "where 1=1";
    	return deviceTypeMapper.queryDeviceTypeList(where);
    }

    /*查询所有设备类型记录*/
    public ArrayList<DeviceType> queryAllDeviceType()  throws Exception {
        return deviceTypeMapper.queryDeviceTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = deviceTypeMapper.queryDeviceTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取设备类型记录*/
    public DeviceType getDeviceType(int typeId) throws Exception  {
        DeviceType deviceType = deviceTypeMapper.getDeviceType(typeId);
        return deviceType;
    }

    /*更新设备类型记录*/
    public void updateDeviceType(DeviceType deviceType) throws Exception {
        deviceTypeMapper.updateDeviceType(deviceType);
    }

    /*删除一条设备类型记录*/
    public void deleteDeviceType (int typeId) throws Exception {
        deviceTypeMapper.deleteDeviceType(typeId);
    }

    /*删除多条设备类型信息*/
    public int deleteDeviceTypes (String typeIds) throws Exception {
    	String _typeIds[] = typeIds.split(",");
    	for(String _typeId: _typeIds) {
    		deviceTypeMapper.deleteDeviceType(Integer.parseInt(_typeId));
    	}
    	return _typeIds.length;
    }
}
