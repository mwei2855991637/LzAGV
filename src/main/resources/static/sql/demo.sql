drop database LzAGV;
create database LzAGV;
use LzAGV;
-- creat table
-- t_transport_plan
create table t_transport_plan(
	id int(8) unsigned primary key auto_increment,
	product varchar(30),
	start_place varchar(30),
	end_place varchar(30),
	car_num varchar(10),
	start_time timestamp default CURRENT_TIMESTAMP,
	end_time timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
	st tinyint(4)
);
-- t_his_transport
create table t_his_transport(
	id int(8) unsigned primary key auto_increment,
	product varchar(30),
	start_place varchar(30),
	end_place varchar(30),
	car_num varchar(10),
	start_time timestamp default CURRENT_TIMESTAMP,
	end_time timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
);
-- 创建触发器，当t_transport_plan的st字段，更新值为1后，将当期记录备份，然后删除
--create trigger backupUpdateTrsPlanColSt 
--	after update on t_transport_plan 
--	for each row 
--begin
--
--end
--	end 
--commit;

