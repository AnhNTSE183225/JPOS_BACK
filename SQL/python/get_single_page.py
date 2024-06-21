from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.common.exceptions import WebDriverException
import csv

# Random required stuff
def get_single_page(url):
    # test_link = 'https://www.allurez.com/0.29-carat-round-poor-cut-k-color-i1-clarity-diamond/gid/3670577'
    driver_path = 'D:/ChromeDriver/chromedriver-win64/chromedriver.exe'
    chrome_binary_path = 'C:/Program Files/Google/Chrome/Application/chrome.exe'
    service = Service(driver_path)
    options = Options()
    options.binary_location = chrome_binary_path
    driver = webdriver.Chrome(service=service, options=options)
    driver.get(url)
    wait = WebDriverWait(driver, 30)

    element = wait.until(EC.presence_of_element_located((By.XPATH,'//div[@class="grid md:grid-cols-2 grid-cols-1 md:gap-16 gap-8"]')))

    rotating_image = driver.find_element(By.XPATH, '//img[@id="azimg"]')
    print(rotating_image.get_attribute('src'))

    name = driver.find_element(By.XPATH,'//h1[@class="md:text-xl uppercase leading-tight"]').text
    values = element.find_elements(By.XPATH, './/td[@class="p-2"]')
    diamondCode = values[0].text
    price = values[1].text.replace('$','')
    shape = values[2].text
    caratWeight = values[3].text
    color = values[4].text
    clarity = values[5].text
    cut = values[6].text
    fluorescence = values[7].text
    polish = values[11].text
    symmetry = values[12].text
    proportions = values[13].text

    # name, diamondCode, price, shape, caratWeight, color, clarity, cut, fluorescence, polish, symmetry, proportions
    # print(f"Name: {name}")
    # print(f"Diamond Code: {diamondCode}")
    # print(f"Price: {price}")
    # print(f"Shape: {shape}")
    # print(f"Carat Weight: {caratWeight}")
    # print(f"Color: {color}")
    # print(f"Clarity: {clarity}")
    # print(f"Cut: {cut}")
    # print(f"Fluorescence: {fluorescence}")
    # print(f"Polish: {polish}")
    # print(f"Symmetry: {symmetry}")
    # print(f"Proportions: {proportions}")
    return name, diamondCode, price, shape, caratWeight, color, clarity, cut, fluorescence, polish, symmetry, proportions