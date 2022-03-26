package aarogya.pharmacy.rohitForm.client;

import aarogya.pharmacy.rohitForm.shared.Find_Mdl;
import aarogya.pharmacy.rohitForm.shared.RohitForm_Mdl;
import aarogya.pharmacy.rohitForm.shared.RohitForm_Srv;
import aarogya.pharmacy.rohitForm.shared.RohitForm_SrvAsync;
import aarogya.pharmacy.rohitForm.shared.RohitForm_Vld;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import dataman.dmBase.self.shared.DmException;
import dataman.dmBase.self.shared.DmHelper;
import dataman.dmBase.self.shared.DmResult;
import dataman.dmBase.self.shared.DmResultPageing;
import dataman.dmBase.self.shared.KeyFieldExtended;
import dataman.dmBase.self.shared.ServiceInvoker;
import dataman.dmBase.self.shared.SetWidgetInvoker;
import dataman.dmBase.self.shared.UserMenu;
import dataman.dmBase.self.shared.VoucherPermissionHandler;
import dataman.dmToolkit.dmFind.client.DmFind;
import dataman.dmToolkit.dmMessageBox.client.DmAlertMessageBox;
import dataman.dmToolkit.dmMessageBox.client.DmConfirmMessageBox;
import dataman.dmToolkit.dmStatic.client.DmClient;
import dataman.dmToolkit.dmStatic.shared.DmShared;
import dataman.dmToolkit.dmTextBox.client.DmTextBox;
import dataman.dmToolkit.dmTopControl.client.DmTopControl;
import java.util.List;

public class RohitForm extends RohitForm_Dsg {

    private final ServiceInvoker<RohitForm_SrvAsync> srvAsync;

    public RohitForm(UserMenu menuPermission, SetWidgetInvoker swi) {
        super();
        this.asWidget().setTitle(widgetName);
        srvAsync = new ServiceInvoker<>(GWT.create(RohitForm_Srv.class), RohitForm.class.getName());

        menuPermission.setUserPermission(DmShared.xNull(menuPermission.getUserPermission()).replace("P", "*"));
        topControlEvents(menuPermission);
        swi.setWidget(this);

//      textHelpEvents();
        findEvents();
    }

    @Override
    public Widget asWidget() {
        return flwForm;
    }

    @Override
    public void searchRecord(String key) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void topControlEvents(UserMenu menuPermission) {
        topControl.setTitle(this.asWidget().getTitle());
        topControl.setOtherRequired(false);
        topControl.setRefreshRequired(false);

        topControl.setUserPermission(menuPermission, (VoucherPermissionHandler) null);

        topControl.addNewHandeller(new DmTopControl.eNew() {
            @Override
            public void newClicked() {
                onAdd();
            }
        });

        topControl.addEditHandeller(new DmTopControl.eEdit() {
            @Override
            public void editClicked() {
                txtEmployeeName.setFocus(true);
            }
        });

        topControl.addDeleteHandeller(new DmTopControl.eDelete() {
            @Override
            public void deleteClicked() {
                new DmConfirmMessageBox("Are you sure? You want to delete this record.", new DialogHideEvent.DialogHideHandler() {

                    @Override
                    public void onDialogHide(DialogHideEvent event) {
                        if (event.getHideButton() == Dialog.PredefinedButton.YES) {
                            topControlDelete(topControl.getDocId());
                        }
                    }
                }).show();
            }
        });

        topControl.addCardHandeller(new DmTopControl.eCard() {
            @Override
            public void cardClicked() {
                topControlCard();
            }
        });

        topControl.addFindHandeller(new DmTopControl.eFind() {
            @Override
            public void findClicked() {
                flwForm.remove(vrtInnerForm);
                flwForm.add(dmFind);
            }
        });

        topControl.addMoveRecHandeller(new DmTopControl.iMoveRec() {
            @Override
            public void moveRec(String strFetchCode) {
                topControlMoveRec(strFetchCode);
            }
        });

        topControl.addPrintHandeller(new DmTopControl.ePrint() {

            @Override
            public void printClicked() {

            }
        });

        topControl.addSaveHandeller(new DmTopControl.eSave() {
            @Override
            public void saveClicked() {
                RohitForm_Mdl mrTemp = new RohitForm_Mdl();

                mrTemp = topControlSaveInitialize();
                try {
                    RohitForm_Vld.chkSave(mrTemp);
                    if (topControl.getMode().equalsIgnoreCase("Edit")) {
                        topControlSave(mrTemp);
                    } else {
                        topControlSave(mrTemp);
                    }
                } catch (DmException e) {
                    topControlSaveError(e.getErrorNo(), e.getMessage());
                }
            }
        });

        topControl.addCloseHandeller(new DmTopControl.eClose() {
            @Override
            public void closeClicked() {
                DmClient.<SetWidgetInvoker>getStoreItem("swiEvent").closeWidget(RohitForm.this);
            }
        });

        topControl.setDisplay(true);
    }

    private RohitForm_Mdl topControlSaveInitialize() {
        RohitForm_Mdl mrTemp = new RohitForm_Mdl();

        if (!topControl.getMode().equalsIgnoreCase("New")) {
            mrTemp.setSharedKeyFieldCode(topControl.getDocId());
        }
        mrTemp.setEmployeeName(DmShared.xNull(txtEmployeeName.getText()));
        mrTemp.setEmail(DmShared.xNull(txtEmail.getText()));
        mrTemp.setRollNumber(DmShared.xNull(txtRollNumber.getText()));
        mrTemp.setMobileNumber(DmShared.xNull(txtMobileNumber.getText()));
        mrTemp.setEmail(DmShared.xNull(txtEmail.getText()));
        mrTemp.setPreparedBy(preparedByBlog);

        return mrTemp;
    }

