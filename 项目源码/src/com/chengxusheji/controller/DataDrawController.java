package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.DataDrawService;
import com.chengxusheji.po.DataDraw;

//DataDraw管理控制层
@Controller
@RequestMapping("/DataDraw")
public class DataDrawController extends BaseController {

    /*业务层对象*/
    @Resource DataDrawService dataDrawService;

	@InitBinder("dataDraw")
	public void initBinderDataDraw(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dataDraw.");
	}
	/*跳转到添加DataDraw视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new DataDraw());
		return "DataDraw_add";
	}

	/*客户端ajax方式提交添加资料图纸信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated DataDraw dataDraw, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		dataDraw.setDrawFile(this.handleFileUpload(request, "drawFileFile"));
        dataDrawService.addDataDraw(dataDraw);
        message = "资料图纸添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询资料图纸信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String ssdw,String drawClass,String drawName,String drawDesc,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (ssdw == null) ssdw = "";
		if (drawClass == null) drawClass = "";
		if (drawName == null) drawName = "";
		if (drawDesc == null) drawDesc = "";
		if(rows != 0)dataDrawService.setRows(rows);
		List<DataDraw> dataDrawList = dataDrawService.queryDataDraw(ssdw, drawClass, drawName, drawDesc, page);
	    /*计算总的页数和总的记录数*/
	    dataDrawService.queryTotalPageAndRecordNumber(ssdw, drawClass, drawName, drawDesc);
	    /*获取到总的页码数目*/
	    int totalPage = dataDrawService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dataDrawService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(DataDraw dataDraw:dataDrawList) {
			JSONObject jsonDataDraw = dataDraw.getJsonObject();
			jsonArray.put(jsonDataDraw);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询资料图纸信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<DataDraw> dataDrawList = dataDrawService.queryAllDataDraw();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(DataDraw dataDraw:dataDrawList) {
			JSONObject jsonDataDraw = new JSONObject();
			jsonDataDraw.accumulate("drawId", dataDraw.getDrawId());
			jsonDataDraw.accumulate("drawName", dataDraw.getDrawName());
			jsonArray.put(jsonDataDraw);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询资料图纸信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String ssdw,String drawClass,String drawName,String drawDesc,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (ssdw == null) ssdw = "";
		if (drawClass == null) drawClass = "";
		if (drawName == null) drawName = "";
		if (drawDesc == null) drawDesc = "";
		List<DataDraw> dataDrawList = dataDrawService.queryDataDraw(ssdw, drawClass, drawName, drawDesc, currentPage);
	    /*计算总的页数和总的记录数*/
	    dataDrawService.queryTotalPageAndRecordNumber(ssdw, drawClass, drawName, drawDesc);
	    /*获取到总的页码数目*/
	    int totalPage = dataDrawService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dataDrawService.getRecordNumber();
	    request.setAttribute("dataDrawList",  dataDrawList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("ssdw", ssdw);
	    request.setAttribute("drawClass", drawClass);
	    request.setAttribute("drawName", drawName);
	    request.setAttribute("drawDesc", drawDesc);
		return "DataDraw/dataDraw_frontquery_result"; 
	}

     /*前台查询DataDraw信息*/
	@RequestMapping(value="/{drawId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer drawId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键drawId获取DataDraw对象*/
        DataDraw dataDraw = dataDrawService.getDataDraw(drawId);

        request.setAttribute("dataDraw",  dataDraw);
        return "DataDraw/dataDraw_frontshow";
	}

	/*ajax方式显示资料图纸修改jsp视图页*/
	@RequestMapping(value="/{drawId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer drawId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键drawId获取DataDraw对象*/
        DataDraw dataDraw = dataDrawService.getDataDraw(drawId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDataDraw = dataDraw.getJsonObject();
		out.println(jsonDataDraw.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新资料图纸信息*/
	@RequestMapping(value = "/{drawId}/update", method = RequestMethod.POST)
	public void update(@Validated DataDraw dataDraw, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String drawFileFileName = this.handleFileUpload(request, "drawFileFile");
		if(!drawFileFileName.equals(""))dataDraw.setDrawFile(drawFileFileName);
		try {
			dataDrawService.updateDataDraw(dataDraw);
			message = "资料图纸更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "资料图纸更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除资料图纸信息*/
	@RequestMapping(value="/{drawId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer drawId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  dataDrawService.deleteDataDraw(drawId);
	            request.setAttribute("message", "资料图纸删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "资料图纸删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条资料图纸记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String drawIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = dataDrawService.deleteDataDraws(drawIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出资料图纸信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String ssdw,String drawClass,String drawName,String drawDesc, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(ssdw == null) ssdw = "";
        if(drawClass == null) drawClass = "";
        if(drawName == null) drawName = "";
        if(drawDesc == null) drawDesc = "";
        List<DataDraw> dataDrawList = dataDrawService.queryDataDraw(ssdw,drawClass,drawName,drawDesc);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "DataDraw信息记录"; 
        String[] headers = { "所属单位","图纸类别","图纸名称","图纸描述"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<dataDrawList.size();i++) {
        	DataDraw dataDraw = dataDrawList.get(i); 
        	dataset.add(new String[]{dataDraw.getSsdw(),dataDraw.getDrawClass(),dataDraw.getDrawName(),dataDraw.getDrawDesc()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"DataDraw.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
