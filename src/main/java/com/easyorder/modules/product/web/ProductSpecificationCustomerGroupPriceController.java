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

import com.easyorder.modules.product.entity.ProductSpecificationCustomerGroupPrice;
import com.easyorder.modules.product.service.ProductSpecificationCustomerGroupPriceService;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/productManager/productSpecificationCustomerGroupPrice")
public class ProductSpecificationCustomerGroupPriceController extends BaseController {
	@Autowired
	private ProductSpecificationCustomerGroupPriceService productSpecificationCustomerGroupPriceService;

	@ModelAttribute
	public ProductSpecificationCustomerGroupPrice get(@RequestParam(required=false) String id) {
		ProductSpecificationCustomerGroupPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productSpecificationCustomerGroupPriceService.get(id);
		}
		if (entity == null){
			entity = new ProductSpecificationCustomerGroupPrice();
		}
		return entity;
	}

	/**
	 * ProductSpecificationCustomerGroupPrice列表页面
	 */
	@RequiresPermissions("product:productSpecificationCustomerGroupPrice:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductSpecificationCustomerGroupPrice productSpecificationCustomerGroupPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductSpecificationCustomerGroupPrice> page = productSpecificationCustomerGroupPriceService.findPage(new Page<ProductSpecificationCustomerGroupPrice>(request, response), productSpecificationCustomerGroupPrice); 
		model.addAttribute("page", page);
		return "easyorder/product/productSpecificationCustomerGroupPriceList";
	}

	/**
	 * 查看，增加，编辑ProductSpecificationCustomerGroupPrice表单页面
	 */
	@RequiresPermissions(value={"product:productSpecificationCustomerGroupPrice:view","product:productSpecificationCustomerGroupPrice:add","product:productSpecificationCustomerGroupPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductSpecificationCustomerGroupPrice productSpecificationCustomerGroupPrice, Model model) {
		model.addAttribute("productSpecificationCustomerGroupPrice", productSpecificationCustomerGroupPrice);
		return "easyorder/product/productSpecificationCustomerGroupPriceForm";
	}

	/**
	 * 保存ProductSpecificationCustomerGroupPrice
	 */
	@RequiresPermissions(value={"product:productSpecificationCustomerGroupPrice:add","product:productSpecificationCustomerGroupPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductSpecificationCustomerGroupPrice productSpecificationCustomerGroupPrice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productSpecificationCustomerGroupPrice)){
			return form(productSpecificationCustomerGroupPrice, model);
		}
		if(!productSpecificationCustomerGroupPrice.getIsNewRecord()){//编辑表单保存
			ProductSpecificationCustomerGroupPrice t = productSpecificationCustomerGroupPriceService.get(productSpecificationCustomerGroupPrice.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productSpecificationCustomerGroupPrice, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productSpecificationCustomerGroupPriceService.save(t);//保存
		}else{//新增表单保存
			productSpecificationCustomerGroupPriceService.save(productSpecificationCustomerGroupPrice);//保存
		}
		addMessage(redirectAttributes, "保存ProductSpecificationCustomerGroupPrice成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerGroupPrice/?repage";
	}

	/**
	 * 删除ProductSpecificationCustomerGroupPrice
	 */
	@RequiresPermissions("product:productSpecificationCustomerGroupPrice:del")
	@RequestMapping(value = "delete")
	public String delete(ProductSpecificationCustomerGroupPrice productSpecificationCustomerGroupPrice, RedirectAttributes redirectAttributes) {
		productSpecificationCustomerGroupPriceService.delete(productSpecificationCustomerGroupPrice);
		addMessage(redirectAttributes, "删除ProductSpecificationCustomerGroupPrice成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerGroupPrice/?repage";
	}

	/**
	 * 批量删除ProductSpecificationCustomerGroupPrice
	 */
	@RequiresPermissions("product:productSpecificationCustomerGroupPrice:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productSpecificationCustomerGroupPriceService.delete(productSpecificationCustomerGroupPriceService.get(id));
		}
		addMessage(redirectAttributes, "删除ProductSpecificationCustomerGroupPrice成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerGroupPrice/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:productSpecificationCustomerGroupPrice:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductSpecificationCustomerGroupPrice productSpecificationCustomerGroupPrice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "ProductSpecificationCustomerGroupPrice"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductSpecificationCustomerGroupPrice> page = productSpecificationCustomerGroupPriceService.findPage(new Page<ProductSpecificationCustomerGroupPrice>(request, response, -1), productSpecificationCustomerGroupPrice);
			new ExportExcel("ProductSpecificationCustomerGroupPrice", ProductSpecificationCustomerGroupPrice.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出ProductSpecificationCustomerGroupPrice记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerGroupPrice/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:productSpecificationCustomerGroupPrice:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductSpecificationCustomerGroupPrice> list = ei.getDataList(ProductSpecificationCustomerGroupPrice.class);
			for (ProductSpecificationCustomerGroupPrice productSpecificationCustomerGroupPrice : list){
				try{
					productSpecificationCustomerGroupPriceService.save(productSpecificationCustomerGroupPrice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条ProductSpecificationCustomerGroupPrice记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条ProductSpecificationCustomerGroupPrice记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入ProductSpecificationCustomerGroupPrice失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerGroupPrice/?repage";
	}

	/**
	 * 下载导入ProductSpecificationCustomerGroupPrice数据模板
	 */
	@RequiresPermissions("product:productSpecificationCustomerGroupPrice:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "ProductSpecificationCustomerGroupPrice数据导入模板.xlsx";
			List<ProductSpecificationCustomerGroupPrice> list = Lists.newArrayList(); 
			new ExportExcel("ProductSpecificationCustomerGroupPrice数据", ProductSpecificationCustomerGroupPrice.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerGroupPrice/?repage";
	}

}
