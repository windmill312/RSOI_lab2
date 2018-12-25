psql -h localhost -U postgres
CREATE database db_user;
CREATE role program WITH password 'test';
GRANT ALL PRIVILEGES ON database db_user TO program;
ALTER role program WITH login;
--
CREATE database db_flight;
CREATE role program WITH password 'test';
GRANT ALL PRIVILEGES ON database db_flight TO program;
ALTER role program WITH login;
--
CREATE database db_ticket;
CREATE role program WITH password 'test';
GRANT ALL PRIVILEGES ON database db_ticket TO program;
ALTER role program WITH login;
--
CREATE database db_route;
CREATE role program WITH password 'test';
GRANT ALL PRIVILEGES ON database db_route TO program;
ALTER role program WITH login;


psql
\connect db_user;
INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

