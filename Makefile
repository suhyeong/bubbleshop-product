container-up:
	docker compose -p product-local --project-directory . -f src/main/resources/db/container/docker-compose.yaml up -d

container-down:
	docker compose -p product-local down --remove-orphans --volumes

container-logs:
	docker compose -p product-local logs -f