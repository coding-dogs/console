/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.customer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.easyorder.modules.customer.entity.CustomerShippingAddress;
import com.easyorder.modules.customer.service.CustomerShippingAddressService;

/**
 * 客户收货地址Controller
 * @author qiudequan
 * @version 2017-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/customerManager/customerShippingAddress")
public class CustomerShippingAddressController extends BaseController {

	@Autowired
	private CustomerShippingAddressService customerShippingAddressService;
	
	@ModelAttribute
	public CustomerShippingAddress get(@RequestParam(required=false) String id) {
		CustomerShippingAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerShippingAddressService.get(id);
		}
		if (entity == null){
			entity = new CustomerShippingAddress();
		}
		return entity;
	}
	
	/**
	 * 客户收货地址列表页面
	 */
	@RequiresPermissions("customer:customerShippingAddress:list")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerShippingAddress customerShippingAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerShippingAddress> page = customerShippingAddressService.findPage(new Page<CustomerShippingAddress>(request, response), customerShippingAddress); 
		model.addAttribute("page", page);
		return "easyorder/customerManager/customerShippingAddressList";
	}

	/**
	 * 查看，增加，编辑客户收货地址表单页面
	 */
	@RequiresPermissions(value={"customer:customerShippingAddress:view","customer:customerShippingAddress:add","customer:customerShippingAddress:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CustomerShippingAddress customerShippingAddress, Model model) {
		model.addAttribute("customerShippingAddress", customerShippingAddress);
		return "easyorder/customerManager/customerShippingAddressForm";
	}

	/**
	 * 保存客户收货地址
	 */
	@RequiresPermissions(value={"customer:customerShippingAddress:add","customer:customerShippingAddress:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CustomerShippingAddress customerShippingAddress, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, customerShippingAddress)){
			return form(customerShippingAddress, model);
		}
		if(!customerShippingAddress.getIsNewRecord()){//编辑表单保存
			CustomerShippingAddress t = customerShippingAddressService.get(customerShippingAddress.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(customerShippingAddress, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			customerShippingAddressService.save(t);//保存
		}else{//新增表单保存
			customerShippingAddressService.save(customerShippingAddress);//保存
		}
		addMessage(redirectAttributes, "保存客户收货地址成功");
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerShippingAddress/?repage";
	}
	
	/**
	 * 删除客户收货地址
	 */
	@RequiresPermissions("customer:customerShippingAddress:del")
	@RequestMapping(value = "delete")
	public String delete(CustomerShippingAddress customerShippingAddress, RedirectAttributes redirectAttributes) {
		customerShippingAddressService.delete(customerShippingAddress);
		addMessage(redirectAttributes, "删除客户收货地址成功");
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerShippingAddress/?repage";
	}
	
	/**
	 * 批量删除客户收货地址
	 */
	@RequiresPermissions("customer:customerShippingAddress:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerShippingAddressService.delete(customerShippingAddressService.get(id));
		}
		addMessage(redirectAttributes, "删除客户收货地址成功");
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerShippingAddress/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("customer:customerShippingAddress:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CustomerShippingAddress customerShippingAddress, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客户收货地址"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CustomerShippingAddress> page = customerShippingAddressService.findPage(new Page<CustomerShippingAddress>(request, response, -1), customerShippingAddress);
    		new ExportExcel("客户收货地址", CustomerShippingAddress.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出客户收货地址记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerShippingAddress/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:customerShippingAddress:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerShippingAddress> list = ei.getDataList(CustomerShippingAddress.class);
			for (CustomerShippingAddress customerShippingAddress : list){
				try{
					customerShippingAddressService.save(customerShippingAddress);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客户收货地址记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条客户收货地址记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入客户收货地址失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerShippingAddress/?repage";
    }
	
	/**
	 * 下载导入客户收货地址数据模板
	 */
	@RequiresPermissions("customer:customerShippingAddress:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客户收货地址数据导入模板.xlsx";
    		List<CustomerShippingAddress> list = Lists.newArrayList(); 
    		new ExportExcel("客户收货地址数据", CustomerShippingAddress.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerShippingAddress/?repage";
    }
	
	
	

}