SELECT 
    CASE 
        WHEN carat_weight BETWEEN 0.0	AND 0.5		THEN '0.0-0.5'
        WHEN carat_weight BETWEEN 0.5	AND 1.0		THEN '0.5-1.0'
		WHEN carat_weight BETWEEN 1.0	and 1.5		THEN '1.0-1.5'
		WHEN carat_weight BETWEEN 1.5	and 2.0		THEN '1.5-2.0'
		WHEN carat_weight BETWEEN 2.0	and 2.5		THEN '2.0-2.5'
		WHEN carat_weight BETWEEN 2.5	and 3.0		THEN '2.5-3.0'
		WHEN carat_weight BETWEEN 3.0	and 3.5		THEN '3.0-3.5'
		WHEN carat_weight BETWEEN 3.5	and 4.0		THEN '3.5-4.0'
		WHEN carat_weight BETWEEN 4.0	and 4.5		THEN '4.0-4.5'
		WHEN carat_weight BETWEEN 4.5	and 5.0		THEN '4.5-5.0'
        ELSE 'Other'
    END AS [Range],
    color,
    clarity,
    cut,
    AVG(price) AS AvgPrice,
    MAX(effective_date) AS LatestEffectiveDate
FROM DiamondPriceList d1
WHERE origin = 'LAB_GROWN' 
    AND shape = 'round' 
    AND carat_weight >= 0 
    AND carat_weight <= 5
    AND NOT EXISTS (
        SELECT 1
        FROM DiamondPriceList d2
        WHERE d1.origin = d2.origin
            AND d1.shape = d2.shape
            AND d1.color = d2.color
            AND d1.clarity = d2.clarity
            AND d1.cut = d2.cut
            AND d2.effective_date > d1.effective_date
    )
GROUP BY 
    CASE 
        WHEN carat_weight BETWEEN 0.0	AND 0.5		THEN '0.0-0.5'
        WHEN carat_weight BETWEEN 0.5	AND 1.0		THEN '0.5-1.0'
		WHEN carat_weight BETWEEN 1.0	and 1.5		THEN '1.0-1.5'
		WHEN carat_weight BETWEEN 1.5	and 2.0		THEN '1.5-2.0'
		WHEN carat_weight BETWEEN 2.0	and 2.5		THEN '2.0-2.5'
		WHEN carat_weight BETWEEN 2.5	and 3.0		THEN '2.5-3.0'
		WHEN carat_weight BETWEEN 3.0	and 3.5		THEN '3.0-3.5'
		WHEN carat_weight BETWEEN 3.5	and 4.0		THEN '3.5-4.0'
		WHEN carat_weight BETWEEN 4.0	and 4.5		THEN '4.0-4.5'
		WHEN carat_weight BETWEEN 4.5	and 5.0		THEN '4.5-5.0'
        ELSE 'Other'
    END,
    color,
    clarity,
    cut
ORDER BY [Range], color, clarity, cut;
