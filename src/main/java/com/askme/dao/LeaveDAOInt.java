package com.askme.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.askme.dto.LeaveDTO;

public interface LeaveDAOInt {

	public long add(LeaveDTO dto);

	public void update(LeaveDTO dto);

	public void delete(LeaveDTO dto);

	public LeaveDTO findByName(String name);

	public LeaveDTO findByPk(long id);

	public List<LeaveDTO> search(LeaveDTO dto, long pageNo, int pageSize);

	public List<LeaveDTO> search(LeaveDTO dto);

	public Map<Long, LeaveDTO> getMapDTO(Set<Long> ids);

}
