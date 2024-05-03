package com.askme.dao;

import java.util.List;

import com.askme.dto.UserDTO;

public interface UserDAOInt {
	public long add(UserDTO dto);

	public void update(UserDTO dto);

	public void delete(UserDTO dto);

	public UserDTO findByLogin(String login);

	public UserDTO findByPk(long id);

	public List<UserDTO> search(UserDTO dto, long pageNo, int pageSize);

	public List<UserDTO> search(UserDTO dto);

	public UserDTO authenticate(UserDTO dto);

}
