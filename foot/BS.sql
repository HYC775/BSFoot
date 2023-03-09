/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.37 : Database - bs_food
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bs_food` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bs_food`;

/*Table structure for table `collection` */

DROP TABLE IF EXISTS `collection`;

CREATE TABLE `collection` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `uid` int(20) DEFAULT NULL,
  `fid` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `collection` */

insert  into `collection`(`id`,`uid`,`fid`) values (1,1,1),(2,1,2);

/*Table structure for table `dalei` */

DROP TABLE IF EXISTS `dalei`;

CREATE TABLE `dalei` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '大类id',
  `name` varchar(100) DEFAULT NULL COMMENT '类别名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `dalei` */

insert  into `dalei`(`id`,`name`) values (1,'菜肴1'),(2,'糕点'),(3,'面点'),(4,'休闲零食'),(5,'干果'),(6,'饮品'),(7,'红烧'),(8,'汤品'),(9,'特色'),(10,'蒸类'),(11,'寿司大卷');

/*Table structure for table `f_dalei` */

DROP TABLE IF EXISTS `f_dalei`;

CREATE TABLE `f_dalei` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `fid` varchar(10) DEFAULT NULL,
  `daleiid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `f_dalei` */

/*Table structure for table `f_xiaolei` */

DROP TABLE IF EXISTS `f_xiaolei`;

CREATE TABLE `f_xiaolei` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `fid` int(10) DEFAULT NULL,
  `xiaolei_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `f_xiaolei` */

insert  into `f_xiaolei`(`id`,`fid`,`xiaolei_id`) values (4,2,2),(5,2,3),(6,2,4),(22,1,1),(23,1,2);

/*Table structure for table `foot` */

DROP TABLE IF EXISTS `foot`;

CREATE TABLE `foot` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '美食id',
  `footname` varchar(50) DEFAULT NULL COMMENT '标题',
  `content` varchar(5000) DEFAULT NULL COMMENT '美食内容',
  `imgs` varchar(1000) DEFAULT NULL COMMENT '图片',
  `userid` int(50) DEFAULT NULL COMMENT '发布人id',
  `userName` varchar(100) DEFAULT NULL COMMENT '上传人',
  `datatime` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `cailiao` varchar(500) DEFAULT NULL COMMENT '材料清单',
  `zhizuofangfa` varchar(2000) DEFAULT NULL COMMENT '制作步骤',
  `dalei_id` int(10) DEFAULT NULL COMMENT '大类id',
  `state` varchar(10) DEFAULT NULL COMMENT '审核状态',
  `guid` varchar(100) DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

/*Data for the table `foot` */

insert  into `foot`(`id`,`footname`,`content`,`imgs`,`userid`,`userName`,`datatime`,`cailiao`,`zhizuofangfa`,`dalei_id`,`state`,`guid`) values (1,'葱爆大虾','xxxx','1.jpeg',1,'admin','2022-11-05','材料1、材料二、材料三.......','制作步骤.......',1,'1','40c8361e-c21a-49ec-b0ee-110316bbb634'),(2,'糖醋里脊','xxxx','2.jpeg',1,'admin','2022-11-05','材料1、材料二、材料三.......','制作步骤.......',1,'1',NULL),(3,'回锅肉','xxxxx','3.jpeg',1,'admin','2022-11-05','材料1、材料二、材料三.......','制作步骤.......',1,'1',NULL),(4,'家常菜','xxxxxx','4.jpeg',1,'admin','2022-11-05','材料1、材料二、材料三.......','制作步骤.......',1,'1',NULL),(78,'秘制汤品1','xxxxxx','54.jpg',2,'commen',NULL,'材料1、材料二、材料三.......','制作步骤.......',8,'1',NULL),(79,'秘制汤品2','xxxxxx','20230219034339000000141.jpg',2,'commen',NULL,'材料1、材料二、材料三.......','制作步骤.......',8,'1',NULL),(80,'秘制汤品3','xxxxxx','56.jpg',1,'admin',NULL,'材料1、材料二、材料三.......','制作步骤.......',8,'1',NULL),(81,'秘制汤品4','xxxxxx','57.jpg',1,'admin',NULL,'材料1、材料二、材料三.......','制作步骤.......',8,'1',NULL),(82,'秘制汤品5','xxxxxx','58.jpg',2,'commen',NULL,'材料1、材料二、材料三.......','制作步骤.......',8,'1',NULL),(83,'特色美食1','xxxxxx','61.jpg',1,'admin',NULL,'材料1、材料二、材料三.......','制作步骤.......',9,'1',NULL),(84,'特色美食2','xxxxxx','62.jpg',1,'admin',NULL,'材料1、材料二、材料三.......','制作步骤.......',9,'1',NULL),(85,'特色美食3','xxxxxx','63.jpg',1,'admin',NULL,'材料1、材料二、材料三.......','制作步骤.......',9,'1',NULL),(86,'特色美食4','xxxxxx','64.jpg',1,'admin',NULL,'材料1、材料二、材料三.......','制作步骤.......',9,'1',NULL);

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单主键ID',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID',
  `order_num` int(11) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `perms` varchar(100) DEFAULT '' COMMENT '权限标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

