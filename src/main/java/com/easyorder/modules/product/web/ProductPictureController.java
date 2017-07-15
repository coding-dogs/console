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

import com.easyorder.common.beans.EasyResponse;
import com.easyorder.common.enums.EasyResponseEnums;
import com.easyorder.common.utils.BeanUtils;
import com.easyorder.modules.product.entity.Product;
import com.easyorder.modules.product.entity.ProductPicture;
import com.easyorder.modules.product.service.ProductPictureService;
import com.easyorder.modules.product.service.ProductService;
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
 * 商品图片Controller
 * @author qiudequan
 * @version 2017-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/productManager/productPicture")
public class ProductPictureController extends BaseController {

	@Autowired
	private ProductPictureService productPictureService;
	@Autowired
	private ProductService productService;

	@ModelAttribute
	public ProductPicture get(@RequestParam(required=false) String id) {
		ProductPicture entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productPictureService.get(id);
		}
		if (entity == null){
			entity = new ProductPicture();
		}
		return entity;
	}

	/**
	 * 商品图片列表页面
	 */
	@RequiresPermissions("product:productPicture:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductPicture productPicture, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductPicture> page = productPictureService.findPage(new Page<ProductPicture>(request, response), productPicture); 
		model.addAttribute("page", page);
		return "easyorder/product/productPictureList";
	}

	/**
	 * 查看，增加，编辑商品图片表单页面
	 */
	@RequiresPermissions(value={"product:productPicture:view","product:productPicture:add","product:productPicture:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductPicture productPicture, Model model) {
		model.addAttribute("productPicture", productPicture);
		return "easyorder/product/productPictureForm";
	}

	/**
	 * 保存商品图片
	 */
	@RequiresPermissions(value={"product:productPicture:add","product:productPicture:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductPicture productPicture, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productPicture)){
			return form(productPicture, model);
		}
		if(!productPicture.getIsNewRecord()){//编辑表单保存
			ProductPicture t = productPictureService.get(productPicture.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productPicture, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productPictureService.save(t);//保存
		}else{//新增表单保存
			productPictureService.save(productPicture);//保存
		}
		addMessage(redirectAttributes, "保存商品图片成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productPicture/?repage";
	}

	/**
	 * 删除商品图片
	 */
	@RequiresPermissions("product:productPicture:del")
	@RequestMapping(value = "delete")
	public String delete(ProductPicture productPicture, RedirectAttributes redirectAttributes) {
		productPictureService.delete(productPicture);
		addMessage(redirectAttributes, "删除商品图片成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productPicture/?repage";
	}

	/**
	 * 批量删除商品图片
	 */
	@RequiresPermissions("product:productPicture:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productPictureService.delete(productPictureService.get(id));
		}
		addMessage(redirectAttributes, "删除商品图片成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productPicture/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:productPicture:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductPicture productPicture, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品图片"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductPicture> page = productPictureService.findPage(new Page<ProductPicture>(request, response, -1), productPicture);
			new ExportExcel("商品图片", ProductPicture.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品图片记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productPicture/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:productPicture:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductPicture> list = ei.getDataList(ProductPicture.class);
			for (ProductPicture productPicture : list){
				try{
					productPictureService.save(productPicture);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品图片记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品图片记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品图片失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productPicture/?repage";
	}

	/**
	 * 下载导入商品图片数据模板
	 */
	@RequiresPermissions("product:productPicture:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品图片数据导入模板.xlsx";
			List<ProductPicture> list = Lists.newArrayList(); 
			new ExportExcel("商品图片数据", ProductPicture.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productPicture/?repage";
	}
	
	@RequestMapping(value = {"async/list"})
	@ResponseBody
	public EasyResponse<List<ProductPicture>> picList(ProductPicture productPicture, HttpServletRequest request, HttpServletResponse response, Model model) {
		String supplierId = UserUtils.getUser().getSupplierId();
		if(com.easyorder.common.utils.StringUtils.isEmpty(supplierId)) {
			logger.error("Did not find the supplier.[supplierId : {}]", supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_SUPPLIER);
		}
		
		String productId = productPicture.getProductId();
		if(StringUtils.isBlank(productId)) {
			logger.error("The param productId is empty.");
			return EasyResponse.buildError("商品ID为空");
		}
		
		Product product = new Product();
		product.setId(productId);
		product.setSupplierId(supplierId);
		// 检验商品有效性
		Product pro = productService.get(product);
		if(BeanUtils.isEmpty(pro)) {
			logger.error("Did not find the product.[productId : {}, supplierId: {}]", productId, supplierId);
			return EasyResponse.buildByEnum(EasyResponseEnums.NOT_FOUND_PRODUCT);
		}

		List<ProductPicture> picList = productPictureService.findList(productPicture);
		return EasyResponse.buildSuccess(picList);
		
	}

}