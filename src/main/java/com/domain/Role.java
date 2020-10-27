package com.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(unique = true, nullable = false)
	private String name;
	@OneToMany
	private List<User> users;
	@ManyToMany
	@JoinTable(
			name="role_privileges",
			joinColumns = {@JoinColumn(name = "role_id")},
		    inverseJoinColumns = {@JoinColumn(name = "privilege_id")}
			)
	private Set<Privilege> privileges;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public Role() {
		super();
	}

	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the privileges
	 */
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges the privileges to set
	 */
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}




}
