CREATE DOMAIN type_object_deleted
    AS VARCHAR
    CONSTRAINT type_object_deleted
        CHECK (VALUE IN (
                         'DIRECTORY',
                         'FILE',
                         'DIRECTORY_REDIS',
                         'FILE_REDIS',
                         'RIGHT_USER',
                         'UNIDENTIFIED_TYPE'
            ));

CREATE TABLE IF NOT EXISTS "deleted_object_statistics"
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    user_login  VARCHAR(30)           NOT NULL DEFAULT 'NOT_LOGIN',
    created_at  TIMESTAMP             NOT NULL DEFAULT NOW(),
    type_object type_object_deleted   NOT NULL DEFAULT 'UNIDENTIFIED_TYPE',
    system_name VARCHAR               NOT NULL DEFAULT 'NOT_NAME',
    system_path TEXT                  NOT NULL DEFAULT 'NO_PATH'
);
