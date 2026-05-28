package com.typemesh.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MeshControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void meshEndpointReturnsDefaultWordmark() throws Exception {
		mockMvc.perform(get("/api/mesh"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.text").value("ASHER BLOOM"))
			.andExpect(jsonPath("$.rows.length()").value(2))
			.andExpect(jsonPath("$.rows[0].length()").value(5))
			.andExpect(jsonPath("$.rows[0][0].grid.columns").value(2))
			.andExpect(jsonPath("$.rows[0][0].grid.rows").value(4));
	}

	@Test
	void meshEndpointSanitizesBrandInput() throws Exception {
		mockMvc.perform(get("/api/mesh").param("text", "orbit 2026 / labs?"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.text").value("ORBIT LABS"))
			.andExpect(jsonPath("$.rows.length()").value(2))
			.andExpect(jsonPath("$.rows[0].length()").value(5))
			.andExpect(jsonPath("$.rows[1].length()").value(4));
	}

	@Test
	void meshEndpointReturnsSvgGeometryShape() throws Exception {
		mockMvc.perform(get("/api/mesh").param("text", "Q"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.rows[0][0].letter").value("Q"))
			.andExpect(jsonPath("$.rows[0][0].strokes.length()").value(5))
			.andExpect(jsonPath("$.rows[0][0].strokes[0].commands[1].verb").value("A"))
			.andExpect(jsonPath("$.rows[0][0].strokes[4].commands[1].verb").value("L"));
	}

	@Test
	void healthEndpointReportsServiceStatus() throws Exception {
		mockMvc.perform(get("/api/health"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("ok"))
			.andExpect(jsonPath("$.service").value("TypeMesh"))
			.andExpect(jsonPath("$.checkedAt").exists());
	}

	@Test
	void apiCorsAllowsLocalFrontend() throws Exception {
		mockMvc.perform(get("/api/mesh").header("Origin", "http://127.0.0.1:5173"))
			.andExpect(status().isOk())
			.andExpect(header().string("Access-Control-Allow-Origin", "http://127.0.0.1:5173"));
	}
}
