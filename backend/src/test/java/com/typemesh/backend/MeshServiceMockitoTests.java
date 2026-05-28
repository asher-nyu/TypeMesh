package com.typemesh.backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.typemesh.backend.mesh.GlyphCatalog;
import com.typemesh.backend.mesh.GlyphMesh;
import com.typemesh.backend.mesh.GridSpec;
import com.typemesh.backend.mesh.MeshPoint;
import com.typemesh.backend.mesh.MeshService;
import com.typemesh.backend.mesh.PathCommand;
import com.typemesh.backend.mesh.Stroke;

class MeshServiceMockitoTests {
	@Test
	void composeRequestsOnlySanitizedLettersFromCatalog() {
		var catalog = mock(GlyphCatalog.class);

		when(catalog.glyph(anyString())).thenAnswer(invocation -> glyph(invocation.getArgument(0)));

		var mesh = new MeshService(catalog).compose("a1 b? z!");

		assertThat(mesh.text()).isEqualTo("A B Z");
		verify(catalog).glyph("A");
		verify(catalog).glyph("B");
		verify(catalog).glyph("Z");
		verifyNoMoreInteractions(catalog);
	}

	private GlyphMesh glyph(String letter) {
		return new GlyphMesh(
			letter,
			new GridSpec(2, 4),
			List.of(new Stroke(List.of(
				new PathCommand("M", List.of(new MeshPoint(0, 0)), null, null, null, null, null),
				new PathCommand("L", List.of(new MeshPoint(2, 4)), null, null, null, null, null)
			)))
		);
	}
}
