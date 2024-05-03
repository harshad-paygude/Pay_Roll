package com.askme.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.askme.dao.TimeSheetDAOInt;
import com.askme.dto.TimeSheetDTO;
import com.askme.exception.DuplicateRecordException;

@Service
public class TimeSheetServiceSpringImpl implements TimeSheetServiceInt {

	@Autowired
	TimeSheetDAOInt dao;

	private static Logger log = Logger.getLogger(TimeSheetServiceSpringImpl.class);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(TimeSheetDTO dto) throws DuplicateRecordException {

		log.debug("TimeSheet spring add start");

		TimeSheetDTO existdto = dao.findByName(dto.getName());

		if (existdto != null) {
			throw new DuplicateRecordException("TimeSheet is already exits");
		}

		log.debug("TimeSheet spring add end");
		return dao.add(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(TimeSheetDTO dto) {
		dao.delete(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(TimeSheetDTO dto) throws DuplicateRecordException {
		log.debug("Role spring add start");

		TimeSheetDTO existdto = dao.findByName(dto.getName());

		if (existdto != null && existdto.getId() != dto.getId()) {
			throw new DuplicateRecordException("TimeSheet is already exits");
		}
		log.debug("TimeSheet spring add end");
		dao.update(dto);
	}

	@Transactional(readOnly = true)
	public TimeSheetDTO findByPK(long pk) {
		return dao.findByPk(pk);
	}

	@Transactional(readOnly = true)
	public TimeSheetDTO findByName(String name) {
		return dao.findByName(name);
	}

	@Transactional(readOnly = true)
	public List<TimeSheetDTO> search(TimeSheetDTO dto) {
		return dao.search(dto);
	}

	@Transactional(readOnly = true)
	public List search(TimeSheetDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Map<Long, TimeSheetDTO> getMapDTO(Set<Long> ids) {
		return dao.getMapDTO(ids);
	}

}
