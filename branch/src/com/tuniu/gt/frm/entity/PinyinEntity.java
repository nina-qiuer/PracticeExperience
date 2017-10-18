package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;


public class PinyinEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 6863755929276433508L;


	private String s=""; //

	private String pinyin=""; //

	private String hanzi=""; //



	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s; 
	}

	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin; 
	}

	public String getHanzi() {
		return hanzi;
	}
	public void setHanzi(String hanzi) {
		this.hanzi = hanzi; 
	}
	
}
