CREATE DATABASE IF NOT EXISTS easyorder default character set utf8;


CREATE USER easyorder IDENTIFIED BY 'easyorder';
grant  SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, LOCK TABLES, EXECUTE on console.* to easyorder@"%" Identified by "easyorder";
flush privileges;