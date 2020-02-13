--Use to run mysl db docker image, optional if youre not using a local mysqldb
-- docker run --name mysqldb -p 3306: 3306 e MYSQL_ALLOW_EMPTY_PAsSWORD=yes -d mysql

-- Connect to mysl and run as root user
-- Create Dabases
CREATE DATABASE sfg_dev;
CREATE DATABASE sfg_prod;

-- Create dabase service accounts
CREATE USER 'sfg_dev_user'@'localhost' IDENTIFIED BY 'p@ssw0rd';
CREATE USER 'sfg_prod_user'@'localhost' IDENTIFIED BY 'p@ssw0rd';

--If on docker
--CREATE USER 'sfg_dev_user'@'%' IDENTIFIED BY 'p@ssw0rd';
--CREATE USER 'sfg_prod_user'@'%' IDENTIFIED BY 'p@ssw0rd';


-- Database grants
GRANT SELECT ON sfg_dev.* to 'sfg_dev_user'@'localhost';
GRANT INSERT ON sfg_dev.* to 'sfg_dev_user'@'localhost';
GRANT UPDATE ON sfg_dev.* to 'sfg_dev_user'@'localhost';
GRANT DELETE ON sfg_dev.* to 'sfg_dev_user'@'localhost';

GRANT SELECT ON sfg_prod.* to 'sfg_prod_user'@'localhost';
GRANT INSERT ON sfg_prod.* to 'sfg_prod_user'@'localhost';
GRANT UPDATE ON sfg_prod.* to 'sfg_prod_user'@'localhost';
GRANT DELETE ON sfg_prod.* to 'sfg_prod_user'@'localhost';

--If on docker
--GRANT SELECT ON sfg_dev.* to 'sfg_dev_user'@'%';
--GRANT INSERT ON sfg_dev.* to 'sfg_dev_user'@'%';
--GRANT UPDATE ON sfg_dev.* to 'sfg_dev_user'@'%';
--GRANT DELETE ON sfg_dev.* to 'sfg_dev_user'@'%';

--GRANT SELECT ON sfg_prod.* to 'sfg_prod_user'@'%';
--GRANT INSERT ON sfg_prod.* to 'sfg_prod_user'@'%';
--GRANT UPDATE ON sfg_prod.* to 'sfg_prod_user'@'%';
--GRANT DELETE ON sfg_prod.* to 'sfg_prod_user'@'%';