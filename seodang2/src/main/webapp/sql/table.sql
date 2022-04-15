create table ouser(
 user_num number not null,
 id varchar2(12) unique not null,
 auth number(1) default 2 not null,/*0탈퇴,1.정지,2일반,3관리자*/
 constraint spuser_pk primary key(user_num)
);

create table ouser_detail(
	user_num number not null,
	name varchar2(30) not null,
	passwd varchar2(35) not null,
	phone varchar2(15) not null,
	photo blob,
	photo_name varchar2(100),
	zipcode varchar2(5) not null,
	address1 varchar2(90) not null,
	address2 varchar2(90) not null,
	age number(10) not null,
	email varchar2(50) not null,
	reg_date date default sysdate not null,
	modify_date date,
	constraint ouser_detail_pk primary key (user_num),
	constraint ouser_detail_fk foreign key (user_num) references ouser (user_num)
);

create sequence ouser_seq;