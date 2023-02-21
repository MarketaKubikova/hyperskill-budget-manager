
class Person {
    protected String name;
    protected String sex;
    protected String dateOfBirth;

    public Person(String name, String sex, String dateOfBirth) {
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

class Employee extends Person{
    protected String startDate;
    protected Long salary;

    public Employee(String name, String sex, String dateOfBirth, String startDate, Long salary) {
        super(name, sex, dateOfBirth);
        this.startDate = startDate;
        this.salary = salary;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}

class Doctor extends Employee{
    protected String specialization;

    public Doctor(String name, String sex, String dateOfBirth, String startDate, Long salary, String specialization) {
        super(name, sex, dateOfBirth, startDate, salary);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

class Patient extends Person{
    protected String disease;

    public Patient(String name, String sex, String dateOfBirth, String disease) {
        super(name, sex, dateOfBirth);
        this.disease = disease;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
