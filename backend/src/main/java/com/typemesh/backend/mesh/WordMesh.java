package com.typemesh.backend.mesh;

import java.util.List;

public record WordMesh(String text, List<List<GlyphMesh>> rows) {
}
