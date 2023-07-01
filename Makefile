generate-keys:
	openssl genpkey -out integration-main-system.pem -algorithm RSA -pkeyopt rsa_keygen_bits:2048
	openssl rsa -in integration-main-system.pem -pubout -out integration-main-system.pub

	mv integration-main-system.{pem,pub} ./src/main/resources/keys

view-pub:
	@cat ./src/main/resources/keys/integration-main-system.pub
