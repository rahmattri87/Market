package model;
import com.db.DBQuery;
import java.sql.Connection;

public class AdminModel {

  private String getPrivilegeItem(boolean vPriv){
      String result="";
      if (vPriv) {
          result="1";
      }else
          result="0";

      return result;
  }

  public void PrivilegeSetup() {
    String[] mPriv;
    mPriv = new String[10];

    mPriv[PrivIndex.Admin.ordinal()]=
      getPrivilegeItem(PrivilegeAdmin);

    mPriv[PrivIndex.Barang.ordinal()]=
      getPrivilegeItem(PrivilegeBarang);

    mPriv[PrivIndex.Pemasok.ordinal()]=
      getPrivilegeItem(PrivilegePemasok);

    mPriv[PrivIndex.Pelanggan.ordinal()]=
      getPrivilegeItem(PrivilegePelanggan);

    mPriv[PrivIndex.Box.ordinal()]=
      getPrivilegeItem(PrivilegeBox);

    mPriv[PrivIndex.Pembelian.ordinal()]=
      getPrivilegeItem(PrivilegePembelian);

    mPriv[PrivIndex.Pembayaran.ordinal()]=
      getPrivilegeItem(PrivilegePembayaran);

    mPriv[PrivIndex.Penjualan.ordinal()]=
      getPrivilegeItem(PrivilegePenjualan);

    mPriv[PrivIndex.Penerimaan.ordinal()]=
      getPrivilegeItem(PrivilegePenerimaan);

    privileges="";
    for (int i=0; i < mPriv.length; i++ ){
      privileges = privileges + mPriv[i].toString();
    }

  }

  public enum PrivIndex {
      Admin, Barang, Pemasok,
      Pelanggan, Box, Pembelian,
      Pembayaran, Penjualan, Penerimaan
    }

  private Connection jConnection;
  public boolean isInsert=true;

  public String kd_user;
  public String user_name;
  public String user_pass;
  public String privileges;

  public String ExceptionMessage="";

  private boolean PrivilegeAdmin=false;
  private boolean PrivilegeBarang=false;
  private boolean PrivilegePemasok=false;
  private boolean PrivilegePelanggan=false;
  private boolean PrivilegeBox=false;
  private boolean PrivilegePembelian=false;
  private boolean PrivilegePembayaran=false;
  private boolean PrivilegePenjualan=false;
  private boolean PrivilegePenerimaan=false;

  public void setPrivilegeAdmin(boolean vPriv){
     PrivilegeAdmin=vPriv;
  }
  public void setPrivilegeBarang(boolean vPriv){
     PrivilegeBarang=vPriv;
  }
  public void setPrivilegePemasok(boolean vPriv){
     PrivilegePemasok=vPriv;
  }
  public void setPrivilegePelanggan(boolean vPriv){
     PrivilegePelanggan=vPriv;
  }
  public void setPrivilegeBox(boolean vPriv){
     PrivilegeBox=vPriv;
  }
  public void setPrivilegePembelian(boolean vPriv){
     PrivilegePembelian=vPriv;
  }
  public void setPrivilegePembayaran(boolean vPriv){
     PrivilegePembayaran=vPriv;
  }
  public void setPrivilegePenjualan(boolean vPriv){
     PrivilegePenjualan=vPriv;
  }
  public void setPrivilegePenerimaan(boolean vPriv){
     PrivilegePenerimaan=vPriv;
  }

  public boolean getPrivilegeAdmin(){
      return PrivilegeAdmin;
  }

  public boolean getPrivilegeBarang(){
      return PrivilegeBarang;
  }

  public boolean getPrivilegePemasok(){
      return PrivilegePemasok;
  }

  public boolean getPrivilegePelanggan(){
      return PrivilegePelanggan;
  }

  public boolean getPrivilegeBox(){
      return PrivilegeBox;
  }

  public boolean getPrivilegePembelian(){
      return PrivilegePembelian;
  }

  public boolean getPrivilegePembayaran(){
      return PrivilegePembayaran;
  }

  public boolean getPrivilegePenjualan(){
      return PrivilegePenjualan;
  }

  public boolean getPrivilegePenerimaan(){
      return PrivilegePenerimaan;
  }

  public void setConnection(Connection vConn){
      jConnection= vConn;
  }
  public AdminModel() {

  }

