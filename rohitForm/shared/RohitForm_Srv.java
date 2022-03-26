package aarogya.pharmacy.rohitForm.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import dataman.dmBase.self.shared.DmHelper;
import dataman.dmBase.self.shared.DmResult;
import dataman.dmBase.self.shared.DmResultPageing;
import dataman.dmBase.self.shared.KeyFieldExtended;
import java.util.List;

public interface RohitForm_Srv extends RemoteService {

    public DmResult<List<KeyFieldExtended>> getNavigator();

    public DmResultPageing<List<String>> getSuggestionDepartmentName(DmHelper helper, boolean blnToEndOfFile);

    public DmResult<RohitForm_Mdl> getMoveRec(String strKey);

    public DmResult<String> saveRecord(RohitForm_Mdl mrTemp);

    public DmResult<String> deleteRecord(String strKey);

    //public DmResultPageing<List<Find_Mdl>> getFind(DmHelper pageing, DmHelper helper, boolean blnToEndOfFile);
    public DmResultPageing<List<Find_Mdl>> getFind(DmHelper pageing, boolean blnToEndOfFile);

}
