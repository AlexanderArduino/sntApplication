ALTER TABLE gardener DISABLE TRIGGER ALL;
ALTER TABLE roles DISABLE TRIGGER ALL;
ALTER TABLE "gardeners-roles" DISABLE TRIGGER ALL;

DELETE FROM "gardeners-roles" WHERE "gardeners-roles".gardener_id >= 1;
DELETE FROM roles WHERE id >= 1;
DELETE FROM gardener WHERE id >= 1;

INSERT INTO roles (id, rolename) VALUES (1, 'USER'),(2, 'ADMIN');

ALTER TABLE gardener ENABLE TRIGGER ALL;
ALTER TABLE roles ENABLE TRIGGER ALL;
ALTER TABLE "gardeners-roles" ENABLE TRIGGER ALL;