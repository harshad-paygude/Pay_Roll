package com.askme.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.askme.dao.SalaryDAOImp;
import com.askme.dto.SalaryDTO;
import com.askme.dto.UserDTO;
import com.askme.exception.DuplicateRecordException;

@Service
public class SalaryServiceSpringImpl implements SalaryServiceInt {

	@Autowired
	SalaryDAOImp dao;

	@Autowired
	UserServiceInt userService;

	private static Logger log = Logger.getLogger(SalaryServiceSpringImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long add(SalaryDTO dto) throws DuplicateRecordException {

		log.debug("Salary spring add start");

		SalaryDTO existdto = dao.findByName(dto.getUserName(), dto.getMonth());

		if (existdto != null) {
			throw new DuplicateRecordException("User Salary This Month Already Exist is already exits");
		}

		UserDTO uDto = userService.findByPK(dto.getUserId());
		dto.setUserName(uDto.getFullName());
		log.debug("Salary spring add end");
		return dao.add(dto);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(SalaryDTO dto) {
		dao.delete(dto);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(SalaryDTO dto) throws DuplicateRecordException {
		log.debug("Salary spring add start");

		SalaryDTO existdto = dao.findByName(dto.getUserName(), dto.getMonth());

		if (existdto != null && existdto.getId() != dto.getId()) {
			throw new DuplicateRecordException("User Salary This Month Already Exist is already exits");
		}
		log.debug("Salary spring add end");
		dao.update(dto);
	}

	@Override
	@Transactional(readOnly = true)
	public SalaryDTO findByPK(long pk) {
		return dao.findByPk(pk);
	}

	@Override
	@Transactional(readOnly = true)
	public SalaryDTO findByName(String name, String month) {
		return dao.findByName(name, month);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SalaryDTO> search(SalaryDTO dto) {
		return dao.search(dto);
	}

	@Override
	@Transactional(readOnly = true)
	public List search(SalaryDTO dto, int pageNo, int pageSize) {
		return dao.search(dto, pageNo, pageSize);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, SalaryDTO> getMapDTO(Set<Long> ids) {
		return dao.getMapDTO(ids);
	}

}
