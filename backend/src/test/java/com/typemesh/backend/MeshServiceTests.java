package com.typemesh.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.typemesh.backend.mesh.GlyphCatalog;
import com.typemesh.backend.mesh.MeshService;
import com.typemesh.backend.mesh.PathCommand;

class MeshServiceTests {
	private final MeshService meshService = new MeshService(new GlyphCatalog());

	@Test
	void composesAsherBloomIntoTwoRows() {
		var mesh = meshService.compose("ASHER BLOOM");

		assertThat(mesh.rows()).hasSize(2);
		assertThat(mesh.rows().getFirst()).hasSize(5);
		assertThat(mesh.rows().getLast()).hasSize(5);
		assertThat(mesh.rows().getFirst().getFirst().grid().columns()).isEqualTo(2);
		assertThat(mesh.rows().getFirst().getFirst().grid().rows()).isEqualTo(4);
	}

	@Test
	void keepsOnlyUppercaseLettersAndRowSpaces() {
		var mesh = meshService.compose("as?her 12!/bloom");

		assertThat(mesh.text()).isEqualTo("ASHER BLOOM");
		assertThat(mesh.rows()).hasSize(2);
		assertThat(mesh.rows().getFirst()).hasSize(5);
		assertThat(mesh.rows().getLast()).hasSize(5);
	}

	@Test
	void curvedGlyphsUseArcCommands() {
		var mesh = meshService.compose("OBS");
		var verbs = mesh.rows().getFirst().stream()
			.flatMap(glyph -> glyph.strokes().stream())
			.flatMap(stroke -> stroke.commands().stream())
			.map(command -> command.verb())
			.toList();

		assertThat(verbs).contains("A");
		assertThat(verbs).doesNotContain("C", "Q");
	}

	@Test
	void composesEveryUppercaseLetterAsItsOwnGlyph() {
		var alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		var mesh = meshService.compose(alphabet);
		var letters = mesh.rows().getFirst().stream()
			.map(glyph -> glyph.letter())
			.toList();

		assertThat(letters).containsExactly(alphabet.split(""));
	}

	@Test
	void aCrossbarIsTrimmedForStrokeIntersection() {
		var a = meshService.compose("A").rows().getFirst().getFirst();
		var crossbar = a.strokes().getLast().commands();

		assertThat(crossbar.getFirst().points().getFirst().x()).isEqualTo(0.6);
		assertThat(crossbar.getLast().points().getFirst().x()).isEqualTo(1.4);
		assertThat(crossbar.getFirst().points().getFirst().y()).isEqualTo(2.0);
		assertThat(crossbar.getLast().points().getFirst().y()).isEqualTo(2.0);
	}

	@Test
	void bPRUseBlendCircleArcs() {
		var arcs = arcCommands("BPR");

		assertThat(arcs).hasSize(4);
		assertThat(arcs).allSatisfy(command -> {
			assertThat(command.radiusX()).isEqualTo(1.08);
			assertThat(command.radiusY()).isEqualTo(1.08);
			assertThat(command.largeArc()).isFalse();
			assertThat(command.sweep()).isTrue();
		});
	}

	@Test
	void qKeepsOneByOneCircleArcs() {
		var arcs = arcCommands("Q");

		assertThat(arcs).hasSize(2);
		assertThat(arcs).allSatisfy(command -> {
			assertThat(command.radiusX()).isEqualTo(1.0);
			assertThat(command.radiusY()).isEqualTo(1.0);
			assertThat(command.largeArc()).isFalse();
		});
	}

	@Test
	void rUsesPWithSquareRootFiveLeg() {
		var r = meshService.compose("R").rows().getFirst().getFirst();
		var leg = r.strokes().getLast().commands();
		var start = leg.getFirst().points().getFirst();
		var end = leg.getLast().points().getFirst();
		var deltaX = end.x() - start.x();
		var deltaY = end.y() - start.y();

		assertThat(deltaX * deltaX + deltaY * deltaY).isEqualTo(5.0);
	}

	@Test
	void sUsesTwoThreeQuarterCircleArcs() {
		var arcs = arcCommands("S");

		assertThat(arcs).hasSize(2);
		assertThat(arcs).allSatisfy(command -> {
			assertThat(command.radiusX()).isEqualTo(1.0);
			assertThat(command.radiusY()).isEqualTo(1.0);
			assertThat(command.largeArc()).isTrue();
		});
	}

	@Test
	void oUsesTwoHalfCirclesAndTwoVerticals() {
		var o = meshService.compose("O").rows().getFirst().getFirst();
		var arcs = arcCommands("O");
		var verticals = o.strokes().stream()
			.map(stroke -> stroke.commands())
			.filter(commands -> commands.size() == 2)
			.filter(commands -> commands.getFirst().points().getFirst().x() == commands.getLast().points().getFirst().x())
			.filter(commands -> Math.abs(commands.getLast().points().getFirst().y() - commands.getFirst().points().getFirst().y()) == 2.0)
			.toList();

		assertThat(o.strokes()).hasSize(4);
		assertThat(arcs).hasSize(2);
		assertThat(arcs).allSatisfy(command -> {
			assertThat(command.radiusX()).isEqualTo(1.0);
			assertThat(command.radiusY()).isEqualTo(1.0);
		});
		assertThat(verticals).hasSize(2);
	}

	@Test
	void gUsesTwoHalfCircleArcs() {
		var arcs = arcCommands("G");

		assertThat(arcs).hasSize(2);
		assertThat(arcs).allSatisfy(command -> {
			assertThat(command.radiusX()).isEqualTo(1.0);
			assertThat(command.radiusY()).isEqualTo(1.0);
			assertThat(command.largeArc()).isFalse();
		});
	}

	@Test
	void mAndWUseExactMiddleGridFormula() {
		var row = meshService.compose("MWY").rows().getFirst();
		var mCommands = row.getFirst().strokes().getFirst().commands();
		var wCommands = row.get(1).strokes().getFirst().commands();
		var yCommands = row.getLast().strokes().getFirst().commands();

		assertThat(mCommands.get(2).points().getFirst().x()).isEqualTo(1.0);
		assertThat(mCommands.get(2).points().getFirst().y()).isEqualTo(2.0);
		assertThat(wCommands.get(2).points().getFirst().x()).isEqualTo(1.0);
		assertThat(wCommands.get(2).points().getFirst().y()).isEqualTo(2.0);
		assertThat(yCommands.get(1).points().getFirst().x()).isEqualTo(1.0);
		assertThat(yCommands.get(1).points().getFirst().y()).isEqualTo(2.0);
	}

	@Test
	void qUsesCenterDiagonal() {
		var q = meshService.compose("Q").rows().getFirst().getFirst();
		var diagonal = q.strokes().getLast().commands();
		var start = diagonal.getFirst().points().getFirst();
		var end = diagonal.getLast().points().getFirst();
		var deltaX = end.x() - start.x();
		var deltaY = end.y() - start.y();

		assertThat(start.x()).isEqualTo(1.0);
		assertThat(start.y()).isEqualTo(2.0);
		assertThat(deltaX * deltaX + deltaY * deltaY).isEqualTo(5.0);
	}

	private List<PathCommand> arcCommands(String text) {
		return meshService.compose(text).rows().getFirst().stream()
			.flatMap(glyph -> glyph.strokes().stream())
			.flatMap(stroke -> stroke.commands().stream())
			.filter(command -> command.verb().equals("A"))
			.toList();
	}
}
