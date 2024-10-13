package com.javaweb.api;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.customexception.FieldRequiredException;
import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;
@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getbuilding(@RequestParam(name = "name",required = false) String name,
										@RequestParam(name = "districtId",required = false) Long districtId,
										@RequestParam(name = "typeCode",required = false) List<String> typeCode) {
				List<BuildingDTO> result = buildingService.findAll(name,districtId);
				return result;
				
	}
	public void valiDate(BuildingDTO buildingDTO) {
		if(buildingDTO.getName() == null || buildingDTO.getName().equals("") ) {
			throw new FieldRequiredException("name or number is null");
		}
	}
//	@PostMapping(value = "/api/building/")
//	public BuildingDTO getbuilding2(@RequestBody BuildingDTO buildingDTO) {
//			return buildingDTO;
//	}
	@DeleteMapping(value = "/api/building/{id}")
	public void deleteBuilding(@PathVariable Integer id) {
		System.out.println(id);
	}
}
