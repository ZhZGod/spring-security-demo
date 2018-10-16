
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zzh', '$2a$10$DWXT7wHLFhI.WtZCoIa9VenpfVImpo5i2vwyGt8NFQHmddSg5Kk22', 'zzh', 'ROLE_USER');
INSERT INTO `user` VALUES ('2', 'zzh2', '$2a$10$8w7CSg4ffAM/jjuyWIBtcuIa.bq0RrXSMfrAgEjyKYMFuRNc1xT8y', 'zzh2', 'ROLE_USER');
