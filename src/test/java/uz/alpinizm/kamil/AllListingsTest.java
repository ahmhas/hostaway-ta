package uz.alpinizm.kamil;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import java.util.logging.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class that tests the functionality of the "All Listings" page on a website.
 */
public class AllListingsTest {
  private static final Logger LOGGER = Logger.getLogger(AllListingsTest.class.getName());

  /**
   * Initializes the Selenide logger.
   */
  @BeforeAll
  static void init() {
    Configuration.headless = false;
    Configuration.baseUrl = "https://kamil-demo.alpinizm.uz";
    SelenideLogger.addListener("AllureSelenide",
            new AllureSelenide()
                    .screenshots(true) // add screenshot as attachments
                    .savePageSource(true) // add DOM source code as attachement
    );
  }

  /**
   * Tests if the "All Listings" page has the same number of listings as the "All" label.
   */
  @Test
  @DisplayName("Task#2: Checking 'All listings' page (https://kamil-demo.alpinizm.uz/all-listings) has the same amount of listings as the 'All' label.")
  void testAllListings() {
    open("/all-listings");

    // Get the number of listings from the 'All' label
    int expectedCount = Integer.parseInt($x("//span[@class='sc-eGJWMs lkeyLH']/span")
            .text()
            .replaceAll("\\D+", ""));

    // Scroll down until all content is loaded
    scrollUntilAllContentIsLoaded();

    // Get the number of listings from the page
    int actualCount = $$x("//div[@class='sc-jgPyTC beqNry']/div")
            .size();

    // Compare the expected and actual counts
    assertEquals(expectedCount, actualCount);
  }

  /**
   * Scrolls down the page until all content is loaded.
   */
  public void scrollUntilAllContentIsLoaded() {
    long previousHeight = Long.MAX_VALUE;

    while (true) {
      // Scroll down by 5000 pixels
      executeJavaScript("window.scrollBy(0, 5000);");

      // TODO: Dynamic timeout solution using waitUntil()
      // Wait for the page to finish loading
      Selenide.sleep(1500);

      // Wait for the page to finish loading before scrolling again
      executeAsyncJavaScript("var callback = arguments[arguments.length - 1];"
              + "if (document.readyState === 'complete') { callback(); }"
              + "else { window.addEventListener('load', callback); }");

      // Get the current page height
      long currentHeight = executeJavaScript("return document.body.scrollHeight;");

      // If the page height did not change, we have reached the end of the page
      if (currentHeight == previousHeight) {
        break;
      }
      previousHeight = currentHeight;
    }
  }
}