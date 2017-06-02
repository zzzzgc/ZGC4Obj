package xinxing.boss.admin.common.schedule.order2csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause.Entry;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.order.cmd.OrderInfoCmd;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.cmd.BossCommonCmd;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.schedule.order2csv.util.OrderInfo2CSVBuilder;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.time.DateUtils;

@Service
public class Order2CSVServiceImpl implements Order2CSVService {
	private static Logger log = LoggerFactory.getLogger(Order2CSVServiceImpl.class);
	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private CustomerInfoService customerInfoService;

	@Override
	public void getCustomerDayOrder2CSV(Date startTime, Date endTime, String fileName) {
		// 1、获取采购商数据
		// 2、获取产品信息
		// 3、获取订单数量
		// 4、分页导出
		// 5、每页2W条数据
		String parthArg = BaseUtil.getWEBINFUrl();
		parthArg = parthArg + "/" + "uploadCSV" + "/";// linux
		File file = new File(parthArg);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String[] inside_fields = OrderInfoCmd.inside_fields;
		String[] inside_hearders = OrderInfoCmd.inside_hearders;
		String[] fields = OrderInfoCmd.fields;
		String[] hearders = OrderInfoCmd.hearders;
		String sql = "select count(1) cnt,customerid from boss_order where status=3 and successTime>='" + DateUtils.formatDateTime(startTime)
				+ "' and successTime<='" + DateUtils.formatDateTime(endTime) + "' group by customerid";
		List<Map<String, Object>> list = orderInfoService.query(sql);

		String inside_fileUrl = parthArg + "0" + "_OrderInfo_" + fileName + ".csv";
		if (new File(inside_fileUrl).isFile()) {
			new File(inside_fileUrl).delete();
		}
		String inside_c = StringUtils.join(inside_hearders, ",") + "\r\n";
		exportCSV(inside_fileUrl, inside_c);
		log.info(inside_fileUrl);
		for (Map<String, Object> rs : list) {
			Object cnt = rs.get("cnt");
			Object cid = rs.get("customerid");
			int totalCnt = Integer.parseInt(cnt + "");
			int pageSize = 60000;
			// 总分页数
			int totalPage = totalCnt / pageSize;
			if (totalPage * pageSize < totalCnt) {
				totalPage = totalPage + 1;
			}
			Map<String, Object> attribute = ParameterListener.sysParamMap;
			@SuppressWarnings("unchecked")
			Map<Integer, ProductCategoryInfo> productCategoryInfoMap = (Map<Integer, ProductCategoryInfo>) attribute.get("productCategoryInputMap");
			@SuppressWarnings("unchecked")
			Map<Integer, CustomerInfo> customerInfoMap = (Map<Integer, CustomerInfo>) attribute.get("customerInputMap");
			@SuppressWarnings("unchecked")
			Map<Integer, ProviderInfo> providerInfoMap = (Map<Integer, ProviderInfo>) attribute.get("providerInputMap");

			for (int i = 1; i <= totalPage; i++) {
				String fileUrl = parthArg + cid + "_OrderInfo_" + fileName + ".csv";
				if (i == 1) {
					if (new File(fileUrl).isFile()) {
						new File(fileUrl).delete();
					}
					String c = StringUtils.join(hearders, ",") + "\r\n";
					exportCSV(fileUrl, c);
				}
				Page<OrderInfo> page = new Page<>(i, pageSize, "id", "desc");
				List<PropertyFilter> filters = new ArrayList<>();
				filters.add(new PropertyFilter("EQI_customerId", cid + ""));
				filters.add(new PropertyFilter("EQI_status", 3 + ""));
				filters.add(new PropertyFilter("GED_successTime", DateUtils.formatDateTime(startTime)));
				filters.add(new PropertyFilter("LED_successTime", DateUtils.formatDateTime(endTime)));
				Long sta = System.currentTimeMillis();
				Page<OrderInfo> infos = orderInfoService.search(page, filters);
				BossCommonCmd.searchUseTime += sta - System.currentTimeMillis();
				// 生成txt
				List<OrderInfo> linfo = infos.getResult();
				log.info("cid:" + cid + ",linfo.size()" + linfo.size());
				FileWriter fwOur =null;
				BufferedWriter bwOur = null;
				FileWriter fwCus = null;
				BufferedWriter bwCus = null;
				try {
					
				
				fwOur = new FileWriter(inside_fileUrl, true);
				bwOur = new BufferedWriter(fwOur);
				
				fwCus = new FileWriter(fileUrl, true);
				bwCus = new BufferedWriter(fwCus);
				
				Long ordSta = System.currentTimeMillis();
				for (OrderInfo orderInfo : linfo) {
					if (productCategoryInfoMap.containsKey(orderInfo.getCategoryId())) {
						Map<String, Object> beanToMap = ExcelUtils.beanToMap(orderInfo);
						ProductCategoryInfo productCategoryInfo = productCategoryInfoMap.get(orderInfo.getCategoryId());
						beanToMap.put("handleTime", (orderInfo.getStatus() == 3 ? orderInfo.getSuccessTime() : orderInfo.getFailTime()));
						// 耗时
						Date time = (orderInfo.getStatus() == 3 ? orderInfo.getSuccessTime() : orderInfo.getFailTime());
						if (time != null && orderInfo.getReceiveTime() != null) {
							beanToMap.put("useTime", (int) ((time.getTime() - orderInfo.getReceiveTime().getTime()) / 1000));
						} else
							beanToMap.put("useTime", "");
						Integer productUnit = productCategoryInfo.getProductUnit();
						String productGuige = "";
						if (productUnit == 1) {
							productGuige = "M";
						} else if (productUnit == 2) {
							productGuige = "G";
						} else if (productUnit == 3) {
							productGuige = "元";
						}
						beanToMap.put("productScale", productCategoryInfo.getProductNum() + productGuige + "");
						if (orderInfo.getCost() != null && orderInfo.getPrice() != null && orderInfo.getStatus() == 3) {
							beanToMap.put("earn", orderInfo.getPrice().subtract(orderInfo.getCost()));
						} else
							beanToMap.put("earn", null);
						if (productCategoryInfoMap.get(orderInfo.getCategoryId()) != null) {
							beanToMap.put("categoryName", productCategoryInfoMap.get(orderInfo.getCategoryId()).getCategoryName());
						}
						if (customerInfoMap.get(orderInfo.getCustomerId()) != null) {
							beanToMap.put("customerName", customerInfoMap.get(orderInfo.getCustomerId()).getCustomerName());
						}
						if (providerInfoMap.get(orderInfo.getSuccessId()) != null) {
							beanToMap.put("providerName", providerInfoMap.get(orderInfo.getSuccessId()).getProviderName());
						}
						ExcelUtils.showValue(beanToMap, "status", "", "新增", "充值中", "充值成功", "充值失败", "等待确认", "需手工处理");
						// 订单状态 1新增2充值中3充值成功4充值失败5等待确认6需手工处理',
						sta = System.currentTimeMillis();
						BossCommonCmd.searchUseTime += sta - System.currentTimeMillis();

						StringBuilder inside_sb = new StringBuilder(200);
						for (int j = 0; j < inside_fields.length; j++) {
							inside_sb.append(beanToMap.get(inside_fields[j])).append(",");
						}
						//exportCSV(inside_fileUrl, inside_sb.toString() + "\r\n");
						bwOur.write(inside_sb.toString() + "\r\n");
						
						StringBuilder sb = new StringBuilder(200);
						for (int j = 0; j < fields.length; j++) {
							sb.append(beanToMap.get(fields[j])).append(",");
						}
						bwCus.write(sb.toString() + "\r\n");
						//exportCSV(fileUrl, sb.toString() + "\r\n");

						BossCommonCmd.csvUseTime += sta - System.currentTimeMillis();
					}
				}
				bwOur.flush();
				bwCus.flush();
				BossCommonCmd.becomeOrderUseTime += ordSta - System.currentTimeMillis();
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					try {
						bwOur.close();
						bwCus.close();
						fwOur.close();
						fwCus.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				

			}
		}

	}

	public void exportCSV(String fileUrl, String c) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileUrl, true);
			fw.write(c);
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void getCustomerMonthOrder2CSV() {
	}

}
