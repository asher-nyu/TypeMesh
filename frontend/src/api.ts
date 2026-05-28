import type { WordMesh } from "./types";

export async function fetchMesh(text: string): Promise<WordMesh> {
  const response = await fetch(`/api/mesh?text=${encodeURIComponent(text)}`);

  if (!response.ok) {
    throw new Error("Mesh request failed");
  }

  return response.json();
}
