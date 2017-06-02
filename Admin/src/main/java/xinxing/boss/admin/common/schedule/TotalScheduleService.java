package xinxing.boss.admin.common.schedule;

public interface TotalScheduleService {

	/**
	 * 根据bossScheduleChange表在规定时间执行方法
	 * @throws Exception
	 */
	public void changeByTime_Job() throws Exception;
	
	/**
	 * 生成昨天的广东各个城市的统计记录
	 * 
	 * @throws Exception
	 */
	public void createYesterdayGuangDongCityData_Job() throws Exception;
	
	/**
	 * 天猫缓存 10分钟一次
	 */
	public void createTmallCache_Job() throws Exception;
}
