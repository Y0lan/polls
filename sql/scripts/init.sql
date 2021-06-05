CREATE DATABASE IF NOT EXISTS polls;


CREATE TABLE IF NOT EXISTS `roles` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `name` varchar(60) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `uk_roles_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');
