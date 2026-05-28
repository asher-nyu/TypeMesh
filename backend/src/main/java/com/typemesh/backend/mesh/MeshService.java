package com.typemesh.backend.mesh;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class MeshService {
	private final GlyphCatalog glyphCatalog;

	public MeshService(GlyphCatalog glyphCatalog) {
		this.glyphCatalog = glyphCatalog;
	}

	public WordMesh compose(String text) {
		String normalized = sanitize(text);
		List<List<GlyphMesh>> rows = Arrays.stream(normalized.split("\\s+"))
			.map(this::composeRow)
			.toList();
		return new WordMesh(normalized, rows);
	}

	private String sanitize(String text) {
		if (text == null || text.isBlank()) {
			return "ASHER BLOOM";
		}

		String normalized = text.toUpperCase(Locale.ROOT)
			.replaceAll("[^A-Z\\s]", "")
			.replaceAll("\\s+", " ")
			.trim();

		return normalized.isBlank() ? "ASHER BLOOM" : normalized;
	}

	private List<GlyphMesh> composeRow(String row) {
		return row.chars()
			.mapToObj(code -> glyphCatalog.glyph(Character.toString(code)))
			.toList();
	}
}
