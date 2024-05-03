package com.askme.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.askme.dao.LeaveDAOInt;
import com.askme.dto.LeaveDTO;
import com.askme.exception.DuplicateRecordException;

@Service
public class LeaveServiceSpringImpl implements LeaveServiceInt {

	@Autowired
	LeaveDAOInt dao;

	private static Logger log = Logger.getLogger(LeaveServiceSpringImpl.class);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(LeaveDTO dto) throws DuplicateRecordException {

		log.debug("Leave spring add start");

		LeaveDTO existdto = dao.findByName(dto.getName());

		if (existdto != null) {
			throw new DuplicateRecordException("Leave is already exits");
		}

		log.debug("Leave spring add end");
		return dao.add(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(LeaveDTO dto) {
		dao.delete(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(LeaveDTO dto) throws DuplicateRecordException {
		log.debug("Leave spring add start");

		LeaveDTO existdto = dao.findByName(dto.getName());
		if (existdto != null && existdto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Leave is already exits");
		}

		log.debug("Leave spring add end");

		dao.update(dto);
	}

	@Transactional(readOnly = true)
	public LeaveDTO findByPK(long pk) {

		return dao.findByPk(pk);
	}

	@Transactional(readOnly = true)
	public LeaveDTO findByName(String name) {
		return dao.findByName(name);
	}

	@Transactional(readOnly = true)
	public List<LeaveDTO> search(LeaveDTO dto) {
		return dao.search(dto);
	}

	@Transactional(readOnly = true)
	public List search(LeaveDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Map<Long, LeaveDTO> getMapDTO(Set<Long> ids) {
		return dao.getMapDTO(ids);
	}

}
