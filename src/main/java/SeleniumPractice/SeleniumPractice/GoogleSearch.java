package SeleniumPractice.SeleniumPractice;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearch {

	public static WebDriver driver;

	public static void main(String[] args) {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		By address_serachBox = By.xpath("//textarea[@title='Search']");
		// Types the given value in google search
		wait.until(ExpectedConditions.visibilityOfElementLocated(address_serachBox)).sendKeys("automation");
		By address_suggestions = By.xpath("//li//div[@class='wM6W7d' and @role]//span");

		// Logic to print all the suggestions and Click on the required suggestion
		try {
			List<WebElement> suggestions = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(address_suggestions));

			if (suggestions.size() != 0) {
				for (WebElement suggestion : suggestions) {
					// Prints all the suggestions
					System.out.println(suggestion.getText());
				}

				Scanner sc = new Scanner(System.in);
				while (true) {
					System.out.println("Please enter the suggestion you are looking for");
					String expSuggestion = sc.nextLine();

					if (expSuggestion != null && expSuggestion.length() != 0) {
						By clickSuggestion = By.xpath("//div[@aria-label='" + expSuggestion + "']");
						// Click on the suggestion we are looking for
						wait.until(ExpectedConditions.visibilityOfElementLocated(clickSuggestion)).click();
						break;
					} else {
						System.out.println("Please enter a valid suggestion.");
						continue;
					}
				}
			} else {
				System.out.println("Suggestion list is empty");
				// My custom exception that throws the exception and stops program execution
				// when given list is empty
				throw new AutomationException("Given list is empty");
			}
		} catch (TimeoutException ex) {
			System.out.println("Unable to find the suggestions/suggestion.");
		}
	}
}
