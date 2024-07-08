import csv
from datetime import datetime
from generate_sql_insert import generate_sql_inserts

colors = ['K','J','I','H','G','F','E','D']
shapes = ['round', 'princess', 'cushion', 'emerald', 'oval', 'radiant', 'asscher', 'marquise', 'heart', 'pear']
clarities = ['I3', 'I2', 'I1', 'SI3', 'SI2', 'SI1', 'VS2', 'VS1', 'VVS2', 'VVS1', 'IF', 'FL']
carat_ranges = []
cuts = ['Fair','Good','Very_Good','Excellent']
MIN_CARAT = 0.05
MAX_CARAT = 10.05
CARAT_STEP = 0.1
i = MIN_CARAT
while i + CARAT_STEP <= MAX_CARAT:
    carat_ranges.append([round(i,2),round(i+CARAT_STEP,2)])
    i += CARAT_STEP

base_diamond = 200
base_carat_price = 250 # per range
color_multiplier = 1.1
clarity_multiplier = 1.1
reverse_origin_multiplier = 0.9
cut_multiplier = [
    {'cut': 'Fair', 'value': 1},
    {'cut': 'Good', 'value': 1.1},
    {'cut': 'Very_Good', 'value': 1.15},
    {'cut': 'Excellent', 'value': 1.2}
]
shape_multiplier = [
    {'shape': 'round','value': 1.5},
    {'shape': 'princess','value':1},
    {'shape': 'cushion','value':1},
    {'shape': 'emerald','value':1},
    {'shape': 'oval','value':1},
    {'shape': 'radiant','value':1},
    {'shape': 'asscher','value':1},
    {'shape': 'marquise','value':1},
    {'shape': 'heart','value':1},
    {'shape': 'pear','value':1},
]
# color 7 clarity 11 -> 52,000
# color 2 clarity 6 -> 14,000
# calculation = (base_diamond + base_carat_price*21) * pow(color_multiplier,2) * pow(clarity_multiplier,6) * cut_multiplier[2]['value'] * shape_multiplier[0]['value']

today = datetime.now().strftime("%Y-%m-%d")

with open('generated_price.csv','w',newline='') as file:
    writer = csv.writer(file)
    writer.writerow(['diamond_price_id','origin','shape','carat_weight_from','carat_weight_to','color','clarity','cut','price','effective_date'])
    i = 1
    for origin_i, origin in enumerate(['NATURAL','LAB_GROWN']):
        for shape_i, shape in enumerate(shapes):
            for clarity_i, clarity in enumerate(clarities):
                for range_i,range in enumerate(carat_ranges):
                    for color_i, color in enumerate(colors):
                        for cut_i, cut in enumerate(cuts):
                            calculation = (base_diamond + base_carat_price*range_i) * pow(color_multiplier,color_i) * pow(clarity_multiplier,clarity_i) * cut_multiplier[cut_i]['value'] * shape_multiplier[shape_i]['value']
                            writer.writerow([i,origin, shape, range[0], range[1], color, clarity, cut, round(calculation,2), today])
                            i += 1
