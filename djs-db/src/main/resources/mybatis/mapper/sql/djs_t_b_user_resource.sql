
DROP TABLE IF EXISTS `t_b_user_resource`;
CREATE TABLE `t_b_user_resource` (
  `user_resource_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  `active` int(11) NOT NULL,
  `create_time` timestamp NULL,
  `update_time` timestamp NULL,
  PRIMARY KEY (`user_resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
LOCK TABLES `t_b_user_resource` WRITE;
INSERT INTO `t_b_user_resource` VALUES
(1,1,1,1,'2018-02-08 10:12:02','2018-02-08 10:12:05'),(2,1,2,1,'2018-02-08 10:15:42','2018-02-08 10:15:45');
UNLOCK TABLES;
