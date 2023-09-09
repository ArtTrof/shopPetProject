FROM oven/bun
WORKDIR /app

COPY . .
RUN bun i --silent
RUN bun run build

EXPOSE 8080

CMD bun run start