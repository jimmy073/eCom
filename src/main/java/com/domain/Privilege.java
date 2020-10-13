package com.domain;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Privilege {
	@Id@GeneratedValue
	private Long id;
	private String privilegeName;
	@ManyToMany
	private Set<Role> roles;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}


}
