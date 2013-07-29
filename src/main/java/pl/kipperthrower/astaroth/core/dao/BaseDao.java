package pl.kipperthrower.astaroth.core.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BaseDao<T> extends PagingAndSortingRepository<T, Long> {

	T findById(Long id);

	List<T> findAll();

	List<T> findAllDistinctWithOrder(Order order);

	List<T> findAllWithOrder(Order order);

	T findOneByCriteria(Criterion... criteria);

	List<T> findAllByCriteria(Criterion... criteria);

	List<T> findAllByCriteriaDistinct(Criterion... criteria);

	List<T> findAllByCriteriaWithOrder(Order order, Criterion... criteria);

	List<T> findAllByCriteriaDistinctWithOrder(Order order,
			Criterion... criteria);

	long count();

	void delete(T entity);
}
