package com.example.demo.controller;



import com.example.demo.util.HttpUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TransRechargeClearJob {

	private static final Logger logger = LoggerFactory.getLogger(TransRechargeClearJob.class);



	protected void executeInternal() throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("【清结算文件】交易明细文件定时任务开始");
		String result = HttpUtil.httpSend("\"flagType\":\"00\"","http://localhost:9055/ams/clearFile/index)");
		logger.info("【清结算文件】交易明细文件定时任务结果：" + result);
	}

	public static void main(String[] args) throws JobExecutionException{
		TransRechargeClearJob s =new TransRechargeClearJob();
		s.executeInternal();
	}
}
