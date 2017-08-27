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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.easyorder.common.beans.EasyResponse;
import com.easyorder.common.enums.EasyResponseEnums;
import com.easyorder.modules.customer.entity.CustomerGroup;
import com.easyorder.modules.customer.service.CustomerGroupService;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 客户组Controller
 * @author qiudequan
 * @version 2017-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/customerManager/customerGroup")
public class CustomerGroupController extends BaseController {

	@Autowired
	private CustomerGroupService customerGroupService;

	@ModelAttribute
	public CustomerGroup get(@RequestParam(required=false) String id) {
		CustomerGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerGroupService.get(id);
		}
		if (entity == null){
			entity = new CustomerGroup();
		}
		return entity;
	}

	/**
	 * 客户组列表页面
	 */
	@RequiresPermissions("customer:customerGroup:list")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerGroup customerGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/customer/customerGroupList";
		}
		customerGroup.setSupplierId(supplierId);
		Page<CustomerGroup> page = customerGroupService.findPage(new Page<CustomerGroup>(request, response), customerGroup); 
		model.addAttribute("page", page);
		return "easyorder/customer/customerGroupList";
	}

	/**
	 * 查看，增加，编辑客户组表单页面
	 */
	@RequiresPermissions(value={"customer:customerGroup:view","customer:customerGroup:add","customer:customerGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CustomerGroup customerGroup, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/customer/customerGroupForm";
		}
		customerGroup.setSupplierId(supplierId);
		model.addAttribute("customerGroup", customerGroup);
		return "easyorder/customer/customerGroupForm";
	}

	/**
	 * 保存客户组
	 */
	@RequiresPermissions(value={"customer:customerGroup:add","customer:customerGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CustomerGroup customerGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(redirectAttributes, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "redirect:"+Global.getAdminPath()+"/customerManager/customerGroup/?repage";
		}
		customerGroup.setSupplierId(supplierId);
		if (!beanValidator(model, customerGroup)){
			return form(customerGroup, model);
		}
		if(!customerGroup.getIsNewRecord()){//编辑表单保存
			CustomerGroup t = customerGroupService.get(customerGroup.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(customerGroup, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			customerGroupService.save(t);//保存
		}else{//新增表单保存
			customerGroupService.save(customerGroup);//保存
		}
		addMessage(redirectAttributes, "保存客户组成功");
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerGroup/?repage";
	}

	/**
	 * 删除客户组
	 */
	@RequiresPermissions("customer:customerGroup:del")
	@RequestMapping(value = "delete")
	public String delete(CustomerGroup customerGroup, RedirectAttributes redirectAttributes) {
		customerGroupService.delete(customerGroup);
		addMessage(redirectAttributes, "删除客户组成功");
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerGroup/?repage";
	}

	/**
	 * 批量删除客户组
	 */
	@RequiresPermissions("customer:customerGroup:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerGroupService.delete(customerGroupService.get(id));
		}
		addMessage(redirectAttributes, "删除客户组成功");
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerGroup/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("customer:customerGroup:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(CustomerGroup customerGroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "客户组"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<CustomerGroup> page = customerGroupService.findPage(new Page<CustomerGroup>(request, response, -1), customerGroup);
			new ExportExcel("客户组", CustomerGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出客户组记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerGroup/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:customerGroup:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerGroup> list = ei.getDataList(CustomerGroup.class);
			for (CustomerGroup customerGroup : list){
				try{
					customerGroupService.save(customerGroup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客户组记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条客户组记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入客户组失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerGroup/?repage";
	}

	/**
	 * 下载导入客户组数据模板
	 */
	@RequiresPermissions("customer:customerGroup:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "客户组数据导入模板.xlsx";
			List<CustomerGroup> list = Lists.newArrayList(); 
			new ExportExcel("客户组数据", CustomerGroup.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customerManager/customerGroup/?repage";
	}

	@RequiresPermissions("customer:customerGroup:list")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public EasyResponse<List<CustomerGroup>> getCustomerGroups() {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		CustomerGroup customerGroup = new CustomerGroup();
		customerGroup.setSupplierId(supplierId);
		List<CustomerGroup> groups = customerGroupService.findList(customerGroup);
		return EasyResponse.buildSuccess(groups);
	}
	
	@RequiresPermissions("customer:customerGroup:add")
	@RequestMapping(value = "/async/save", method = RequestMethod.POST)
	@ResponseBody
	public EasyResponse<String> saveCustomerGroup(CustomerGroup customerGroup) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		customerGroup.setSupplierId(supplierId);
		customerGroupService.save(customerGroup);
		return EasyResponse.buildSuccess(customerGroup.getId(), "新增客户组成功");
	}
	
	@RequiresPermissions("customer:customerGroup:list")
	@RequestMapping(value = "/async/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyResponse<Page<CustomerGroup>> asyncList(CustomerGroup customerGroup, HttpServletRequest request, HttpServletResponse response) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		customerGroup.setSupplierId(supplierId);
		Page<CustomerGroup> page = customerGroupService.findPage(new Page<CustomerGroup>(request, response), customerGroup); 
		return EasyResponse.buildSuccess(page);
	}

	@RequiresPermissions("customer:customerGroup:list")
	@RequestMapping(value = "/selector", method = RequestMethod.GET)
	public String getCustomerGroupSelector(Model model, String type, String productId) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
		}
		model.addAttribute("type", type);
		model.addAttribute("productId", productId);
		return "easyorder/common/customerGroupSelector";
	}

}