package com.askme.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.askme.dao.AttendanceDAOInt;
import com.askme.dto.AttendanceDTO;
import com.askme.exception.DuplicateRecordException;

@Service
public class AttendanceServiceSpringImpl implements AttendanceServiceInt {

	@Autowired
	AttendanceDAOInt dao;

	private static Logger log = Logger.getLogger(AttendanceServiceSpringImpl.class);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(AttendanceDTO dto) throws DuplicateRecordException {

		log.debug("Attendance spring add start");

		AttendanceDTO existdto = dao.findByName(dto.getName());

		if (existdto != null) {
			throw new DuplicateRecordException("Attendance is already exits");
		}

		log.debug("Attendance spring add end");
		return dao.add(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(AttendanceDTO dto) {
		dao.delete(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(AttendanceDTO dto) throws DuplicateRecordException {
		log.debug("Attendance spring add start");

		AttendanceDTO existdto = dao.findByName(dto.getName());

		if (existdto != null && existdto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Attendance is already exits");
		}

		log.debug("Attendance spring add end");

		dao.update(dto);
	}

	@Transactional(readOnly = true)
	public AttendanceDTO findByPK(long pk) {

		return dao.findByPk(pk);
	}

	@Transactional(readOnly = true)
	public AttendanceDTO findByName(String name) {
		return dao.findByName(name);
	}

	@Transactional(readOnly = true)
	public List<AttendanceDTO> search(AttendanceDTO dto) {
		return dao.search(dto);
	}

	@Transactional(readOnly = true)
	public List search(AttendanceDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Map<Long, AttendanceDTO> getMapDTO(Set<Long> ids) {
		return dao.getMapDTO(ids);
	}

}
