package aarogya.pharmacy.rohitForm.shared;

import dataman.dmBase.dmBaseStatic.shared.DmBaseShared;
import dataman.dmBase.self.shared.DmException;
import dataman.dmToolkit.dmStatic.shared.DmShared;

public class RohitForm_Vld {

    public static void chkSave(RohitForm_Mdl mrTemp) {
        if (DmShared.iNull(mrTemp.getEmployeeName().length()).intValue() == 0) {
            throw new DmException(100, "Please fill Employee Name.");
        }
        if (DmShared.iNull(mrTemp.getRollNumber().length()).intValue() == 0) {
            throw new DmException(200, "Please fill Roll Number.");
        }
        if (DmShared.iNull(mrTemp.getEmail().length()).intValue() == 0) {
            throw new DmException(300, "Please fill Email.");
        }
        DmBaseShared.isValidEMail(mrTemp.getEmail(), 300);

        if (DmShared.iNull(mrTemp.getMobileNumber().length()).intValue() < 10) {
            throw new DmException(400, "Invalid Mobile Number.");
        }
    }
}
