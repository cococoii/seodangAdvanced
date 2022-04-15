create table offclass(
	off_num number not null,
	category_num number not null,
	off_name varchar2(100) not null,
	off_limit number(2) not null,
	off_price number(8) not null,
	off_content clob not null,
	off_filename varchar2(100),
	off_uploadfile blob,
	mimage varchar2(300),
	reg_date date default sysdate not null,
	modify_date date,
	user_num number not null,
	offzipcode varchar2(5) not null,
	offaddress1 varchar2(90) not null,
	offaddress2 varchar2(90) not null,
	onoff number(1) default 2 not null,
	constraint offclass_pk primary key(off_num),
	constraint offclass_fk foreign key(user_num) references ouser(user_num)
	/*constraint offclass_fk2 foreign key(category_num) references category(category_num)*/
);

create sequence offclass_seq;

/*찜하기*/
create table offlike(
	offlike_num number not null,
	user_num number not null,
	off_num number not null,
	olike number(1) not null,
	constraint offlike_pk primary key (offlike_num),
	constraint offlike_fk1 foreign key (user_num) references ouser(user_num),
	constraint offlike_fk2 foreign key (off_num) references offclass(off_num)
);
create sequence offlike_seq;

/*평점*/
create table offstar(
	offstar_num number not null,
	user_num number not null,
	off_num number not null,
	rating number not null,
	text varchar(900) not null,
	reg_date date default SYSDATE not null,
	constraint offstar_pk primary key (offstar_num),
	constraint offstar_fk1 foreign key (user_num) references ouser(user_num),
	constraint offstar_fk2 foreign key (off_num) references offclass(off_num)
);
create sequence offstar_seq;

create table offstar_reply(
	offre_num number not null,
	offstar_num number not null,
	user_num number not null,
	offre_content varchar2(900) not null,
	offre_date date default sysdate not null,
	constraint offstar_reply_pk primary key(offre_num),
	constraint offstar_reply_fk1 foreign key(user_num) references ouser(user_num),
	constraint offstar_reply_fk2 foreign key(offstar_num) references offstar(offstar_num)
);
create sequence offstar_reply_seq;

/*다중 이미지 업로드*/
create table uploadfileoff(
	file_num number not null,
	off_num number not null,
	o_name varchar2(300) not null,
	file_name varchar2(300) not null,
	file_size number not null,
	constraint uploadfileoff_pk primary key(file_num)
);
create sequence uploadfileoff_seq;