/*Data for the table `menu` */

insert  into `menu`(`id`,`name`,`icon`,`parent_id`,`order_num`,`path`,`component`,`menu_type`,`perms`,`create_time`,`update_time`,`remark`) values (1,'系统管理','system',0,1,'/sys','','M','','2022-07-04 14:56:29','2022-07-04 14:56:31','系统管理目录'),(2,'美食数据','monitor',0,2,'/bsns','','M','','2022-07-04 14:59:43','2023-02-04 16:31:55','业务管理目录'),(3,'用户管理','user',1,1,'/sys/user','sys/user/index','C','system:user:list','2022-07-04 15:20:51','2022-07-04 15:20:53','用户管理菜单'),(4,'角色管理','peoples',1,2,'/sys/role','sys/role/index','C','system:role:list','2022-07-04 15:23:35','2022-07-04 15:23:39','角色管理菜单'),(5,'菜单管理','treetable',1,3,'/sys/menu','sys/menu/index','C','system:menu:list','2022-07-04 15:23:41','2022-07-04 15:23:43','菜单管理菜单'),(6,'美食信息管理','tree',2,1,'/bsns/foots','bsns/foots/index','C','','2022-07-04 15:24:40','2023-02-04 16:34:24','部门管理菜单'),(7,'美食分类管理','post',2,2,'/bsns/bclass','bsns/bclass/index','C','','2022-07-04 15:24:42','2023-02-06 18:21:17','美食分类管理菜单'),(9,'用户修改','#',3,3,'','','F','system:user:edit','2022-07-04 15:24:42','2022-07-04 15:24:46','修改用户按钮'),(10,'用户删除','#',3,4,'','','F','system:user:delete','2022-07-04 15:24:42','2022-07-04 15:24:46','删除用户按钮'),(11,'分配角色','#',3,5,'','','F','system:user:role','2022-07-04 15:24:42','2022-07-04 15:24:46','分配角色按钮'),(12,'重置密码','#',3,6,'','','F','system:user:resetPwd','2022-07-04 15:24:42','2022-07-04 15:24:46','重置密码按钮'),(13,'角色新增','#',4,2,'','','F','system:role:add','2022-07-04 15:24:42','2022-07-04 15:24:46','添加用户按钮'),(14,'角色修改','#',4,3,'','','F','system:role:edit','2022-07-04 15:24:42','2022-07-04 15:24:46','修改用户按钮'),(15,'角色删除','#',4,4,'',NULL,'F','system:role:delete','2022-07-04 15:24:42','2022-07-04 15:24:46','删除用户按钮'),(16,'分配权限','#',4,5,'','','F','system:role:menu','2022-07-04 15:24:42','2022-07-04 15:24:46','分配权限按钮'),(17,'菜单新增','#',5,2,'',NULL,'F','system:menu:add','2022-07-04 15:24:42','2022-07-04 15:24:46','添加菜单按钮'),(18,'菜单修改','#',5,3,'',NULL,'F','system:menu:edit','2022-07-04 15:24:42','2022-07-04 15:24:46','修改菜单按钮'),(19,'菜单删除','#',5,4,'',NULL,'F','system:menu:delete','2022-07-04 15:24:42','2022-07-04 15:24:46','删除菜单按钮'),(20,'用户查询','#',3,1,'',NULL,'F','system:user:query','2022-07-04 15:24:42','2022-07-04 15:24:46','用户查询按钮'),(21,'角色查询','#',4,1,'',NULL,'F','system:role:query','2022-07-04 15:24:42','2022-07-04 15:24:46','角色查询按钮'),(22,'菜单查询','#',5,1,'',NULL,'F','system:menu:query','2022-07-04 15:24:42','2022-07-04 15:24:46','菜单查询按钮'),(39,'用户新增','',3,2,'','','F','system:user:add','2023-02-03 23:46:27',NULL,'用户新增按钮'),(41,'美食查询','',6,1,'','','F','system:foot:query','2023-02-05 16:29:23','2023-02-05 16:30:30','美食查询按钮'),(42,'美食新增','',6,2,'','','F','system:foot:add','2023-02-05 16:30:05','2023-02-05 16:31:33','美食新增按钮'),(43,'美食修改','',6,3,'','','F','system:foot:edit','2023-02-05 16:31:26',NULL,'美食修改按钮'),(44,'美食删除','',6,4,'','','F','system:foot:delete','2023-02-05 16:32:32','2023-02-06 21:05:04','美食删除按钮'),(47,'美食细类管理','post',2,3,'/bsns/xclass','bsns/xclass/index','C','','2023-02-06 21:11:13',NULL,'美食细类管理菜单'),(48,'分类新增','',7,1,'','','F','system:dalei:add','2023-02-06 21:22:46',NULL,NULL),(49,'分类查询','',7,2,'','','F','system:dalei:query','2023-02-06 21:23:09','2023-02-06 21:25:18',NULL),(50,'分类修改','',7,3,'','','F','system:dalei:edit','2023-02-06 21:23:33','2023-02-06 21:25:22',NULL),(51,'分类删除','',7,4,'','','F','system:dalei:delete','2023-02-06 21:23:48','2023-02-06 21:25:15',NULL),(52,'细类新增','',47,1,'','','F','system:xiaolei:add','2023-02-06 21:23:57','2023-02-06 21:25:02',NULL),(53,'细类修改','',47,2,'','','F','system:xiaolei:edit','2023-02-06 21:24:15','2023-02-06 21:25:06',NULL),(54,'细类查询','',47,3,'','','F','system:xiaolei:query','2023-02-06 21:24:30','2023-02-06 21:25:09',NULL),(55,'细类删除','',47,4,'','','F','system:xiaolei:delete','2023-02-06 21:24:49','2023-02-06 21:25:11',NULL),(58,'我的','user',0,3,'/my','','M','','2022-07-04 14:59:43','2023-02-04 16:31:55','个人操作目录'),(59,'我的收藏','',58,1,'/my/shoucang','my/shoucang/index','C','','2022-07-04 14:59:43','2023-02-04 16:31:55','我的收藏菜单'),(61,'我的发布','',58,2,'/my/fabu','my/fabu/index','C','','2023-02-19 22:58:02',NULL,'我的发布'),(62,'新增','',59,1,'','','F','system:shoucang:add','2023-02-19 23:02:31',NULL,NULL),(63,'修改','',59,2,'','','F','system:shoucang:edit','2023-02-19 23:04:07',NULL,NULL),(64,'查询','',59,3,'','','F','system:shoucang:query','2023-02-19 23:04:40','2023-02-19 23:05:21',NULL),(65,'删除','',59,4,'','','F','system:shoucang:delete','2023-02-19 23:05:10',NULL,NULL),(66,'新增','',61,1,'','','F','system:fabu:add','2023-02-19 23:06:00',NULL,NULL),(67,'修改','',61,2,'','','M','system:fabu:edit','2023-02-19 23:06:29','2023-02-19 23:07:17',NULL),(68,'查询','',61,3,'','','F','system:fabu:query','2023-02-19 23:07:00',NULL,NULL),(69,'删除','',61,4,'','','M','system:fabu:delete','2023-02-19 23:07:44',NULL,NULL);

