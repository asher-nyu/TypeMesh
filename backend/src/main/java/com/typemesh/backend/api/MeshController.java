package com.typemesh.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.typemesh.backend.mesh.MeshService;
import com.typemesh.backend.mesh.WordMesh;

@RestController
public class MeshController {
	private final MeshService meshService;

	public MeshController(MeshService meshService) {
		this.meshService = meshService;
	}

	@GetMapping("/api/mesh")
	public WordMesh mesh(@RequestParam(defaultValue = "ASHER BLOOM") String text) {
		return meshService.compose(text);
	}
}
