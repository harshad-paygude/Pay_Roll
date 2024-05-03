package com.askme.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.askme.dto.SalaryDTO;

@Repository
public class SalaryDAOHibImpl implements SalaryDAOImp {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long add(SalaryDTO dto) {
		long pk = (Long) sessionFactory.getCurrentSession().save(dto);
		return pk;
	}

	@Override
	public void update(SalaryDTO dto) {
		sessionFactory.getCurrentSession().merge(dto);

	}

	@Override
	public void delete(SalaryDTO dto) {
		sessionFactory.getCurrentSession().delete(dto);

	}

	public SalaryDTO findByName(String name, String month) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(SalaryDTO.class);

		criteria.add(Restrictions.eq("userName", name));
		criteria.add(Restrictions.eq("month", month));

		return (SalaryDTO) criteria.uniqueResult();
	}

	@Override
	public SalaryDTO findByPk(long id) {
		Session session = sessionFactory.getCurrentSession();
		SalaryDTO dto = (SalaryDTO) session.get(SalaryDTO.class, id);
		return dto;
	}

	@Override
	public List<SalaryDTO> search(SalaryDTO dto, long pageNo, int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SalaryDTO.class);

		if (dto != null) {

			if (dto.getId() > 0) {

				criteria.add(Restrictions.eq("id", dto.getId()));

			}
			if (dto.getUserName() != null && dto.getUserName().length() > 0) {
				criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
			}
			if (dto.getUserId() > 0) {

				criteria.add(Restrictions.eq("userId", dto.getUserId()));

			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult((int) pageNo);
				criteria.setMaxResults(pageSize);
			}
		}

		return criteria.list();
	}

	@Override
	public List<SalaryDTO> search(SalaryDTO dto) {
		return search(dto, 0, 0);
	}

	@Override
	public Map<Long, SalaryDTO> getMapDTO(Set<Long> ids) {
		Session session = sessionFactory.getCurrentSession();
		Map<Long, SalaryDTO> map = new HashMap<Long, SalaryDTO>();

		for (Long id : ids) {
			map.put(id, (SalaryDTO) session.get(SalaryDTO.class, id));
		}
		return map;
	}

}
