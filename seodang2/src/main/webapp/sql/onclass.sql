create table onclass(
	on_num number not null,
	user_num number not null,
	on_name varchar2(12) not null,
	on_price number(8) not null, 
	hit number default 0 not null,
	category_num number not null,
	mimage varchar2(300) not null,
	on_content clob not null,
	onoff number(1) default 1 not null,
	reg_date date default sysdate not null,
	modify_date date,
	constraint onclass_pk primary key (on_num),
	constraint onclass_fk foreign key (user_num) 
                         references ouser (user_num)
);
create sequence onclass_seq;

create table onreg(
    onreg_num number not null,
    user_num number not null,
    on_regdate date default sysdate not null,
    on_moregdate date,
    constraint onreg_pk primary key (onreg_num),
	constraint onreg_fk1 foreign key (user_num) references ouser (user_num)
);
create sequence onreg_seq;

create table onreg_detail(
    onreg_num number not null,
    on_num number not null,
    on_payment number not null,
    on_status number(1) default 1 not null, -- 수강신청상태(1), 수강취소상태(2)
	constraint onreg_detail_fk1 foreign key (onreg_num) references onreg (onreg_num),
    constraint onreg_detail_fk2 foreign key (on_num) references onclass (on_num)
);
create sequence onreg_detail_seq;
--찜
create table onlike (
	onlike_num number not null,
	user_num number not null,
	on_num number not null,
	olike number(1) not null,
	constraint onlike_pk primary key (onlike_num),
	constraint onlike_fk1 foreign key (user_num) references ouser(user_num),
	constraint onlike_fk2 foreign key (on_num) references onclass(on_num)
);
create sequence onlike_seq; 

--다중이미지 업로드
create table uploadfile(
	file_num number not null,
	on_num number not null,
	o_name varchar2(300) not null,
	file_name varchar2(300) not null,
	file_size number not null,
	constraint uploadfile_pk primary key(file_num)
);
create sequence uploadfile_seq;

/*평점*/
create table onstar(
	onstar_num number not null,
	user_num number not null,
	on_num number not null,
	rating number not null,
	text varchar(900) not null,
	reg_date date default SYSDATE not null,
	constraint onstar_pk primary key (onstar_num),
	constraint onstar_fk1 foreign key (user_num) references ouser(user_num),
	constraint onstar_fk2 foreign key (on_num) references onclass(on_num)
);
create sequence onstar_seq;

create table onstar_reply(
	onre_num number not null,
	onstar_num number not null,
	user_num number not null,
	onre_content varchar2(900) not null,
	onre_date date default sysdate not null,
	constraint onstar_reply_pk primary key(onre_num),
	constraint onstar_reply_fk1 foreign key(user_num) references ouser(user_num),
	constraint onstar_reply_fk2 foreign key(onstar_num) references onstar(onstar_num)
);
create sequence onstar_reply_seq;










