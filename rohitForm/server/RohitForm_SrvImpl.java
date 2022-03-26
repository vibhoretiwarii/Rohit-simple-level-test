package aarogya.pharmacy.rohitForm.server;

import aarogya.pharmacy.rohitForm.shared.Find_Mdl;
import aarogya.pharmacy.rohitForm.shared.RohitForm_Mdl;
import aarogya.pharmacy.rohitForm.shared.RohitForm_Srv;
import aarogya.pharmacy.rohitForm.shared.RohitForm_Vld;
import dataman.dmBase.dmPrepareStatement.server.DmPreparedStatement;
import dataman.dmBase.dmPrepareStatement.server.DmSavePoint;
import dataman.dmBase.dmSession.server.DmRSSPrivate;
import dataman.dmBase.self.shared.DmException;
import dataman.dmBase.self.shared.DmHelper;
import dataman.dmBase.self.shared.DmResult;
import dataman.dmBase.self.shared.DmResultPageing;
import dataman.dmBase.self.shared.DmSQLWithParam;
import dataman.dmBase.self.shared.KeyFieldExtended;
import dataman.dmToolkit.dmStatic.server.DmServer;
import dataman.dmToolkit.dmStatic.shared.DmShared;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RohitForm_SrvImpl extends DmRSSPrivate implements RohitForm_Srv {

    @Override
    public DmResult<String> saveRecord(RohitForm_Mdl mrTemp) {
        DmResult<String> dmrTemp = new DmResult();
        Connection cnTemp = null;
        DmPreparedStatement psTemp = null;
        ResultSet rsTemp = null;
        DmSavePoint spBegin = null;
        boolean blnAdd = false;
        String sqlQuery;
        dmrTemp.setData("");

        try {
            RohitForm_Vld.chkSave(mrTemp);

            cnTemp = DmServer.getConnection(DmServer.getSsnDBName(this));
            spBegin = new DmSavePoint(cnTemp);

            mrTemp.setSharedKeyFieldName(DmShared.removeSpace(mrTemp.getSharedKeyFieldName()));
            if (DmShared.iNull(mrTemp.getSharedKeyFieldCode()).intValue() == 0) {
                blnAdd = true;
            }

            /*===============================< Insert/Update record into table >==============================*/
            if (blnAdd) {
                sqlQuery = "Insert Into Rohitnewtable(employeeName, rollNumber, mobileNumber, email, preparedBy, preparedDt ) "
                        + " Values (:employeeName,:rollNumber, :mobileNumber, :email, :preparedBy, :preparedDt ) ";
                // + " returning code; ";
                psTemp = DmPreparedStatement.prepareStatement(cnTemp, sqlQuery);
                psTemp.setString("employeeName", DmShared.xNull(mrTemp.getEmployeeName().trim()));
                psTemp.setString("rollNumber", DmShared.xNull(mrTemp.getRollNumber().trim()));
                psTemp.setString("mobileNumber", DmShared.xNull(mrTemp.getMobileNumber().trim()));
                //psTemp.setString("mobileNumber", DmShared.xNull(mrTemp.getMobileNumber()).trim());
                psTemp.setString("email", DmShared.xNull(mrTemp.getEmail()).trim());
                psTemp.setString("preparedBy", DmShared.xNull(DmServer.getSsnUserName(this)));
                psTemp.setTimestamp("prepareddt", DmServer.getServerTimestamp());
                //rsTemp = 
                psTemp.execute();
//                if (rsTemp.next()) {
//                    mrTemp.setSharedKeyFieldCode(DmShared.iNull(rsTemp.getInt(1)).toString());
//                }
                psTemp.close();

            } else {
                sqlQuery = "Update Rohitnewtable Set "
                        + " employeeName = :employeeName, "
                        + " rollNumber = :rollNumber, "
                        + " mobileNumber = :mobileNumber, "
                        + " email = :email, "
                        + " Where code = :code ";
                psTemp = DmPreparedStatement.prepareStatement(cnTemp, sqlQuery);
                psTemp.setInt("code", DmShared.iNull(mrTemp.getSharedKeyFieldCode()).intValue());
                psTemp.setString("employeeName", DmShared.xNull(mrTemp.getEmployeeName()).trim());
                psTemp.setInt("rollNumber", DmShared.iNull(mrTemp.getRollNumber()).intValue());
                psTemp.setInt("mobileNumber", DmShared.iNull(mrTemp.getMobileNumber()).intValue());
                psTemp.setString("email", DmShared.xNull(mrTemp.getEmail()).trim());
                psTemp.executeUpdate();
                psTemp.close();
            }
            dmrTemp.setData(mrTemp.getSharedKeyFieldCode());
            spBegin.commit();
            dmrTemp.setStatus(true);
            cnTemp.close();

        } catch (Exception e) {
            try {
                if (spBegin != null) {
                    spBegin.rollBack();
                }
            } catch (Exception ex) {
            }
            if (e instanceof DmException) {
                dmrTemp.setErrorNumber(((DmException) e).getErrorNo());
            }
            dmrTemp.setErrorMessage(DmShared.xNull(e.getMessage()));
        } finally {
            try {
                if (rsTemp != null) {
                    rsTemp.close();
                }
                if (psTemp != null) {
                    psTemp.close();
                }
                if (cnTemp != null) {
                    cnTemp.close();
                }
            } catch (SQLException e) {
            }
        }

        return dmrTemp;
    }

    @Override
    public DmResult<List<KeyFieldExtended>> getNavigator() {
        DmResult<List<KeyFieldExtended>> dmrTemp = new DmResult();
        Connection cnTemp = null;
        ResultSet rsTemp = null;
        List<KeyFieldExtended> lstNavigator = new ArrayList<>();
        KeyFieldExtended lstNavigatorTmp;
        dmrTemp.setData(lstNavigator);
        String sqlQuery;
        DmPreparedStatement psTemp = null;

        try {
            cnTemp = DmServer.getConnection(DmServer.getSsnDBName(this));

            sqlQuery = "Select code From Rohitnewtable order by employeeName ";
            psTemp = DmPreparedStatement.prepareStatement(cnTemp, sqlQuery);
            rsTemp = psTemp.executeQuery();
            while (rsTemp.next()) {
                lstNavigatorTmp = new KeyFieldExtended();
                lstNavigatorTmp.setSharedKeyFieldCode(DmShared.iNull(rsTemp.getInt("code")).toString());
                lstNavigator.add(lstNavigatorTmp);
            }
            rsTemp.close();
            psTemp.close();
            cnTemp.close();
            dmrTemp.setStatus(true);
        } catch (SQLException e) {
            dmrTemp.setErrorMessage(DmShared.xNull(e.getMessage()));
        } finally {
            try {
                if (rsTemp != null) {
                    rsTemp.close();
                }
                if (psTemp != null) {
                    psTemp.close();
                }
                if (cnTemp != null) {
                    cnTemp.close();
                }
            } catch (SQLException e) {
            }
        }
        return dmrTemp;
    }

    @Override
    public DmResultPageing<List<String>> getSuggestionDepartmentName(DmHelper helper, boolean blnToEndOfFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DmResult<RohitForm_Mdl> getMoveRec(String strKey) {
        DmResult<RohitForm_Mdl> dmrTemp = new DmResult();
        Connection cnTemp = null;
        DmPreparedStatement psTemp = null;
        ResultSet rsTemp = null;
        String sqlQuery;
        RohitForm_Mdl mrTemp = new RohitForm_Mdl();
        dmrTemp.setData(mrTemp);

        try {
            cnTemp = DmServer.getConnection(DmServer.getSsnDBName(this));

            /*========< Get Detail >========*/
            sqlQuery = "Select sm.code, sm.employeeName, sm.mobileNumber, sm.email, sm.rollNumber, sm.email, sm.preparedBy "
                    + "         from Rohitnewtable as sm "
                    + "         Where sm.code = :code ";
            psTemp = DmPreparedStatement.prepareStatement(cnTemp, sqlQuery);
            psTemp.setInt("code", DmShared.iNull(strKey).intValue());
            rsTemp = psTemp.executeQuery();
            if (rsTemp.next()) {
                mrTemp.setSharedKeyFieldCode(DmShared.iNull(rsTemp.getInt("code")).toString());
                mrTemp.setEmployeeName(DmShared.xNull(rsTemp.getString("employeeName")));
                //mrTemp.setEmail(DmShared.iNull(rsTemp.getInt("email")).toString());
                mrTemp.setMobileNumber(DmShared.xNull(rsTemp.getString("mobileNumber")));
                mrTemp.setEmail(DmShared.xNull(rsTemp.getString("email")));
                mrTemp.setRollNumber(DmShared.xNull(rsTemp.getString("rollNumber")));
                mrTemp.setPreparedBy(DmShared.xNull(rsTemp.getString("preparedBy")));
            }
            psTemp.close();
            rsTemp.close();
            cnTemp.close();
            dmrTemp.setStatus(true);

            //============================<Set Old Data in Session >==============================//
            DmServer.setSsnOldData(getClass().getName(), mrTemp, this);

        } catch (Exception e) {
            dmrTemp.setErrorMessage(DmShared.xNull(e.getMessage()));
        } finally {
            try {
                if (rsTemp != null) {
                    rsTemp.close();
                }
                if (psTemp != null) {
                    psTemp.close();
                }
                if (cnTemp != null) {
                    cnTemp.close();
                }
            } catch (SQLException e) {
            }
        }

        return dmrTemp;
    }

    @Override
    public DmResult<String> deleteRecord(String strKey) {
        DmResult<String> dmrTemp = new DmResult();
        Connection cnTemp = null;
        DmPreparedStatement psTemp = null;
        DmSavePoint spBegin = null;
        String sqlQuery;
        ResultSet rsTemp = null;
        dmrTemp.setData(strKey);

        try {
            cnTemp = DmServer.getConnection(DmServer.getSsnDBName(this));
            spBegin = new DmSavePoint(cnTemp);

            sqlQuery = "Delete From Rohitnewtable Where code= :code ";
            psTemp = DmPreparedStatement.prepareStatement(cnTemp, sqlQuery);
            psTemp.setInt("code", DmShared.iNull(strKey).intValue());
            psTemp.executeUpdate();
            psTemp.close();

            spBegin.commit();
            cnTemp.close();
            dmrTemp.setStatus(true);
        } catch (Exception e) {
            try {
                if (spBegin != null) {
                    spBegin.rollBack();
                }
            } catch (Exception ex) {
            }
            dmrTemp.setErrorMessage(DmShared.xNull(e.getMessage()));
        } finally {
            try {
                if (rsTemp != null) {
                    rsTemp.close();
                }
                if (psTemp != null) {
                    psTemp.close();
                }
                if (cnTemp != null) {
                    cnTemp.close();
                }
            } catch (SQLException e) {
            }
        }
        return dmrTemp;
    }

    @Override
    public DmResultPageing<List<Find_Mdl>> getFind(DmHelper helper, boolean blnToEndOfFile) {
        DmResultPageing<List<Find_Mdl>> rstTemp = new DmResultPageing<>();
        List<Find_Mdl> listNode = new ArrayList<>();
        Find_Mdl node;
        Connection cnTemp = null;
        ResultSet rsTemp = null;
        DmPreparedStatement psTemp = null;
        String sqlQuery;
        DmSQLWithParam sqlWithParam = new DmSQLWithParam();
        rstTemp.setData(listNode);

        try {
            cnTemp = DmServer.getConnection(DmServer.getSsnDBName(this));

            sqlQuery = " Select rt.code, rt.employeename , rt.email, rt.rollNumber,"
                    + "  rt.mobileNumber "
                    + "  from Rohitnewtable rt ";
            sqlWithParam.setSQL(sqlQuery);
            psTemp = DmServer.evaluateHelper(helper, sqlWithParam, cnTemp, blnToEndOfFile, this);
            rsTemp = psTemp.executeQuery();
            while (rsTemp.next()) {
                node = new Find_Mdl();
                node.setSharedKeyFieldCode(DmShared.iNull(rsTemp.getLong("code")).toString());
                node.setEmployeeName(DmShared.xNull(rsTemp.getString("employeename").trim()));
                node.setEmail(DmShared.xNull(rsTemp.getString("email").trim()));
                node.setRollNumber(DmShared.xNull(rsTemp.getString("rollNumber").trim()));
                node.setMobileNumber(DmShared.xNull(rsTemp.getString("mobileNumber").trim()));

//                node.setRollNumber(DmShared.dNull(rsTemp.getDate("rollNumber")));
//                node.setMobileNumber(DmShared.iNull(rsTemp.getInt("mobileNumber")).toString());
                // node.setPreparedBy(DmShared.xNull(rsTemp.getString("preparedBy")));
                // node.setModifiedBy(DmShared.xNull(rsTemp.getString("modifiedBy")));
                listNode.add(node);
            }
            rsTemp.close();
            psTemp.close();

            helper.positioning(listNode.size(), blnToEndOfFile);
            rstTemp.setPaging(helper.getPaging());
            rstTemp.setStatus(true);
            cnTemp.close();
        } catch (Exception e) {
            rstTemp.setErrorMessage(DmShared.xNull(e.getMessage()));
        } finally {
            try {
                if (rsTemp != null) {
                    rsTemp.close();
                }
                if (psTemp != null) {
                    psTemp.close();
                }
                if (cnTemp != null) {
                    cnTemp.close();
                }
            } catch (SQLException e) {
            }
        }
        return rstTemp;
    }

}
