import concurrent.futures
from get_single_page import get_single_page
from get_links import get_links
from generate import generate_sql_inserts
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
    writer.writerow(["diamond_code","diamond_name","shape","origin","proportions","fluorescence","symmetry","polish","cut","color","clarity","carat_weight","note","image","active"])

    with concurrent.futures.ThreadPoolExecutor(max_workers=1) as executor:
        results = list(executor.map(process_link, links))

    for result in results:
        writer.writerow(result)
        # print(result)
    
    file.close()
    
    print('get results complete')
    
    generate_sql_inserts('result.csv', 'Diamond', 'output.sql')

if __name__ == "__main__":
    main()

