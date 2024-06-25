from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.common.exceptions import WebDriverException
import csv
import logging

def find_properties(values):
    
    diamondCode = None
    price = None
    shape = None
    caratWeight = None
    color = None
    clarity = None
    cut = None
    fluorescence = None
    polish = None
    symmetry = None
    proportions = None
    
    
    
    for value in values:
        if "Style number" in value.text:
            diamondCode = value.text.replace("Style number","").strip()
        if 'Price' in value.text:
            price = value.text.replace("Price","").replace("$","").strip()
        if 'Shape' in value.text:
            shape = value.text.replace("Shape","").strip().lower()
        if 'Carat Weight' in value.text:
            caratWeight = value.text.replace("Carat Weight","").strip()
        if "Color" in value.text:
            color = value.text.replace("Color","").strip()
        if "Clarity" in value.text:
            clarity = value.text.replace("Clarity","").strip()
        if "Cut Grade" in value.text:
            cut = value.text.replace("Cut Grade","").strip().replace(" ","_")
        if "Fluorescence" in value.text:
            fluorescence = value.text.replace("Fluorescence","").strip().replace(" ","_")
            if fluorescence not in ['None', 'Faint', 'Medium', 'Strong', 'Very_Strong']:
                fluorescence = 'None'
        if "Polish" in value.text:
            polish = value.text.replace("Polish","").strip().replace(" ","_")
        if "Symmetry" in value.text:
            symmetry = value.text.replace("Symmetry","").strip().replace(" ","_")
        if "Measurements" in value.text:
            proportions = value.text.replace("Measurements","").strip()
        
        
    return diamondCode, price, shape, caratWeight, color, clarity, cut, fluorescence, polish, symmetry, proportions

# Random required stuff
def get_single_page(url):
    # test_link = 'https://www.allurez.com/0.29-carat-round-poor-cut-k-color-i1-clarity-diamond/gid/3670577'
    driver_path = 'D:/ChromeDriver/chromedriver-win64/chromedriver.exe'
    chrome_binary_path = 'C:/Program Files/Google/Chrome/Application/chrome.exe'
    service = Service(driver_path)
    options = Options()
    options.binary_location = chrome_binary_path
    options.add_argument('--headless')
    options.add_argument('--disable-gpu')
    options.add_argument('--no-sandbox')
    # Suppress console logs
    options.add_argument('--log-level=3')
    options.add_argument('--silent')
    options.add_experimental_option('excludeSwitches', ['enable-logging'])
    # Suppress WebDriver logs
    service.log_path = "nul"  # On Windows, use "nul"; on Unix-like systems, use "/dev/null"
    service.log_level = logging.ERROR
    
    driver = webdriver.Chrome(service=service, options=options)
    driver.get(url)
    wait = WebDriverWait(driver, 30)

    elements = wait.until(EC.presence_of_all_elements_located((By.XPATH,"//tr[starts-with(@class,'bg-')]")))

    diamondCode, price, shape, caratWeight, color, clarity, cut, fluorescence, polish, symmetry, proportions = find_properties(elements)
    
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
    # rotating_image = driver.find_element(By.XPATH, '//img[@id="azimg"]')
    name = driver.find_element(By.XPATH,'//h1[@class="md:text-xl uppercase leading-tight"]').text
    origin = None
    if 'NATURAL' in name:
        origin = 'NATURAL'
    else:
        origin = 'LAB_GROWN'
    image = driver.find_element(By.XPATH, "//img[starts-with(@id,'product_box_img_')]").get_attribute('src')
    
    spinning_images = list()
    
    try:
        spinning_images = wait.until(EC.presence_of_all_elements_located((By.XPATH,"//img[@style='display: none;']")))
    except Exception:
        spinning_images = list()
        
    spinning_urls = list(map(lambda image: image.get_attribute('src') ,spinning_images))    
        
    all_images = "|".join([image] + spinning_urls)
    
    driver.close()
    return [price, diamondCode, name, shape, origin, proportions, fluorescence, symmetry, polish, cut, color, clarity, caratWeight, "Allurez", all_images, "1"]
