# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists xiaofei_site_search;

-- 切换库
use xiaofei_site_search;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';


CREATE TABLE `file` (
                        `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                        `fileName` VARCHAR(255) NOT NULL COMMENT '原始文件名',
                        `filePath` VARCHAR(255) NOT NULL COMMENT '存储路径（相对路径）',
                        `fileType` VARCHAR(100) NOT NULL COMMENT '文件类型/MIME类型',
                        `fileExtension` VARCHAR(10) COMMENT '文件扩展名',
                        `fileSize` BIGINT NOT NULL COMMENT '文件大小（字节）',
                        `md5` VARCHAR(32) NOT NULL COMMENT '文件MD5值',
                        `userId` BIGINT COMMENT '创建用户 id',
                        `biz` VARCHAR(50) NOT NULL COMMENT '业务',
                        `isDelete` TINYINT DEFAULT 0 COMMENT '是否删除',
                        `downloadCount` INT DEFAULT 0 COMMENT '下载次数',
                        `description` TEXT COMMENT '文件描述',
                        `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `content` LONGTEXT COMMENT '文件内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';

-- 创建索引
# CREATE INDEX idx_userId ON file(userId);
# CREATE INDEX idx_biz ON file(biz);
# CREATE INDEX idx_createTime ON file(createTime);
# CREATE INDEX idx_md5 ON file(md5);
