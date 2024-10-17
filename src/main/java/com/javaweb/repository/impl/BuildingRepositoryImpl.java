package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.parser.Vary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository{
	 public static void joinTable(BuildingSearchBuilder buildingSearchBuilder,StringBuilder sql) {
		 Long staffId = buildingSearchBuilder.getStaffId();
		 if(staffId != null) {
			 sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		 }
		 List<String> typeCode = buildingSearchBuilder.getTypeCode();
		 if(typeCode != null && typeCode.size() != 0) {
			 sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		 }
//		 String rentAreaTo = buildingSearchBuilder.getAreaTo().toString();
//		 String rentAreaFrom = buildingSearchBuilder.getAreaForm().toString();
//		 if(StringUtil.checkString(rentAreaTo) || StringUtil.checkString(rentAreaFrom)) {
//			 sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id ");
//		 }
	 }
	 public static void queryNomal(BuildingSearchBuilder buildingSearchBuilder,StringBuilder where) {
//		 for(Map.Entry<String, Object> it : params.entrySet()) {
//			 if(!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && !it.getKey().startsWith("area") && !it.getKey().startsWith("rentPrice") ) {
//				 String value = it.getValue().toString();
//				 if(StringUtil.checkString(value)) {
//					 if(NumberUtil.isNumber(value) == true) {
//							where.append(" AND b." + it.getKey() + "=" + value);
//						}
//						else {
//							where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%' ");
//						}	
//				 }
//			 }
//		 }
		 try {
			 Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			 for(Field item :fields) {
				 item.setAccessible(true);
				 String fieldName = item.getName();
				 if(!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("area") && !fieldName.startsWith("rentPrice") ) {
					 Object value = item.get(buildingSearchBuilder).toString();
					 if(value != null) {
						 if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
								where.append(" AND b." + fieldName + "=" + value);
							}
							else  if(item.getType().getName().equals("java.lang.String")){
								where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
							}	
					 }
					 
			 }
		 }}catch(Exception ex) {
			 ex.printStackTrace();
		 }
	 }
	 public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder,StringBuilder where) {
		 Long staffId = buildingSearchBuilder.getStaffId();
			if(staffId != null) {
				where.append(" AND assignmentbuilding.staffid = " + staffId);
			}
			Long rentAreaTo = buildingSearchBuilder.getAreaTo();
			Long rentAreaFrom = buildingSearchBuilder.getAreaForm();
			if(rentAreaTo != null || rentAreaFrom != null) {
				where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
				if(rentAreaFrom != null) {
					where.append(" AND r.value >=" + rentAreaFrom);
				}
				if(rentAreaTo != null) {
					where.append(" AND r.value <=" + rentAreaTo);
				}
				where.append(")");
			}
//			String rentPriceTo = buildingSearchBuilder.getRentPriceTo().toString();
//			String rentPriceFrom = buildingSearchBuilder.getRentPriceForm().toString();
//			if(StringUtil.checkString(rentPriceTo) || StringUtil.checkString(rentPriceFrom)) {
//				if(StringUtil.checkString(rentAreaFrom)) {
//					where.append(" AND b.rentprice >=" + rentPriceFrom);
//				}
//				if(StringUtil.checkString(rentAreaTo)) {
//					where.append(" AND b.rentprice <=" + rentPriceTo);
//				}
//			}
			List<String> typeCode = buildingSearchBuilder.getTypeCode();
			if(typeCode != null && typeCode.size() != 0) {
				String joinedTypeCode = typeCode.stream()
	                    .map(code -> "'" + code + "'")  
	                    .collect(Collectors.joining(",")); 
							where.append(" AND renttype.code IN (" + joinedTypeCode + ")");
			}
	 }
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.id ,b.name ,b.districtid ,b.street ,b.ward ,b.numberofbasement ,b.floorarea ,b.rentprice ,b.managername ,b.managerphonenumber ,b.servicefee ,b.brokeragefee FROM building b");
		joinTable(buildingSearchBuilder, sql);
		System.out.println(sql);
		StringBuilder where = new StringBuilder(" WHERE 1 = 1");
		queryNomal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id ");
		sql.append(where);
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection conn = ConnectionJDBCUtil.getConnection()){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while(rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getLong("b.id"));
				buildingEntity.setName(rs.getString("b.name"));
				buildingEntity.setWard(rs.getString("b.ward"));
				buildingEntity.setDistrictid(rs.getLong("b.districtid"));
				buildingEntity.setStreet(rs.getString("b.street"));
				buildingEntity.setFloorArea(rs.getLong("floorarea"));
				buildingEntity.setRentPrice(rs.getLong("rentPrice"));
				buildingEntity.setServiceFee(rs.getString("servicefee"));
				buildingEntity.setBrokerageFee(rs.getLong("brokeragefee"));
				buildingEntity.setManagerName(rs.getString("managerName"));
				buildingEntity.setManagerPhoneNumber(rs.getString("managerPhoneNumber"));
				result.add(buildingEntity);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("connect fasle");
		}
		return result;
	}



}
