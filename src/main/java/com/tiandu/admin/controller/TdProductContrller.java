package com.tiandu.admin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiandu.common.controller.BaseController;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.product.entity.TdBrand;
import com.tiandu.product.entity.TdProduct;
import com.tiandu.product.entity.TdProductAttachment;
import com.tiandu.product.entity.TdProductAttribute;
import com.tiandu.product.entity.TdProductAttributeOption;
import com.tiandu.product.entity.TdProductDescription;
import com.tiandu.product.entity.TdProductSku;
import com.tiandu.product.entity.TdProductStat;
import com.tiandu.product.entity.TdProductType;
import com.tiandu.product.entity.TdProductTypeAttribute;
import com.tiandu.product.search.TdBrandSearchCriteria;
import com.tiandu.product.search.TdProductAttributeOptionCriteria;
import com.tiandu.product.search.TdProductCriteria;
import com.tiandu.product.search.TdProductDescriptionCriteria;
import com.tiandu.product.search.TdProductTypeCriteria;
import com.tiandu.product.service.TdBrandService;
import com.tiandu.product.service.TdProductAttachmentService;
import com.tiandu.product.service.TdProductAttributeOptionService;
import com.tiandu.product.service.TdProductAttributeService;
import com.tiandu.product.service.TdProductDescriptionService;
import com.tiandu.product.service.TdProductService;
import com.tiandu.product.service.TdProductSkuService;
import com.tiandu.product.service.TdProductStatService;
import com.tiandu.product.service.TdProductTypeAttributeService;
import com.tiandu.product.service.TdProductTypeService;

@Controller
@RequestMapping("/admin/product")
public class TdProductContrller extends BaseController{
	
	@Autowired
	private TdProductService tdProductService;
	
	@Autowired
	TdProductAttributeOptionService tdProductAttributeOptionService; 
	
	@Autowired
	private TdProductTypeService tdProductTypeService;
	
	@Autowired
	private TdProductAttachmentService tdProductAttachmentService;
	
	@Autowired
	private TdProductDescriptionService tdProductDescriptionService; 
	
	@Autowired
	TdProductAttributeService tdProductAttributeService; 
	
	@Autowired
	TdProductTypeAttributeService tdProductTypeAttributeService; 
	
	@Autowired
	private TdProductStatService tdProductStatService;
	
	@Autowired
	TdProductSkuService tdProductSkuService;
	
	@Autowired
	private TdBrandService tdBrandService;
	
	@RequestMapping("/list")
	public String list(TdProductCriteria sc, HttpServletRequest req, ModelMap map){
		map.addAttribute("sc", sc);
		return "/admin/product/list";
	}

