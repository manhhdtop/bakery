INSERT INTO `bakery`.`action` (`deleted`,`created_date`,`updated_date`,`code`,`description`,`name`,`status`) VALUES(0, 1617801168481, 1617801168481, ACTION.ADD, Quản lý Action, -99);
INSERT INTO `bakery`.`action` (`deleted`,`created_date`,`updated_date`,`code`,`description`,`name`,`status`) VALUES(0, 1617801168481, 1617801168481, ACTION.VIEW, Xem danh sách Action, -99);
INSERT INTO `bakery`.`action` (`deleted`,`created_date`,`updated_date`,`code`,`description`,`name`,`status`) VALUES(0, 1617801168481, 1617801168481, USER.ADD, Quản lý người dùng, -99);
INSERT INTO `bakery`.`action` (`deleted`,`created_date`,`updated_date`,`code`,`description`,`name`,`status`) VALUES(0, 1617801168481, 1617801168481, USER.VIEW, Xem danh sách người dùng, -99);
INSERT INTO `bakery`.`role_action`(`role_id`,`action_id`) VALUES (1, 1),(1, 2),(1, 3),(1, 4);
INSERT INTO `bakery`.`user` (`deleted`,`created_date`,`updated_date`,`username`,`name`,`password`,`email`,`status`) VALUES(0, 1617801168481, 1617801168481, admin, Administrator, 1);
INSERT INTO `bakery`.`user_role`(`user_id`,`role_id`) VALUES (1, 1);