    private void topControlSave(RohitForm_Mdl mrTemp) {
        try {

            srvAsync.invokeRPC().saveRecord(mrTemp, new AsyncCallback<DmResult<String>>() //what??
            {
                @Override
                public void onFailure(Throwable caught) {
                    new DmAlertMessageBox(caught.getMessage(), txtEmployeeName).show();
                }

                @Override
                public void onSuccess(DmResult<String> result) {
                    if (result.isStatus()) {
                        topControl.setDocId(result.getData());

                        if (topControl.getMode().equalsIgnoreCase("New")) {
                            topControl.btnClick("New");
                        } else {
                            topControlMoveRec(topControl.getDocId());
                        }
                    } else {
                        topControlSaveError(result.getErrorNumber(), result.getErrorMessage());
                    }
                }
            });
        } catch (DmException e) {
            topControlSaveError(e.getErrorNo(), e.getMessage());
        }
    }

    private void topControlSaveError(int errorNo, String errorMessage) {
        switch (errorNo) {
            case 100:
                new DmAlertMessageBox(errorMessage, txtEmployeeName).show();
                break;
            case 200:
                new DmAlertMessageBox(errorMessage, txtRollNumber).show();
                break;
            case 300:
                new DmAlertMessageBox(errorMessage, txtEmail).show();
                break;
            case 400:
                new DmAlertMessageBox(errorMessage, txtMobileNumber).show();
                break;
            default:
                new DmAlertMessageBox(errorMessage, txtEmail).show();
        }
    }

    private void topControlCard() {
        srvAsync.invokeRPC().getNavigator(new AsyncCallback<DmResult<List<KeyFieldExtended>>>() //rpc is hitting.    
        {
            @Override
            public void onFailure(Throwable caught) {
                new DmAlertMessageBox(caught.getMessage(), (DmTextBox) null).show();
            }

            @Override
            public void onSuccess(DmResult<List<KeyFieldExtended>> result) {
                if (result.isStatus()) {
                    if (result.isStatus()) {
                        topControl.setNavigator(result.getData());
                    } else {
                        new DmAlertMessageBox(result.getErrorMessage(), (DmTextBox) null).show();
                    }
                }
            }
        });
    }

    private void topControlMoveRec(String strKey) {
        srvAsync.invokeRPC().getMoveRec(strKey, new AsyncCallback<DmResult<RohitForm_Mdl>>() {

            @Override
            public void onFailure(Throwable caught) {
                new DmAlertMessageBox(caught.getMessage(), (DmTextBox) null).show();
            }

            @Override
            public void onSuccess(DmResult<RohitForm_Mdl> result) {
                topControlMoveRecSuccess(result);
            }
        });
    }

    private void findEvents() {
        dmFind.setTitle(this.asWidget().getTitle());

        dmFind.addHelpGridCallBack(new DmFind.FindCallBack<Find_Mdl>() {

            @Override
            public void populateData(DmHelper helper, boolean blnToEndOfFile, boolean blnCallBackWait) {
                srvAsync.invokeRPC(blnCallBackWait).getFind(helper, blnToEndOfFile, new AsyncCallback<DmResultPageing<List<Find_Mdl>>>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        new DmAlertMessageBox(caught.getMessage(), dmFind.getTextBox()).show();
                    }

                    @Override
                    public void onSuccess(DmResultPageing<List<Find_Mdl>> result) {
                        if (result.isStatus()) {
                            dmFind.setData(result.getData(), result.getPaging());
                        } else {
                            new DmAlertMessageBox(result.getErrorMessage(), dmFind.getTextBox()).show();
                        }
                    }
                });
            }

            @Override
            public void getSelectedRow(boolean blnStatus, Find_Mdl tRow) {
                flwForm.remove(dmFind);
                flwForm.add(vrtInnerForm);
                if (blnStatus) {
                    topControlMoveRec(tRow.getSharedKeyFieldCode());
                }
            }
        });
    }

    private void topControlMoveRecSuccess(DmResult<RohitForm_Mdl> result) {
        RohitForm_Mdl data;
        clear();
        if (result.isStatus()) {
            data = result.getData();
            topControl.setDocId(DmShared.xNull(data.getSharedKeyFieldCode()));
            txtEmployeeName.setText(DmShared.xNull(data.getEmployeeName()));
            txtRollNumber.setText(DmShared.xNull(data.getRollNumber()));
            txtEmail.setText(DmShared.xNull(data.getEmail()));
            txtMobileNumber.setText(DmShared.xNull(data.getMobileNumber()));
            userControl.setTextBlog(preparedByBlog, DmShared.xNull(data.getPreparedBy()));
            userControl.setTextBlog(modifiedByBlog, DmShared.xNull(data.getSharedModifiedBy()));
        } else {
            new DmAlertMessageBox(result.getErrorMessage(), (DmTextBox) null).show();
        }
        topControl.setDisplay(true);
    }

    private void topControlDelete(String strKey) {
        srvAsync.invokeRPC().deleteRecord(strKey, new AsyncCallback<DmResult<String>>() {

            @Override
            public void onFailure(Throwable caught) {
                new DmAlertMessageBox(caught.getMessage(), (DmTextBox) null).show();
            }

            @Override
            public void onSuccess(DmResult<String> result) {
                if (result.isStatus()) {
                    clear();
                    topControlCard();
                } else {
                    new DmAlertMessageBox(DmShared.xNull(result.getErrorMessage()), (DmTextBox) null).show();
                }
            }
        });
    }

    private void onAdd() {
        clear();
        txtEmployeeName.setFocus(true);
    }

    private void clear() {
        userControl.setTextBlog(preparedByBlog, "");
        userControl.setTextBlog(modifiedByBlog, "");
    }

}
