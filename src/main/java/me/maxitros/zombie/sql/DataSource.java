package me.maxitros.zombie.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSource {
    private static HikariDataSource ds;
    public DataSource(ConfigurationSection section) throws SQLException
    {
        ds = new HikariDataSource();
        ds.setMaximumPoolSize(section.getInt("poolsize"));
        ds.setDriverClassName(section.getString("driver-class"));
        ds.setJdbcUrl(String.format(
                section.getString("jdbc-url"),
                section.getString("host"),
                section.getString("port"),
                section.getString("database")));
        ds.addDataSourceProperty("user", section.getString("user"));
        ds.addDataSourceProperty("password", section.getString("password"));
        try (Connection con = DataSource.getConnection())
        {
            PreparedStatement pst = con.prepareStatement(String.format(
                            "CREATE TABLE IF NOT EXISTS `%s`.`%s` (\n" +
                            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `Name` VARCHAR(10) NOT NULL,\n" +
                            "  `Killer` VARCHAR(16) NOT NULL,\n" +
                            "  PRIMARY KEY (`ID`));\n",
                    section.getString("database"),"zombies"));
            pst.executeUpdate();
        }
        catch (SQLException exeption)
        {
            exeption.printStackTrace();
            throw exeption;
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
