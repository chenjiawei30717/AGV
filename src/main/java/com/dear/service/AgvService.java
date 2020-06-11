package com.dear.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dear.bean.recordDetails;
import com.dear.mapper.AgvMapper;

@Service("agvService")
public class AgvService {
	@Resource
	private AgvMapper agvdao;

	public void saveRecord(String agv,String car, String userid, String datetime)
			throws Exception {
		agvdao.saveRecord(agv,car,userid,datetime);
	}
	public void u_agv_status(String agv)
			throws Exception {
		agvdao.u_agv_status(agv);
	}
	public List<recordDetails> getRecord()
			throws Exception {
		return agvdao.getRecord();
	}
}
