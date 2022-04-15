/*키트 테이블*/
create table okit(
	kit_num number not null,
	user_num number not null,
	kit_name varchar2(30) not null,
	kit_price number not null,
	kit_quantity number not null,
	kit_content clob not null,
	kit_content2 clob not null,
    hit number(5) default 0 not null,
    onoff number(1) default 3 not null,
    filename varchar2(100),
	uploadfile blob,
	mimage varchar2(300),
	reg_date date default sysdate not null,
	modify_date date,
	constraint okit_pk primary key (kit_num),
	constraint okit_fk foreign key (user_num) references ouser (user_num),
);
/* constraint okit_fk2 foreign key (category_num) references category (category_num)*/
create sequence okit_seq;

/*(찜한 상품 테이블)*/
CREATE TABLE okitlike(
	kitlike_num number not null,
	kit_num number not null,
	user_num number not null,
	olike number(1) not null,
	constraint kitlike_pk primary key(kitlike_num),
	constraint kit_fk1 foreign key(kit_num) references okit(kit_num),
	constraint user_fk2 foreign key(user_num) references ouser(user_num)
);
create sequence okitlike_seq;

/*키트 연관 클래스 리스트 보이기*/
CREATE TABLE olist(
	list_num number not null,
	o_list varchar2(4) not null,
	kit_num number not null,
	on_num number not null,
	off_num number not null,
    user_num number not null,
	constraint olist_pk primary key (list_num),
	constraint olist_fk foreign key (kit_num) references okit (kit_num),
	constraint olist_fk2 foreign key (on_num) references onclass (on_num),
	constraint olist_fk3 foreign key (off_num) references offclass (off_num),
    constraint olist_fk4 foreign key (user_num) references ouser (user_num)
);
create sequence olist_seq;

/*다중이미지 업로드*/
create table uploadfilekit(
	file_num number not null,
	kit_num number not null,
	o_name varchar2(300) not null,
	file_name varchar2(300) not null,
	file_size number not null,
	constraint uploadfilekit_pk primary key(file_num)
);
create sequence uploadfilekit_seq;