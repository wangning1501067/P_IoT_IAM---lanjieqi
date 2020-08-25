
-- ----------------------------
-- Table structure for iam_area
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_area";
CREATE TABLE "iam"."iam_area" (
  "id" serial PRIMARY KEY NOT NULL,
  "area_name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int4,
  "level" int4,
  "create_time" timestamp(6) DEFAULT now(),
  "update_time" timestamp(6) DEFAULT now(),
  "merchant_id" int4
)
;
COMMENT ON COLUMN "iam"."iam_area"."id" IS '主键id';
COMMENT ON COLUMN "iam"."iam_area"."area_name" IS '区域名称';
COMMENT ON COLUMN "iam"."iam_area"."parent_id" IS '父类的id-主键id';
COMMENT ON COLUMN "iam"."iam_area"."level" IS '层级级别';
COMMENT ON COLUMN "iam"."iam_area"."merchant_id" IS '商户id';
COMMENT ON TABLE "iam"."iam_area" IS '区域';


-- ----------------------------
-- Table structure for iam_device
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_device";
CREATE TABLE "iam"."iam_device" (
  "id" serial PRIMARY KEY NOT NULL,
  "device_name" varchar(255) COLLATE "pg_catalog"."default",
  "device_location" varchar(255) COLLATE "pg_catalog"."default",
  "merchant_id" int4,
  "device_number" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "create_time" timestamp(6) DEFAULT now(),
  "update_time" timestamp(6) DEFAULT now(),
  "area_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "iam"."iam_device"."id" IS '主键id';
COMMENT ON COLUMN "iam"."iam_device"."device_name" IS '设备名称';
COMMENT ON COLUMN "iam"."iam_device"."device_location" IS '设备位置';
COMMENT ON COLUMN "iam"."iam_device"."merchant_id" IS '商户id';
COMMENT ON COLUMN "iam"."iam_device"."device_number" IS '设备编号';
COMMENT ON COLUMN "iam"."iam_device"."status" IS '在线状态(0离线，1在线)';
COMMENT ON COLUMN "iam"."iam_device"."area_id" IS '区域id';
COMMENT ON TABLE "iam"."iam_device" IS '设备';

-- ----------------------------
-- Table structure for iam_flow
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_flow";
CREATE TABLE "iam"."iam_flow" (
  "id" serial PRIMARY KEY NOT NULL,
  "merchant_id" int4,
  "men_num" int4,
  "device_id" int4,
  "create_time" timestamp(6) DEFAULT now(),
  "update_time" timestamp(6) DEFAULT now(),
  "push_date" timestamp(0)
)
;
COMMENT ON COLUMN "iam"."iam_flow"."merchant_id" IS '商户ID';
COMMENT ON COLUMN "iam"."iam_flow"."men_num" IS '通过人数';
COMMENT ON COLUMN "iam"."iam_flow"."device_id" IS '设备ID';
COMMENT ON COLUMN "iam"."iam_flow"."create_time" IS '创建时间';
COMMENT ON COLUMN "iam"."iam_flow"."update_time" IS '更新时间';
COMMENT ON COLUMN "iam"."iam_flow"."push_date" IS '推送时间';
COMMENT ON TABLE "iam"."iam_flow" IS '客流';

-- ----------------------------
-- Table structure for iam_images
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_images";
CREATE TABLE "iam"."iam_images" (
  "id" serial PRIMARY KEY NOT NULL,
  "images_path" varchar(255) COLLATE "pg_catalog"."default",
  "images_name" varchar(255) COLLATE "pg_catalog"."default",
  "images_start_time" timestamp(6),
  "images_end_time" timestamp(6),
  "images_time_start" int4,
  "create_time" timestamp(6) DEFAULT now(),
  "update_time" timestamp(6) DEFAULT now(),
  "merchant_id" int4
)
;
COMMENT ON COLUMN "iam"."iam_images"."images_path" IS '图片路径';
COMMENT ON COLUMN "iam"."iam_images"."images_name" IS '内容名称';
COMMENT ON COLUMN "iam"."iam_images"."images_start_time" IS '选择时效开始时间';
COMMENT ON COLUMN "iam"."iam_images"."images_end_time" IS '选择时效结束时间';
COMMENT ON COLUMN "iam"."iam_images"."images_time_start" IS '是否是长期有效0无效1有效';
COMMENT ON COLUMN "iam"."iam_images"."merchant_id" IS '商户id';
COMMENT ON TABLE "iam"."iam_images" IS '图片表';

-- ----------------------------
-- Table structure for iam_images_device
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_images_device";
CREATE TABLE "iam"."iam_images_device" (
  "id" serial PRIMARY KEY NOT NULL,
  "images_id" int4,
  "device_id" int4,
  "device_area" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "iam"."iam_images_device" IS '图片设备关系表';

-- ----------------------------
-- Table structure for iam_images_label
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_images_label";
CREATE TABLE "iam"."iam_images_label" (
  "id" serial PRIMARY KEY NOT NULL,
  "images_id" int4,
  "label_id" int4,
  "label_type" int4
)
;
COMMENT ON TABLE "iam"."iam_images_label" IS '图片标签关系表';

-- ----------------------------
-- Table structure for iam_label
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_label";
CREATE TABLE "iam"."iam_label" (
  "id" serial PRIMARY KEY NOT NULL,
  "label_name" varchar(255) COLLATE "pg_catalog"."default",
  "label_type" int4,
  "label_status" int4,
  "create_time" timestamp(6) DEFAULT now(),
  "update_time" timestamp(6) DEFAULT now(),
  "merchant_id" int4
)
;
COMMENT ON COLUMN "iam"."iam_label"."label_name" IS '标签名称';
COMMENT ON COLUMN "iam"."iam_label"."label_type" IS '标签分类1性别，2表情，3年龄';
COMMENT ON COLUMN "iam"."iam_label"."label_status" IS '标签状态0禁用1启用';
COMMENT ON COLUMN "iam"."iam_label"."merchant_id" IS '商户id';
COMMENT ON TABLE "iam"."iam_label" IS '标签';

INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (1, 'man', 1, 1, '2020-07-28 06:05:33', '2020-07-28 06:05:33', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (2, 'women', 1, 1, '2020-07-28 06:05:43', '2020-07-28 06:05:43', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (3, 'happy', 2, 1, '2020-07-28 06:06:33', '2020-07-28 06:07:53', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (4, 'sad', 2, 1, '2020-07-28 06:06:36', '2020-07-28 06:07:53', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (5, 'neutral', 2, 1, '2020-07-28 06:06:38', '2020-07-28 06:07:53', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (6, 'surprise', 2, 1, '2020-07-28 06:06:40', '2020-07-28 06:07:53', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (7, 'fear', 2, 1, '2020-07-28 06:06:43', '2020-07-28 06:07:53', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (8, 'child(0-15)', 3, 1, '2020-07-28 06:06:45', '2020-07-28 06:08:28', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (9, 'youth(16-35)', 3, 1, '2020-07-28 06:06:47', '2020-07-28 06:08:28', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (10, 'middle-aged(36-60)', 3, 1, '2020-07-28 06:06:49', '2020-07-28 06:08:28', 2);
INSERT INTO "iam"."iam_label"("id", "label_name", "label_type", "label_status", "create_time", "update_time", "merchant_id") VALUES (11, 'the elderly(61-100)', 3, 1, '2020-07-28 06:06:51', '2020-07-28 06:08:28', 2);


-- ----------------------------
-- Table structure for iam_merchant
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_merchant";
CREATE TABLE "iam"."iam_merchant" (
  "id" serial PRIMARY KEY NOT NULL,
  "merchant_name" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" int4,
  "create_time" timestamp(6) DEFAULT now(),
  "update_time" timestamp(6) DEFAULT now(),
  "status" int4
)
;
COMMENT ON COLUMN "iam"."iam_merchant"."id" IS '主键id';
COMMENT ON COLUMN "iam"."iam_merchant"."merchant_name" IS '商户名称';
COMMENT ON COLUMN "iam"."iam_merchant"."user_id" IS '用户ID';
COMMENT ON COLUMN "iam"."iam_merchant"."status" IS '商户状态0禁用，1启用';
COMMENT ON TABLE "iam"."iam_merchant" IS '商户';

-- ----------------------------
-- Records of iam_merchant
-- ----------------------------
INSERT INTO "iam"."iam_merchant" VALUES (2, '昌域万科中心', 1, '2020-07-29 16:48:24.919611', '2020-08-18 10:56:43.885275', 1);

-- ----------------------------
-- Table structure for iam_push_data
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_push_data";
CREATE TABLE "iam"."iam_push_data" (
  "id" serial PRIMARY KEY NOT NULL,
  "user_id" int4,
  "merchant_id" int4,
  "device_id" int4,
  "create_time" timestamp(6) DEFAULT now(),
  "update_time" timestamp(6) DEFAULT now(),
  "push_date" date,
  "images_id" int4
)
;
COMMENT ON COLUMN "iam"."iam_push_data"."user_id" IS '用户ID';
COMMENT ON COLUMN "iam"."iam_push_data"."merchant_id" IS '商户ID';
COMMENT ON COLUMN "iam"."iam_push_data"."device_id" IS '设备ID';
COMMENT ON COLUMN "iam"."iam_push_data"."push_date" IS '推送时间';
COMMENT ON COLUMN "iam"."iam_push_data"."images_id" IS '图片ID';
COMMENT ON TABLE "iam"."iam_push_data" IS '推送数据记录';

-- ----------------------------
-- Table structure for iam_user
-- ----------------------------
DROP TABLE IF EXISTS "iam"."iam_user";
CREATE TABLE "iam"."iam_user" (
  "id" serial PRIMARY KEY NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "create_time" timestamp(6) DEFAULT now(),
  "update_time" timestamp(6) DEFAULT now()
)
;
COMMENT ON COLUMN "iam"."iam_user"."id" IS '主键id';
COMMENT ON COLUMN "iam"."iam_user"."username" IS '用户名';
COMMENT ON COLUMN "iam"."iam_user"."password" IS '密码';
COMMENT ON COLUMN "iam"."iam_user"."status" IS '用户状态0禁用，1启用';
COMMENT ON TABLE "iam"."iam_user" IS '用户表';

-- ----------------------------
-- Records of iam_user
-- ----------------------------
INSERT INTO "iam"."iam_user" VALUES (1, 'admin', '$2a$10$iYwsnaeMPpu7IGmJoX1uludefcC4a6z2y/5kWmRmcnD.ryym3WEEi', 1, '2020-07-29 16:36:37.023555', '2020-07-29 16:36:37.023555');
