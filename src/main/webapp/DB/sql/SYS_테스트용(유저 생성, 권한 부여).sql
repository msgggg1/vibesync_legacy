-- vibesync_springlegacy

CREATE USER vibesync_springlegacy IDENTIFIED BY 1234;
GRANT CREATE SESSION TO vibesync_springlegacy;
GRANT CREATE TABLE, CREATE VIEW, CREATE PROCEDURE TO vibesync_springlegacy;
ALTER USER vibesync_springlegacy DEFAULT TABLESPACE users QUOTA UNLIMITED ON users;
GRANT CREATE SEQUENCE TO vibesync_springlegacy;
GRANT CREATE TRIGGER TO vibesync_springlegacy;

-- DROP USER vibesync_springlegacy CASCADE;