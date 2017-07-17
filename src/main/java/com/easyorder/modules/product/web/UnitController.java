/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.web;

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

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.easyorder.common.beans.EasyResponse;
import com.easyorder.common.enums.EasyResponseEnums;
import com.easyorder.modules.product.entity.Unit;
import com.easyorder.modules.product.service.UnitService;

/**
 * 商品类目单位Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/unit")
public class UnitController extends BaseController {

	@Autowired
	private UnitService unitService;

	@ModelAttribute
	public Unit get(@RequestParam(required=false) String id) {
		Unit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = unitService.get(id);
		}
		if (entity == null){
			entity = new Unit();
		}
		return entity;
	}

	/**
	 * 商品类目单位列表页面
	 */
	@RequiresPermissions("product:unit:list")
	@RequestMapping(value = {"list", ""})
	public String list(Unit unit, HttpServletRequest request, HttpServletResponse response, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/product/unitList";
		}
		unit.setSupplierId(supplierId);
		Page<Unit> page = unitService.findPage(new Page<Unit>(request, response), unit); 
		model.addAttribute("page", page);
		return "easyorder/product/unitList";
	}

	/**
	 * 查看，增加，编辑商品类目单位表单页面
	 */
	@RequiresPermissions(value={"product:unit:view","product:unit:add","product:unit:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Unit unit, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/product/unitList";
		}
		unit.setSupplierId(supplierId);
		model.addAttribute("unit", unit);
		return "easyorder/product/unitForm";
	}

	/**
	 * 保存商品类目单位
	 */
	@RequiresPermissions(value={"product:unit:add","product:unit:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Unit unit, Model model, RedirectAttributes redirectAttributes) throws Exception{
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(redirectAttributes, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "redirect:"+Global.getAdminPath()+"/productManager/unit/?repage";
		}
		unit.setSupplierId(supplierId);
		if (!beanValidator(model, unit)){
			return form(unit, model);
		}
		if(!unit.getIsNewRecord()){//编辑表单保存
			Unit t = unitService.get(unit.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(unit, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			unitService.save(t);//保存
		}else{//新增表单保存
			unitService.save(unit);//保存
		}
		addMessage(redirectAttributes, "保存商品类目单位成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/unit/?repage";
	}

	/**
	 * 删除商品类目单位
	 */
	@RequiresPermissions("product:unit:del")
	@RequestMapping(value = "delete")
	public String delete(Unit unit, RedirectAttributes redirectAttributes) {
		unitService.delete(unit);
		addMessage(redirectAttributes, "删除商品类目单位成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/unit/?repage";
	}

	/**
	 * 批量删除商品类目单位
	 */
	@RequiresPermissions("product:unit:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			unitService.delete(unitService.get(id));
		}
		addMessage(redirectAttributes, "删除商品类目单位成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/unit/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:unit:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(Unit unit, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品类目单位"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Unit> page = unitService.findPage(new Page<Unit>(request, response, -1), unit);
			new ExportExcel("商品类目单位", Unit.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品类目单位记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/unit/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:unit:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Unit> list = ei.getDataList(Unit.class);
			for (Unit unit : list){
				try{
					unitService.save(unit);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品类目单位记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品类目单位记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品类目单位失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/unit/?repage";
	}

	/**
	 * 下载导入商品类目单位数据模板
	 */
	@RequiresPermissions("product:unit:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品类目单位数据导入模板.xlsx";
			List<Unit> list = Lists.newArrayList(); 
			new ExportExcel("商品类目单位数据", Unit.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/unit/?repage";
	}

	/**
	 * 异步获取单位列表
	 * @param unit
	 * @return
	 */
	@RequiresPermissions("product:unit:list")
	@RequestMapping(value = "async/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyResponse<List<Unit>> asyncList(Unit unit) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		unit.setSupplierId(supplierId);
		List<Unit> units = unitService.findList(unit);
		return EasyResponse.buildSuccess(units);
	}
	
	/**
	 * 异步获取单位列表
	 * @param unit
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("product:unit:add")
	@RequestMapping(value = "async/save", method = RequestMethod.POST)
	@ResponseBody
	public EasyResponse<String> asyncSave(Unit unit, Model model) throws Exception {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		unit.setSupplierId(supplierId);
		if (!beanValidator(model, unit)){
			return EasyResponse.buildByEnum(EasyResponseEnums.REQUEST_PARAM_ERROR);
		}
		if(!unit.getIsNewRecord()){//编辑表单保存
			Unit t = unitService.get(unit.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(unit, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			unitService.save(t);//保存
		}else{//新增表单保存
			unitService.save(unit);//保存
		}
		return EasyResponse.buildSuccess(unit.getId(), "保存成功");
	}
}