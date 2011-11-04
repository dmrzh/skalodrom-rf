alter table "Image" drop column imagedata ;
alter table "Image" add column imagedata bytea;
UPDATE "SysProperties" SET value= '0/2' where key='database_version'