package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Repair {
    /*记录id*/
    private Integer repairId;
    public Integer getRepairId(){
        return repairId;
    }
    public void setRepairId(Integer repairId){
        this.repairId = repairId;
    }

    /*设备类型*/
    private DeviceType deviceTypeObj;
    public DeviceType getDeviceTypeObj() {
        return deviceTypeObj;
    }
    public void setDeviceTypeObj(DeviceType deviceTypeObj) {
        this.deviceTypeObj = deviceTypeObj;
    }

    /*设备名称*/
    @NotEmpty(message="设备名称不能为空")
    private String deviceName;
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /*校验周期*/
    @NotEmpty(message="校验周期不能为空")
    private String jyzq;
    public String getJyzq() {
        return jyzq;
    }
    public void setJyzq(String jyzq) {
        this.jyzq = jyzq;
    }

    /*使用日期*/
    @NotEmpty(message="使用日期不能为空")
    private String useDate;
    public String getUseDate() {
        return useDate;
    }
    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    /*校准状态*/
    @NotEmpty(message="校准状态不能为空")
    private String jzzt;
    public String getJzzt() {
        return jzzt;
    }
    public void setJzzt(String jzzt) {
        this.jzzt = jzzt;
    }

    /*校准日期*/
    private String jzrq;
    public String getJzrq() {
        return jzrq;
    }
    public void setJzrq(String jzrq) {
        this.jzrq = jzrq;
    }

    /*所属单位*/
    @NotEmpty(message="所属单位不能为空")
    private String ssdw;
    public String getSsdw() {
        return ssdw;
    }
    public void setSsdw(String ssdw) {
        this.ssdw = ssdw;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonRepair=new JSONObject(); 
		jsonRepair.accumulate("repairId", this.getRepairId());
		jsonRepair.accumulate("deviceTypeObj", this.getDeviceTypeObj().getTypeName());
		jsonRepair.accumulate("deviceTypeObjPri", this.getDeviceTypeObj().getTypeId());
		jsonRepair.accumulate("deviceName", this.getDeviceName());
		jsonRepair.accumulate("jyzq", this.getJyzq());
		jsonRepair.accumulate("useDate", this.getUseDate().length()>19?this.getUseDate().substring(0,19):this.getUseDate());
		jsonRepair.accumulate("jzzt", this.getJzzt());
		jsonRepair.accumulate("jzrq", this.getJzrq().length()>19?this.getJzrq().substring(0,19):this.getJzrq());
		jsonRepair.accumulate("ssdw", this.getSsdw());
		return jsonRepair;
    }}