  public boolean saveAdmin() {

    boolean result=false;
    String strQ="";

    DBQuery q = new DBQuery();
    q.setConnection(jConnection);

    if (isInsert==true)
      strQ="insert into admin set ";
    else
      strQ="update admin set ";

    strQ=strQ + " user_name=?"+
      ", user_pass=?, privileges=? ";

    if (isInsert==true)
        strQ=strQ +", kd_user=? ";
    else
        strQ=strQ +" where kd_user=? ";

    q.setStrSql(strQ);
    q.preparedStatement();

    try {
     q.params().setString(1, user_name);
     q.params().setString(2, user_pass);
     q.params().setString(3, privileges);
     q.params().setString(4, kd_user);

     q.params().executeUpdate();

     result=true;
    }
    catch(Exception e){
       System.out.println(e);
    }

    return result;
    }

  public boolean getAdmin(String vID) {

    boolean result=false;
    String strQ="";
    String FieldList= " user_name"+
     ", user_pass, privileges ";

    strQ="select "+ FieldList +
     " from admin " +
     " where kd_user='" + vID + "'";

    try {
     DBQuery q = new DBQuery();
     q.setConnection(jConnection);
     q.setStrSql(strQ);
     q.makeResulset();

     if (q.next()==false)
       return false;

     kd_user = vID;
     user_name=q.getString(1);
     user_pass=q.getString(2);
     readPrivileges(q.getString(3));

    result=true;
  }catch(Exception e){
    System.out.println(e);
  }
  return result;
 }

  public boolean isValidLogin(
   String vUserID, String vPass) {

   boolean result=false;
   try {
     DBQuery q1 = new DBQuery();
     q1.setConnection(jConnection);
     String strSql =
      "select kd_user, user_name "+
      ", user_pass, privileges from admin " +
      " where user_name = '" + vUserID +
      "' AND user_pass = ('" + vPass + "')";

     q1.setStrSql(strSql);
     q1.makeResulset();
     if (q1.next() ) {
       result=true;

       kd_user=q1.getString(1);
       user_name=q1.getString(2);
       user_pass=q1.getString(3);
       privileges=q1.getString(4);

       readPrivileges(privileges);
     }

   } catch (Exception ex) {
    System.out.println(ex);
   }
   return result;
  }
  public boolean cekRefrens(
    String vTable, String vID){

    boolean result=false;
    try{
     DBQuery q = new DBQuery();
     q.setConnection(jConnection);
    q.setStrSql("select * from "+ vTable +
     " where kd_user='" + vID + "'");
    q.makeResulset();
    if (q.getRowCount()>0)
      result=true;
    }catch (Exception e){

    }
    return result;
  }

  public boolean deleteAdmin(String vID) {

    boolean result=false;
    try{
     DBQuery q = new DBQuery();
     q.setConnection(jConnection);


    q.setStrSql("delete from admin "+
     " where kd_user='" + vID + "'");
    q.preparedStatement();
    q.executeUpdate();

    result=true;
    }catch (Exception e){

    }
    return result;
  }

  private boolean getPriveValue(
      PrivIndex vIndex, String vPriv){
      boolean result =false;
      String mValue="";
      try{
          mValue=vPriv.substring(
            vIndex.ordinal()  , vIndex.ordinal()+1);

          if (mValue.equals("1")) {
              result=true;
          }

      }catch(Exception e){

      }
      return result;
  }

  private void readPrivileges(String vPriv) {

   try{
    PrivilegeAdmin=
      getPriveValue(PrivIndex.Admin, vPriv);

    PrivilegeBarang=
      getPriveValue(PrivIndex.Barang, vPriv);

    PrivilegePelanggan=
      getPriveValue(PrivIndex.Pelanggan, vPriv);

    PrivilegePemasok=
      getPriveValue(PrivIndex.Pemasok, vPriv);

    PrivilegeBox=
      getPriveValue(PrivIndex.Box, vPriv);

    PrivilegePembelian=
      getPriveValue(PrivIndex.Pembelian, vPriv);

    PrivilegePembayaran=
      getPriveValue(PrivIndex.Pembayaran, vPriv);

    PrivilegePenjualan=
      getPriveValue(PrivIndex.Penjualan, vPriv);

    PrivilegePenerimaan=
      getPriveValue(PrivIndex.Penerimaan, vPriv);

   }catch(Exception x){

   }
  }

}