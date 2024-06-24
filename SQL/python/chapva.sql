INSERT INTO [DiamondPriceList] (
    [origin],
    [shape],
    [carat_weight_from],
    [carat_weight_to],
    [color],
    [clarity],
    [cut],
    [price],
    [effective_date]
)
SELECT
    d.[origin],
    d.[shape],
    0 AS [carat_weight_from], /* Set your desired carat weight range */
    10 AS [carat_weight_to], /* Set your desired carat weight range */
    d.[color],
    d.[clarity],
    d.[cut],
    /* Calculate price based on your business logic (e.g., cost markup) */
    /* Example: d.[cost] * 1.2 for a 20% markup */
    1000 * 1.2 AS [price],
    GETDATE() AS [effective_date] /* Set the effective date */
FROM
    [Diamond] d
WHERE
    d.[active] = 1 /* Filter for active diamonds */