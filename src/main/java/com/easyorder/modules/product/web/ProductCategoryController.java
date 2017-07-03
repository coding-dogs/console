/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.easyorder.modules.product.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.easyorder.common.constant.Constants;
import com.easyorder.common.enums.EasyResponseEnums;
import com.easyorder.common.utils.CollectionUtils;
import com.easyorder.common.utils.SortUtils;
import com.easyorder.modules.product.entity.ProductCategory;
import com.easyorder.modules.product.service.ProductCategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
 * 商品分类Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/productCategory")
public class ProductCategoryController extends BaseController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	private static final String ROOT_ID = "0";

	@ModelAttribute
	public ProductCategory get(@RequestParam(required=false) String id) {
		ProductCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCategoryService.get(id);
		}
		if (entity == null){
			entity = new ProductCategory();
		}
		return entity;
	}

	/**
	 * 商品分类列表页面
	 */
	@RequiresPermissions("product:productCategory:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductCategory productCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/product/productCategoryList";
		}
		productCategory.setSupplierId(supplierId);
		List<ProductCategory> resultList = Lists.newArrayList();
		List<ProductCategory> productCategoryList = productCategoryService.findList(productCategory);
		SortUtils.productCategorySort(resultList, productCategoryList, ROOT_ID, true);
		model.addAttribute("productCategoryList", resultList);
		return "easyorder/product/productCategoryList";
	}

	/**
	 * 查看，增加，编辑商品分类表单页面
	 */
	@RequiresPermissions(value={"product:productCategory:view","product:productCategory:add","product:productCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductCategory productCategory, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(model, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "easyorder/product/productCategoryForm";
		}
		productCategory.setSupplierId(supplierId);
		if (StringUtils.isEmpty(productCategory.getPid())) {
			productCategory.setPid(ROOT_ID);
    }
		
		if(StringUtils.isBlank(productCategory.getId())) {
			Integer maxSort = productCategoryService.getMaxSort(productCategory);
			productCategory.setSort(maxSort != null ? maxSort + 1 : 1);
		}
		
		if(ROOT_ID.equals(productCategory.getPid())) {
			productCategory.setParent(productCategoryService.getRootCategory());
		} else {
			productCategory.setParent(productCategoryService.get(productCategory.getPid()));
		}
		
		model.addAttribute("productCategory", productCategory);
		return "easyorder/product/productCategoryForm";
	}

	/**
	 * 保存商品分类
	 */
	@RequiresPermissions(value={"product:productCategory:add","product:productCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "async/save")
	@ResponseBody
	public EasyResponse<String> save(ProductCategory productCategory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		productCategory.setSupplierId(supplierId);
		if (!beanValidator(model, productCategory)){
			return EasyResponse.buildByEnum(EasyResponseEnums.REQUEST_PARAM_ERROR);
		}
		if(!productCategory.getIsNewRecord()){//编辑表单保存
			ProductCategory t = productCategoryService.get(productCategory.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productCategory, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productCategoryService.save(t);//保存
		}else{//新增表单保存
			productCategoryService.save(productCategory);//保存
		}
		return EasyResponse.buildSuccess(productCategory.getId(), "保存成功");
	}
	
	/**
	 * 保存商品分类 异步调用
	 */
	@RequiresPermissions(value={"product:productCategory:add","product:productCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save", method=RequestMethod.POST)
	public String saveAsync(ProductCategory productCategory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			addMessage(redirectAttributes, EasyResponseEnums.NOT_FOUND_SUPPLIER.message);
			return "redirect:"+Global.getAdminPath()+"/productManager/productCategory/?repage";
		}
		productCategory.setSupplierId(supplierId);
		if (!beanValidator(model, productCategory)){
			return form(productCategory, model);
		}
		if(!productCategory.getIsNewRecord()){//编辑表单保存
			ProductCategory t = productCategoryService.get(productCategory.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productCategory, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productCategoryService.save(t);//保存
		}else{//新增表单保存
			productCategoryService.save(productCategory);//保存
		}
		addMessage(redirectAttributes, "商品分类保存成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategory/?repage";
	}

	/**
	 * 删除商品分类
	 */
	@RequiresPermissions("product:productCategory:del")
	@RequestMapping(value = "delete")
	public String delete(ProductCategory productCategory, RedirectAttributes redirectAttributes) {
		// 删除选定分类
		productCategoryService.delete(productCategory);
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategory/?repage";
	}

	/**
	 * 批量删除商品分类
	 */
	@RequiresPermissions("product:productCategory:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productCategoryService.delete(productCategoryService.get(id));
		}
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategory/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:productCategory:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductCategory productCategory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品分类"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductCategory> page = productCategoryService.findPage(new Page<ProductCategory>(request, response, -1), productCategory);
			new ExportExcel("商品分类", ProductCategory.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品分类记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategory/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:productCategory:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductCategory> list = ei.getDataList(ProductCategory.class);
			for (ProductCategory productCategory : list){
				try{
					productCategoryService.save(productCategory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品分类记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品分类记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品分类失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategory/?repage";
	}

	/**
	 * 下载导入商品分类数据模板
	 */
	@RequiresPermissions("product:productCategory:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品分类数据导入模板.xlsx";
			List<ProductCategory> list = Lists.newArrayList(); 
			new ExportExcel("商品分类数据", ProductCategory.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productCategory/?repage";
	}

	/**
	 * 查看分类树状结构
	 * @param model
	 * @return
	 */
	@RequiresPermissions("product:productCategory:list")
	@RequestMapping(value = "tree")
	@ResponseBody
	public EasyResponse<List<Map<String, Object>>> categoryTree(Model model, String hasRoot) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		List<Map<String, Object>> resultList = new ArrayList<>();
		if(!Constants.NO.equals(hasRoot)) {
			Map<String, Object> root = Maps.newHashMap();
			root.put("id", ROOT_ID);
			root.put("pId", null);
			root.put("name", "顶级分类");
			resultList.add(root);
		}
		ProductCategory productCategory = new ProductCategory();
		productCategory.setSupplierId(supplierId);
		List<ProductCategory> categoryList = productCategoryService.findList(productCategory);
		if(CollectionUtils.isNotEmpty(categoryList)) {
			categoryList.forEach(category -> {
				Map<String, Object> dataMap = Maps.newHashMap();
				dataMap.put("id", category.getId());
				dataMap.put("pId", category.getPid());
				dataMap.put("name", category.getName());
				resultList.add(dataMap);
			});
		}
		return EasyResponse.buildSuccess(resultList);
	}


}