	@RequestMapping("/search")
	public String search(TdProductCriteria sc,HttpServletRequest req,ModelMap map)
	{
		sc.setIsNormalProduct(true);
		map.addAttribute("productList", tdProductService.findBySearchCriteria(sc));
		map.addAttribute("sc", sc);
		
		return "/admin/product/listbody";
	}
	
	
	@RequestMapping("/edit")
	public String edit(Integer id,HttpServletRequest req,ModelMap map) throws Exception
	{
		TdProductTypeCriteria tsc = new TdProductTypeCriteria();
		tsc.setStatus((byte) 1);
		List<TdProductType> productTypeList = tdProductTypeService.findAll(tsc);
		// 初始化对应的规格
		initTypeWithSpecifiaction(productTypeList);
		map.addAttribute("productTypeList", productTypeList);
		if(null != id && id != 0)
		{
			// 商品主要信息
			TdProduct product = tdProductService.findOne(id);
			map.addAttribute("tdProduct", product);
			
			// 商品图片
			map.addAttribute("imgList", tdProductAttachmentService.findByProductId(id));
			
			TdProductDescriptionCriteria sc = new TdProductDescriptionCriteria();
			sc.setProductId(id);
			sc.setFlag(false);
			// 图文详情
			sc.setType(1);
			map.addAttribute("detail", tdProductDescriptionService.findByProductId(sc));
			// 包装配送
			sc.setType(2);
			map.addAttribute("packDetail", tdProductDescriptionService.findByProductId(sc));
			// 售后
			sc.setType(3);
			map.addAttribute("afterSale", tdProductDescriptionService.findByProductId(sc));
			
			map.addAttribute("productStat", tdProductStatService.findOne(id));
			// 商品规格
			TdProductType productType =  tdProductTypeService.findOne(product.getTypeId());
			List<TdProductTypeAttribute> typeAttributeList = tdProductTypeAttributeService.findByTypeId(productType.getId());
			List<TdProductAttribute> attributeList = new ArrayList<TdProductAttribute>();
			for(TdProductTypeAttribute ta : typeAttributeList){
				TdProductAttribute attribute = tdProductAttributeService.findOne(ta.getAttriId());
				attributeList.add(attribute);
			}
			// 规格数量
			map.addAttribute("specifiactionNum", attributeList.size());
			// 设置规格值
			for(TdProductAttribute pa : attributeList){
				TdProductAttributeOptionCriteria aosc = new TdProductAttributeOptionCriteria();
				aosc.setFlag(false);
				aosc.setAttriId(pa.getAttriId());
				List<TdProductAttributeOption> aoList = tdProductAttributeOptionService.findBySearchCriteria(aosc);
				pa.setTdProductAttributeOptionList(aoList);
			}
			map.addAttribute("attributeList", attributeList);
			// 商品对应的货品
			List<TdProductSku> productSkuList = tdProductSkuService.findByProductId(id);
			// 商品key=value字符串
			String keyValueStr = "";
			for(TdProductSku ps : productSkuList){
				String jsonStr = ps.getSpecifications(); // 数据格式{"颜色":"白色", "尺码":"39"};
				JSONObject json=new JSONObject(jsonStr);
				String[] keys = json.getNames(json);
				for(String key : keys){
					keyValueStr += key + "=" + json.getString(key) + ",";
				}
			}
			map.addAttribute("keyValueStr", keyValueStr);
			// 货品表Json数据
			JSONObject tableJsonData = this.getJsonFromSku(attributeList, productSkuList, map);
			map.addAttribute("tableJsonData", tableJsonData);
		}
		
		//品牌数据
		TdBrandSearchCriteria bsc = new TdBrandSearchCriteria();
		bsc.setFlag(false);
		List<TdBrand> brandList = tdBrandService.findBySearchCriteria(bsc);
		map.addAttribute("brandList", brandList);
		
		return "/admin/product/productform";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> save(TdProduct tdProduct, String tableData, Integer[] attId,
					String detail, String  packDetail,
					String afterSale,
					HttpServletRequest req,ModelMap map) throws Exception
	{
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 0);
		
		if(null != tdProduct)
		{
			if(null == tdProduct.getCreateTime())
			{
				tdProduct.setCreateTime(new Date());
			}
			TdUser user = getCurrentUser();
			if(null != user)
			{
//				tdProduct.setBrandId(0);
				tdProduct.setDefaultSkuId(0);
				tdProduct.setSpecification(true);
				tdProduct.setUpdateBy(user.getUid());
				tdProduct.setUid(user.getUid());
			}
			tdProduct.setUpdateTime(new Date());
			
			Integer typeId = tdProduct.getTypeId();
			Integer attributeNum = null;
			if(typeId != null){
				List<TdProductTypeAttribute> taList = tdProductTypeAttributeService.findByTypeId(typeId);
				attributeNum = taList.size();
			}
			boolean isUpdate = false;
			if(tdProduct.getId() != null){
				isUpdate = true;
			}
			if(isUpdate){
				tdProductSkuService.deleteByProductId(tdProduct.getId());
			}
			tdProductService.save(tdProduct);
			
			Integer defautsku = null;
			
			//--------保存货品表------------
			if(tableData != null && !tableData.equals("") && attributeNum != null){
				JSONObject trJson = new JSONObject(tableData);
				
				String[] keys = trJson.getNames(trJson);
				JSONArray contentJsonArray = trJson.getJSONArray("tableContent");
				String tableHeadDataStr =  trJson.get("tableHead").toString();
				tableHeadDataStr = tableHeadDataStr.replace("[", "");
				tableHeadDataStr = tableHeadDataStr.replace("]", "");
				String[] tableHeadDataArray = tableHeadDataStr.split(",");
				for(int i = 0; i < tableHeadDataArray.length; i ++){
					tableHeadDataArray[i] = tableHeadDataArray[i].replace("\"", "");	
				}
				
				for(int i = 0; i < contentJsonArray.length(); i ++){
					JSONObject jo = contentJsonArray.getJSONObject(i);
					String trDataStr = jo.get("trData").toString();
					trDataStr = trDataStr.replace("[", "");
					trDataStr = trDataStr.replace("]", "");
					String[] trDataArray = trDataStr.split(",");
					for(int ii = 0; ii < trDataArray.length; ii ++){
						trDataArray[ii] = trDataArray[ii].replace("\"", "");	
					}
					String specificationStr = "";
					JSONObject jsonObject = new JSONObject();
					for(int j = 0; j < attributeNum; j ++){
						jsonObject.put(tableHeadDataArray[j], trDataArray[j]);
					}
					TdProductSku sku = new TdProductSku();
					sku.setSpecifications(jsonObject.toString());
					sku.setSkuCode(trDataArray[attributeNum]);
					Double supplierPrice = Double.parseDouble(trDataArray[attributeNum+1]);
					sku.setSupplierPrice(BigDecimal.valueOf(supplierPrice));
					Double SalesPrice = Double.parseDouble(trDataArray[attributeNum+2]);
					sku.setSalesPrice(BigDecimal.valueOf(SalesPrice));
					Double marketPrice = Double.parseDouble(trDataArray[attributeNum+3]);
					sku.setMarketPrice(BigDecimal.valueOf(marketPrice));
					Double highPrice = Double.parseDouble(trDataArray[attributeNum+4]);
					sku.setHighPrice(BigDecimal.valueOf(highPrice));
					Double lowPrice = Double.parseDouble(trDataArray[attributeNum+5]);
					sku.setLowPrice(BigDecimal.valueOf(lowPrice));
					sku.setStock(Integer.parseInt(trDataArray[attributeNum+6]));
					sku.setProductId(tdProduct.getId());
					sku.setUpdateTime(new Date());
					sku.setUpdateBy(this.getCurrentUser().getUid());
					sku.setStatus(Byte.valueOf("1"));
					tdProductSkuService.save(sku);
					
					if(defautsku == null){defautsku = sku.getId();} 
				}
				
			}
			//--------------------
			
			// 默认货品ID
			tdProduct.setDefaultSkuId(defautsku);
			
			tdProductService.save(tdProduct);
			
			// 修改展示图片
			if(null != attId)
			{
				for (Integer attaId : attId) {
					TdProductAttachment attachment = tdProductAttachmentService.findOne(attaId);
					attachment.setProductId(tdProduct.getId());
					tdProductAttachmentService.save(attachment);
				}
			}
			// 保存其他信息
			TdProductDescriptionCriteria sc = new TdProductDescriptionCriteria();
			sc.setProductId(tdProduct.getId());
			sc.setFlag(false);
			
			// 图文详情
			sc.setType(1);
			TdProductDescription imgDetail = tdProductDescriptionService.findByProductId(sc);
			if(null == imgDetail)
			{
				imgDetail = new TdProductDescription();
				imgDetail.setProductId(tdProduct.getId());
				imgDetail.setType((byte) 1);
			}
			imgDetail.setDescription(detail);
			imgDetail.setUpdateBy(user.getUid());
			imgDetail.setUpdateTime(new Date());
			
			// 包装配送
			sc.setType(2);
			TdProductDescription packdetail = tdProductDescriptionService.findByProductId(sc);
			if(null == packdetail)
			{
				packdetail = new TdProductDescription();
				packdetail.setProductId(tdProduct.getId());
				packdetail.setType((byte) 2);
			}
			packdetail.setDescription(packDetail);
			packdetail.setUpdateBy(user.getUid());
			packdetail.setUpdateTime(new Date());
			
			// 售后说明
			sc.setType(3);
			TdProductDescription afterSaleDetail = tdProductDescriptionService.findByProductId(sc);
			
			if(null == afterSaleDetail)
			{
				afterSaleDetail = new TdProductDescription();
				afterSaleDetail.setProductId(tdProduct.getId());
				afterSaleDetail.setType((byte) 3);
			}
			afterSaleDetail.setDescription(afterSale);
			afterSaleDetail.setUpdateBy(user.getUid());
			afterSaleDetail.setUpdateTime(new Date());
			
			tdProductDescriptionService.save(imgDetail);
			tdProductDescriptionService.save(packdetail);
			tdProductDescriptionService.save(afterSaleDetail);
			
			// 新增商品时添加统计表
			TdProductStat stat = tdProductStatService.findOne(tdProduct.getId());
			if(null == stat)
			{
				stat = new TdProductStat();
				stat.setBuyCount(0);
				stat.setBuyTimes(0);
				stat.setNegativeRate(0);
				stat.setNeutralRate(0);
				stat.setPositiveRate(0);
				stat.setProductId(tdProduct.getId());
				stat.setReviewCount(0);
				stat.setReviewScore(new BigDecimal(0));
				stat.setShowreviewCount(0);
				stat.setViewCount(0);
			}
			tdProductStatService.Insert(stat);
			
			res.put("code", 1);
		}
		
		
		return res;
	}
	
