package com.typemesh.backend.mesh;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PathCommand(String verb, List<MeshPoint> points, Double radiusX, Double radiusY, Double rotation, Boolean largeArc, Boolean sweep) {
}
