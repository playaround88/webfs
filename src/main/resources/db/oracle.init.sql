-- Create sequence 
create sequence SEQ_FILEMETA_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;
-- Create table
create table file_meta
(
  id        number not null,
  ori_name  varchar2(30) not null,
  save_name varchar2(20) not null,
  fsize     number not null,
  file_url  varchar2(100) not null,
  del_url   varchar2(100) not null,
  del_type  varchar2(10) default 'delete',
  catalog   varchar2(20) default 'default' not null
)
;
-- Add comments to the columns 
comment on column file_meta.id
  is '编码';
comment on column file_meta.ori_name
  is '上传文件原名称';
comment on column file_meta.save_name
  is '文件保存名称';
comment on column file_meta.fsize
  is '文件大小';
comment on column file_meta.file_url
  is '文件访问地址';
comment on column file_meta.del_url
  is '文件删除地址';
comment on column file_meta.del_type
  is '删除类型';
comment on column file_meta.catalog
  is '项目分类，为便于维护';
-- Create/Recreate primary, unique and foreign key constraints 
alter table file_meta
  add constraint file_meta_pk primary key (ID);