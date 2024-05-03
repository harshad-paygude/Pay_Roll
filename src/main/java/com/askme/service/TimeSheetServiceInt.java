package com.askme.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.askme.dto.TimeSheetDTO;
import com.askme.exception.DuplicateRecordException;

public interface TimeSheetServiceInt {

	public long add(TimeSheetDTO dto) throws DuplicateRecordException;

	public void delete(TimeSheetDTO dto);

	public void update(TimeSheetDTO dto) throws DuplicateRecordException;

	public TimeSheetDTO findByPK(long pk);

	public TimeSheetDTO findByName(String name);

	public List<TimeSheetDTO> search(TimeSheetDTO dto);

	public List search(TimeSheetDTO dto, int pageNo, int pageSize);

	public Map<Long, TimeSheetDTO> getMapDTO(Set<Long> ids);

}
