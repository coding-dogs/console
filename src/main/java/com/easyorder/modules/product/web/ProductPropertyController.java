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
import com.easyorder.modules.product.entity.ProductProperty;
import com.easyorder.modules.product.service.ProductPropertyService;

/**
 * 商品属性Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/productProperty")
public class ProductPropertyController extends BaseController {

	@Autowired
	private ProductPropertyService productPropertyService;

	@ModelAttribute
	public ProductProperty get(@RequestParam(required=false) String id) {
		ProductProperty entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productPropertyService.get(id);
		}
		if (entity == null){
			entity = new ProductProperty();
		}
		return entity;
	}

	/**
	 * 商品属性列表页面
	 */
	@RequiresPermissions("product:productProperty:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductProperty productProperty, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductProperty> page = productPropertyService.findPage(new Page<ProductProperty>(request, response), productProperty); 
		model.addAttribute("page", page);
		return "easyorder/product/productPropertyList";
	}

	/**
	 * 查看，增加，编辑商品属性表单页面
	 */
	@RequiresPermissions(value={"product:productProperty:view","product:productProperty:add","product:productProperty:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductProperty productProperty, Model model) {
		model.addAttribute("productProperty", productProperty);
		return "easyorder/product/productPropertyForm";
	}

	/**
	 * 保存商品属性
	 */
	@RequiresPermissions(value={"product:productProperty:add","product:productProperty:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductProperty productProperty, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productProperty)){
			return form(productProperty, model);
		}
		if(!productProperty.getIsNewRecord()){//编辑表单保存
			ProductProperty t = productPropertyService.get(productProperty.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productProperty, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productPropertyService.save(t);//保存
		}else{//新增表单保存
			productPropertyService.save(productProperty);//保存
		}
		addMessage(redirectAttributes, "保存商品属性成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productProperty/?repage";
	}

	/**
	 * 删除商品属性
	 */
	@RequiresPermissions("product:productProperty:del")
	@RequestMapping(value = "delete")
	public String delete(ProductProperty productProperty, RedirectAttributes redirectAttributes) {
		productPropertyService.delete(productProperty);
		addMessage(redirectAttributes, "删除商品属性成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productProperty/?repage";
	}

	/**
	 * 批量删除商品属性
	 */
	@RequiresPermissions("product:productProperty:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productPropertyService.delete(productPropertyService.get(id));
		}
		addMessage(redirectAttributes, "删除商品属性成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productProperty/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:productProperty:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductProperty productProperty, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品属性"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductProperty> page = productPropertyService.findPage(new Page<ProductProperty>(request, response, -1), productProperty);
			new ExportExcel("商品属性", ProductProperty.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品属性记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productProperty/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:productProperty:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductProperty> list = ei.getDataList(ProductProperty.class);
			for (ProductProperty productProperty : list){
				try{
					productPropertyService.save(productProperty);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品属性记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品属性记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品属性失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productProperty/?repage";
	}

	/**
	 * 下载导入商品属性数据模板
	 */
	@RequiresPermissions("product:productProperty:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品属性数据导入模板.xlsx";
			List<ProductProperty> list = Lists.newArrayList(); 
			new ExportExcel("商品属性数据", ProductProperty.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productProperty/?repage";
	}




}