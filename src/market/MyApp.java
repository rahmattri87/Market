/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market;

/**
 *
 * @author rahmat & ulfah
 */
public class MyApp {

    public static MarketApp getApp() {
        return app;
    }
    static{
        app=MarketApp.getApplication();
    }
    private static MarketApp app;

}
