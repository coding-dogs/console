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
import com.easyorder.modules.product.entity.SpecificationGroup;
import com.easyorder.modules.product.service.SpecificationGroupService;

/**
 * 商品规格组Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/specificationGroup")
public class SpecificationGroupController extends BaseController {

	@Autowired
	private SpecificationGroupService specificationGroupService;

	@ModelAttribute
	public SpecificationGroup get(@RequestParam(required=false) String id) {
		SpecificationGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = specificationGroupService.get(id);
		}
		if (entity == null){
			entity = new SpecificationGroup();
		}
		return entity;
	}

	/**
	 * 商品规格组列表页面
	 */
	@RequiresPermissions("product:specificationGroup:list")
	@RequestMapping(value = {"list", ""})
	public String list(SpecificationGroup specificationGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SpecificationGroup> page = specificationGroupService.findPage(new Page<SpecificationGroup>(request, response), specificationGroup); 
		model.addAttribute("page", page);
		return "easyorder/product/specificationGroupList";
	}

	/**
	 * 查看，增加，编辑商品规格组表单页面
	 */
	@RequiresPermissions(value={"product:specificationGroup:view","product:specificationGroup:add","product:specificationGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SpecificationGroup specificationGroup, Model model) {
		model.addAttribute("specificationGroup", specificationGroup);
		return "easyorder/product/specificationGroupForm";
	}

	/**
	 * 保存商品规格组
	 */
	@RequiresPermissions(value={"product:specificationGroup:add","product:specificationGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SpecificationGroup specificationGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, specificationGroup)){
			return form(specificationGroup, model);
		}
		if(!specificationGroup.getIsNewRecord()){//编辑表单保存
			SpecificationGroup t = specificationGroupService.get(specificationGroup.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(specificationGroup, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			specificationGroupService.save(t);//保存
		}else{//新增表单保存
			specificationGroupService.save(specificationGroup);//保存
		}
		addMessage(redirectAttributes, "保存商品规格组成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/specificationGroup/?repage";
	}

	/**
	 * 删除商品规格组
	 */
	@RequiresPermissions("product:specificationGroup:del")
	@RequestMapping(value = "delete")
	public String delete(SpecificationGroup specificationGroup, RedirectAttributes redirectAttributes) {
		specificationGroupService.delete(specificationGroup);
		addMessage(redirectAttributes, "删除商品规格组成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/specificationGroup/?repage";
	}

	/**
	 * 批量删除商品规格组
	 */
	@RequiresPermissions("product:specificationGroup:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			specificationGroupService.delete(specificationGroupService.get(id));
		}
		addMessage(redirectAttributes, "删除商品规格组成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/specificationGroup/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:specificationGroup:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(SpecificationGroup specificationGroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品规格组"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<SpecificationGroup> page = specificationGroupService.findPage(new Page<SpecificationGroup>(request, response, -1), specificationGroup);
			new ExportExcel("商品规格组", SpecificationGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品规格组记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/specificationGroup/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:specificationGroup:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SpecificationGroup> list = ei.getDataList(SpecificationGroup.class);
			for (SpecificationGroup specificationGroup : list){
				try{
					specificationGroupService.save(specificationGroup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品规格组记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品规格组记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品规格组失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/specificationGroup/?repage";
	}

	/**
	 * 下载导入商品规格组数据模板
	 */
	@RequiresPermissions("product:specificationGroup:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品规格组数据导入模板.xlsx";
			List<SpecificationGroup> list = Lists.newArrayList(); 
			new ExportExcel("商品规格组数据", SpecificationGroup.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/specificationGroup/?repage";
	}




}