create table off_timetable(
	time_num number not null,
	off_num number not null,
	time_date date not null,
	time_start varchar2(10) not null,
	time_end varchar2(10) not null,
	off_personcount number(2) default 0 not null,
	off_limit NUMBER(2) NOT NULL,
	constraint off_timetable_pk primary key(time_num),
	constraint off_timetalbe_fk foreign key(off_num) references offclass (off_num)
);

create sequence off_timetable_seq;