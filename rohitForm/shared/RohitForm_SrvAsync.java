package aarogya.pharmacy.rohitForm.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import dataman.dmBase.self.shared.DmHelper;
import dataman.dmBase.self.shared.DmResult;
import dataman.dmBase.self.shared.DmResultPageing;
import dataman.dmBase.self.shared.KeyFieldExtended;
import java.util.List;

public interface RohitForm_SrvAsync {

    public void getNavigator(AsyncCallback<DmResult<List<KeyFieldExtended>>> result);

    public void getSuggestionDepartmentName(DmHelper helper, boolean blnToEndOfFile, AsyncCallback<DmResultPageing<List<String>>> result);

    public void getMoveRec(String strKey, AsyncCallback<DmResult<RohitForm_Mdl>> result);

    public void deleteRecord(String strKey, AsyncCallback<DmResult<String>> result);

    public void saveRecord(RohitForm_Mdl mrTemp, AsyncCallback<DmResult<String>> result);

    //public void getFind(DmHelper pageing, DmHelper helper, boolean blnToEndOfFile, AsyncCallback<DmResultPageing<List<Find_Mdl>>> result);
    public void getFind(DmHelper pageing, boolean blnToEndOfFile, AsyncCallback<DmResultPageing<List<Find_Mdl>>> result);

}
