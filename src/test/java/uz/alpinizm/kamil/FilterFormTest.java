package uz.alpinizm.kamil;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Random;
import java.util.logging.Logger;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class FilterFormTest {
  private static final Logger LOGGER = Logger.getLogger(FilterFormTest.class.getName());

  /**
   * Initializes the Selenide configuration and sets the base URL.
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
   * Opens a web page and checks the filtering form by performing a series of actions.
   */
  @Test
  @DisplayName("Task#1: Checking Filters form")
  void checkFilterForm() {
    open("/");
    clickSearchButton();
    selectCheckInOutDate();
    openFiltersForm();
    setPriceRange();
    addRoomsBedsBathrooms("beds");
    addRoomsBedsBathrooms("bedrooms");
    addRoomsBedsBathrooms("bathrooms");
    checkMinAndMaxValues();
    checkAmenitiesCheckboxes();
    checkClearAllButton();
    checkButtonsWithoutClickingApply();
  }


  @Step("Click search button")
  private void clickSearchButton() {
    // TODO: check why CSS selector doesn't work!
    // $("button > span:contains('Search')").click();
    $x("//button/span[contains(text(), 'Search')]").click();
  }

  @Step("Select check-in/out date")
  private void selectCheckInOutDate() {
    int checkInDate = LocalDate.now().plusDays(1).getDayOfMonth();
    int checkOutDate = checkInDate + new Random().nextInt(10) + 1;
    $x("//div[contains(text(), 'Check-in')]").click();
    $x("(//div[@class='sc-WZYut bJAXZb CalendarDay' and contains(text(), '" + checkInDate +"')])[1]").click();
    $x("(//div[@class='sc-WZYut bJAXZb CalendarDay' and contains(text(), '" + checkOutDate +"')])[1]").click();
  }

  @Step("Open the filters form")
  private void openFiltersForm() {
    $x("//button/span[contains(text(), 'Filter')]").click();
  }

  @Step("Check the entry fields")
  private void setPriceRange() {
    $x("//input[@class='sc-gstuGz kqWUWP' and @placeholder= 'From']").setValue("100");
    $x("//input[@class='sc-gstuGz kqWUWP' and @placeholder= 'To']").setValue("200");
  }

  @Step("Add beds/bedrooms/bathroom")
  private void addRoomsBedsBathrooms(String roomTypeSelection) {
    final String BASE_ROOMS_AND_BEDS_SELECTOR = "(*//button[@class='sc-fWWYYk sc-gzcbmu bZTTYU fKwyEY'])";
    int selectedRoomIndex = getSelectedRoomIndex (roomTypeSelection);
    int num = new Random().nextInt(11); // Generate a random number between 0 and 10

    // Add random rooms number between 0 and 10
    while (num != 0) {
      $x(BASE_ROOMS_AND_BEDS_SELECTOR + "[" + selectedRoomIndex + "]").click();
      num --;
    }
  }

  @Step("Check the minimum and maximum values")
  private void checkMinAndMaxValues() {
    $x("//input[@class='sc-gstuGz kqWUWP' and @placeholder= 'From']").shouldHave(value("100"));
    $x("//input[@class='sc-gstuGz kqWUWP' and @placeholder= 'To']").shouldHave(value("200"));
  }

  @Step("Check the amenities checkboxes")
  private void checkAmenitiesCheckboxes() {
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Beach front')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Swimming pool')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Free WiFi')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Kitchen')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Air conditioning')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Washing Machine')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Pets allowed')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Hot tub')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Street parking')]").click();
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Suitable for children')]").click();
  }

  @Step("Check the \"Clear all\" button")
  private void checkClearAllButton() {
    $x("//b[contains(text(),'Clear all')]").click();

    // Check Price is reset
    $x("//input[@class='sc-gstuGz kqWUWP' and @placeholder= 'From']").shouldHave(value(""));
    $x("//input[@class='sc-gstuGz kqWUWP' and @placeholder= 'To']").shouldHave(value(""));

    // Check Rooms and beds are reset
    $x("(//span[@class='sc-gVFcvn gnLtVL'])[1]").shouldHave(value(""));
    $x("(//span[@class='sc-gVFcvn gnLtVL'])[2]").shouldHave(value(""));
    $x("(//span[@class='sc-gVFcvn gnLtVL'])[3]").shouldHave(value(""));

    //TODO: Separate the locators in a json file
    // Check Amenities are reset
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Beach front')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Swimming pool')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Free WiFi')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Kitchen')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Air conditioning')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Washing Machine')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Pets allowed')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Hot tub')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Street parking')]").shouldNotBe(selected);
    $x("//span[@class='sc-htmcrh emRwHY' and contains(text(), 'Suitable for children')]").shouldNotBe(selected);
  }

  @Step("Check the buttons without clicking \"Apply\"")
  private void checkButtonsWithoutClickingApply() {
    $x("//button[@class='sc-giAqHp bsokiQ']/span[contains(text(), 'Apply')]").should(exist);
  }

  /**
   Returns the selected room index based on the given target selection.
   @param targetSelection The target selection for which the room index needs to be determined.
   @return The room index corresponding to the target selection.
   */
  private int getSelectedRoomIndex (String targetSelection){
    int targetSelectionIndex = 0;

    switch(targetSelection.toLowerCase()) {
      case "beds":
        targetSelectionIndex = 1;
        break;
      case "bedrooms":
        targetSelectionIndex = 2;
      case "bathrooms":
        targetSelectionIndex = 3;
        break;
      default:
        LOGGER.info("Please provide the right target selection from (beds, bedrooms, bathrooms");
    }
    return targetSelectionIndex;
  }
}