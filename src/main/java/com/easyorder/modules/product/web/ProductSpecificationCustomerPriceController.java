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

import com.easyorder.modules.product.entity.ProductSpecificationCustomerPrice;
import com.easyorder.modules.product.service.ProductSpecificationCustomerPriceService;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/productManager/productSpecificationCustomerPrice")
public class ProductSpecificationCustomerPriceController extends BaseController {
	@Autowired
	private ProductSpecificationCustomerPriceService productSpecificationCustomerPriceService;

	@ModelAttribute
	public ProductSpecificationCustomerPrice get(@RequestParam(required=false) String id) {
		ProductSpecificationCustomerPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productSpecificationCustomerPriceService.get(id);
		}
		if (entity == null){
			entity = new ProductSpecificationCustomerPrice();
		}
		return entity;
	}

	/**
	 * 商品规格客户价列表页面
	 */
	@RequiresPermissions("product:product:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProductSpecificationCustomerPrice productSpecificationCustomerPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductSpecificationCustomerPrice> page = productSpecificationCustomerPriceService.findPage(new Page<ProductSpecificationCustomerPrice>(request, response), productSpecificationCustomerPrice); 
		model.addAttribute("page", page);
		return "easyorder/product/productSpecificationCustomerPriceList";
	}

	/**
	 * 查看，增加，编辑商品规格客户价表单页面
	 */
	@RequiresPermissions(value={"product:product:view","product:product:add","product:product:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductSpecificationCustomerPrice productSpecificationCustomerPrice, Model model) {
		model.addAttribute("productSpecificationCustomerPrice", productSpecificationCustomerPrice);
		return "easyorder/product/productSpecificationCustomerPriceForm";
	}

	/**
	 * 保存商品规格客户价
	 */
	@RequiresPermissions(value={"product:product:add","product:product:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProductSpecificationCustomerPrice productSpecificationCustomerPrice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, productSpecificationCustomerPrice)){
			return form(productSpecificationCustomerPrice, model);
		}
		if(!productSpecificationCustomerPrice.getIsNewRecord()){//编辑表单保存
			ProductSpecificationCustomerPrice t = productSpecificationCustomerPriceService.get(productSpecificationCustomerPrice.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(productSpecificationCustomerPrice, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			productSpecificationCustomerPriceService.save(t);//保存
		}else{//新增表单保存
			productSpecificationCustomerPriceService.save(productSpecificationCustomerPrice);//保存
		}
		addMessage(redirectAttributes, "保存商品规格客户价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerPrice/?repage";
	}

	/**
	 * 删除商品规格客户价
	 */
	@RequiresPermissions("product:product:del")
	@RequestMapping(value = "delete")
	public String delete(ProductSpecificationCustomerPrice productSpecificationCustomerPrice, RedirectAttributes redirectAttributes) {
		productSpecificationCustomerPriceService.delete(productSpecificationCustomerPrice);
		addMessage(redirectAttributes, "删除商品规格客户价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerPrice/?repage";
	}

	/**
	 * 批量删除商品规格客户价
	 */
	@RequiresPermissions("product:product:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productSpecificationCustomerPriceService.delete(productSpecificationCustomerPriceService.get(id));
		}
		addMessage(redirectAttributes, "删除商品规格客户价成功");
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerPrice/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("product:product:export")
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(ProductSpecificationCustomerPrice productSpecificationCustomerPrice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品规格客户价"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ProductSpecificationCustomerPrice> page = productSpecificationCustomerPriceService.findPage(new Page<ProductSpecificationCustomerPrice>(request, response, -1), productSpecificationCustomerPrice);
			new ExportExcel("商品规格客户价", ProductSpecificationCustomerPrice.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商品规格客户价记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerPrice/?repage";
	}

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("product:product:import")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductSpecificationCustomerPrice> list = ei.getDataList(ProductSpecificationCustomerPrice.class);
			for (ProductSpecificationCustomerPrice productSpecificationCustomerPrice : list){
				try{
					productSpecificationCustomerPriceService.save(productSpecificationCustomerPrice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品规格客户价记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品规格客户价记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品规格客户价失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/productManager/productSpecificationCustomerPrice/?repage";
	}

}
