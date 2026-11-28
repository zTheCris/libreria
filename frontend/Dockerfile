FROM node:22 AS build

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

FROM node:22-slim

WORKDIR /app

COPY --from=build /app/dist ./dist
COPY package*.json ./

RUN npm ci --omit=dev

EXPOSE 4000

CMD ["npm", "run", "serve:ssr:Libreria"]
