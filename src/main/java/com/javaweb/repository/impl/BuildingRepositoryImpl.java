package com.javaweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepository{
	@PersistenceContext
	private EntityManager entityManager;
//	thay the cho statement
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
//		String sql = "FROM BuildingEntity";
//		Jpql
//		Query query = entityManager.createQuery(sql,BuildingEntity.class);
//		sqlnative
		String sql = "SELECT * FROM building ";
		Query query = entityManager.createNativeQuery(sql,BuildingEntity.class);
		return query.getResultList();
	}
	
}
