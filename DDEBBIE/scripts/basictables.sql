DROP DATABASE IF EXISTS `ddebbie_courier`;
CREATE DATABASE  IF NOT EXISTS `ddebbie_courier` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ddebbie_courier`;

--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(250) DEFAULT NULL,
  `password` varchar(250) NOT NULL,
  `email` varchar(500) DEFAULT NULL,
  `displayname` varchar(250) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `profileImageFileId` int(11) NOT NULL,
  `active` tinyint(4) DEFAULT '0' COMMENT 'active/inactive',
  `cts` datetime DEFAULT NULL,
  `mts` datetime DEFAULT NULL,
  `createdby` int(11) DEFAULT '0',
  `modifiedby` int(11) DEFAULT '0',
  `deleted` tinyint(4) DEFAULT '0' COMMENT 'true / false',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `userid` int(11) DEFAULT '0',
  `type` tinyint(4) DEFAULT '0' COMMENT '1 -  admin, 2 - normal',
  `cts` datetime DEFAULT NULL,
  `mts` datetime DEFAULT NULL,
  `createdby` int(11) DEFAULT '0',
  `modifiedby` int(11) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0' COMMENT 'true / false',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='authorized users for admins, ';
DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(2000) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `path` varchar(2000) DEFAULT NULL COMMENT 'relative path',
  `cts` datetime DEFAULT NULL,
  `mts` datetime DEFAULT NULL,
  `createdby` int(11) DEFAULT '0',
  `modifiedby` int(11) DEFAULT '0',
  `deleted` tinyint(4) DEFAULT '0' COMMENT 'true / false',
  `fileHomePath` varchar(255) DEFAULT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='all the files information are stored in this table';

--
-- Table structure for table `hibernate_sequences`
--
DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
   `sequence_name`  varchar(500) NOT NULL DEFAULT 'DDEBBIE_SEQ',
   `next_val` int(11) NOT NULL,
	PRIMARY KEY (`sequence_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
