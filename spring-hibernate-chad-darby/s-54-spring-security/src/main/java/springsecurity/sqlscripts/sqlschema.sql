DROP DATABASE  IF EXISTS `spring_security_demo`;

CREATE DATABASE  IF NOT EXISTS `spring_security_demo`;
USE `spring_security_demo`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `username` varchar(50) NOT NULL,
    `password` varchar(68) NOT NULL,
    `enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--

INSERT INTO `users`
VALUES
    ('john','{bcrypt}$2y$12$AaUBkppms7XtKrHjdvdaqOuMFsVNTSOP/Df6ZDSRL9Y.JD2sCqrtS',1),
    ('mary','{bcrypt}$2y$12$Wb.tbrIYfkmUK7vw3n3vLOQz.D/hnzq4yJ73xL7jbflDio1SVbJ5K',1),
    ('susan','{bcrypt}$2y$12$JyaF2aEtBD5vbqw.6Pl3veHF6kbsNNc29QAVsE0zeCUuHfrVgRuXG',1);
-- {noop} in password fields means plain text - passwords are not encrypted
-- bcrypt encrypted password has a length of 60 characters + encryption code {bcrypt}
-- actual password is 12345

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
        `username` varchar(50) NOT NULL,
        `authority` varchar(50) NOT NULL,
        UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
        CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities`
VALUES
    ('john','ROLE_EMPLOYEE'),
    ('mary','ROLE_EMPLOYEE'),
    ('mary','ROLE_MANAGER'),
    ('susan','ROLE_EMPLOYEE'),
    ('susan','ROLE_ADMIN');


