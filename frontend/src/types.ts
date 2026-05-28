export type MeshPoint = {
  x: number;
  y: number;
};

export type PathCommand = {
  verb: "M" | "L" | "A";
  points: MeshPoint[];
  radiusX?: number | null;
  radiusY?: number | null;
  rotation?: number | null;
  largeArc?: boolean | null;
  sweep?: boolean | null;
};

export type Stroke = {
  commands: PathCommand[];
};

export type GridSpec = {
  columns: number;
  rows: number;
};

export type GlyphMesh = {
  letter: string;
  grid: GridSpec;
  strokes: Stroke[];
};

export type WordMesh = {
  text: string;
  rows: GlyphMesh[][];
};
