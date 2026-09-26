# Portfolio frontend

React 19 and Vite frontend for the portfolio landing page and its protected admin workspace.

## Requirements and local run

- Node.js 22 or a supported current LTS release
- npm
- The backend running at http://localhost:8080

~~~powershell
Copy-Item .env.example .env
npm ci
npm run dev
~~~

Open http://localhost:5173. The API base URL is read from VITE_API_BASE_URL in .env and must include /api. The default is http://localhost:8080/api.

The page uses the About fallback already present in the source repository when the database has no About record. No example projects, skills, experience, education, or certificates are fabricated; empty sections explain how to add data in the admin workspace.

## Admin workspace

Open /login and sign in with an account provisioned by the backend. The admin route checks for a login token and the API enforces ROLE_ADMIN for all /api/admin/** requests. The frontend sends the JWT as an Authorization: Bearer header and clears it when the API returns 401.

The admin workspace manages About, projects, skills, experience, education, and certifications. About, projects, and skills preserve the source UI/API concepts. Experience completes an existing backend API and a dashboard link whose frontend route was missing. Education and certifications are additions described in the root README.

## Render static-site deployment

The included render.yaml defines a Vite static site, SPA rewrite, and basic response headers.

1. Create a Render Static Site from this repository, or deploy the included Blueprint.
2. Set the build command to npm ci && npm run build and the publish directory to dist.
3. Set VITE_API_BASE_URL to https://YOUR-API.onrender.com/api at build time.
4. Copy the exact frontend HTTPS origin into the backend CORS_ALLOWED_ORIGINS variable and redeploy the API.
5. Open the frontend URL and sign in at /login after the backend is ready.

Render static sites use a build command and a published output directory; Vite builds to dist. The included rewrite routes browser refreshes to index.html. See [Render’s Blueprint reference](https://render.com/docs/blueprint-spec) and [static-site guide](https://render.com/docs/static-sites).

## Docker alternative

The Dockerfile builds the Vite app and serves dist through Nginx. VITE_API_BASE_URL is a build argument because it is embedded in the static JavaScript bundle:

~~~powershell
docker build --build-arg VITE_API_BASE_URL=https://YOUR-API.onrender.com/api -t portfolio-frontend .
docker run --rm -p 8081:80 portfolio-frontend
~~~

The app is then available at http://localhost:8081. Configure the backend CORS allowlist for this origin if the container is used locally.

## Content and API

The landing page loads About, skills, projects, experience, education, and certifications from the public API. Public content remains readable without an admin token. Admin management endpoints use the JWT login flow from the original backend. The contact section uses the saved email, phone, GitHub, and LinkedIn fields; it does not store messages.
