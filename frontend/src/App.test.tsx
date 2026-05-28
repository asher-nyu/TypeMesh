import { cleanup, fireEvent, render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { afterEach, beforeEach, describe, expect, test, vi } from "vitest";
import App from "./App";
import type { GlyphMesh, WordMesh } from "./types";

function testGlyph(letter: string): GlyphMesh {
  return {
    letter,
    grid: { columns: 2, rows: 4 },
    strokes: [
      {
        commands: [
          { verb: "M", points: [{ x: 0, y: 0 }] },
          { verb: "L", points: [{ x: 2, y: 4 }] }
        ]
      }
    ]
  };
}

function sanitizeText(text: string) {
  const normalized = text.toUpperCase().replace(/[^A-Z\s]/g, "").replace(/\s+/g, " ").trim();
  return normalized || "ASHER BLOOM";
}

function testMesh(text: string): WordMesh {
  const normalized = sanitizeText(text);
  return {
    text: normalized,
    rows: normalized.split(" ").map((row) => Array.from(row).map(testGlyph))
  };
}

function installFetchMock() {
  const fetchMock = vi.fn((input: RequestInfo | URL) => {
    const url = new URL(String(input), "http://localhost");
    const text = url.searchParams.get("text") ?? "ASHER BLOOM";
    return Promise.resolve(new Response(JSON.stringify(testMesh(text)), {
      status: 200,
      headers: { "Content-Type": "application/json" }
    }));
  });

  vi.stubGlobal("fetch", fetchMock);
  return fetchMock;
}

describe("TypeMesh app", () => {
  beforeEach(() => {
    window.localStorage.clear();
    installFetchMock();
  });

  afterEach(() => {
    cleanup();
    window.localStorage.clear();
    vi.restoreAllMocks();
    vi.unstubAllGlobals();
  });

  test("renders uppercase brand text from user input", async () => {
    const user = userEvent.setup();
    render(<App />);

    await screen.findByRole("img", { name: "ASHER BLOOM wordmark" });
    const input = screen.getByLabelText("Brand text");

    await user.clear(input);
    await user.type(input, "orbit labs 2026!");

    expect(input).toHaveValue("ORBIT LABS ");

    await user.click(screen.getByRole("button", { name: /render wordmark/i }));

    await waitFor(() => {
      expect(screen.getByRole("img", { name: "ORBIT LABS wordmark" })).toBeInTheDocument();
    });
  });

  test("toggles construction grid and alignment controls", async () => {
    const user = userEvent.setup();
    const { container } = render(<App />);

    await screen.findByRole("img", { name: "ASHER BLOOM wordmark" });

    expect(container.querySelectorAll(".logo-svg line").length).toBeGreaterThan(0);

    await user.click(screen.getByRole("button", { name: /grid/i }));

    expect(screen.getByRole("button", { name: /grid/i })).toHaveAttribute("aria-pressed", "false");
    expect(container.querySelectorAll(".logo-svg line")).toHaveLength(0);

    await user.click(screen.getByRole("button", { name: /right/i }));

    expect(screen.getByRole("button", { name: /right/i })).toHaveClass("active");
  });

  test("shows custom color and opacity controls", async () => {
    const user = userEvent.setup();
    render(<App />);

    await screen.findByRole("img", { name: "ASHER BLOOM wordmark" });
    await user.click(screen.getByRole("button", { name: /custom/i }));

    const strokeInput = screen.getByLabelText(/^Stroke$/i);
    const backgroundInput = screen.getByLabelText(/^Background$/i);

    fireEvent.input(strokeInput, { target: { value: "#cc00ff" } });
    fireEvent.input(backgroundInput, { target: { value: "#002244" } });

    expect(strokeInput).toHaveValue("#cc00ff");
    expect(backgroundInput).toHaveValue("#002244");
    expect(screen.getByLabelText(/stroke opacity/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/background opacity/i)).toBeInTheDocument();
  });

  test("exports an svg download from the current wordmark", async () => {
    const user = userEvent.setup();
    const createObjectURL = vi.fn(() => "blob:typemesh");
    const revokeObjectURL = vi.fn();
    const clickSpy = vi.spyOn(HTMLAnchorElement.prototype, "click").mockImplementation(() => {});

    Object.assign(URL, { createObjectURL, revokeObjectURL });

    render(<App />);

    await screen.findByRole("img", { name: "ASHER BLOOM wordmark" });
    await user.click(screen.getByRole("button", { name: /^SVG$/i }));

    expect(createObjectURL).toHaveBeenCalled();
    expect(clickSpy).toHaveBeenCalled();
    expect(revokeObjectURL).toHaveBeenCalledWith("blob:typemesh");
  });
});