	/**
	 * 商品删除
	 * @author Max
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(Integer id,HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		
		if(null != id)
		{
			// 删除图片
			tdProductAttachmentService.deleteByProductId(id);
			
			// 删除图文、包装、售后信息
			tdProductDescriptionService.deleteByProductId(id);
			
			// 删除统计表
			tdProductStatService.deleteByPrimaryKey(id);
			
			// 删除商品
			tdProductService.deleteByPrimaryKey(id);
			res.put("code", 1);
		}
		
		return res;
	}
	
	/**
	 * @author Max
	 * 图片移除
	 * 
	 */
	@RequestMapping(value="/deleteImg",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> imgDelete(Integer attId,HttpServletRequest req)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		
		if(null != attId)
		{
			tdProductAttachmentService.deleteByPrimaryKey(attId);
			res.put("code", 1);
		}
		return res;
	}
	
	
	
	@ModelAttribute
    public void getModel(@RequestParam(value = "productId", required = false) Integer productId,
                        Model model) {
        if (null != productId) {
            model.addAttribute("tdProduct", tdProductService.findOne(productId));
        }
    }
	// 产生表头及表体json数据
	public JSONObject getJsonFromSku(List<TdProductAttribute> attributeList, List<TdProductSku> skuList, ModelMap map)throws Exception{
		if(skuList == null || attributeList == null){
			return null;
		}
		JSONObject tableJson = new JSONObject();
		// 表头
		List<String> columnList = new ArrayList<>();
		for(TdProductAttribute ta : attributeList){
			columnList.add(ta.getName());
		}
		int specSize =  columnList.size();
		
		// 设置到前台确定规格顺序
		String speOrder = "";
		for(int i = 0; i < columnList.size(); i ++){
			speOrder += columnList.get(i);
			if(i < columnList.size() - 1){
				speOrder +=  "_";
			}
		}
		map.addAttribute("speOrder", speOrder);
		
		columnList.add("货品号");
		columnList.add("供应商价");
		columnList.add("建议零售价");
		columnList.add("市场价");
		columnList.add("最高价");
		columnList.add("最低价");
		columnList.add("库存");
		String[] columnArray = this.listToArray(columnList);
		tableJson.put("tableHead", columnArray);
		
		// 表体数据
		JSONArray ja = new JSONArray();
		for(TdProductSku sku : skuList){
			String trStr = sku.getSpecifications(); // 数据格式{"颜色":"白色", "尺码":"39"};
			JSONObject trJson = new JSONObject(trStr);
			List<String> trList = new ArrayList<>();
			String trId = "";
			for(int i = 0; i < specSize; i ++){
				String spes = "";
				try{
					spes = trJson.getString(columnArray[i]);
				}catch(JSONException e){
					System.err.println("编辑商品前添加了新的规格");
				}
				trList.add(spes);
				trId += trJson.getString(columnArray[i]);
				if(i < specSize - 1){
					trId += "_";
				}
			}
			
			JSONObject trDataJson = new JSONObject();
			trDataJson.put("trId", trId);
			
			trList.add(sku.getSkuCode());
			trList.add(sku.getSupplierPrice().toString());
			trList.add(sku.getSalesPrice().toString());
			trList.add(sku.getMarketPrice().toString());
			trList.add(sku.getHighPrice().toString());
			trList.add(sku.getLowPrice().toString());
			trList.add(sku.getStock().toString());
			String[] trArray = this.listToArray(trList);
			trDataJson.put("trData", trArray);
			
			ja.put(trDataJson);
		}
		
		tableJson.put("tableContent", ja);
		return tableJson;
	}
	
