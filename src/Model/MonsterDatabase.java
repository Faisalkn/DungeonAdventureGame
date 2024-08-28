/**
 * Team members: Pham Nguyen, Ali Mohamed and Faisal Nur
 * Class: TCSS360
 * Date: Jun 7, 2024
 * @version 1.0
 */
package Model;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class MonsterDatabase {
    public static MonsterDatabase myUniqueInstance;

    private MonsterDatabase() {
    }

    public static Monster getMonster(String theMonsterName) throws SQLException {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:monster.db");

        Monster monster = null;
        String selectDataQuery = "SELECT * FROM monsters WHERE name=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectDataQuery)) {

            stmt.setString(1, theMonsterName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int hitPoints = rs.getInt("hitPoints");
                int attackSpeed = rs.getInt("attackSpeed");
                double chanceToHit = rs.getDouble("chanceToHit");
                int minDamage = rs.getInt("minDamage");
                int maxDamage = rs.getInt("maxDamage");
                double chanceToHeal = rs.getDouble("chanceToHeal");
                int minHealPoints = rs.getInt("minHealPoints");
                int maxHealPoints = rs.getInt("maxHealPoints");

                int x = (int) (Math.random() * Dungeon.getInstance().getMySize());
                int y = (int) (Math.random() * Dungeon.getInstance().getMySize());
                monster = new Monster(name, hitPoints, attackSpeed, chanceToHit, maxDamage, minDamage, chanceToHeal,
                        minHealPoints, maxHealPoints, x, y);
            }
        }
        return monster;
    }

    public static MonsterDatabase getMyUniqueInstance() {
        if (myUniqueInstance == null) {
            myUniqueInstance = new MonsterDatabase();
        }
        return myUniqueInstance;
    }

    public static MonsterDatabase getMy_TEST_Instance() {
        return new MonsterDatabase();
    }
}