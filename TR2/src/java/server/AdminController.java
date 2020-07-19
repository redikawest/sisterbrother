/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author USER
 */

@Path("adminController")
public class AdminController {
    koneksi con = new koneksi();
    java.sql.Statement st;
    java.sql.ResultSet rs;
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(usergs data) throws ClassNotFoundException {
        String result = "0";
        try {
            java.sql.Connection conn = (Connection) con.configDB();
            st = conn.createStatement();
        
            String a = "SELECT * FROM tb_user WHERE level='admin' AND nama = '" + data.getNama()+ "' AND password = '" + data.getPassword()+ "'";
            rs = st.executeQuery(a);
            while (rs.next()) {            
                result = "1";
            }
        } catch (Exception e) {
            result = "Error! \n" + e.toString();
        }
        
        return Response.status(201).entity(result).build();
    }
    
    
    @GET
    @Path("/getdataaset")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<testModel> getData() throws ClassNotFoundException {
        ArrayList<testModel> tmn = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tb_aset";
            java.sql.Connection conn = (Connection) con.configDB();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
          
            while (rs.next()) {
                testModel tm = new testModel();
                tm.setKode_aset(rs.getString(1));
                tm.setNama_aset(rs.getString(2));
                tm.setJenis_aset(rs.getString(3));
                tm.setKategori(rs.getString(4));
                tm.setTanggal_terima(rs.getString(5));
                tm.setMasa_pemakaian(rs.getString(6));
                tm.setNilai_aset(rs.getInt(7));
                
                tmn.add(tm);
            }
            st.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tmn;
    }
    
    @GET
    @Path("/getdatauser")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<usergs> getDataUser() throws ClassNotFoundException {
        ArrayList<usergs> tmn = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tb_user";
            java.sql.Connection conn = (Connection) con.configDB();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
          
            while (rs.next()) {
                usergs tm = new usergs();
                tm.setId_user(rs.getInt(1));
                tm.setNama(rs.getString(2));
                tm.setPassword(rs.getString(3));
                tm.setLevel(rs.getString(4));
                tmn.add(tm);
            }
            st.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tmn;
    }
    
    
     @POST
    @Path("/updateuser")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateuser(usergs model) {
        try {
            String sql = "UPDATE tb_user SET nama='" + model.getNama()+ "', password = '" + model.getPassword()+ "', level = '" + model.getLevel()+ "' "
                    + "where id_user='"+model.getId_user()+"'";
            java.sql.Connection conn = (Connection) con.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String result =   model.getNama()  + model.getPassword();
         System.out.println(model.getId_user());
        return result;
    }
    
    
    @POST
    @Path("/updateaset")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateaset(testModel model) {
        try {
            String sql = "UPDATE tb_aset SET kode_aset='" + model.getKode_aset()+ "', nama_aset = '" + model.getNama_aset()+ "', jenis_aset = '" + model.getJenis_aset()
                    + "', kategori = '" + model.getKategori() + "', tanggal_terima = '" + model.getTanggal_terima() + "', masa_pemakaian = '" + model.getMasa_pemakaian()
                    + "', nilai_aset = '" + model.getNilai_aset() + "' "
                    + "where kode_aset='" + model.getKode_aset()+"'";
            java.sql.Connection conn = (Connection) con.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String result =   model.getNama_aset()  + model.getJenis_aset() + model.getKategori() + model.getTanggal_terima() + model.getMasa_pemakaian() + model.getNilai_aset();
         System.out.println(model.getKode_aset());
        return result;
    }
    
    

    @POST
    @Path("/inputuser")
    @Produces(MediaType.APPLICATION_JSON)
    public String inputuser(usergs model) {
        try {
            String sql = "INSERT INTO tb_user(nama,password,level) VALUES ('" + model.getNama()+ "','" + model.getPassword()+ "','" + model.getLevel()+ "')";
            java.sql.Connection conn = (Connection) con.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String result = model.getNama() + model.getPassword();
        return result;
    }
    @POST
    @Path("/inputaset")
    @Produces(MediaType.APPLICATION_JSON)
    public String inputaset(testModel model) {
        try {
            String sql = "INSERT INTO tb_aset(kode_aset,nama_aset,jenis_aset,kategori,tanggal_terima,masa_pemakaian,nilai_aset) VALUES "
                    + "('" + model.getKode_aset()+ "','" + model.getNama_aset()+ "','" + model.getJenis_aset()+ 
                     "','" + model.getKategori()+ "','" + model.getTanggal_terima()+ "','" + model.getMasa_pemakaian()+
                     "','" + model.getNilai_aset()+"')";
            java.sql.Connection conn = (Connection) con.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String result = model.getKode_aset()+ model.getNama_aset() + model.getJenis_aset()+model.getKategori()+model.getTanggal_terima()+
                model.getMasa_pemakaian()+model.getTanggal_terima()+model.getNilai_aset();
        return result;
    }
    
    
    
    @POST
    @Path("/deleteuser")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteuser(usergs model) {
        try {
            String sql = "DELETE FROM tb_user where id_user='"+model.getId_user()+"'";
            java.sql.Connection conn = (Connection) con.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        String result = ""+ model.getId_user();
        return result;
    }
    @POST
    @Path("/deleteaset")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteaset(testModel model) {
        try {
            String sql = "DELETE FROM tb_aset where kode_aset='"+(model.getKode_aset())+"'";
            java.sql.Connection conn = (Connection) con.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        String result = model.getKode_aset();
        return result;
    }
    
}
