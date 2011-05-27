--climb time status
ALTER TABLE "ClimbTime" ADD COLUMN status character varying(7) NOT NULL DEFAULT 'OPEN';

INSERT INTO "SysProperties"("key", "value")VALUES ('database_version', '0/0');