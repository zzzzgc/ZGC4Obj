package xinxing.boss.admin.system.user.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="sys_user")
@DynamicInsert @DynamicUpdate
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String loginName;
	private String name;
	private String salt;
	private String password;
	private String email;
	private String phone;
	private Integer state;
	private Integer bind;
	private Timestamp time;
	private String yangId;
	private String yangEventId;
	private Integer isCustomer;
	
	private String plainPassword;
	
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	
	public User() {
		
	}
	
	public User(Integer userId) {
		this.id = userId;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="login_name", nullable=false, length=20)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@Column(name="name", nullable=false, length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="password",nullable=false,length=50)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="email",length=30)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="phone", length=11)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Column(name="time")
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	@Column(name="salt",nullable=false,length=20)
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Transient
	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	@Column(name="bind")
	public Integer getBind() {
		return bind;
	}

	public void setBind(Integer bind) {
		this.bind = bind;
	}

	@Column(name="yangid")
	public String getYangId() {
		return yangId;
	}

	public void setYangId(String yangId) {
		this.yangId = yangId;
	}

	@Column(name="yangeventid")
	public String getYangEventId() {
		return yangEventId;
	}

	public void setYangEventId(String yangEventId) {
		this.yangEventId = yangEventId;
	}

	@Column(name="iscustomer")
	public Integer getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(Integer isCustomer) {
		this.isCustomer = isCustomer;
	}
	
	
	
	
}
