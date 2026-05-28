import { expect, test } from "@playwright/test";

function sanitizeText(text: string) {
  const normalized = text.toUpperCase().replace(/[^A-Z\s]/g, "").replace(/\s+/g, " ").trim();
  return normalized || "ASHER BLOOM";
}

function glyph(letter: string) {
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

function mesh(text: string) {
  const normalized = sanitizeText(text);
  return {
    text: normalized,
    rows: normalized.split(" ").map((row) => Array.from(row).map(glyph))
  };
}

test.beforeEach(async ({ page }) => {
  await page.route("**/api/mesh**", async (route) => {
    const url = new URL(route.request().url());
    const text = url.searchParams.get("text") ?? "ASHER BLOOM";
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(mesh(text))
    });
  });

  await page.goto("/");
  await expect(page.getByRole("img", { name: "ASHER BLOOM wordmark" })).toBeVisible();
});

test("creates a wordmark from brand input", async ({ page }) => {
  await page.getByLabel("Brand text").fill("nova mark 2026!");
  await expect(page.getByLabel("Brand text")).toHaveValue("NOVA MARK ");
  await page.getByRole("button", { name: /render wordmark/i }).click();
  await expect(page.getByRole("img", { name: "NOVA MARK wordmark" })).toBeVisible();
});

test("updates construction, alignment, scale, and theme controls", async ({ page }) => {
  expect(await page.locator(".logo-svg line").count()).toBeGreaterThan(0);
  await page.getByRole("button", { name: /grid/i }).click();
  await expect(page.locator(".logo-svg line")).toHaveCount(0);
  await page.getByRole("button", { name: /center/i }).click();
  await expect(page.getByRole("button", { name: /center/i })).toHaveClass(/active/);
  await page.locator("input[type='range']").first().fill("160");
  await expect(page.getByText("160%")).toBeVisible();
  await page.getByRole("button", { name: /custom/i }).click();
  await page.getByLabel("Stroke", { exact: true }).fill("#cc00ff");
  await page.getByLabel("Background", { exact: true }).fill("#002244");
  await expect(page.getByLabel("Stroke", { exact: true })).toHaveValue("#cc00ff");
  await expect(page.getByLabel("Background", { exact: true })).toHaveValue("#002244");
});

test("exports the current wordmark as svg", async ({ page }) => {
  await page.getByLabel("Brand text").fill("nova mark");
  await page.getByRole("button", { name: /render wordmark/i }).click();
  await expect(page.getByRole("img", { name: "NOVA MARK wordmark" })).toBeVisible();

  const downloadPromise = page.waitForEvent("download");
  await page.getByRole("button", { name: /^SVG$/ }).click();
  const download = await downloadPromise;

  expect(download.suggestedFilename()).toBe("nova-mark.svg");
});
