GO
DROP PROCEDURE [dbo].[InsertIntoDiamond]
GO
CREATE PROCEDURE InsertIntoDiamond
AS
BEGIN
    DECLARE @i decimal(19, 4);
    DECLARE @shape varchar(8);
    DECLARE @origin varchar(12);
    DECLARE @fluorescence varchar(11);
    DECLARE @symmetry varchar(9);
    DECLARE @polish varchar(9);
    DECLARE @cut varchar(9);
    DECLARE @color varchar(1);
    DECLARE @clarity varchar(4);

    DECLARE shape_cursor CURSOR FOR SELECT 'round' UNION SELECT 'princess' UNION SELECT 'cushion' UNION SELECT 'emerald' UNION SELECT 'oval' UNION SELECT 'radiant' UNION SELECT 'asscher' UNION SELECT 'marquise' UNION SELECT 'heart' UNION SELECT 'pear';
    DECLARE origin_cursor CURSOR FOR SELECT 'LAB_GROWN' UNION SELECT 'NATURAL';
    DECLARE fluorescence_cursor CURSOR FOR SELECT 'None' UNION SELECT 'Faint' UNION SELECT 'Medium' UNION SELECT 'Strong' UNION SELECT 'Very_Strong';
    DECLARE symmetry_cursor CURSOR FOR SELECT 'Poor' UNION SELECT 'Fair' UNION SELECT 'Good' UNION SELECT 'Very_Good' UNION SELECT 'Excellent';
    DECLARE polish_cursor CURSOR FOR SELECT 'Poor' UNION SELECT 'Fair' UNION SELECT 'Good' UNION SELECT 'Very_Good' UNION SELECT 'Excellent';
    DECLARE cut_cursor CURSOR FOR SELECT 'Poor' UNION SELECT 'Fair' UNION SELECT 'Good' UNION SELECT 'Very_Good' UNION SELECT 'Excellent';
    DECLARE color_cursor CURSOR FOR SELECT 'K' UNION SELECT 'J' UNION SELECT 'I' UNION SELECT 'H' UNION SELECT 'G' UNION SELECT 'F' UNION SELECT 'E' UNION SELECT 'D';
    DECLARE clarity_cursor CURSOR FOR SELECT 'I3' UNION SELECT 'I2' UNION SELECT 'I1' UNION SELECT 'SI2' UNION SELECT 'SI1' UNION SELECT 'VS2' UNION SELECT 'VS1' UNION SELECT 'VVS2' UNION SELECT 'VVS1' UNION SELECT 'IF' UNION SELECT 'FL';

    OPEN shape_cursor;
    OPEN origin_cursor;
    OPEN fluorescence_cursor;
    OPEN symmetry_cursor;
    OPEN polish_cursor;
    OPEN cut_cursor;
    OPEN color_cursor;
    OPEN clarity_cursor;

    FETCH NEXT FROM shape_cursor INTO @shape;
    WHILE @@FETCH_STATUS = 0
    BEGIN
        FETCH NEXT FROM origin_cursor INTO @origin;
        WHILE @@FETCH_STATUS = 0
        BEGIN
            FETCH NEXT FROM fluorescence_cursor INTO @fluorescence;
            WHILE @@FETCH_STATUS = 0
            BEGIN
                FETCH NEXT FROM symmetry_cursor INTO @symmetry;
                WHILE @@FETCH_STATUS = 0
                BEGIN
                    FETCH NEXT FROM polish_cursor INTO @polish;
                    WHILE @@FETCH_STATUS = 0
                    BEGIN
                        FETCH NEXT FROM cut_cursor INTO @cut;
                        WHILE @@FETCH_STATUS = 0
                        BEGIN
                            FETCH NEXT FROM color_cursor INTO @color;
                            WHILE @@FETCH_STATUS = 0
                            BEGIN
                                FETCH NEXT FROM clarity_cursor INTO @clarity;
                                WHILE @@FETCH_STATUS = 0
                                BEGIN
                                    SET @i = 0.2;
                                    WHILE @i <= 5
                                    BEGIN
                                        INSERT INTO Diamond (diamond_code, diamond_name, shape, origin, proportions, fluorescence, symmetry, polish, cut, color, clarity, carat_weight, note, image, active)
                                        VALUES ('code', 'name', @shape, @origin, 'image link', @fluorescence, @symmetry, @polish, @cut, @color, @clarity, @i, 'note', 'image', 1);
                                        SET @i = @i + 0.2;
                                    END;
                                    FETCH NEXT FROM clarity_cursor INTO @clarity;
                                END;
                                FETCH NEXT FROM color_cursor INTO @color;
                            END;
                            FETCH NEXT FROM cut_cursor INTO @cut;
                        END;
                        FETCH NEXT FROM polish_cursor INTO @polish;
                    END;
                    FETCH NEXT FROM symmetry_cursor INTO @symmetry;
                END;
                FETCH NEXT FROM fluorescence_cursor INTO @fluorescence;
            END;
            FETCH NEXT FROM origin_cursor INTO @origin;
        END;
        FETCH NEXT FROM shape_cursor INTO @shape;
    END;

    CLOSE clarity_cursor;
    DEALLOCATE clarity_cursor;
    CLOSE color_cursor;
    DEALLOCATE color_cursor;
    CLOSE cut_cursor;
    DEALLOCATE cut_cursor;
    CLOSE polish_cursor;
    DEALLOCATE polish_cursor;
    CLOSE symmetry_cursor;
    DEALLOCATE symmetry_cursor;
    CLOSE fluorescence_cursor;
    DEALLOCATE fluorescence_cursor;
    CLOSE origin_cursor;
    DEALLOCATE origin_cursor;
    CLOSE shape_cursor;
    DEALLOCATE shape_cursor;
END
GO
EXEC [dbo].[InsertIntoDiamond]