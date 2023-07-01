# CreepTenuousImplants

The project requires a deployed system [CreepTenuous](https://github.com/Zer0S2m/CreepTenuous).

Before starting, specify the environment variables that are in the main system.

An important environment variable is **`CT_CRON_CLEAN_STORAGE`** and **`CT_CRON_CLEAN_REDIS`**. 
Installation [format](https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm)

Env params:

| Env param                        | Value                                                                       | Example       |
|----------------------------------|-----------------------------------------------------------------------------|---------------|
| **`CT_CRON_CLEAN_STORAGE`**      | Installing a task scheduler to clean up file storage                        | `0 0 0 * * *` |
| **`CT_CRON_CLEAN_REDIS`**        | Installing a task scheduler to clean up Redis                               | `0 0 0 * * *` |
| **`IS_INTEGRATION_MAIN_SYSTEM`** | Open a service for integration with the main system (activates controllers) | `true`        |
