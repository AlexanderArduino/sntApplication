ALTER TABLE gardener DISABLE TRIGGER ALL;
ALTER TABLE roles DISABLE TRIGGER ALL;
ALTER TABLE "gardeners-roles" DISABLE TRIGGER ALL;

DELETE FROM "gardeners-roles" WHERE "gardeners-roles".gardener_id >= 1;
DELETE FROM roles WHERE id >= 1;
DELETE FROM gardener WHERE id >= 1;

INSERT INTO roles (id, rolename) VALUES (1, 'USER'),(2, 'ADMIN');
INSERT INTO gardener
    (id, username, password, last_name, first_name, middle_name, phone, email,
     is_member, date_enter, document_enter, date_out, reason_out,
     passport, address_registration, address_residential, note, is_archive)
VALUES
(1, 'admin', 'admin', 'Ivanov', 'Ivan', 'Ivanovich', '+79001234567', 'i.i.ivanov@mail.ru',
 true, '2000-1-1 00:00:00', 'Заявление на вступление', NULL, NULL, '4001 123456',
 '190000, г.Санкт-Петербург, ул.Регистрации д.1 корп.1 кв.1',
 '190000, г.Санкт-Петербург, ул.Проживания д.1 корп.1 кв.1', 'Председатель', false),

(2, 'sovet1', 'sovet1', 'Petrov', 'Petr', 'Petrovich', '+79011234567', 'p.p.petrov@mail.ru',
 true, '2000-1-2 00:00:00', 'Заявление на вступление', NULL, NULL, '4002 123456',
 '190000, г.Санкт-Петербург, ул.Регистрации д.1 корп.1 кв.2',
 '190000, г.Санкт-Петербург, ул.Проживания д.1 корп.1 кв.2', 'Совет', false),

(3, 'sovet2', 'sovet2', 'Sidorov', 'Sidor', 'Sidorovich', '+79021234567', 's.s.sidorov@mail.ru',
 true, '2000-1-3 00:00:00', 'Заявление на вступление', NULL, NULL, '4003 123456',
 '190000, г.Санкт-Петербург, ул.Регистрации д.1 корп.1 кв.3',
 '190000, г.Санкт-Петербург, ул.Проживания д.1 корп.1 кв.3', 'Совет', false),

(4, 'user1', 'user1', 'Kiselev', 'Andrey', 'Viktorovich', '+79031234567', 'a.v.kiselev@mail.ru',
 true, '2000-1-4 00:00:00', 'Заявление на вступление', NULL, NULL, '4004 123456',
 '190000, г.Санкт-Петербург, ул.Регистрации д.1 корп.1 кв.4',
 '190000, г.Санкт-Петербург, ул.Проживания д.1 корп.1 кв.4', 'Садовод', false),

(5, 'user2', 'user2', 'Topuriya', 'Tash', 'Pileevich', '+79041234567', 't.p.topuria@mail.ru',
 true, '2000-1-5 00:00:00', 'Заявление на вступление', NULL, NULL, '4005 123456',
 '190000, г.Санкт-Петербург, ул.Регистрации д.1 корп.1 кв.5',
 '190000, г.Санкт-Петербург, ул.Проживания д.1 корп.1 кв.5', 'Садовод', false);
INSERT INTO "gardeners-roles" (gardener_id, role_id)
VALUES (1, 1), (1, 2), (2, 1), (2, 2), (3, 1), (3, 2), (4, 1), (5, 1);

ALTER TABLE gardener ENABLE TRIGGER ALL;
ALTER TABLE roles ENABLE TRIGGER ALL;
ALTER TABLE "gardeners-roles" ENABLE TRIGGER ALL;