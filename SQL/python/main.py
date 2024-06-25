import concurrent.futures
from get_single_page import get_single_page
from get_links import get_links
from generate import generate_sql_inserts
from average_price import process_file
import csv

def process_link(link):
    result = get_single_page(link)
    print(result)
    return result

def main():
    links = get_links()

    print('get links completed')
    
    file = open("result.csv","w",newline="")
    writer = csv.writer(file)
    
    price_file = open("prices.csv","w",newline="")
    price_writer = csv.writer(price_file)
    
    writer.writerow(["diamond_code","diamond_name","shape","origin","proportions","fluorescence","symmetry","polish","cut","color","clarity","carat_weight","note","image","active"])
    price_writer.writerow(["origin","shape","carat_weight","color","clarity","cut","price"])
    
    with concurrent.futures.ThreadPoolExecutor(max_workers=30) as executor:
        results = list(executor.map(process_link, links))

    for result in results:
        writer.writerow(result[1:])
        price_writer.writerow([result[4],result[3],result[12],result[10],result[11],result[9],result[0]])
        # print(result)
    
    file.close()
    price_file.close()
    
    print('get results complete')
    
    generate_sql_inserts('result.csv', 'Diamond', 'output.sql')
    process_file()
    generate_sql_inserts('average_prices.csv', 'DiamondPriceList', 'price_output.sql')

if __name__ == "__main__":
    main()