	// 产生表头json数据
	public  TableHeadData getTableHeadJsonFromSku(List<TdProductAttribute> attributeList) throws Exception{
		if(attributeList == null){
			return null;
		}
		TableHeadData tableHeadData = new TableHeadData();
		JSONObject tableJson = new JSONObject();
		// 表头
		List<String> columnList = new ArrayList<>();
		for(TdProductAttribute ta : attributeList){
			columnList.add(ta.getName());
		}
		int specSize =  columnList.size();
		tableHeadData.setSpecSize(specSize);
		// 设置到前台确定规格顺序
		String speOrder = "";
		for(int i = 0; i < columnList.size(); i ++){
			speOrder += columnList.get(i);
			if(i < columnList.size() - 1){
				speOrder +=  "_";
			}
		}
		tableHeadData.setSpecOrder(speOrder);
		
		columnList.add("货品号");
		columnList.add("供应商价");
		columnList.add("建议零售价");
		columnList.add("市场价");
		columnList.add("最高价");
		columnList.add("最低价");
		columnList.add("库存");
		String[] columnArray = this.listToArray(columnList);
		tableJson.put("tableHead", columnArray);
		tableHeadData.setHeadJson(tableJson);
		return tableHeadData;
	}
	
	public String[] listToArray(List<String> strList){
		String[] strArray = strList.toArray(new String[strList.size()]);
		return strArray;
	}
	
