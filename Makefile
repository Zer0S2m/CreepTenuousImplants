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
