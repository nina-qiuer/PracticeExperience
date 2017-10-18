package com.tuniu.gt.score.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.util.PageUtil;
import com.tuniu.gt.score.dao.impl.ScoreRecordDao;
import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.score.service.ScoreRecordService;
import com.tuniu.gt.score.util.ScoreRecordPage;


@Service("score_service_impl-score_record")
public class ScoreRecordServiceImpl extends ServiceBaseImpl<ScoreRecordDao> implements ScoreRecordService {
	
	@Autowired
	@Qualifier("score_dao_impl-score_record")
	public void setDao(ScoreRecordDao dao) {
		this.dao = dao;
	}

	@Override
	public void getScoreRecordPage(ScoreRecordPage page) {
		Map<String, Object> total = dao.getTotal(page);
		long totalRecords = (Long) total.get("totalRecords");
		BigDecimal totalValue = (BigDecimal) total.get("totalValue");
		if (totalRecords > 0) {
			int totalPages = PageUtil.getTotalPages((int) totalRecords);
			int pageNo = PageUtil.processPageNo(page.getPageNo(), totalPages);;
			page.setTotalRecords((int) totalRecords);
			page.setTotalValue(totalValue.longValue());
			page.setTotalPages(totalPages);
			page.setPageNo(pageNo);
			page.setPageSize(PageUtil.PAGE_SIZE);
			page.setStart(PageUtil.getStartPosition(pageNo));
			page.setSrList(dao.getScoreRecordList(page));
		}
	}

	@Override
	public void deleteByComplaintId(int complaintId) {
		dao.deleteByComplaintId(complaintId);
	}

	@Override
	public void batchDel(Map<String, Object> params) {
		dao.batchDel(params);
	}

	@Override
	public void batchInsert(List<ScoreRecordEntity> list) {
		dao.batchInsert(list);
	}

	@Override
	public List<ScoreRecordEntity> getScoreRecordList(ScoreRecordPage page) {
		return dao.getScoreRecordList(page);
	}

}