	public void initTypeWithSpecifiaction(List<TdProductType> productTypeList){
		if(productTypeList != null && productTypeList.size() > 0){
			for(TdProductType pt : productTypeList){
				List<TdProductTypeAttribute> typeAttributeList = tdProductTypeAttributeService.findByTypeId(pt.getId());
				List<TdProductAttribute> attributeList = new ArrayList<TdProductAttribute>();
				for(TdProductTypeAttribute ta : typeAttributeList){
					TdProductAttribute attribute = tdProductAttributeService.findOne(ta.getAttriId());
					attributeList.add(attribute);
				}
				// 规格数量
				pt.setSpecifiactionNum(attributeList.size());
				// 设置规格值
				for(TdProductAttribute pa : attributeList){
					TdProductAttributeOptionCriteria aosc = new TdProductAttributeOptionCriteria();
					aosc.setFlag(false);
					aosc.setAttriId(pa.getAttriId());
					List<TdProductAttributeOption> aoList = tdProductAttributeOptionService.findBySearchCriteria(aosc);
					pa.setTdProductAttributeOptionList(aoList);
				}
				pt.setTdProductAttributeList(attributeList);
				if(pt.getSubList() != null){
					initTypeWithSpecifiaction(pt.getSubList());
				}
			}
		}
	}
	
	@RequestMapping("/getHpggPage")
	public String getHpggPage(Integer typeId,HttpServletRequest req,ModelMap map){
		TdProductType productType = tdProductTypeService.findOne(typeId);
		List<TdProductType> productTypeList = new ArrayList<TdProductType>();
		productTypeList.add(productType);
		initTypeWithSpecifiaction(productTypeList);
		map.addAttribute("attrList", productTypeList.get(0).getTdProductAttributeList());
		return "/admin/product/type/hpgg";
	}
	
	@RequestMapping(value="/getTableHead",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> getTableHead(Integer typeId, HttpServletRequest req)throws Exception
	{
		Map<String, String> res = new HashMap<>();
		TdProductType productType = tdProductTypeService.findOne(typeId);
		List<TdProductType> productTypeList = new ArrayList<TdProductType>();
		productTypeList.add(productType);
		initTypeWithSpecifiaction(productTypeList);
		List<TdProductAttribute> attributeList = productTypeList.get(0).getTdProductAttributeList();
		TableHeadData thd = getTableHeadJsonFromSku(attributeList);
		res.put("specSize", thd.getSpecSize().toString());
		res.put("specOrder", thd.getSpecOrder());
		res.put("headJson", thd.getHeadJson().toString());
		return res;
	}
	/**
	 * 批量操作商品
	 * @param type 操作类型 1-上架，2-下架，3-热门推荐，4-取消热门推荐，5-新品推荐，6-取消热门推荐，7-精品推荐，8-取消精品推荐，9-分类推荐，10-取消分类推荐
	 * @param productIds 商品id，多个逗号隔开
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/batchoperproducts",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> batchOperProducts(Integer type, String productIds, HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, String> res = new HashMap<>();
		if(null!=type && type>0 && type<11 && StringUtils.isNotEmpty(productIds)){
			int count = tdProductService.batchOperProducts(type,productIds);
			if(count>0){
				res.put("code", "1");
				return res;
			}else{
				res.put("code", "0");
				res.put("msg", "操作失败：更新失败！");
				return res;
			}
			
		}else{
			res.put("code", "0");
			res.put("msg", "操作失败：操作非法！");
			return res;
		}
	}
}

class TableHeadData{
	JSONObject headJson;
	String specOrder;
	Integer specSize;
	
	public JSONObject getHeadJson() {
		return headJson;
	}
	public void setHeadJson(JSONObject headJson) {
		this.headJson = headJson;
	}
	public String getSpecOrder() {
		return specOrder;
	}
	public void setSpecOrder(String specOrder) {
		this.specOrder = specOrder;
	}
	public Integer getSpecSize() {
		return specSize;
	}
	public void setSpecSize(Integer specSize) {
		this.specSize = specSize;
	}
	
}
