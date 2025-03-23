package com.github.isaevisa05.proxycaptcha;

import org.slf4j.Logger;

import java.io.IOException;

public class TunnelRunner {

    public TunnelRunner(Logger logger) {
        logger.info("Tunnel starting");
        new Thread(() -> {
            run(logger);
        }).start();
    }

    public void run(Logger logger) {
        try {
            // Запускаем другой JAR
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "plugins/tunnel.jar");
            processBuilder.inheritIO(); // Передаём консольный вывод

            Process process = processBuilder.start();

            // Добавляем shutdown hook, чтобы гарантированно убивать процесс при выходе
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Главный процесс завершён. Останавливаю tunnel.jar...");
                process.destroy(); // Попробуем мягкое завершение
                try {
                    Thread.sleep(1000); // Даем время процессу завершиться
                } catch (InterruptedException ignored) {}
                process.destroyForcibly(); // Принудительное завершение, если не остановился
            }));

            // Ждём завершения дочернего процесса (по желанию)
            int exitCode = process.waitFor();
            logger.info("tunnel.jar завершён с кодом: " + exitCode);
        } catch (IOException | InterruptedException e) {
            logger.info(e.toString());
        }
    }
}
