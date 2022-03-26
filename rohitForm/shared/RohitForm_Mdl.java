package aarogya.pharmacy.rohitForm.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import dataman.dmBase.self.shared.KeyField;
import java.io.Serializable;

public class RohitForm_Mdl extends KeyField implements Serializable, IsSerializable {

    private String email;
    private String mobileNumber;
    private String employeeName;
    private String rollNumber;
    private String preparedBy;
    private String preparedDt;

    public RohitForm_Mdl() {

    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedDt() {
        return preparedDt;
    }

    public void setPreparedDt(String preparedDt) {
        this.preparedDt = preparedDt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

}
