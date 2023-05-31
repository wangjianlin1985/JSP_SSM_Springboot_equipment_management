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
import com.chengxusheji.service.MaintainService;
import com.chengxusheji.po.Maintain;

//Maintain管理控制层
@Controller
@RequestMapping("/Maintain")
public class MaintainController extends BaseController {

    /*业务层对象*/
    @Resource MaintainService maintainService;

	@InitBinder("maintain")
	public void initBinderMaintain(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("maintain.");
	}
	/*跳转到添加Maintain视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Maintain());
		return "Maintain_add";
	}

	/*客户端ajax方式提交添加维护情况信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Maintain maintain, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        maintainService.addMaintain(maintain);
        message = "维护情况添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询维护情况信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String ssdw,String jhsj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (ssdw == null) ssdw = "";
		if (jhsj == null) jhsj = "";
		if(rows != 0)maintainService.setRows(rows);
		List<Maintain> maintainList = maintainService.queryMaintain(ssdw, jhsj, page);
	    /*计算总的页数和总的记录数*/
	    maintainService.queryTotalPageAndRecordNumber(ssdw, jhsj);
	    /*获取到总的页码数目*/
	    int totalPage = maintainService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = maintainService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Maintain maintain:maintainList) {
			JSONObject jsonMaintain = maintain.getJsonObject();
			jsonArray.put(jsonMaintain);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询维护情况信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Maintain> maintainList = maintainService.queryAllMaintain();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Maintain maintain:maintainList) {
			JSONObject jsonMaintain = new JSONObject();
			jsonMaintain.accumulate("maintainId", maintain.getMaintainId());
			jsonArray.put(jsonMaintain);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询维护情况信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String ssdw,String jhsj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (ssdw == null) ssdw = "";
		if (jhsj == null) jhsj = "";
		List<Maintain> maintainList = maintainService.queryMaintain(ssdw, jhsj, currentPage);
	    /*计算总的页数和总的记录数*/
	    maintainService.queryTotalPageAndRecordNumber(ssdw, jhsj);
	    /*获取到总的页码数目*/
	    int totalPage = maintainService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = maintainService.getRecordNumber();
	    request.setAttribute("maintainList",  maintainList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("ssdw", ssdw);
	    request.setAttribute("jhsj", jhsj);
		return "Maintain/maintain_frontquery_result"; 
	}

     /*前台查询Maintain信息*/
	@RequestMapping(value="/{maintainId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer maintainId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键maintainId获取Maintain对象*/
        Maintain maintain = maintainService.getMaintain(maintainId);

        request.setAttribute("maintain",  maintain);
        return "Maintain/maintain_frontshow";
	}

	/*ajax方式显示维护情况修改jsp视图页*/
	@RequestMapping(value="/{maintainId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer maintainId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键maintainId获取Maintain对象*/
        Maintain maintain = maintainService.getMaintain(maintainId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonMaintain = maintain.getJsonObject();
		out.println(jsonMaintain.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新维护情况信息*/
	@RequestMapping(value = "/{maintainId}/update", method = RequestMethod.POST)
	public void update(@Validated Maintain maintain, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			maintainService.updateMaintain(maintain);
			message = "维护情况更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "维护情况更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除维护情况信息*/
	@RequestMapping(value="/{maintainId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer maintainId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  maintainService.deleteMaintain(maintainId);
	            request.setAttribute("message", "维护情况删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "维护情况删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条维护情况记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String maintainIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = maintainService.deleteMaintains(maintainIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出维护情况信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String ssdw,String jhsj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(ssdw == null) ssdw = "";
        if(jhsj == null) jhsj = "";
        List<Maintain> maintainList = maintainService.queryMaintain(ssdw,jhsj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Maintain信息记录"; 
        String[] headers = { "所属单位","计划时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<maintainList.size();i++) {
        	Maintain maintain = maintainList.get(i); 
        	dataset.add(new String[]{maintain.getSsdw(),maintain.getJhsj()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Maintain.xls");//filename是下载的xls的名，建议最好用英文 
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
