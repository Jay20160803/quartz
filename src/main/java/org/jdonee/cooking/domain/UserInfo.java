package org.jdonee.cooking.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


public class UserInfo implements Serializable {

	private static final long serialVersionUID = -2736075850489736894L;

	private int id;
	private String account;
	private String password;
	private String name;
	private Date createTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}