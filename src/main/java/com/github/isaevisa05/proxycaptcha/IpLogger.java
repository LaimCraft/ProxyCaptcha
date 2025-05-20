package com.github.isaevisa05.proxycaptcha;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

import java.sql.*;
import java.util.Date;

public class IpLogger {

    @Subscribe
    public void playerJoinEvent2(LoginEvent event) {
        Player player = event.getPlayer();
        String address = player.getRemoteAddress().getHostName();
        addToLogs(player.getUsername(), address);
    }

    private void addToLogs(String login, String address) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://web.hosting-minecraft.pro:1501/?", "laimcraft", "jR%BNG-#9@P-Y1cTGdN4tU2hJ9d")){
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `laimcraft`.`ip_logger` (`login`, `ip`, `date`) VALUES (?, ?, ?);");
            ps.setString(1, login);
            ps.setString(2, address);
            ps.setLong(3, new Date().getTime());
            ps.executeUpdate();
        } catch (SQLException e) {
            ProxyCaptcha.logger.warn(e.getMessage());
        }
    }

}
