package com.askme.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.askme.dao.RoleDAOInt;
import com.askme.dto.RoleDTO;
import com.askme.exception.DuplicateRecordException;

@Service
public class RoleServiceSpringImpl implements RoleServiceInt {

	@Autowired
	RoleDAOInt dao;

	private static Logger log = Logger.getLogger(RoleServiceSpringImpl.class);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(RoleDTO dto) throws DuplicateRecordException {

		log.debug("Role spring add start");

		RoleDTO existdto = dao.findByName(dto.getRoleName());

		if (existdto != null) {
			throw new DuplicateRecordException("role is already exits");
		}

		log.debug("Role spring add end");
		return dao.add(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(RoleDTO dto) {
		dao.delete(dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(RoleDTO dto) throws DuplicateRecordException {
		log.debug("Role spring add start");

		RoleDTO existdto = dao.findByName(dto.getRoleName());
		System.out.println("dto.getRoleName()************" + dto.getRoleName());
		System.out.println("exist dto in role service " + existdto.getRoleName());
		if (existdto != null && existdto.getId() != dto.getId()) {
			throw new DuplicateRecordException("role is already exits");
		}
		log.debug("Role spring add end");
		dao.update(dto);
	}

	@Transactional(readOnly = true)
	public RoleDTO findByPK(long pk) {

		return dao.findByPk(pk);
	}

	@Transactional(readOnly = true)
	public RoleDTO findByName(String name) {
		return dao.findByName(name);
	}

	@Transactional(readOnly = true)
	public List<RoleDTO> search(RoleDTO dto) {
		return dao.search(dto);
	}

	@Transactional(readOnly = true)
	public List search(RoleDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Map<Long, RoleDTO> getMapDTO(Set<Long> ids) {
		return dao.getMapDTO(ids);
	}

}
