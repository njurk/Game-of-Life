import javax.swing.*;
import java.awt.*;

public class Theme {
    private static boolean darkTheme = false;

    static void toggleTheme() {
        darkTheme = !darkTheme;
        BoardPanel.getPanel().setBackground(darkTheme ? Color.BLACK : Color.WHITE);
        BoardPanel.getPanel().repaint();
        ControlPanel.getPanel().setBackground(darkTheme ? Color.BLACK : Color.WHITE);
        ControlPanel.getPanel().repaint();

        if (darkTheme) {
            setDark(ControlPanel.panel);
            ControlPanel.getOverpopulationSpinner().setForeground(Color.WHITE);
            ControlPanel.getSurvivalSpinner().setForeground(Color.WHITE);
            ((JSpinner.DefaultEditor) ControlPanel.getOverpopulationSpinner().getEditor()).getTextField().setForeground(Color.WHITE);
            ((JSpinner.DefaultEditor) ControlPanel.getSurvivalSpinner().getEditor()).getTextField().setForeground(Color.WHITE);
        } else {
            setLight(ControlPanel.panel);
            ControlPanel.getOverpopulationSpinner().setForeground(Color.BLACK);
            ControlPanel.getSurvivalSpinner().setForeground(Color.BLACK);
            ((JSpinner.DefaultEditor) ControlPanel.getOverpopulationSpinner().getEditor()).getTextField().setForeground(Color.BLACK);
            ((JSpinner.DefaultEditor) ControlPanel.getSurvivalSpinner().getEditor()).getTextField().setForeground(Color.BLACK);
        }
    }

    private static void setDark(Component comp) {
        comp.setBackground(Color.BLACK);
        if (comp instanceof JButton || comp instanceof JComboBox || comp instanceof JSpinner) {
            comp.setForeground(Color.WHITE);
        } else if (comp instanceof JLabel) {
            comp.setForeground(Color.WHITE);
        }
        if (comp instanceof Container) {
            for (Component c : ((Container) comp).getComponents()) {
                setDark(c);
            }
        }
    }

    private static void setLight(Component comp) {
        comp.setBackground(Color.WHITE);
        if (comp instanceof JButton || comp instanceof JComboBox || comp instanceof JSpinner) {
            comp.setForeground(Color.BLACK);
        } else if (comp instanceof JLabel) {
            comp.setForeground(Color.BLACK);
        }
        if (comp instanceof Container) {
            for (Component c : ((Container) comp).getComponents()) {
                setLight(c);
            }
        }
    }

    public static boolean isDarkTheme() {
        return darkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
    }
}
