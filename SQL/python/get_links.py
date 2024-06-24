from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.common.exceptions import WebDriverException
import logging

# Random required stuff
def get_links():
    test_link = 'https://www.allurez.com/loose-diamonds'
    driver_path = 'D:/ChromeDriver/chromedriver-win64/chromedriver.exe'
    chrome_binary_path = 'C:/Program Files/Google/Chrome/Application/chrome.exe'
    service = Service(driver_path)
    options = Options()
    options.binary_location = chrome_binary_path
    # Headless
    # options.add_argument('--headless')
    # options.add_argument('--disable-gpu')
    # options.add_argument('--no-sandbox')
    # Suppress console logs
    options.add_argument('--log-level=3')
    options.add_argument('--silent')
    options.add_experimental_option('excludeSwitches', ['enable-logging'])
    # Suppress WebDriver logs
    service.log_path = "nul"  # On Windows, use "nul"; on Unix-like systems, use "/dev/null"
    service.log_level = logging.ERROR
    
    driver = webdriver.Chrome(service=service, options=options)
    driver.get(test_link)
    wait = WebDriverWait(driver, 30)

    def click_button():
        try:
            button = wait.until(EC.presence_of_element_located((By.XPATH,'//button[@onClick="seeMore();"]')))
            button.click()
            return True
        except Exception:
            return False

    # Variables
    arr = set()
    duplicate_count = 0

    elements = wait.until(EC.presence_of_all_elements_located((By.XPATH, '//div[starts-with(@class,"product-sbox")]')))


    while click_button() and len(arr) < 10:
        elements = wait.until(EC.presence_of_all_elements_located((By.XPATH, '//div[starts-with(@class,"product-sbox")]')))

        for element in elements:
            # print(element.text)
            link = element.find_element(By.XPATH, './/a[starts-with(@href,"https://www.allurez.com/")]')
            
            if link.get_attribute('href') in arr:
                duplicate_count += 1
            else:
                print(link.get_attribute('href'))
                arr.add(link.get_attribute('href'))
            
            # print(link.get_attribute('href'))
            # print(f'Duplicate: {duplicate_count} | Length: {len(arr)}')

    return arr
