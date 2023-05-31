package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DataDraw;

import com.chengxusheji.mapper.DataDrawMapper;
@Service
public class DataDrawService {

	@Resource DataDrawMapper dataDrawMapper;
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

    /*添加资料图纸记录*/
    public void addDataDraw(DataDraw dataDraw) throws Exception {
    	dataDrawMapper.addDataDraw(dataDraw);
    }

    /*按照查询条件分页查询资料图纸记录*/
    public ArrayList<DataDraw> queryDataDraw(String ssdw,String drawClass,String drawName,String drawDesc,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_dataDraw.ssdw like '%" + ssdw + "%'";
    	if(!drawClass.equals("")) where = where + " and t_dataDraw.drawClass like '%" + drawClass + "%'";
    	if(!drawName.equals("")) where = where + " and t_dataDraw.drawName like '%" + drawName + "%'";
    	if(!drawDesc.equals("")) where = where + " and t_dataDraw.drawDesc like '%" + drawDesc + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return dataDrawMapper.queryDataDraw(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<DataDraw> queryDataDraw(String ssdw,String drawClass,String drawName,String drawDesc) throws Exception  { 
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_dataDraw.ssdw like '%" + ssdw + "%'";
    	if(!drawClass.equals("")) where = where + " and t_dataDraw.drawClass like '%" + drawClass + "%'";
    	if(!drawName.equals("")) where = where + " and t_dataDraw.drawName like '%" + drawName + "%'";
    	if(!drawDesc.equals("")) where = where + " and t_dataDraw.drawDesc like '%" + drawDesc + "%'";
    	return dataDrawMapper.queryDataDrawList(where);
    }

    /*查询所有资料图纸记录*/
    public ArrayList<DataDraw> queryAllDataDraw()  throws Exception {
        return dataDrawMapper.queryDataDrawList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String ssdw,String drawClass,String drawName,String drawDesc) throws Exception {
     	String where = "where 1=1";
    	if(!ssdw.equals("")) where = where + " and t_dataDraw.ssdw like '%" + ssdw + "%'";
    	if(!drawClass.equals("")) where = where + " and t_dataDraw.drawClass like '%" + drawClass + "%'";
    	if(!drawName.equals("")) where = where + " and t_dataDraw.drawName like '%" + drawName + "%'";
    	if(!drawDesc.equals("")) where = where + " and t_dataDraw.drawDesc like '%" + drawDesc + "%'";
        recordNumber = dataDrawMapper.queryDataDrawCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取资料图纸记录*/
    public DataDraw getDataDraw(int drawId) throws Exception  {
        DataDraw dataDraw = dataDrawMapper.getDataDraw(drawId);
        return dataDraw;
    }

    /*更新资料图纸记录*/
    public void updateDataDraw(DataDraw dataDraw) throws Exception {
        dataDrawMapper.updateDataDraw(dataDraw);
    }

    /*删除一条资料图纸记录*/
    public void deleteDataDraw (int drawId) throws Exception {
        dataDrawMapper.deleteDataDraw(drawId);
    }

    /*删除多条资料图纸信息*/
    public int deleteDataDraws (String drawIds) throws Exception {
    	String _drawIds[] = drawIds.split(",");
    	for(String _drawId: _drawIds) {
    		dataDrawMapper.deleteDataDraw(Integer.parseInt(_drawId));
    	}
    	return _drawIds.length;
    }
}
