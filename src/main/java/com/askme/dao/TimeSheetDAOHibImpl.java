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

import com.askme.dto.TimeSheetDTO;

@Repository
public class TimeSheetDAOHibImpl implements TimeSheetDAOInt {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long add(TimeSheetDTO dto) {
		long pk = (Long) sessionFactory.getCurrentSession().save(dto);
		return pk;
	}

	@Override
	public void update(TimeSheetDTO dto) {
		sessionFactory.getCurrentSession().merge(dto);

	}

	@Override
	public void delete(TimeSheetDTO dto) {
		sessionFactory.getCurrentSession().delete(dto);

	}

	@Override
	public TimeSheetDTO findByName(String name) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(TimeSheetDTO.class);

		criteria.add(Restrictions.eq("name", name));

		return (TimeSheetDTO) criteria.uniqueResult();
	}

	@Override
	public TimeSheetDTO findByPk(long id) {
		Session session = sessionFactory.getCurrentSession();
		TimeSheetDTO dto = (TimeSheetDTO) session.get(TimeSheetDTO.class, id);

		return dto;
	}

	@Override
	public List<TimeSheetDTO> search(TimeSheetDTO dto, long pageNo, int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(TimeSheetDTO.class);

		if (dto != null) {

			if (dto.getId() > 0) {

				criteria.add(Restrictions.eq("id", dto.getId()));

			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getEmailId() != null && dto.getEmailId().length() > 0) {
				criteria.add(Restrictions.like("emailId", dto.getEmailId() + "%"));
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
	public List<TimeSheetDTO> search(TimeSheetDTO dto) {
		return search(dto, 0, 0);
	}

	@Override
	public Map<Long, TimeSheetDTO> getMapDTO(Set<Long> ids) {
		Session session = sessionFactory.getCurrentSession();
		Map<Long, TimeSheetDTO> map = new HashMap<Long, TimeSheetDTO>();

		for (Long id : ids) {
			map.put(id, (TimeSheetDTO) session.get(TimeSheetDTO.class, id));
		}
		return map;
	}

}
