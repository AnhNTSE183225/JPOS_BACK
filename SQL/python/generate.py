import csv

def generate_sql_inserts(file_name, table_name, output_file_name):
    with open(file_name, 'r') as file, open(output_file_name, 'w') as output_file:
        reader = csv.reader(file)
        headers = next(reader)
        for row in reader:
            values = ', '.join([f"'{value}'" for value in row])
            sql = f"INSERT INTO {table_name} ({', '.join(headers)}) VALUES ({values});\n"
            output_file.write(sql)

generate_sql_inserts('diamond_data.csv', 'Diamond', 'output.sql')
generate_sql_inserts('diamond_price_data.csv','DiamondPriceList','output_price.sql')