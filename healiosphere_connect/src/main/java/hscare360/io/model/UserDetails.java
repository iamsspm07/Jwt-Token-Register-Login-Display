package hscare360.io.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "user_details", schema = "users_masters")
public class UserDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(nullable = false)
    private String password;

    private LocalDateTime registrationDate;

    private LocalDateTime userDeactivationDate;

    @Column(nullable = false)
    private String userProfession;
    
    @Column(nullable = false)
    private String userRole;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public LocalDateTime getUserDeactivationDate() {
		return userDeactivationDate;
	}

	public void setUserDeactivationDate(LocalDateTime userDeactivationDate) {
		this.userDeactivationDate = userDeactivationDate;
	}

	public String getUserProfession() {
		return userProfession;
	}

	public void setUserProfession(String userProfession) {
		this.userProfession = userProfession;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", email=" + email + ", name=" + name + ", number=" + number
				+ ", password=" + password + ", registrationDate=" + registrationDate + ", userDeactivationDate="
				+ userDeactivationDate + ", userProfession=" + userProfession + ", userRole=" + userRole + "]";
	}

	public UserDetails(Long userId, String email, String name, String number, String password,
			LocalDateTime registrationDate, LocalDateTime userDeactivationDate, String userProfession,
			String userRole) {
		super();
		this.userId = userId;
		this.email = email;
		this.name = name;
		this.number = number;
		this.password = password;
		this.registrationDate = registrationDate;
		this.userDeactivationDate = userDeactivationDate;
		this.userProfession = userProfession;
		this.userRole = userRole;
	}

	public UserDetails() {
		super();
	}
}
