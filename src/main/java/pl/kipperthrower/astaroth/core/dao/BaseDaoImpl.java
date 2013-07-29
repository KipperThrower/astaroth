package pl.kipperthrower.astaroth.core.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	private static final Logger LOGGER = Logger.getLogger(BaseDaoImpl.class);

	private Class<T> persistentClass;

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public BaseDaoImpl(Class<T> persistentClass) {
		this.persistentClass = persistentClass;

	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Class<T> getPersistentClass() {
		return persistentClass;
	}

	protected Criteria createCriteria(Criterion... criteria) {
		Criteria c = getSession().createCriteria(getPersistentClass());

		for (Criterion criterion : criteria) {
			c.add(criterion);
		}

		return c;
	}

	public <S extends T> S save(S entity) {
		try {
			getSession().saveOrUpdate(entity);
			return entity;
		} catch (ConstraintViolationException ex) {
			LOGGER.warn(ex);
			return null;
		}
	}

	public T findById(Long id) {
		return (T) getSession().get(getPersistentClass(), id);
	}

	public List<T> findAll() {
		return createCriteria().list();
	}

	public List<T> findAllWithOrder(Order order) {
		return createCriteria().addOrder(order).list();
	}

	public T findOneByCriteria(Criterion... criteria) {
		return (T) createCriteria(criteria).setResultTransformer(
				Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
	}

	public T findOneByCriteriaAndOrder(Order order, Criterion... criteria) {
		return (T) createCriteria(criteria).addOrder(order)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.uniqueResult();
	}

	public List<T> findAllByCriteria(Criterion... criteria) {
		return createCriteria(criteria).list();
	}

	public List<T> findAllByCriteriaDistinct(Criterion... criteria) {
		return createCriteria(criteria).setResultTransformer(
				Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	public List<T> findAllByCriteriaWithOrder(Order order,
			Criterion... criteria) {
		return createCriteria(criteria).addOrder(order).list();
	}

	public List<T> findAllByCriteriaDistinctWithOrder(Order order,
			Criterion... criteria) {
		return createCriteria(criteria).addOrder(order)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	public long count() {
		return ((Long) createCriteria().setProjection(Projections.rowCount())
				.uniqueResult()).longValue();
	}

	@Override
	public List<T> findAllDistinctWithOrder(Order order) {
		return createCriteria().addOrder(order)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public Iterable<T> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		for (T eachEntity : entities) {
			this.delete(eachEntity);
		}
	}
	

	@Override
	public void deleteAll() {
		String hql = String.format("delete from %s", getPersistentClass()
				.getSimpleName());
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<T> findAll(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		Collection<T> results = new ArrayList<T>();
		for (T eachEntity : entities) {
			T result = this.save(eachEntity);
			if (result != null) {
				results.add(result);
			}
		}
		return entities;
	}

}
