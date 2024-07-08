from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.common.exceptions import WebDriverException
import csv

# Random required stuff
test_link = 'https://www.bluenile.com/diamond-details/21626610'
driver_path = 'D:/ChromeDriver/chromedriver-win64/chromedriver.exe'
chrome_binary_path = 'C:/Program Files/Google/Chrome/Application/chrome.exe'
service = Service(driver_path)
options = Options()
options.binary_location = chrome_binary_path


driver = webdriver.Chrome(service=service, options=options)
driver.get(test_link)
#Required stuff ends

# element = driver.find_element(By.XPATH, '//div[starts-with(@data-qa,"show-more")]')
element = driver.find_element(By.XPATH, '//div[@data-ct-type="li"]')

driver.execute_script("arguments[0].scrollIntoView(true);", element)

element.click()

test = driver.find_element(By.XPATH, '//div[starts-with(@dataqa,"Measurements-")]')
print(test.text)

driver.quit()