from bs4 import BeautifulSoup
import requests
import csv
import re
from datetime import datetime

def my_function(name):
    name = name.replace('Lab-Created','').replace('Diamond','').replace('Very Good','Very_Good').strip().split(' ')
    
    shape = None
    carat = None
    color = None
    clarity = None
    cut = None
    
    i = 0
    for i in range(len(name)):
        if shape is not None and carat is not None and color is not None and cut is not None:
            break
        if name[i].replace('.', '', 1).isdigit():
            carat = float(name[i].strip())
        if '-' in name[i]:
            color, clarity = name[i].split('-')
        if name[i].lower() in ['round', 'princess', 'cushion', 'emerald', 'oval', 'radiant', 'asscher', 'marquise', 'heart', 'pear']:
            shape = name[i].strip().lower()
        if name[i] in ['Very_Good','Excellent','Good']:
            cut = name[i].strip()

    if cut is None:
        cut = 'Excellent'

    return shape, carat, color, clarity, cut

def is_container(css_class):
    return css_class is not None and css_class.startswith('bn_comp_itemData')

def is_name(css_class):
    return css_class is not None and css_class.startswith('bn_comp_itemTitle')

def is_price(css_class):
    return css_class is not None and css_class.startswith('price--')

def is_image_container(css_class):
    return css_class is not None and css_class.startswith('imgBgContainer')

shapes = ['round-cut','princess-cut','cushion-cut','emerald-cut','oval-cut','radiant-cut','asscher-cut','marquise-cut','heart-cut','pear-cut']
# colors = ['K','J','I','H','G','F','E','D']
colors = ['E','D']
# clarity = ['SI2','SI1','VS2','VS1','VVS2','VVS1','IF','FL']
clarity = ['IF','FL']
min_carat = 0.05
max_carat = 5
cuts = ['Good','Very+Good','Ideal']
origins = ['True','False']

def split_diamond_info(diamond_info):
    carat, rest = diamond_info.split()

    return 0


# Get current date
current_date = datetime.now()

# Format date to fit SQL Server
formatted_date = current_date.strftime('%Y-%m-%d %H:%M:%S')

with open('diamond_data.csv','w',newline='') as file1, open('diamond_price_data.csv','w',newline='') as file2:
    writer = csv.writer(file1)
    writer2 = csv.writer(file2)
    writer.writerow(['diamond_code','diamond_name','shape','origin','proportions','fluorescence','symmetry','polish','cut','color','clarity','carat_weight','note','image','active'])
    writer2.writerow(['origin','shape','carat_weight','color','clarity','cut','price','effective_date'])
    for shape in shapes:
        for color in colors:
            for cut in cuts:
                for clarity in clarity:
                    for origin in origins:
                        html_text = requests.get(f'https://www.bluenile.com/diamond-search/?IsLabDiamond={origin}&Sort=Latest&Shape={shape}&Color={color}&Clarity={clarity}&Cut={cut}').text
                        soup = BeautifulSoup(html_text, 'lxml')
                        elements = soup.find_all('a', href=re.compile('^/diamond-details/'))
                        print(f'Setting: {shape},{color},{clarity},{cut},lab:{origin} - length: {len(elements)}')
                        for element in elements:
                            name = element.find('h3',class_=is_name).text
                            d_shape, d_carat_weight, d_color, d_clarity, d_cut = my_function(name)
                            price = element.find('div',class_=is_price).text.replace('$', '').replace(',','')
                            image_container = element.find('div',class_=is_image_container)
                            # image = element.find('img',src=re.compile('^https://ion.bluenile.com/sgmdirect/photoID/'))['src']
                            image = image_container.find('img')['src']

                            d_origin = 'LAB_GROWN'
                            if not origin:
                                d_origin = 'NATURAL'

                            writer.writerow(['CODE',name,d_shape,d_origin,'proportions','Very_Strong','Excellent','Excellent',d_cut,d_color,d_clarity,d_carat_weight,'BlueNile',image,1])
                            writer2.writerow([d_origin, d_shape, d_carat_weight, d_color, d_clarity, d_cut, price, formatted_date])