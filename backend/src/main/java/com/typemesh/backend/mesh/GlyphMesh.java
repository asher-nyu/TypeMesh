package com.typemesh.backend.mesh;

import java.util.List;

public record GlyphMesh(String letter, GridSpec grid, List<Stroke> strokes) {
}
