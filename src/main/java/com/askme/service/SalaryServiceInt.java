package com.askme.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.askme.dto.SalaryDTO;
import com.askme.exception.DuplicateRecordException;

public interface SalaryServiceInt {

	public long add(SalaryDTO dto) throws DuplicateRecordException;

	public void delete(SalaryDTO dto);

	public void update(SalaryDTO dto) throws DuplicateRecordException;

	public SalaryDTO findByPK(long pk);

	public SalaryDTO findByName(String name, String month);

	public List<SalaryDTO> search(SalaryDTO dto);

	public List search(SalaryDTO dto, int pageNo, int pageSize);

	public Map<Long, SalaryDTO> getMapDTO(Set<Long> ids);

}
