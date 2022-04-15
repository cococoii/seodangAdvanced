package kr.spring.offclass.vo;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

import org.hibernate.validator.constraints.Range;

public class OffTimetableVO {
	private int time_num;
	private int off_num;
	private Date time_date;
	private String string_date;//string으로 받을 수 있는 공간
	private String day;//day로 받을 수 있는 공간
	private String time_start;
	private String time_end;
	private int off_personcount;//신청한 인원수
	private int off_limit;//최대 인원수
	
	public OffTimetableVO() {}
	public OffTimetableVO(int time_num,int off_num,Date time_date,String time_start,String time_end) {
		this.time_num=time_num;
		this.off_num= off_num;
		this.time_date=time_date;
		this.time_start=time_start;
		this.time_end=time_end;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this)
            return true;
        if (!(o instanceof OffTimetableVO))
            return false;
        OffTimetableVO other = (OffTimetableVO) o;
        boolean time_numEquals = (Integer.valueOf(this.time_num) == null && Integer.valueOf(other.time_num) == null)
          || (Integer.valueOf(this.time_num) != null && Integer.valueOf(this.time_num).equals(other.time_num));
        boolean off_numEquals = (Integer.valueOf(this.off_num) == null && Integer.valueOf(other.off_num) == null)
          || (Integer.valueOf(this.off_num) != null && Integer.valueOf(this.off_num).equals(other.off_num));
        return time_numEquals && (this.time_date.equals(other.time_date))
        		&& (this.time_start.equals(other.time_start)) && (this.time_end.equals(other.time_end));
	}
    // hashcode 재정의
    @Override
    public int hashCode() {
        return Objects.hash(time_num, off_num,time_date,time_start,time_end);
    }
	
	public int getTime_num() {
		return time_num;
	}
	public void setTime_num(int time_num) {
		this.time_num = time_num;
	}
	public int getOff_num() {
		return off_num;
	}
	public void setOff_num(int off_num) {
		this.off_num = off_num;
	}
	public Date getTime_date() {
		return time_date;
	}
	public String getTime_start() {
		return time_start;
	}
	public String getString_date() {
		return string_date;
	}
	public void setString_date(String string_date) {
		this.string_date = string_date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public void setTime_date(Date time_date) {
		this.time_date = time_date;
	}
	public int getOff_personcount() {
		return off_personcount;
	}
	public void setOff_personcount(int off_personcount) {
		this.off_personcount = off_personcount;
	}
	public int getOff_limit() {
		return off_limit;
	}
	public void setOff_limit(int off_limit) {
		this.off_limit = off_limit;
	}
	@Override
	public String toString() {
		return "OffTimetableVO [time_num=" + time_num + ", off_num=" + off_num + ", time_date=" + time_date
				+ ", string_date=" + string_date + ", day=" + day + ", time_start=" + time_start + ", time_end="
				+ time_end + ", off_personcount=" + off_personcount + ", off_limit=" + off_limit + "]";
	}
	
}
