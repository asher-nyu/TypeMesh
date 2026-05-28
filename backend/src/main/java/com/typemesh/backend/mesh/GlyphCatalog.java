package com.typemesh.backend.mesh;

import static java.util.Map.entry;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class GlyphCatalog {
	private static final GridSpec GRID = new GridSpec(2, 4);
	private static final double BOWL_RADIUS = 1.08;
	private final Map<String, List<Stroke>> glyphs = Map.ofEntries(
		entry("A", glyphA()),
		entry("B", glyphB()),
		entry("C", glyphC()),
		entry("D", glyphD()),
		entry("E", glyphE()),
		entry("F", glyphF()),
		entry("G", glyphG()),
		entry("H", glyphH()),
		entry("I", glyphI()),
		entry("J", glyphJ()),
		entry("K", glyphK()),
		entry("L", glyphL()),
		entry("M", glyphM()),
		entry("N", glyphN()),
		entry("O", glyphO()),
		entry("P", glyphP()),
		entry("Q", glyphQ()),
		entry("R", glyphR()),
		entry("S", glyphS()),
		entry("T", glyphT()),
		entry("U", glyphU()),
		entry("V", glyphV()),
		entry("W", glyphW()),
		entry("X", glyphX()),
		entry("Y", glyphY()),
		entry("Z", glyphZ()),
		entry("?", glyphFallback())
	);

	public GlyphMesh glyph(String letter) {
		String normalized = letter.toUpperCase(Locale.ROOT);
		String key = glyphs.containsKey(normalized) ? normalized : "?";
		return new GlyphMesh(key, GRID, glyphs.get(key));
	}

	private static List<Stroke> glyphA() {
		return List.of(
			stroke(move(point(0, 4)), line(point(1, 0)), line(point(2, 4))),
			stroke(move(point(0.6, 2)), line(point(1.4, 2)))
		);
	}

	private static List<Stroke> glyphB() {
		return List.of(
			stroke(move(point(0, 0)), line(point(0, 4))),
			stroke(move(point(0, 0)), line(point(1, 0))),
			stroke(move(point(0, 2)), line(point(1, 2))),
			stroke(move(point(0, 4)), line(point(1, 4))),
			stroke(move(point(1, 0)), arc(point(1, 2), BOWL_RADIUS, BOWL_RADIUS, false, true)),
			stroke(move(point(1, 2)), arc(point(1, 4), BOWL_RADIUS, BOWL_RADIUS, false, true))
		);
	}

	private static List<Stroke> glyphC() {
		return List.of(
			stroke(move(point(2, 1)), arc(point(0, 1), 1, 1, false, false)),
			stroke(move(point(0, 1)), line(point(0, 3))),
			stroke(move(point(0, 3)), arc(point(2, 3), 1, 1, false, false))
		);
	}

	private static List<Stroke> glyphD() {
		return List.of(
			stroke(move(point(0, 0)), line(point(0, 4))),
			stroke(move(point(0, 0)), line(point(1, 0))),
			stroke(move(point(1, 0)), arc(point(2, 1), 1, 1, false, true)),
			stroke(move(point(2, 1)), line(point(2, 3))),
			stroke(move(point(2, 3)), arc(point(1, 4), 1, 1, false, true)),
			stroke(move(point(1, 4)), line(point(0, 4)))
		);
	}

	private static List<Stroke> glyphE() {
		return List.of(
			stroke(move(point(2, 0)), line(point(0, 0)), line(point(0, 4)), line(point(2, 4))),
			stroke(move(point(0, 2)), line(point(1.55, 2)))
		);
	}

	private static List<Stroke> glyphF() {
		return List.of(
			stroke(move(point(0, 4)), line(point(0, 0)), line(point(2, 0))),
			stroke(move(point(0, 2)), line(point(1.55, 2)))
		);
	}

	private static List<Stroke> glyphG() {
		return List.of(
			stroke(move(point(2, 1)), arc(point(0, 1), 1, 1, false, false)),
			stroke(move(point(0, 1)), line(point(0, 3))),
			stroke(move(point(0, 3)), arc(point(2, 3), 1, 1, false, false)),
			stroke(move(point(2, 3)), line(point(2, 2))),
			stroke(move(point(2, 2)), line(point(1, 2)))
		);
	}

	private static List<Stroke> glyphH() {
		return List.of(
			stroke(move(point(0, 0)), line(point(0, 4))),
			stroke(move(point(2, 0)), line(point(2, 4))),
			stroke(move(point(0, 2)), line(point(2, 2)))
		);
	}

	private static List<Stroke> glyphI() {
		return List.of(
			stroke(move(point(0, 0)), line(point(2, 0))),
			stroke(move(point(1, 0)), line(point(1, 4))),
			stroke(move(point(0, 4)), line(point(2, 4)))
		);
	}

	private static List<Stroke> glyphJ() {
		return List.of(
			stroke(move(point(2, 0)), line(point(2, 3)), arc(point(0, 3), 1, 1, false, true))
		);
	}

	private static List<Stroke> glyphK() {
		return List.of(
			stroke(move(point(0, 0)), line(point(0, 4))),
			stroke(move(point(2, 0)), line(point(0, 2)), line(point(2, 4)))
		);
	}

	private static List<Stroke> glyphL() {
		return List.of(
			stroke(move(point(0, 0)), line(point(0, 4)), line(point(2, 4)))
		);
	}

	private static List<Stroke> glyphM() {
		return List.of(
			stroke(move(point(0, 4)), line(point(0, 0)), line(point(1, 2)), line(point(2, 0)), line(point(2, 4)))
		);
	}

	private static List<Stroke> glyphN() {
		return List.of(
			stroke(move(point(0, 4)), line(point(0, 0)), line(point(2, 4)), line(point(2, 0)))
		);
	}

	private static List<Stroke> glyphO() {
		return List.of(
			stroke(move(point(2, 1)), arc(point(0, 1), 1, 1, false, false)),
			stroke(move(point(0, 1)), line(point(0, 3))),
			stroke(move(point(0, 3)), arc(point(2, 3), 1, 1, false, false)),
			stroke(move(point(2, 3)), line(point(2, 1)))
		);
	}

	private static List<Stroke> glyphP() {
		return List.of(
			stroke(move(point(0, 4)), line(point(0, 0))),
			stroke(move(point(0, 0)), line(point(1, 0))),
			stroke(move(point(0, 2)), line(point(1, 2))),
			stroke(move(point(1, 0)), arc(point(1, 2), BOWL_RADIUS, BOWL_RADIUS, false, true))
		);
	}

	private static List<Stroke> glyphQ() {
		return List.of(
			stroke(move(point(2, 1)), arc(point(0, 1), 1, 1, false, false)),
			stroke(move(point(0, 1)), line(point(0, 3))),
			stroke(move(point(0, 3)), arc(point(2, 3), 1, 1, false, false)),
			stroke(move(point(2, 3)), line(point(2, 1))),
			stroke(move(point(1, 2)), line(point(2, 4)))
		);
	}

	private static List<Stroke> glyphR() {
		return List.of(
			stroke(move(point(0, 4)), line(point(0, 0))),
			stroke(move(point(0, 0)), line(point(1, 0))),
			stroke(move(point(0, 2)), line(point(1, 2))),
			stroke(move(point(1, 0)), arc(point(1, 2), BOWL_RADIUS, BOWL_RADIUS, false, true)),
			stroke(move(point(1, 2)), line(point(2, 4)))
		);
	}

	private static List<Stroke> glyphS() {
		return List.of(
			stroke(move(point(2, 1)), arc(point(1, 2), 1, 1, true, false), arc(point(0, 3), 1, 1, true, true))
		);
	}

	private static List<Stroke> glyphT() {
		return List.of(
			stroke(move(point(0, 0)), line(point(2, 0))),
			stroke(move(point(1, 0)), line(point(1, 4)))
		);
	}

	private static List<Stroke> glyphU() {
		return List.of(
			stroke(move(point(0, 0)), line(point(0, 3)), arc(point(2, 3), 1, 1, false, false), line(point(2, 0)))
		);
	}

	private static List<Stroke> glyphV() {
		return List.of(
			stroke(move(point(0, 0)), line(point(1, 4)), line(point(2, 0)))
		);
	}

	private static List<Stroke> glyphW() {
		return List.of(
			stroke(move(point(0, 0)), line(point(0.5, 4)), line(point(1, 2)), line(point(1.5, 4)), line(point(2, 0)))
		);
	}

	private static List<Stroke> glyphX() {
		return List.of(
			stroke(move(point(0, 0)), line(point(2, 4))),
			stroke(move(point(2, 0)), line(point(0, 4)))
		);
	}

	private static List<Stroke> glyphY() {
		return List.of(
			stroke(move(point(0, 0)), line(point(1, 2)), line(point(2, 0))),
			stroke(move(point(1, 2)), line(point(1, 4)))
		);
	}

	private static List<Stroke> glyphZ() {
		return List.of(
			stroke(move(point(0, 0)), line(point(2, 0)), line(point(0, 4)), line(point(2, 4)))
		);
	}

	private static List<Stroke> glyphFallback() {
		return List.of(
			stroke(move(point(0, 0.75)), arc(point(2, 0.75), 1, 0.75, false, true), arc(point(1, 2.4), 1, 1, false, true), line(point(1, 3))),
			stroke(move(point(1, 3.75)), line(point(1, 4)))
		);
	}

	private static Stroke stroke(PathCommand... commands) {
		return new Stroke(List.of(commands));
	}

	private static PathCommand move(MeshPoint point) {
		return new PathCommand("M", List.of(point), null, null, null, null, null);
	}

	private static PathCommand line(MeshPoint point) {
		return new PathCommand("L", List.of(point), null, null, null, null, null);
	}

	private static PathCommand arc(MeshPoint point, double radiusX, double radiusY, boolean largeArc, boolean sweep) {
		return new PathCommand("A", List.of(point), radiusX, radiusY, 0.0, largeArc, sweep);
	}

	private static MeshPoint point(double x, double y) {
		return new MeshPoint(x, y);
	}
}
