package aarogya.pharmacy.rohitForm.client;

import aarogya.pharmacy.rohitForm.shared.Find_Mdl;
import aarogya.projectMenu.self.shared.pmPharmacy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.menu.Menu;
import dataman.dmBase.self.shared.DmEnum;
import dataman.dmBase.self.shared.KeyFieldHashCode_T_PrpAcs;
import dataman.dmToolkit.dmFind.client.DmFind;
import dataman.dmToolkit.dmLabel.client.DmLabel;
import dataman.dmToolkit.dmStatic.client.DmClient;
import dataman.dmToolkit.dmTextBox.client.DmTextBox;
import dataman.dmToolkit.dmTextBoxNumeric.client.DmTextBoxNumeric;
import dataman.dmToolkit.dmTopControl.client.DmTopControl;
import dataman.dmToolkit.dmUserControl.client.DmUserControl;
import dataman.dmToolkit.isWidgetExtended.client.IsWidgetExtended;

abstract class RohitForm_Dsg extends IsWidgetExtended {

    private pmPharmacy pmPharmacy = new pmPharmacy();
    final String widgetName = pmPharmacy.ROHIT_FORM.caption;

    public final static String preparedByBlog = "Prepared By";
    public final static String modifiedByBlog = "Modified By";

    final DmClient.ViaList vl = new DmClient.ViaList();
    final FlowPanel flwForm = new FlowPanel();
    final VerticalPanel vrtInnerForm = new VerticalPanel();
    final DmUserControl userControl = new DmUserControl();

    final DmTextBox txtEmployeeName = new DmTextBox(100, true);
    final DmTextBox txtRollNumber = new DmTextBox(100, true);
    final DmTextBox txtEmail = new DmTextBox(100, true);
    final DmTextBoxNumeric txtMobileNumber = new DmTextBoxNumeric(DmTextBoxNumeric.DecimalNature.DEFAULT, false, 10, true);

    final DmTopControl topControl = new DmTopControl(vl, userControl);
    final Menu menu = new Menu();

    //<editor-fold defaultstate="collapsed" desc="dmFind">
    interface DmFind_PrpAcs extends KeyFieldHashCode_T_PrpAcs<Find_Mdl> {

        @Editor.Path("EmployeeName")
        ValueProvider<Find_Mdl, String> employeeName();

        @Editor.Path("RollNumber")
        ValueProvider<Find_Mdl, String> rollNumber();

        @Editor.Path("Email")
        ValueProvider<Find_Mdl, String> email();

        @Editor.Path("MobileNumber")
        ValueProvider<Find_Mdl, String> mobileNumber();

    }
    final DmFind<Find_Mdl, DmFind_PrpAcs> dmFind = new DmFind(GWT.create(DmFind_PrpAcs.class), 22);

    private void iniFind() {
        dmFind.getDmGrid().addTextColumn(dmFind.getDmGrid().getPropertyAccess().employeeName(), "Employee Name", 110, "employeeName");
        dmFind.getDmGrid().addTextColumn(dmFind.getDmGrid().getPropertyAccess().rollNumber(), "Roll Number", 110, "rollNumber");
        dmFind.getDmGrid().addTextColumn(dmFind.getDmGrid().getPropertyAccess().email(), "Email", 150, "email");
        dmFind.getDmGrid().addTextColumn(dmFind.getDmGrid().getPropertyAccess().mobileNumber(), "Mobile Number", 110, "mobileNumber");
        dmFind.getDmGrid().initializeGrid(dmFind.getDmGrid().getPropertyAccess().fieldHashCode(), 1260, 440, 1);

        dmFind.getHelper().addSort("employeeName", DmEnum.DmSortType.ASC);
        dmFind.getHelper().addSort("rollNumber", DmEnum.DmSortType.ASC);
    }
    //</editor-fold>   

    public RohitForm_Dsg() {
        ini();
        iniFind();
    }

    private void ini() {
        FlowPanel flw;
        DmClient.setStyle(vrtInnerForm, "100%", "100%", null, null, DmEnum.DmPosition.NONE, true);
        flwForm.setStyleName("dm-FlwForm");
        flwForm.setHeight("15px");
        vrtInnerForm.add(vl.add(topControl));
        flw = new FlowPanel();
        flw.setStyleName("dm-FlwWorkingArea");
        flw.add(workingArea());

        vrtInnerForm.add(flw);
        vrtInnerForm.add(addUserControl());
        vrtInnerForm.setCellHeight(flw, "100%");
        flwForm.add(vrtInnerForm);
    }

