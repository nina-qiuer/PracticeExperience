package com.tuniu.qms.qc.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcNoteDto;
import com.tuniu.qms.qc.model.QcNote;

public interface QcNoteService extends BaseService<QcNote, QcNoteDto> {
	Integer count(QcNoteDto dto);
}
