--climb time status
ALTER TABLE "ClimbTime" ADD COLUMN emailCode character varying(32);

UPDATE "SysProperties" SET value= '0/1' where key='database_version';