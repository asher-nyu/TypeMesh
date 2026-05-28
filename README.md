# TypeMesh

[Live Demo](https://typemesh.onrender.com)

TypeMesh is a full-stack wordmark design tool for creating geometric typographic logos. Users enter a brand name and TypeMesh renders uppercase letterforms from clean grid-based geometry: lines, arcs, circles, and proportional curves.

The product is built for logo design, brand identity exploration, and export-ready typography workflows.

## Product

TypeMesh turns typed brand names into custom SVG wordmarks. Each letter is generated from its own geometric formula, so the shapes can be refined precisely without breaking the rest of the alphabet. The interface supports alignment, scaling, construction-grid visibility, theme controls, custom stroke and background colors, and SVG, PNG, and PDF export.

## Structure

- `frontend` - React, TypeScript, Vite, and SVG rendering for the wordmark editor.
- `backend` - Java Spring Boot API for glyph and wordmark generation support.
- `Dockerfile` - production build that serves the frontend through the Spring Boot app.
- `package.json` - root scripts for the local development workflow.

## Install Locally

Clone the GitHub repository into the current local folder. The current folder should be empty before running this command:

```sh
git clone git@github.com:asher-nyu/TypeMesh.git .
```

Requirements:

- Node.js 20+
- Java 21+
- Maven 3.9+

Install frontend dependencies from the repository root:

```sh
npm install
```

Install backend dependencies through Maven:

```sh
cd backend
mvn dependency:resolve
```

## Run Locally

Start the Spring Boot backend:

```sh
cd backend
mvn spring-boot:run
```

In a second terminal, start the Vite frontend from the repository root:

```sh
npm run frontend:dev
```

Open the local app at [http://127.0.0.1:5173](http://127.0.0.1:5173).

The Spring Boot backend runs at [http://localhost:8080](http://localhost:8080), but this address is for API requests, not the local frontend page.

## Testing

Run the backend JUnit, Spring Boot Test, MockMvc, Mockito, and JaCoCo workflow:

```sh
npm run backend:coverage
```

Run the frontend Vitest and React Testing Library workflow:

```sh
npm run frontend:coverage
```

Install Playwright browsers before the first local end-to-end run:

```sh
cd frontend
npx playwright install chromium firefox webkit
cd ..
```

Run the Playwright end-to-end suite:

```sh
npm run frontend:e2e
```

Run the full local CI workflow:

```sh
npm run ci
```

## Deployment

TypeMesh is deployed as a single Docker-based Render web service.
