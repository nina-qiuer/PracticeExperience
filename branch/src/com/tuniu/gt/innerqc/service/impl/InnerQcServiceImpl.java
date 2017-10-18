package com.tuniu.gt.innerqc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.util.PageUtil;
import com.tuniu.gt.innerqc.dao.impl.InnerQcDao;
import com.tuniu.gt.innerqc.entity.InnerQcEntity;
import com.tuniu.gt.innerqc.service.InnerQcService;
import com.tuniu.gt.innerqc.vo.InnerQcPage;


@Service("innerqc_service_impl-inner_qc")
public class InnerQcServiceImpl extends ServiceBaseImpl<InnerQcDao> implements InnerQcService {
	
	@Autowired
	@Qualifier("innerqc_dao_impl-inner_qc")
	public void setDao(InnerQcDao dao) {
		this.dao = dao;
	}

	@Override
	public void getInnerQcPage(InnerQcPage page) {
		long totalRecords = dao.getTotal(page);
		if (totalRecords > 0) {
			int totalPages = PageUtil.getTotalPages((int) totalRecords);
			int pageNo = PageUtil.processPageNo(page.getPageNo(), totalPages);;
			page.setTotalRecords((int) totalRecords);
			page.setTotalPages(totalPages);
			page.setPageNo(pageNo);
			page.setPageSize(PageUtil.PAGE_SIZE);
			page.setStart(PageUtil.getStartPosition(pageNo));
			page.setIqcList(dao.getInnerQcList(page));
		}
	}

	@Override
	public List<InnerQcEntity> getInnerQcList(InnerQcPage page) {
		return dao.getInnerQcList(page);
	}

	@Override
	public void deleteInnerQc(InnerQcEntity entity) {
		dao.deleteInnerQc(entity);
	}

	@Override
	public InnerQcEntity getInnerQc(int id) {
		return dao.getInnerQc(id);
	}

}
