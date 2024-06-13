GO
DROP PROCEDURE [dbo].[InsertIntoDiamondPriceList]
GO
CREATE PROCEDURE InsertIntoDiamondPriceList
AS
BEGIN
    DECLARE @diamond_id int;
    DECLARE @origin varchar(12);
    DECLARE @shape varchar(8);
    DECLARE @carat_weight decimal(19, 4);
    DECLARE @color varchar(1);
    DECLARE @clarity varchar(4);
    DECLARE @cut varchar(9);
    DECLARE @price decimal(19, 4);
    DECLARE @effective_date datetime;

    DECLARE diamond_cursor CURSOR FOR SELECT diamond_id, origin, shape, carat_weight, color, clarity, cut FROM Diamond;
    OPEN diamond_cursor;

    FETCH NEXT FROM diamond_cursor INTO @diamond_id, @origin, @shape, @carat_weight, @color, @clarity, @cut;
    WHILE @@FETCH_STATUS = 0
    BEGIN
        SET @price = @carat_weight * 1000; -- Set the price based on carat weight, adjust this as needed
        SET @effective_date = GETDATE();

        -- Insert current price
        INSERT INTO DiamondPriceList (origin, shape, carat_weight, color, clarity, cut, price, effective_date)
        VALUES (@origin, @shape, @carat_weight, @color, @clarity, @cut, @price, @effective_date);

        -- Insert past price
        SET @price = @price * 2; -- Double the price for the past record, adjust this as needed
        SET @effective_date = DATEADD(day, -30, @effective_date); -- Set the past date to 30 days ago, adjust this as needed
        INSERT INTO DiamondPriceList (origin, shape, carat_weight, color, clarity, cut, price, effective_date)
        VALUES (@origin, @shape, @carat_weight, @color, @clarity, @cut, @price, @effective_date);

        FETCH NEXT FROM diamond_cursor INTO @diamond_id, @origin, @shape, @carat_weight, @color, @clarity, @cut;
    END;

    CLOSE diamond_cursor;
    DEALLOCATE diamond_cursor;
END;
GO
EXEC [dbo].[InsertIntoDiamondPriceList]
