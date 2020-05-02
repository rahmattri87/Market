/*
 * MarketApp.java
 */

package market;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import com.conn.SettingUI;
import com.util.FormUtil;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import model.AdminModel;
/**
 * The main class of the application.
 */
public class MarketApp extends SingleFrameApplication {

public SingleFrameApplication getApp(){
    return this;
}
    private MarketView jMainMenu;
    private Connection jConnection;
    private AdminModel jAdmin;
    private SettingUI jConfig;


    public AdminModel getjAdmin() {
        return jAdmin;
    }

    public void setjAdmin(AdminModel jAdmin) {
        this.jAdmin = jAdmin;
    }

    public SettingUI getjConfig() {
        return jConfig;
    }

    public void setjConfig(SettingUI jConfig) {
        this.jConfig = jConfig;
    }

    public Connection getjConnection() {
        return jConnection;
    }

    public void setjConnection(Connection jConnection) {
        this.jConnection = jConnection;
    }

    public MarketView getjMainMenu() {
        return jMainMenu;
    }

    public void showMainMenu(){
        show(jMainMenu);
    }

    public void setjMainMenu(MarketView jMainMenu) {
        this.jMainMenu = jMainMenu;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        //show(new MarketView(this));

        jMainMenu = new MarketView(this);
        show(jMainMenu);
        SettingUI config = new SettingUI(null,true);
        config.makeConnect();
        if(config.isConnected()==true){
            jConnection = config.getConnection();

        }else{
            config.setVisible(true);
            jConnection = config.getConnection();
            if (config.isConnected()==false)
                jMainMenu.getApplication().exit();
        }
            //FormUtil.setMainFrame(jMainMenu.getFrame());
        //mengaktifkan form Login
        //new LoginForm(this.getMainFrame(),true).setVisible(true);
        //----------------------------------------
        //aktifkan jika sudah membuat form login
        //new LoginForm(this.getMainFrame(), true).
         // setVisible(true);
       //-----------------------------------------
    }


    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
        @Override protected void configureWindow(java.awt.Window root) {
        root.addWindowListener(new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            // write your code here
            MarketApp.getApplication().exit();
        }
        });
        }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of MarketApp
     */
    public static MarketApp getApplication() {
        return Application.getInstance(MarketApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(MarketApp.class, args);
    }
}
