
DROP TABLE IF EXISTS `t_b_user_info`;
CREATE TABLE `t_b_user_info` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `login_name` varchar(100) DEFAULT NULL,
  `login_pwd` varchar(100) DEFAULT NULL,
  `user_status` int(11) DEFAULT NULL COMMENT '用户状态(0:初始化，1：正常，2：冻结，3：过期 4:删除)',
  `parent_id` bigint(11) DEFAULT NULL,
  `login_invallid` timestamp NULL DEFAULT NULL,
  `last_login_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

LOCK TABLES `t_b_user_info` WRITE;
INSERT INTO `t_b_user_info` VALUES (1,'chenbiao','bill','a02f6f0009b898916d0794508cd508652df76d3e1093e9198b2e2b84802a4b92',1,NULL,'2019-02-08 09:30:13','2018-02-08 10:20:56','2018-02-08 10:20:56','2018-02-08 10:20:56');
UNLOCK TABLES;
