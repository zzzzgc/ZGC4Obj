package xinxing.boss.admin.common.schedule;


public interface UploadCSV4DayScheduleService {

	/**
	 * 每月定时上传CSV
	 * @throws Exception 
	 */
	public void doScheduleJob() throws Exception;
}
