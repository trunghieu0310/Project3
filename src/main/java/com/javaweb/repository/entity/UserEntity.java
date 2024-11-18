package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {
	@Id
//	Tự tăng
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "username",nullable = false,unique = true)
	private String userName;
	@Column(name = "password",nullable = false)
	private String passWord;
	@Column(name = "fullname")
	private String fullName;
	@Column(name = "status",nullable = false)
	private String status;
	@Column(name = "email",nullable = false)
	private String email;
	@OneToMany(mappedBy="user",fetch = FetchType.LAZY)
    private List<UserRoleEntity> userRoleEntities = new ArrayList<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<UserRoleEntity> getUserRoleEntities() {
		return userRoleEntities;
	}
	public void setUserRoleEntities(List<UserRoleEntity> userRoleEntities) {
		this.userRoleEntities = userRoleEntities;
	}
	
}
