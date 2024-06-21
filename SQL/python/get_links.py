from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.common.exceptions import WebDriverException
import csv

# Random required stuff
test_link = 'https://www.allurez.com/loose-diamonds'
driver_path = 'D:/ChromeDriver/chromedriver-win64/chromedriver.exe'
chrome_binary_path = 'C:/Program Files/Google/Chrome/Application/chrome.exe'
service = Service(driver_path)
options = Options()
options.binary_location = chrome_binary_path
driver = webdriver.Chrome(service=service, options=options)
driver.get(test_link)
wait = WebDriverWait(driver, 30)

# Variables
arr = set()
duplicate_count = 0
file = open('hrefs.csv','w',newline='')
writer = csv.writer(file)

def click_button():
    try:
        button = wait.until(EC.presence_of_element_located((By.XPATH,'//button[@onClick="seeMore();"]')))
        button.click()
        return True
    except Exception:
        return False

elements = wait.until(EC.presence_of_all_elements_located((By.XPATH, '//div[starts-with(@class,"product-sbox")]')))


while click_button() and len(arr) < 300:
    elements = wait.until(EC.presence_of_all_elements_located((By.XPATH, '//div[starts-with(@class,"product-sbox")]')))

    for element in elements:
        # print(element.text)
        link = element.find_element(By.XPATH, './/a[starts-with(@href,"https://www.allurez.com/")]')
        
        if link.get_attribute('href') in arr:
            duplicate_count += 1
        else:
            arr.add(link.get_attribute('href'))
        
        # print(link.get_attribute('href'))
        print(f'Duplicate: {duplicate_count} | Length: {len(arr)}')

for item in arr:
    writer.writerow([item])


input()
file.close()