    private DmUserControl addUserControl() {

        userControl.addTextBlog(preparedByBlog, 100, false, true);
        userControl.addGap(100);
        userControl.addTextBlog(modifiedByBlog, 100, false, true);

        return userControl;
    }

    private FlowPanel workingArea() {
        FlowPanel flwTable, flwTr, flwTd;

        flwTable = new FlowPanel();
        DmClient.setStyle(flwTable, "90%", "540px", null, null, DmEnum.DmPosition.CENTER, true);

        /*====================================< topArea >====================================*/
        flwTr = new FlowPanel();
        DmClient.setStyle(flwTr, "100%", "550px", "right", null, DmEnum.DmPosition.NONE, false);
        flwTr.add(topArea());
        flwTable.add(flwTr);
        return flwTable;

    }

    private FlowPanel topArea() {
        FlowPanel flwTable, flwTr, flwTd;
        DmLabel lbl;

        flwTable = new FlowPanel();
        DmClient.setStyle(flwTable, "70%", "90px", null, null, DmEnum.DmPosition.CENTER, true);


        /*=====< 1st Row >==========================< Employee Name | City >========================*/
        flwTr = new FlowPanel();
        DmClient.setStyle(flwTr, "100%", null, "left", null, DmEnum.DmPosition.NONE, true);

        flwTd = new FlowPanel();
        DmClient.setStyle(flwTd, "15%", null, "left", null, DmEnum.DmPosition.NONE, false);
        lbl = new DmLabel("Employee Name");
        flwTd.add(lbl);
        flwTr.add(flwTd);

        flwTd = new FlowPanel();
        DmClient.setStyle(flwTd, "25%", null, "left", null, DmEnum.DmPosition.NONE, false);
        flwTd.add(vl.add(txtEmployeeName));
        flwTr.add(flwTd);
        flwTable.add(flwTr);

        /*+++++ ===============< >================ City*/
        flwTd = new FlowPanel();
        DmClient.setStyle(flwTd, "15%", null, "left", null, DmEnum.DmPosition.NONE, false);
        lbl = new DmLabel("Roll Number");
        lbl.getElement().getStyle().setMarginLeft(20, Style.Unit.PCT); //custom margin
        flwTd.add(lbl);
        flwTr.add(flwTd);

        flwTd = new FlowPanel();
        DmClient.setStyle(flwTd, "25%", null, "left", null, DmEnum.DmPosition.NONE, false);
        flwTd.add(vl.add(txtRollNumber));
        flwTr.add(flwTd);
        flwTable.add(flwTr);

        /*=====< 2nd Row >======================< State | Country >=======================*/
        flwTr = new FlowPanel();
        DmClient.setStyle(flwTr, "100%", null, "left", null, DmEnum.DmPosition.NONE, true);

        flwTd = new FlowPanel();
        DmClient.setStyle(flwTd, "15%", null, "left", null, DmEnum.DmPosition.NONE, false);
        lbl = new DmLabel("Email");
        flwTd.add(lbl);
        flwTr.add(flwTd);

        flwTd = new FlowPanel();
        DmClient.setStyle(flwTd, "25%", null, "left", null, DmEnum.DmPosition.NONE, false);
        flwTd.add(vl.add(txtEmail));
        flwTr.add(flwTd);
        flwTable.add(flwTr);

        /*+++++ ===============< >================ Country*/
        flwTd = new FlowPanel();
        DmClient.setStyle(flwTd, "15%", null, "left", null, DmEnum.DmPosition.NONE, false);
        lbl = new DmLabel("Mobile Number");
        lbl.getElement().getStyle().setMarginLeft(20, Style.Unit.PCT); //custom margin
        flwTd.add(lbl);
        flwTr.add(flwTd);

        flwTd = new FlowPanel();
        DmClient.setStyle(flwTd, "25%", null, "left", null, DmEnum.DmPosition.NONE, false);
        flwTd.add(vl.add(txtMobileNumber));
        flwTr.add(flwTd);
        flwTable.add(flwTr);

        return flwTable;

    }
}
