USE master;
IF (EXISTS (SELECT name FROM sys.databases WHERE name = 'fouaddb'))
    -- "SET SINGLE_USER": This puts the database in single-user mode hence no new users can connect.
    -- "WITH ROLLBACK IMMEDIATE" This option will rollback all the open transactions. This option does not wait for the transaction to complete.
ALTER DATABASE fouaddb SET SINGLE_USER WITH ROLLBACK IMMEDIATE;

DROP DATABASE IF EXISTS fouaddb;
-- Collation: Arabic-100, case-sensitive, accent-sensitive, kanatype-sensitive, width-sensitive, supplementary characters, UTF8 encoded data
CREATE DATABASE fouaddb COLLATE Arabic_100_CS_AS_KS_WS_SC_UTF8;
ALTER LOGIN sa WITH DEFAULT_DATABASE = fouaddb;
USE fouaddb;