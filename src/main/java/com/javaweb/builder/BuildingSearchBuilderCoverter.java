package com.javaweb.builder;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.utils.MapUtil;
@Component
public class BuildingSearchBuilderCoverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String,Object> params,List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
														.setName(MapUtil.getObject(params, "name", String.class))
														.setFloorArea(MapUtil.getObject(params, "floorArea", Long.class))
														.setWard(MapUtil.getObject(params, "ward", String.class))
														.setStreet(MapUtil.getObject(params, "street", String.class))
														.setDistrictId(MapUtil.getObject(params, "districtId", String.class))
														.setNumberOfBasement(MapUtil.getObject(params, "numberOfBasement",Integer.class))
														.setTypeCode(typeCode)
														.setManagerName(MapUtil.getObject(params, "managerName", String.class))
														.setManagerPhoneNumber(MapUtil.getObject(params, "managerPhoneNumber", String.class))
														.setRentPriceTo(MapUtil.getObject(params, "rentPriceTo", Long.class))
														.setRentPriceForm(MapUtil.getObject(params, "rentPriceFrom", Long.class))
														.setAreaTo(MapUtil.getObject(params, "areaTo", Long.class))
														.setAreaForm(MapUtil.getObject(params, "areaFrom", Long.class))
														.setStaffId(MapUtil.getObject(params, "staffId", Long.class))
														.build();
													
														
														
													
														
				return buildingSearchBuilder;
	}
}
