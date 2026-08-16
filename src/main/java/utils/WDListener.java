package utils;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WDListener implements WebDriverListener {
    Logger logger = LoggerFactory.getLogger(WDListener.class);
    private long getStartedAt;
    private long clickStartedAt;

    @Override
    public void beforeGet(WebDriver driver, String url) {
        getStartedAt = System.currentTimeMillis();
        logger.info("GET {} - navigating", url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        logger.info("GET {} - done in {} ms, landed on {}",
                url,
                System.currentTimeMillis() - getStartedAt,
                driver.getCurrentUrl());
    }

    @Override
    public void beforeClick(WebElement element) {
        clickStartedAt = System.currentTimeMillis();
        logger.info("CLICK {} - clicking", describe(element));
    }

    @Override
    public void afterClick(WebElement element) {
        logger.info("CLICK {} - done in {} ms",
                describe(element),
                System.currentTimeMillis() - clickStartedAt);
    }

    // builds a "<tag id="..."> "text"" summary for logs; falls back if the element went stale after a navigating click
    private String describe(WebElement element) {
        try {
            StringBuilder description = new StringBuilder("<").append(element.getTagName());
            String id = element.getAttribute("id");
            if (id != null && !id.isEmpty()) {
                description.append(" id=\"").append(id).append("\"");
            }
            description.append(">");
            String text = element.getText();
            if (text != null && !text.isEmpty()) {
                description.append(" \"").append(text.length() > 40 ? text.substring(0, 40) + "..." : text).append("\"");
            }
            return description.toString();
        } catch (StaleElementReferenceException e) {
            return "<stale element>";
        }
    }
}