/*Table structure for table `pinglun` */

DROP TABLE IF EXISTS `pinglun`;

CREATE TABLE `pinglun` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fid` int(10) DEFAULT NULL COMMENT '美食id',
  `neirong` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `username` varchar(100) DEFAULT NULL COMMENT '评论用户名称',
  `userimg` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `parentid` int(10) DEFAULT NULL COMMENT '父id',
  `uid` int(10) DEFAULT NULL COMMENT '用户id',
  `createDate` datetime DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `pinglun` */

insert  into `pinglun`(`id`,`fid`,`neirong`,`username`,`userimg`,`parentid`,`uid`,`createDate`) values (1,78,'好','admin','20221026112159000000680.jpg',0,1,'2023-02-23 22:46:21'),(2,78,'确实好','common','222.jpg',1,2,'2023-02-23 22:47:24'),(3,78,'我也这么觉得','test','333.jpg',1,3,'2023-02-23 22:47:28'),(5,78,'456456','admin','20221026112159000000680.jpg',2,1,'2023-02-24 19:15:11'),(6,78,'789','admin','20221026112159000000680.jpg',2,1,'2023-02-24 19:20:10'),(34,78,'我是1','admin','20221026112159000000680.jpg',0,1,'2023-02-24 23:30:11'),(35,78,'我是2','admin','20221026112159000000680.jpg',0,1,'2023-02-25 00:16:01'),(36,78,NULL,'admin','20221026112159000000680.jpg',0,1,'2023-02-28 22:04:32');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`code`,`create_time`,`update_time`,`remark`) values (1,'超级管理员','admin','2022-07-04 14:40:44','2022-07-04 14:40:47','拥有系统\n最高权限'),(2,'普通角色','common','2022-07-04 14:41:56','2022-07-04 14:41:58','普通\n角色'),(3,'测试角色','test','2022-07-04 14:42:24','2022-07-04 14:42:27','测试角色'),(19,'测2','cc2','2022-08-13 21:06:21','2022-08-13 13:06:27','eewew2'),(20,'ccc测试','test2','2022-08-29 17:10:33',NULL,'xxx');

/*Table structure for table `role_menu` */

DROP TABLE IF EXISTS `role_menu`;

CREATE TABLE `role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色菜单主键ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=495 DEFAULT CHARSET=utf8;

