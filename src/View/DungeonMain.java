package View;
import java.awt.*;

public class DungeonMain {

    public static void main(final String... theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DungeonGUI();

            }
        });
    }


}