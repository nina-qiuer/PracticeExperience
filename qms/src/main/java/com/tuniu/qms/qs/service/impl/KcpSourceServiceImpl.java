package com.tuniu.qms.qs.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tuniu.qms.qs.dao.KcpSourceMapper;
import com.tuniu.qms.qs.dto.KcpSourceDto;
import com.tuniu.qms.qs.model.KcpSource;
import com.tuniu.qms.qs.service.KcpSourceService;

@Service
public class KcpSourceServiceImpl implements KcpSourceService {

	
	@Autowired
	private KcpSourceMapper mapper;
	@Override
	public void add(KcpSource obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(KcpSource obj) {
		
		mapper.update(obj);
		
	}

	@Override
	public KcpSource get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<KcpSource> list(KcpSourceDto dto) {
	
		return mapper.list(dto);
	}

	@Override
	public void loadPage(KcpSourceDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
	}

	@Override
	public int getKcpSourceIsExist(KcpSourceDto dto) {
		
		return mapper.getKcpSourceIsExist(dto);
	}

	
	
}
