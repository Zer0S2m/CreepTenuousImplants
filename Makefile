define generate-keys
openssl genpkey -out integration-main-system.pem -algorithm RSA -pkeyopt rsa_keygen_bits:2048
openssl rsa -in integration-main-system.pem -pubout -out integration-main-system.pub
endef

generate-keys:
	@$(generate-keys)
	mv integration-main-system.pub ./src/main/resources/keys

generate-keys-test:
	@$(generate-keys)
	mv integration-main-system.{pem,pub} ./src/test/resources/keys

view-pub:
	@cat ./src/main/resources/keys/integration-main-system.pub

init-migrate:
	@mvn clean flyway:baseline \
		 -Dflyway.url=jdbc:postgresql://${CT_POSTGRES_HOST}:${CT_POSTGRES_PORT}/${CT_POSTGRES_DB} \
		  -Dflyway.user=${CT_POSTGRES_USER} \
		  -Dflyway.password=${CT_POSTGRES_PASSWORD} \
		  -Dflyway.locations="filesystem:./src/main/resources/db/migrations" \
		  -Dflyway.table="flyway_schema_history_module_implants" \
		  -Dflyway.defaultSchema="public" \
		  -Dflyway.baselineOnMigrate="true"

migrate:
	@mvn clean flyway:migrate \
		  -Dflyway.url=jdbc:postgresql://${CT_POSTGRES_HOST}:${CT_POSTGRES_PORT}/${CT_POSTGRES_DB} \
		  -Dflyway.user=${CT_POSTGRES_USER} \
		  -Dflyway.password=${CT_POSTGRES_PASSWORD} \
		  -Dflyway.locations="filesystem:./src/main/resources/db/migrations" \
		  -Dflyway.table="flyway_schema_history_module_implants" \
		  -Dflyway.defaultSchema="public"