/*Data for the table `role_menu` */

insert  into `role_menu`(`id`,`role_id`,`menu_id`) values (21,7,1),(22,7,2),(23,7,6),(24,7,7),(25,6,1),(26,6,3),(27,6,9),(28,6,10),(29,19,1),(30,19,3),(31,19,2),(32,19,6),(208,20,1),(209,20,3),(210,20,20),(211,20,8),(212,20,9),(213,20,33),(214,20,10),(215,20,11),(216,20,4),(217,20,21),(218,20,13),(219,20,5),(220,20,22),(221,20,17),(222,20,18),(223,20,2),(224,20,6),(225,20,7),(232,21,1),(233,21,9),(234,21,4),(235,21,21),(236,21,2),(237,21,6),(238,21,7),(243,3,22),(244,3,17),(245,3,18),(246,3,19),(423,1,1),(424,1,3),(425,1,20),(426,1,39),(427,1,9),(428,1,10),(429,1,11),(430,1,12),(431,1,4),(432,1,21),(433,1,13),(434,1,14),(435,1,15),(436,1,16),(437,1,5),(438,1,22),(439,1,17),(440,1,18),(441,1,19),(442,1,2),(443,1,6),(444,1,41),(445,1,42),(446,1,43),(447,1,44),(448,1,7),(449,1,48),(450,1,49),(451,1,50),(452,1,51),(453,1,47),(454,1,52),(455,1,53),(456,1,54),(457,1,55),(458,1,58),(459,1,59),(460,1,62),(461,1,63),(462,1,64),(463,1,65),(464,1,61),(465,1,66),(466,1,67),(467,1,68),(468,1,69),(480,2,41),(481,2,42),(482,2,43),(483,2,44),(484,2,58),(485,2,59),(486,2,62),(487,2,63),(488,2,64),(489,2,65),(490,2,61),(491,2,66),(492,2,67),(493,2,68),(494,2,69);

