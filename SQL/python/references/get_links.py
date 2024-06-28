from bs4 import BeautifulSoup
import requests
import csv
import re
from datetime import datetime

shapes = ['round-cut','princess-cut','cushion-cut','emerald-cut','oval-cut','radiant-cut','asscher-cut','marquise-cut','heart-cut','pear-cut']
colors = ['K','J','I','H','G','F','E','D']
clarities = ['SI2','SI1','VS2','VS1','VVS2','VVS1','IF','FL']
min_carat = 0.05
max_carat = 5
cuts = ['Good','Very+Good','Ideal']
origins = ['True','False']

for shape in shapes:
        for color in colors:
            for cut in cuts:
                for clarity in clarities:
                    for origin in origins:
                        html_text = requests.get(f'https://www.bluenile.com/diamond-search/?IsLabDiamond={origin}&Sort=Latest&Shape={shape}&Color={color}&Clarity={clarity}&Cut={cut}').text
                        soup = BeautifulSoup(html_text, 'lxml')
                        elements = soup.find_all('a', href=re.compile('^/diamond-details/'))
                        for element in elements:
                            page_html = requests.get(f'https://www.bluenile.com/{element['href']}').text
                            page_soup = BeautifulSoup(page_html, 'lxml')
                            table = page_soup.find('div',class_=re.compile('^summery-section-details-table'))
                            find_shape = page_soup.find('div',{'dataqa': lambda x: x and x.startswith('Shape-') })
                            find_shape = page_soup.find('div',{'dataqa': lambda x: x and x.startswith('Shape-') })
                            print(find_shape)
                            input()
                                
                            