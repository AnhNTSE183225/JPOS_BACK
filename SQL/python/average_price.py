import csv
from collections import defaultdict
from datetime import datetime

def read_csv(file_path):
    with open(file_path, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        return list(reader)

def categorize_carat(carat):
    carat = float(carat)
    carat_rounded = round(carat * 10) / 10
    return (carat_rounded - 0.05, carat_rounded + 0.05)

def process_data(rows):
    grouped_data = defaultdict(list)
    for row in rows:
        carat_range = categorize_carat(row['carat_weight'])
        key = (row['origin'], row['shape'], carat_range[0], carat_range[1], row['color'], row['clarity'], row['cut'])
        grouped_data[key].append(float(row['price']))
    
    average_prices = {}
    for key, prices in grouped_data.items():
        average_prices[key] = sum(prices) / len(prices)
    
    return average_prices

def process_file():
    rows = read_csv('prices.csv')
    average_prices = process_data(rows)
    today = datetime.now().strftime("%Y-%m-%d")
    with open('average_prices.csv', 'w', newline='') as csvfile:
        fieldnames = ['origin', 'shape', 'carat_weight_from', 'carat_weight_to', 'color', 'clarity', 'cut', 'price', 'effective_date']
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()
        for key, avg_price in average_prices.items():
            writer.writerow({
                'origin': key[0],
                'shape': key[1],
                'carat_weight_from': key[2],
                'carat_weight_to': key[3],
                'color': key[4],
                'clarity': key[5],
                'cut': key[6],
                'price': f"{avg_price:.2f}",
                'effective_date': today
            })