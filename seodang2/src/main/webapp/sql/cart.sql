create table ocart(
	cart_num number not null,
	user_num number not null,
	class_num number,
	class_kind varchar2(4) not null, /*off,on,kit*/
	order_quantity number default 1 not null,	/*담긴 상품 총 수량 */
	reg_date date default sysdate not null, /*장바구니에 담은 날짜*/
	constraint ocart_pk primary key (cart_num),
	constraint ocart_fk1 foreign key (user_num) references ouser (user_num)
);
create sequence ocart_seq;

create table oorder(
	order_num number not null,
	item_name varchar2(200) not null,
	order_total number(9) not null,
	payment number(1) not null, /*지불방식(1:통장입금 /2:카드결제)*/
	status number(1), /* order테이블에서는 status 사용이 필요없을 것 같다. */
	receive_name varchar2(30) not null,
	receive_post varchar2(5) not null,
	receive_address1 varchar2(90) not null,
	receive_address2 varchar2(90) not null,
	receive_phone varchar2(15) not null,
	notice varchar2(4000),
	reg_date date default sysdate not null,
	modify_date date,
	user_num number not null,
	constraint order_pk primary key (order_num),
	constraint order_fk1 foreign key (user_num) references ouser(user_num)
);
create sequence oorder_seq;

create table oorder_detail(
	odetail_num number not null,
	class_num number not null,	
	class_kind varchar2(4) not null, /*off,on,kit*/
	item_name varchar2(30) not null,
	item_price number(8) not null,
	item_total number(8) not null,
	order_quantity number(7) not null,
	order_num number not null,
	status number(1), /* null인정해야할듯 (kit이외)/ 배송상태 (1:배송대기 /2:배송준비중 /3:배송중 /4:배송완료 /5:주문취소)*/
	constraint odetail_pk primary key(odetail_num),
	constraint odetail_fk1 foreign key(order_num) references oorder(order_num)
);
create sequence odetail_seq;
















