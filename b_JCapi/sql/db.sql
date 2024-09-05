create database if not exists my_db;

use my_db;

-- 接口信息
create table if not exists my_db.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `description` varchar(256) null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    `requestParams` text null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
    ) comment '接口信息';

insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (1, '许擎宇', '薛聪健', 'www.cary-king.net', '潘博涛', '谭聪健', 0, '石炫明', 9500534531, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (2, '陆弘文', '白志强', 'www.leslee-kuhn.net', '潘懿轩', '马鸿涛', 0, '陈峻熙', 3982575846, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (3, '毛建辉', '罗文', 'www.rosaria-kilback.io', '冯子默', '彭哲瀚', 1, '赵远航', 121776355, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (4, '彭雨泽', '蔡煜祺', 'www.norris-bergstrom.biz', '董思源', '田晓博', 1, '潘擎宇', 740, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (5, '傅志强', '陈梓晨', 'www.jordan-reinger.com', '金志强', '熊锦程', 1, '邓睿渊', 35542559, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (6, '吕黎昕', '孔越彬', 'www.fe-okon.info', '万伟宸', '林昊然', 1, '孟荣轩', 1445, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (7, '夏雪松', '许子骞', 'www.lashawna-legros.co', '蔡昊然', '胡鹏涛', 1, '钟立辉', 34075514, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (8, '严钰轩', '阎志泽', 'www.kay-funk.biz', '莫皓轩', '郭黎昕', 1, '龚天宇', 70956, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (9, '萧嘉懿', '曹熠彤', 'www.margarette-lindgren.biz', '田泽洋', '邓睿渊', 0, '梁志强', 98, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (10, '杜驰', '冯思源', 'www.vashti-auer.org', '黎健柏', '武博文', 0, '李伟宸', 9, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (11, '史金鑫', '蔡鹏涛', 'www.diann-keebler.org', '徐烨霖', '阎建辉', 1, '李烨伟', 125, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (12, '林炫明', '贾旭尧', 'www.dotty-kuvalis.io', '梁雨泽', '龙伟泽', 0, '许智渊', 79998, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (13, '何钰轩', '赖智宸', 'www.andy-adams.net', '崔思淼', '白鸿煊', 0, '邵振家', 7167482751, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (14, '魏志强', '于立诚', 'www.ione-aufderhar.biz', '朱懿轩', '万智渊', 0, '唐昊强', 741098, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (15, '严君浩', '金胤祥', 'www.duane-boyle.org', '雷昊焱', '侯思聪', 0, '郝思', 580514, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (16, '姚皓轩', '金鹏', 'www.lyda-klein.biz', '杜昊强', '邵志泽', 1, '冯鸿涛', 6546, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (17, '廖驰', '沈泽洋', 'www.consuelo-sipes.info', '彭昊然', '邓耀杰', 0, '周彬', 7761037, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (18, '赖智渊', '邓志泽', 'www.emerson-mann.co', '熊明哲', '贺哲瀚', 0, '田鹏', 381422, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (19, '许涛', '陆致远', 'www.vella-ankunding.name', '贾哲瀚', '莫昊焱', 0, '袁越彬', 4218096, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (20, '吕峻熙', '沈鹏飞', 'www.shari-reichel.org', '郭鸿煊', '覃烨霖', 0, '熊黎昕', 493, '2024-08-14 16:18:46', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (21, '01', '第一次测试', 'http://localhost:114514', 'DA', 'JCGAGA', 0, 'POST', 1, '2024-08-16 21:26:01', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (22, '02Test', '', '', '', '', 0, '', 1, '2024-08-16 21:31:40', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (23, 'required字段第三次测试', 'required 测试第三次', 'localhost:1234', 'ni', 'hao', 0, 'Test', 1, '2024-08-17 09:07:01', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (24, '删除测试第一次', '测试删除以及添加红线的', 'http://localhost:1234', 'dao', 'dnadia', 0, 'Test', 1, '2024-08-17 11:04:39', '2024-08-22 14:38:42', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (25, '新建测试接口', '此接口用来做一些测试操作', 'localhost:9527/Test', 'nihaoa', 'niyeshi', 0, 'GET', 4, '2024-08-23 16:32:21', '2024-08-23 16:32:21', 0);
insert into my_db.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDelete) values (26, '我推的孩子2-18', '第十八集', 'https://www.mute01.com/vodplay/2644-2-1.html', '推子真好看', '确实很好看', 0, 'Video', 1, '2024-08-26 09:49:36', '2024-08-26 09:49:36', 0);
