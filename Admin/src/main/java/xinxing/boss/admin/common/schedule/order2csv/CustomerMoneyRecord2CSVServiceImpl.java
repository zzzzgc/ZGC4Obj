package xinxing.boss.admin.common.schedule.order2csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.customer.cmd.CustomerMoneyRecordCmd;
import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.customer.service.CustomerMoneyRecordService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.time.DateUtils;

@Service
public class CustomerMoneyRecord2CSVServiceImpl implements CustomerMoneyRecord2CSVService {
	private static Logger log = LoggerFactory.getLogger(CustomerMoneyRecord2CSVServiceImpl.class);
	@Autowired
	private CustomerMoneyRecordService customerMoneyRecordService;

	@Autowired
	private CustomerInfoService customerInfoService;

	public void getDayCustomerMoneyRecord2CSV(Date startTime, Date endTime, String fileName) {
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
		String[] inside_fields = CustomerMoneyRecordCmd.inside_fields;
		String[] inside_hearders = CustomerMoneyRecordCmd.inside_hearders;
		String[] fields = CustomerMoneyRecordCmd.fields;
		String[] hearders = CustomerMoneyRecordCmd.hearders;
		String sql = "select count(1) cnt,customerid from boss_customer_balance_record where recordTime>='" + DateUtils.formatDateTime(startTime)
				+ "' and recordTime<='" + DateUtils.formatDateTime(endTime) + "' group by customerid";
		List<Map<String, Object>> list = customerMoneyRecordService.query(sql);

		String inside_fileUrl = parthArg + "0" + "_CustomerMoneyRecord_" + fileName + ".csv";
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
			Map<Integer, CustomerInfo> customerInfoMap = (Map<Integer, CustomerInfo>) attribute.get("customerInputMap");

			for (int i = 1; i <= totalPage; i++) {
				String fileUrl = parthArg + cid + "_CustomerMoneyRecord_" + fileName + ".csv";
				if (i == 1) {
					if (new File(fileUrl).isFile()) {
						new File(fileUrl).delete();
					}
					String c = StringUtils.join(hearders, ",") + "\r\n";
					exportCSV(fileUrl, c);
				}
				Page<CustomerMoneyRecord> page = new Page<>(i, pageSize, "id", "desc");
				List<PropertyFilter> filters = new ArrayList<>();
				filters.add(new PropertyFilter("EQI_customerId", cid + ""));
				filters.add(new PropertyFilter("GED_recordTime", DateUtils.formatDateTime(startTime)));
				filters.add(new PropertyFilter("LED_recordTime", DateUtils.formatDateTime(endTime)));
				Page<CustomerMoneyRecord> infos = customerMoneyRecordService.search(page, filters);
				// 生成txt

				List<CustomerMoneyRecord> linfo = infos.getResult();
				log.info("cid:"+cid+",linfo.size()"+linfo.size());
				for (CustomerMoneyRecord customerMoneyRecord : linfo) {
					Map<String, Object> beanToMap = ExcelUtils.beanToMap(customerMoneyRecord);
					CustomerInfo customerInfo = customerInfoMap.get(customerMoneyRecord.getCustomerId());
					if (customerInfo != null) {
						beanToMap.put("customerName", customerInfo.getCustomerName());
					}
					ExcelUtils.showValue(beanToMap, "costType", "", "人工注资", "系统注资", "消费", "失败返还", "人工扣款");
					beanToMap.put("beforeMoney", customerMoneyRecord.getFundBalance().subtract(customerMoneyRecord.getCost()));
					// -------------------------------------------
					StringBuilder inside_sb = new StringBuilder(200);
					for (int j = 0; j < inside_fields.length; j++) {
						inside_sb.append(beanToMap.get(inside_fields[j])).append(",");
					}
					exportCSV(inside_fileUrl, inside_sb.toString() + "\r\n");
					StringBuilder sb = new StringBuilder(200);
					for (int j = 0; j < fields.length; j++) {
						sb.append(beanToMap.get(fields[j])).append(",");
					}
					exportCSV(fileUrl, sb.toString() + "\r\n");
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

}
