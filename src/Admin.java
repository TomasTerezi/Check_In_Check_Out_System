
public class Admin {
	
	private String name, surname, gender, age, email, address, checkIn, checkOut, jobPosition;

	public Admin(String name, String surname, String age, String gender, String email, String address, String checkIn,
			String checkOut, String jobPosition) {
		super();
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.address = address;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.jobPosition = jobPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	@Override
	public String toString() {
		return "Admin [name=" + name + ", surname=" + surname + ", age=" + age + ", gender=" + gender + ", email="
				+ email + ", address=" + address + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", jobPosition="
				+ jobPosition + "]";
	}
	
	
	
	

}
