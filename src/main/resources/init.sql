----------------
-- User
----------------
INSERT INTO `bakery`.`user` (`id`, `username`, `email`, `name`, `password`, `status`, `created_date`, `updated_date`, `created_by`, `updated_by`, `deleted`)
VALUES (1, 'admin', 'contact@bakery.com', 'Administrator', '$2a$10$ESiHPIp66GDRAmmVbrb3heW35sa9a88ykZd0vcEy5AoHUtRwW2sPu', 1, now(), now(), 1, 1, 0);

----------------
-- Action
----------------
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(1, 'ACTION.VIEW', 'Action group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(2, 'ACTION.ADD', 'Action group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(3, 'ROLE.VIEW', 'Role Group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(4, 'ROLE.ADD', 'Role Group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(5, 'USER.VIEW', 'User group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(6, 'USER.ADD', 'User group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(7, 'CATEGORY.VIEW', 'CATEGORY group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(8, 'CATEGORY.ADD', 'CATEGORY group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(9, 'PRODUCT.VIEW', 'PRODUCT group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(10, 'PRODUCT.ADD', 'PRODUCT group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(11, 'OPTION_TYPE.VIEW', 'OPTION_TYPE group', '', 1, now(), now(), 1, 1, 0);
INSERT INTO action(`id`,`code`,`name`,`description`,`status`,`created_date`,`updated_date`,`created_by`,`updated_by`,`deleted`)
VALUES(12, 'OPTION_TYPE.ADD', 'OPTION_TYPE group', '', 1, now(), now(), 1, 1, 0);
----------------
-- Role
----------------
INSERT INTO `bakery`.`role`(`id`, `code`, `name`, `status`, `created_date`, `updated_date`, `created_by`, `updated_by`, `deleted`)
VALUES (1, 'FULL_ACTION', 'Quản lý Action', 1, now(), now(), 1, 1, 0);
INSERT INTO `bakery`.`role`(`id`, `code`, `name`, `status`, `created_date`, `updated_date`, `created_by`, `updated_by`, `deleted`)
VALUES (2, 'ACTION', 'Quản lý Action', 1, now(), now(), 1, 1, 0);
INSERT INTO `bakery`.`role`(`id`, `code`, `name`, `status`, `created_date`, `updated_date`, `created_by`, `updated_by`, `deleted`)
VALUES (3, 'ROLE', 'Quản lý Action', 1, now(), now(), 1, 1, 0);
INSERT INTO `bakery`.`role`(`id`, `code`, `name`, `status`, `created_date`, `updated_date`, `created_by`, `updated_by`, `deleted`)
VALUES (4, 'USER', 'Quản lý Action', 1, now(), now(), 1, 1, 0);
INSERT INTO `bakery`.`role`(`id`, `code`, `name`, `status`, `created_date`, `updated_date`, `created_by`, `updated_by`, `deleted`)
VALUES (5, 'CATEGORY', 'Quản lý Category', 1, now(), now(), 1, 1, 0);
INSERT INTO `bakery`.`role`(`id`, `code`, `name`, `status`, `created_date`, `updated_date`, `created_by`, `updated_by`, `deleted`)
VALUES (6, 'PRODUCT', 'Quản lý Product', 1, now(), now(), 1, 1, 0);
INSERT INTO `bakery`.`role`(`id`, `code`, `name`, `status`, `created_date`, `updated_date`, `created_by`, `updated_by`, `deleted`)
VALUES (7, 'OPTION_TYPE', 'Quản lý Option_Type', 1, now(), now(), 1, 1, 0);
----------------
-- Role_Action
----------------
-- FULL_ACTION
INSERT INTO `bakery`.`role_action`(`role_id`, `action_id`)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12);
-- ACTION
INSERT INTO `bakery`.`role_action`(`role_id`, `action_id`)
VALUES (2, 1), (2, 2);
-- ROLE
INSERT INTO `bakery`.`role_action`(`role_id`, `action_id`)
VALUES (3, 3), (3, 4);
-- USER
INSERT INTO `bakery`.`role_action`(`role_id`, `action_id`)
VALUES (4, 5), (4, 6);
-- CATEGORY
INSERT INTO `bakery`.`role_action`(`role_id`, `action_id`)
VALUES (5, 7), (5, 8);
-- PRODUCT
INSERT INTO `bakery`.`role_action`(`role_id`, `action_id`)
VALUES (6, 9), (6, 10);
-- OPTION_TYPE
INSERT INTO `bakery`.`role_action`(`role_id`, `action_id`)
VALUES (7, 11), (7, 12);

----------------
-- USER_Role
----------------
INSERT INTO `bakery`.`user_role`(`user_id`, `role_id`)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7);