/*Table structure for table `shoucang` */

DROP TABLE IF EXISTS `shoucang`;

CREATE TABLE `shoucang` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) DEFAULT NULL,
  `fid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `shoucang` */

insert  into `shoucang`(`id`,`uid`,`fid`) values (1,1,1),(2,1,2),(3,1,3),(4,1,4);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) DEFAULT 'default.jpg' COMMENT '用户头像',
  `email` varchar(100) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`avatar`,`email`,`phonenumber`,`login_date`,`status`,`create_time`,`update_time`,`remark`) values (1,'admin','$2a$10$tiArwm0GxChyEP5k0JGzsOuzyY15IKA.ZTl8S2aj3haYlKAfpwfl.','20221026112159000000680.jpg','caofeng4012@126.com','18862857417','2022-08-29 22:10:52','0','2022-06-09 08:47:52','2022-10-26 23:22:09','备注'),(2,'common','$2a$10$tiArwm0GxChyEP5k0JGzsOuzyY15IKA.ZTl8S2aj3haYlKAfpwfl.','222.jpg','common@qq.com','13566998835','2022-08-22 21:34:39','0',NULL,'2022-10-31 16:26:24',NULL),(3,'test','$2a$10$tiArwm0GxChyEP5k0JGzsOuzyY15IKA.ZTl8S2aj3haYlKAfpwfl.','333.jpg','','','2022-07-24 17:36:07','0',NULL,NULL,NULL),(4,'1','$2a$10$5GJYKpLWkmsyB9zluEQjEuIQRSOI/DFm61wNuAqPU9/9S9nJspC3C','default.jpg','','',NULL,'1',NULL,'2023-02-01 20:13:10',NULL),(5,'zhangsan','$2a$10$IICzb1bD6NvjS0pbR/3M0uj.TKGCV2K0FRUUJhcrSoYTPKTLb46MK','default.jpg','1634571804@qq.com','13557480888',NULL,'0','2022-10-31 16:14:45',NULL,'123'),(7,'wangwu','$2a$10$Ljy3tFBggxGTVBe8phwgIeCwkoq9JFAiKjCA14ZRbkH9pfn6/TDCG','default.jpg','wangwu@qq.com','13578995555',NULL,'1','2022-10-31 16:25:00','2022-11-01 15:10:17','789'),(8,'zhaoliu','$2a$10$..h9Ni8ItXhzeUB6LlPNge8GVYQQuL9GhE.KhUotu1b4Q4/qRencK','default.jpg','zhoaliu@qq.com','13557480888',NULL,'0','2023-02-20 22:46:11',NULL,'0000000'),(9,'wnaglaoban','$2a$10$6NXh/EoZsaqVCO3RMxYCMeZrdjFsqm13u4JMocxqkPbqZuy5xl7Z2','default.jpg','common@qq.com','13852467566',NULL,'0','2023-02-20 22:55:17',NULL,'1255');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色主键ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`) values (1,1,1),(2,2,2),(4,1,2),(6,3,3),(7,3,2),(9,4,3),(10,5,3),(11,15,3),(16,28,2),(17,28,3),(20,29,20),(21,30,17),(23,9,2);

/*Table structure for table `xiaolei` */

DROP TABLE IF EXISTS `xiaolei`;

CREATE TABLE `xiaolei` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `xiaolei` */

insert  into `xiaolei`(`id`,`name`) values (1,'细类1'),(2,'细类二'),(3,'细类三'),(4,'三高'),(5,'低糖'),(6,'高碳水'),(7,'低脂');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
