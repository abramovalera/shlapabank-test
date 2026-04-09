package ru.shlapabank.ui.helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;
import static org.openqa.selenium.logging.LogType.BROWSER;

public final class Attach {

    private Attach() {
    }

    /**
     * Скриншот, HTML и логи консоли браузера в Allure (если сессия WebDriver уже открыта).
     * Опционально: {@code -Dselenoid.video.base=https://host/video} для вложения ссылки на видео (RemoteWebDriver).
     */
    public static void attachUiArtifacts() {
        if (!hasWebDriverStarted()) {
            return;
        }
        try {
            screenshotAs("Last screenshot");
        } catch (Exception ignored) {
            // сессия могла оборваться до скриншота
        }
        try {
            pageSource();
        } catch (Exception ignored) {
        }
        try {
            browserConsoleLogs();
        } catch (Exception ignored) {
        }
        attachRemoteVideoIfApplicable();
    }

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName) {
        WebDriver driver = getWebDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    private static void browserConsoleLogs() {
        attachAsText("Browser console logs", String.join("\n", Selenide.getWebDriverLogs(BROWSER)));
    }

    private static void attachRemoteVideoIfApplicable() {
        String base = System.getProperty("selenoid.video.base");
        if (base == null || base.isBlank()) {
            return;
        }
        WebDriver driver = getWebDriver();
        if (!(driver instanceof RemoteWebDriver)) {
            return;
        }
        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        String url = base.replaceAll("/$", "") + "/" + sessionId + ".mp4";
        String html = "<html><body><video width='100%' height='100%' controls autoplay>"
                + "<source src='" + url + "' type='video/mp4'></video></body></html>";
        attachVideoHtml(html);
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    private static String attachVideoHtml(String html) {
        return html;
    }
}
