package com.dear.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dear.bean.recordDetails;

public interface AgvMapper {
	@Insert("insert into agv_record (agvid,carid,userid,otime) values (#{agv},#{car},#{userid},#{datetime})")
	void saveRecord(@Param("agv") String agv,@Param("car") String car,@Param("userid") String userid,@Param("datetime") String datetime);
	
	@Update("update agv_status set status=1 where agvid=#{agv}")
	void u_agv_status(@Param("agv") String agv);
	
	@Select("select * from agv_record where DATEDIFF(dd, datetime, GETDATE())<31 ORDER BY datetime desc")
	List<recordDetails> getRecord();
}
