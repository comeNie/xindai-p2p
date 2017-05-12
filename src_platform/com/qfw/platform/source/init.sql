DROP TABLE IF EXISTS `cms_catalog`;

CREATE TABLE `cms_catalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `type` int(11) NOT NULL COMMENT '栏目类型',
  `htmlFilePath` varchar(255) DEFAULT NULL COMMENT '静态页面地址',
  `isPublication` int(1) NOT NULL COMMENT '是否发布',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `catalog_type_index` (`type`),
  KEY `catalog_ispublication_index` (`isPublication`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `cms_catalog` */

insert  into `cms_catalog`(`id`,`title`,`content`,`type`,`htmlFilePath`,`isPublication`,`sort`) values (3,'1','1',3,NULL,1,1),(4,'4545','333',4,NULL,1,33);

/*Table structure for table `cms_catalog_type` */

DROP TABLE IF EXISTS `cms_catalog_type`;

CREATE TABLE `cms_catalog_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `ftlFilePath` varchar(255) DEFAULT NULL COMMENT '模板地址',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `cms_catalog_type` */

insert  into `cms_catalog_type`(`id`,`name`,`ftlFilePath`,`sort`) values (3,'分类一',NULL,1),(4,'分类二',NULL,1);

/*Table structure for table `cms_friendlink` */

DROP TABLE IF EXISTS `cms_friendlink`;

CREATE TABLE `cms_friendlink` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '友情链接名称',
  `url` varchar(255) NOT NULL COMMENT '友情链接URL',
  `logo` varchar(255) DEFAULT NULL COMMENT 'LOGO地址',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `cms_friendlink` */

insert  into `cms_friendlink`(`id`,`name`,`url`,`logo`,`sort`) values (10,'安全联盟','http://www.anquan.org/','/upload/images/20140516/friendlink07ea9651a59c40c9b69eb8dcde4689ab.gif',0),(11,'可信网站','http://t.knet.cn/','/upload/images/20140516/friendlink4753c118455340868b84643cda02e265.gif',1),(12,'中国互联网协会','http://www.isc.org.cn/','/upload/images/20140516/friendlinke94c153647504bbd897ce616dd6a517d.gif',2),(13,'认证联盟','http://www.isoyes.com/','/upload/images/20140516/friendlinke32401c352f0412395c76ce828f39c2f.gif',3),(14,'厦门网络警察报警平台','http://www.xiamen.cyberpolice.cn/','/upload/images/20140518/friendlink6379c683045343608b255a8962aaec96.gif',4);

/*Table structure for table `cms_navigation` */

DROP TABLE IF EXISTS `cms_navigation`;

CREATE TABLE `cms_navigation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `url` varchar(255) NOT NULL COMMENT '链接地址',
  `position` int(1) NOT NULL DEFAULT '0' COMMENT '位置(0:顶部;1:中间;2:底部)',
  `isblanktarget` int(1) NOT NULL DEFAULT '0' COMMENT '是否在新窗口中打开',
  `isvisible` int(1) NOT NULL DEFAULT '0' COMMENT '是否显示',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `nav_name_index` (`name`),
  KEY `nav_position_index` (`position`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `cms_navigation` */

insert  into `cms_navigation`(`id`,`name`,`url`,`position`,`isblanktarget`,`isvisible`,`sort`) values (22,'首页','#',0,0,1,0),(23,'我要理财','#',0,1,1,1),(24,'我要借款','#',0,1,1,2),(25,'债权转让','#',0,1,1,3),(26,'本金保障','#',0,1,1,4),(27,'我的账户','#',0,1,1,5),(29,'公司简介','#',2,1,1,0),(30,'新手指引','#',2,1,1,1),(31,'安全保障','#',2,1,1,2),(32,'帮助中心','#',2,1,1,3),(33,'联系我们','#',2,1,1,4),(34,'友情链接','#',2,1,1,5),(35,'网站协议','#',2,1,1,6),(36,'网站地图','#',2,1,1,7),(37,'意见反馈','#',2,1,1,8);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
