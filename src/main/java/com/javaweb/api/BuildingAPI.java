package com.javaweb.api;
import java.util.List;
import java.util.Map;

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
	public List<BuildingDTO> getbuilding(@RequestParam Map<String,Object> params,
										@RequestParam(name = "typeCode") List<String> typeCode) {
				List<BuildingDTO> result = buildingService.findAll(params,typeCode);
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
