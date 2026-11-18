package proyecto.pkg1progra2;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vista.Login.FrmLogin;

/**
 *
 *
 * @author USER
 */
public class Proyecto1Progra2 {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proyecto1Progra2.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(() -> {
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        });
    }
}
