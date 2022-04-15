/*온라인 클래스 1:1게시판*/
create table onqna(
	onqna_num number not null,
	on_num number not null,
	user_num number not null,
	title varchar2(30) not null,
	content clob not null,
	reg_date date default sysdate not null,
	modify_date date,	
	filename varchar2(100),
	uploadfile blob,

	constraint onqna_pk primary key (onqna_num),
	constraint onqna_fk1 foreign key (on_num) references onclass (on_num),
	constraint onqna_fk2 foreign key (user_num) references ouser (user_num)
);
create sequence onqna_seq;



/*온라인 클래스 1:1게시판 답변*/
create table onqna_reply(
	onqnare_num number not null,
	onqna_num number not null,
	user_num number not null,
	re_content clob not null,
	re_date date default sysdate not null,
	re_mdate date,
	constraint onqna_reply_pk primary key (onqnare_num),
	constraint onqna_reply_fk1 foreign key (onqna_num) references onqna (onqna_num),
	constraint onqna_reply_fk2 foreign key (user_num) references ouser (user_num)
); 
create sequence onqna_reply_seq;




/*오프라인 클래스 1:1게시판*/
create table offqna(
	offqna_num number not null,
	off_num number not null,
	user_num number not null,
	title varchar2(30) not null,
	content clob not null,
	filename varchar2(100),
	uploadfile blob,
	reg_date date default sysdate not null,
	modify_date date,
	constraint offqna_pk primary key (offqna_num),
	constraint offqna_fk1 foreign key (off_num) references offclass (off_num),
	constraint offqna_fk2 foreign key (user_num) references ouser (user_num)
);
create sequence offqna_seq;


/*오프라인 클래스 1:1게시판 답변*/
create table offqna_reply(
	offqnare_num number not null,
	offqna_num number not null,
	user_num number not null,
	re_content clob not null,
	re_date date default sysdate not null,
	re_mdate date,
	constraint offqna_reply_pk primary key (offqnare_num),
	constraint offqna_reply_fk1 foreign key (offqna_num) references offqna (offqna_num),
	constraint offqna_reply_fk2 foreign key (user_num) references ouser (user_num)
);
create sequence offqna_reply_seq;


/*전체 게시판*/
create table oqna(
	qna_num number not null,
	user_num number not null,
	title varchar2(30) not null,
	content clob not null,
	filename varchar2(100),
	uploadfile blob,
	reg_date date default sysdate not null,
	modify_date date,
	constraint oqna_pk primary key (qna_num),
	constraint oqna_fk1 foreign key (user_num) references ouser (user_num)
);
create sequence oqna_seq;



/*전체게시판 댓글*/
create table oqna_reply(
	oqnare_num number not null,
	qna_num number not null,
	user_num number not null,
	content clob not null,
	re_date date default sysdate not null,
	re_mdate date,
	constraint oqna_reply_pk primary key (oqnare_num),
	constraint oqna_reply_fk1 foreign key (qna_num) references oqna (qna_num),
	constraint oqna_reply_fk2 foreign key (user_num) references ouser (user_num)
); 
create sequence oqna_reply_seq;

