BULK INSERT [DiamondPriceList]
FROM 'C:\Github\JPOS_BACK\SQL\python_generate_prices\generated_price.csv'
WITH (
FIELDTERMINATOR = ','
,ROWTERMINATOR = '\n'
,BATCHSIZE = 1000
,MAXERRORS = 1
,FIRSTROW = 2
,ERRORFILE = 'C:\Github\JPOS_BACK\SQL\python_generate_prices\error.txt'
)
UPDATE STATISTICS DiamondPriceList;
UPDATE STATISTICS Diamond;
CREATE INDEX idx_diamondpricelist 
ON DiamondPriceList (
    origin, shape, carat_weight_from, carat_weight_to, color, clarity, cut, effective_date
);
