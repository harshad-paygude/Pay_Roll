package com.askme.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.askme.dto.SalaryDTO;

public interface SalaryDAOImp {
	public long add(SalaryDTO dto);

	public void update(SalaryDTO dto);

	public void delete(SalaryDTO dto);

	public SalaryDTO findByName(String name, String month);

	public SalaryDTO findByPk(long id);

	public List<SalaryDTO> search(SalaryDTO dto, long pageNo, int pageSize);

	public List<SalaryDTO> search(SalaryDTO dto);

	public Map<Long, SalaryDTO> getMapDTO(Set<Long> ids);

}
