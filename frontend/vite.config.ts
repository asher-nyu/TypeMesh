import react from "@vitejs/plugin-react";
import { defineConfig } from "vitest/config";

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      "/api": "http://localhost:8080"
    }
  },
  test: {
    environment: "jsdom",
    setupFiles: "src/test/setup.ts",
    exclude: ["tests/**", "node_modules/**", "dist/**"],
    coverage: {
      provider: "v8",
      reporter: ["text", "html", "lcov"],
      exclude: ["src/main.tsx", "src/style.d.ts"]
    }
  }
});
