USE master
GO
IF EXISTS (SELECT name
           FROM master.dbo.sysdatabases
           WHERE name = 'JPOS')
    BEGIN
        ALTER DATABASE JPOS SET SINGLE_USER WITH ROLLBACK IMMEDIATE
        DROP DATABASE JPOS
    END
GO
CREATE DATABASE JPOS
GO
USE JPOS
GO

create table [Account]
(
    [username] varchar(255) not null,
    [password] varchar(255) not null,
    [status]   bit          not null,
    [role]     varchar(255) not null,
    primary key ([username])
)
go
insert into [Account]
values ('user_admin', '{bcrypt}$2a$12$EXmafYMsWPDLj0CS5BdHgOzV.739rBEm8uiui/SEtdAylspWuWJJq', 1, 'admin'),
       ('user_customer_01', '{bcrypt}$2a$12$cM74/PBTg.N6tQXsA/FL3ef.R/bLiCoLFMmsCQolQPtFoFTSm6Q4y', 1, 'customer'),
       ('user_customer_02', '123', 1, 'customer'),
       ('user_customer_03', '123', 1, 'customer'),
       ('user_sale_staff', '123', 1, 'staff'),
       ('user_design_staff', '123', 1, 'staff'),
       ('user_manager', '123', 1, 'staff'),
       ('user_production_staff', '123', 1, 'staff'),
       ('disabled_user_account', '323', 0, 'customer')
go
/*
status: enabled(1)/disabled(0)
role:
	+ customer
	+ staff
	+ admin
*/
create table [Staff]
(
    [staff_id]   int identity (1,1),
    [username]   varchar(255) not null,
    [name]       varchar(255),
    [phone]      varchar(255),
    [staff_type] varchar(255),
    primary key ([staff_id]),
    foreign key ([username]) references [Account]
)
/*
staff_type:
	+ 'sale'
	+ 'design'
	+ 'produce'
	+ 'manage'
*/
go
insert into [Staff]([username], [name], [phone], [staff_type])
values ('user_sale_staff', 'Nguyen', '0123456789', 'sale'),
       ('user_design_staff', 'Tran', '0192301823', 'design'),
       ('user_production_staff', 'Le', '08289304728', 'produce'),
       ('user_manager', 'Thanh', '02938492893', 'manage')
go
create table [Customer]
(
    [customer_id] int identity (1,1),
    [username]    varchar(255) not null,
    [name]        varchar(255),
    [address]     varchar(255),
    primary key ([customer_id]),
    foreign key ([username]) references [Account]
)
go
insert into [Customer]([username], [name], [address])
values ('user_customer_01', 'Minh', '123 Becker Street'),
       ('user_customer_02', 'Binh', '234 New York'),
       ('user_customer_03', 'Hannah', '999 6th Avenue')
go
create table [Product]
(
    [product_id]       int identity (1,1),
    [product_name]     varchar(255),
    [e_diamond_price]  decimal(19, 4),
    [e_material_price] decimal(19, 4),
    [production_price] decimal(19, 4),
    [markup_rate]      decimal(19, 4),
    [product_type]     varchar(255),
    primary key ([product_id])
)
go
create table [Order]
(
    [order_id]            int identity (1,1),
    [customer_id]         int,
    [product_id]          int,
    [sale_staff_id]       int,
    [design_staff_id]     int,
    [production_staff_id] int,
    [status]              varchar(255),
    [order_date]          datetime,
    [order_type]          varchar(255),
    [budget]              varchar(255),
    [design_file]         varchar(255),
    [description]         varchar(255),
    [q_diamond_price]     decimal(19, 4),
    [q_material_price]    decimal(19, 4),
    [q_date]              datetime,
    [o_diamond_price]     decimal(19, 4),
    [o_material_price]    decimal(19, 4),
    [o_date]              datetime,
    [e_diamond_price]     decimal(19, 4),
    [e_material_price]    decimal(19, 4),
    [production_price]    decimal(19, 4),
    [markup_rate]         decimal(19, 4),
    [total_amount]        decimal(19, 4),
    [model_file]          varchar(255),
    [model_feedback]      varchar(255),
    [product_image]       varchar(255),
    [shipping_fee]        decimal(19, 4),
    [tax_fee]             decimal(19, 4),
    [discount]            decimal(19, 4),
    primary key ([order_id]),
    foreign key ([product_id]) references [Product],
    foreign key ([customer_id]) references [Customer],
    foreign key ([sale_staff_id]) references [Staff],
    foreign key ([production_staff_id]) references [Staff],
    foreign key ([design_staff_id]) references [Staff]
)
/*
	1. wait_sale_staff - no price (staff handle)
	2. wait_manager - no price (manager handle)
	3. manager_approved - q_price (staff handle)
	4. wait_customer - q_price (customer handle)
	5. customer_accept - o_price (staff handle)
	6. designing - o_price (customize) (design staff handle)
	7. pending_design - wait customer to accept 3d design (customer handle)
	8. production - o_price (from_design starts here) (production staff handle)
	9. completed - payment
	==> orderStatus

	orderType:
	"customize"
	"from_design"
*/
go
create table [Payment]
(
    [payment_id]     int identity (1,1),
    [order_id]       int,
    [payment_date]   datetime,
    [payment_method] varchar(255),
    [payment_status] varchar(255),
    [amount_paid]    decimal(19, 4),
    [amount_total]   decimal(19, 4),
    primary key ([payment_id]),
    foreign key ([order_id]) references [Order]
)
go
create table [Warranty]
(
    [warranty_id]         int identity (1,1),
    [customer_id]         int,
    [product_id]          int,
    [purchase_date]       datetime,
    [end_of_support_date] datetime,
    [terms]               varchar(255),
    primary key ([warranty_id]),
    foreign key ([customer_id]) references [Customer],
    foreign key ([product_id]) references [Product]
)
go
create table [Material]
(
    [material_id]   int identity (1,1),
    [material_name] varchar(255),
    primary key ([material_id])
)
go
insert into [Material]([material_name])
values ('gold_sjc'),
       ('gold_pnj'),
       ('platinum'),
       ('gold_14k'),
       ('gold_18k'),
       ('gold_10k'),
       ('silver')
go
create table [ProductMaterial]
(
    [product_id]  int,
    [material_id] int,
    [weight]      decimal(19, 4),
    primary key ([product_id], [material_id]),
    foreign key ([product_id]) references [Product],
    foreign key ([material_id]) references [Material]
)
go
create table [MaterialPriceList]
(
    [material_id]    int,
    [effective_date] datetime,
    [price]          decimal(19, 4),
    primary key ([material_id], [effective_date]),
    foreign key ([material_id]) references [Material]
)
go
insert into [MaterialPriceList]
values
(1,'2024-05-27 15:16:00',5525),
(2,'2024-05-27 15:16:00',5200),
(3,'2024-05-27 15:16:00',7700),
(4,'2024-05-27 15:16:00',5300),
(5,'2024-05-27 15:16:00',5450),
(6,'2024-05-27 15:16:00',5000),
(1,'2024-05-28 15:16:00',5500),
(2,'2024-05-28 15:16:00',5250),
(3,'2024-05-28 15:16:00',7690),
(4,'2024-05-28 15:16:00',5330),
(5,'2024-05-28 15:16:00',5500),
(6,'2024-05-28 15:16:00',5120),
(1,'2024-05-29 15:16:00',5555),
(2,'2024-05-29 15:16:00',5274),
(3,'2024-05-29 15:16:00',7780),
(4,'2024-05-29 15:16:00',5385),
(5,'2024-05-29 15:16:00',5621),
(6,'2024-05-29 15:16:00',5234),
(1,'2024-05-30 15:16:00',5543),
(2,'2024-05-30 15:16:00',5301),
(7,'2024-05-30 15:16:00',1000)
go
create table [Diamond]
(
    [diamond_id]   int identity (1,1),
    [diamond_code] varchar(255),
    [diamond_name] varchar(255),

    [shape]        varchar(8),

    [origin]       varchar(12),
    [proportions]  varchar(255),
    [fluorescence] varchar(11),
    [symmetry]     varchar(9),
    [polish]       varchar(9),

    [cut]          varchar(9),
    [color]        varchar(1),
    [clarity]      varchar(4),
    [carat_weight] decimal(19, 4),
    [note]         varchar(255),

    [image]        varchar(MAX),

    [active]       bit,
    primary key ([diamond_id])
)
/*
	shape: round, princess, cushion, emerald, oval, radiant, asscher, marquise, heart, pear
	origin: LAB_GROWN, NATURAL
	proportions: image link
	fluorescence: None, Faint, Medium, Strong, Very_Strong
	symmetry: Poor, Fair, Good, Very_Good, Excellent
	polish: Poor, Fair, Good, Very_Good, Excellent
	cut: Poor, Fair, Good, Very_Good, Excellent
	color: Z,Y,X,W,V,U,T,S,R,Q,P,O,N,M,L,K,J,I,H,G,F,E,D
	clarity: I3, I2, I1, SI2, SI1, VS2, VS1, VVS2, VVS1, IF, FL
	carat_weight: 0 to 30
*/
INSERT INTO [Diamond]
([diamond_code], [diamond_name], [shape], [origin], [proportions], [fluorescence], [symmetry], [polish], [cut], [color],
 [clarity], [carat_weight], [note], [image], [active])
VALUES ('D001', 'Brilliant Star', 'round', 'LAB_GROWN', 'http://example.com/proportions1', 'None', 'Excellent',
        'Excellent', 'Excellent', 'D', 'FL', 1.2000, 'Perfect diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/ybul65wajinvijcqfsrm.jpg', 1),
       ('D002', 'Sparkling Heart', 'heart', 'LAB_GROWN', 'http://example.com/proportions2', 'Faint', 'Very_Good',
        'Very_Good', 'Very_Good', 'G', 'VVS1', 0.7500, 'Heart-shaped diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/qafryf1zk6kptdmojo2g.jpg', 1),
       ('D003', 'Radiant Beauty', 'radiant', 'NATURAL', 'http://example.com/proportions3', 'Medium', 'Good', 'Good',
        'Good', 'J', 'SI1', 2.5000, 'Radiant cut diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/ojrayiy4ta0xaqhfk4od.jpg', 1),
       ('D004', 'Princess Charm', 'princess', 'NATURAL', 'http://example.com/proportions4', 'Strong', 'Fair', 'Fair',
        'Fair', 'E', 'VS2', 1.0000, 'Princess cut diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001575/diamonds/woondzemluk9v0fz3lns.jpg', 1),
       ('D005', 'Cushion Delight', 'cushion', 'NATURAL', 'http://example.com/proportions5', 'Very_Strong', 'Poor',
        'Poor', 'Poor', 'H', 'I1', 3.0000, 'Cushion cut diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/kqfdlvjgqx0frjzsn7yq.jpg', 1),
       ('D006', 'Oval Grace', 'oval', 'NATURAL', 'http://example.com/proportions6', 'None', 'Very_Good', 'Very_Good',
        'Very_Good', 'I', 'IF', 1.5000, 'Oval cut diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/yscrkl1o3vwicmpccxhf.jpg', 1),
       ('D007', 'Emerald Elegance', 'emerald', 'NATURAL', 'http://example.com/proportions7', 'Faint', 'Excellent',
        'Excellent', 'Excellent', 'F', 'VVS2', 2.2000, 'Emerald cut diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/bwo1q43ptsfbeusxst1y.jpg', 1),
       ('D008', 'Marquise Splendor', 'marquise', 'NATURAL', 'http://example.com/proportions8', 'Medium', 'Good',
        'Good', 'Good', 'K', 'VS1', 0.9000, 'Marquise cut diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/viokaja8fyb3c6ww2wlg.jpg', 1),
       ('D009', 'Asscher Radiance', 'asscher', 'NATURAL', 'http://example.com/proportions9', 'Strong', 'Fair', 'Fair',
        'Fair', 'L', 'SI2', 1.8000, 'Asscher cut diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001575/diamonds/im3jp4dsqv7jselgeif0.jpg', 1),
       ('D010', 'Pear Spark', 'pear', 'NATURAL', 'http://example.com/proportions10', 'Very_Strong', 'Poor', 'Poor',
        'Poor', 'M', 'I2', 2.7500, 'Pear cut diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001575/diamonds/tqefwyvc1fvyobsvlrbz.jpg', 1),
       ('D011', 'Brilliant Gem', 'round', 'NATURAL', 'http://example.com/proportions11', 'None', 'Excellent',
        'Excellent', 'Excellent', 'N', 'FL', 1.3500, 'Another perfect diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/ybul65wajinvijcqfsrm.jpg', 1),
       ('D012', 'Radiant Star', 'radiant', 'NATURAL', 'http://example.com/proportions12', 'Faint', 'Very_Good',
        'Very_Good', 'Very_Good', 'O', 'VVS1', 0.6500, 'Radiant star diamond',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/ojrayiy4ta0xaqhfk4od.jpg', 1),
       ('D013', 'Princess Beauty', 'princess', 'NATURAL', 'http://example.com/proportions13', 'Medium', 'Good',
        'Good', 'Good', 'P', 'SI1', 2.0000, 'Beautiful princess cut',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001575/diamonds/woondzemluk9v0fz3lns.jpg', 1),
       ('D014', 'Cushion Charm', 'cushion', 'NATURAL', 'http://example.com/proportions14', 'Strong', 'Fair', 'Fair',
        'Fair', 'Q', 'VS2', 0.8000, 'Charming cushion cut',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/kqfdlvjgqx0frjzsn7yq.jpg', 1),
       ('D015', 'Oval Delight', 'oval', 'NATURAL', 'http://example.com/proportions15', 'Very_Strong', 'Poor', 'Poor',
        'Poor', 'R', 'I1', 1.7000, 'Delightful oval cut',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/yscrkl1o3vwicmpccxhf.jpg', 1),
       ('D016', 'Emerald Grace', 'emerald', 'NATURAL', 'http://example.com/proportions16', 'None', 'Very_Good',
        'Very_Good', 'Very_Good', 'S', 'IF', 2.4000, 'Graceful emerald cut',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/bwo1q43ptsfbeusxst1y.jpg', 1),
       ('D017', 'Marquise Radiance', 'marquise', 'NATURAL', 'http://example.com/proportions17', 'Faint', 'Excellent',
        'Excellent', 'Excellent', 'T', 'VVS2', 1.1000, 'Radiant marquise cut',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/viokaja8fyb3c6ww2wlg.jpg', 1),
       ('D018', 'Asscher Splendor', 'asscher', 'NATURAL', 'http://example.com/proportions18', 'Medium', 'Good', 'Good',
        'Good', 'U', 'VS1', 2.9000, 'Splendid asscher cut',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001575/diamonds/im3jp4dsqv7jselgeif0.jpg', 1),
       ('D019', 'Pear Elegance', 'pear', 'NATURAL', 'http://example.com/proportions19', 'Strong', 'Fair', 'Fair',
        'Fair', 'V', 'SI2', 3.5000, 'Elegant pear cut',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001575/diamonds/tqefwyvc1fvyobsvlrbz.jpg', 1),
       ('D020', 'Brilliant Sparkle', 'round', 'NATURAL', 'http://example.com/proportions20', 'Very_Strong', 'Poor',
        'Poor', 'Poor', 'W', 'I2', 1.9500, 'Sparkling brilliant cut',
        'https://res.cloudinary.com/dbfbigo0e/image/upload/v1718001574/diamonds/ybul65wajinvijcqfsrm.jpg', 1);
go
create table [DiamondPriceList]
(
    [diamond_price_id]  int identity (1,1),
    [origin]            varchar(255),
	[shape]				varchar(255),
    [carat_weight]		decimal(19, 4),
    [color]             varchar(255),
    [clarity]           varchar(255),
    [cut]               varchar(255),
    [price]             decimal(19, 4),
    [effective_date]    datetime
    primary key ([diamond_price_id])
)
GO
/************************************************************************************************************************************/
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
GO
/************************************************************************************************************************************/
create table [ProductDiamond]
(
    [product_id] int,
    [diamond_id] int,
    primary key ([product_id], [diamond_id]),
    foreign key ([product_id]) references [Product],
    foreign key ([diamond_id]) references [Diamond]
)
go
create table [ProductDesign]
(
    [product_design_id] int identity (1,1),
    [design_name]       varchar(255),
    [design_type]       varchar(255),
    [design_file]       varchar(MAX),
    primary key ([product_design_id])
)
go
insert into [ProductDesign]
values ('Four Stone Emerald Diamond Engagement Ring In Platinum', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/194280/EMR/Images/gallery.jpg'),
       ('Channel Set Round Diamond Engagement Ring In 14k White Gold (2 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/197133/RND/Images/gallery.jpg'),
       ('Seven Stone Oval Diamond Engagement Ring In Platinum (1 Ct.Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/192959/RND/Images/gallery.jpg'),
       ('Asscher-Cut Diamond Engagement Ring In 14k White Gold', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/196442/RND/Images/gallery.jpg'),
       ('Two Stone Engagement Ring With Half Moon Diamond In 14k White Gold (1/2 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/150541/RND/Images/gallery.jpg'),
       ('Studio Double Halo Gala Diamond Engagement Ring In Platinum (7/8 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/195312/RND/Images/gallery.jpg'),
       (N'Crescent Fancy Pink Pavé Diamond Open Engagement Ring In 14k White Gold (1/10 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/150524/RND/Images/gallery.jpg'),
       ('Two Stone Engagement Ring With Cushion Shaped Diamond In 14k White Gold (1/2 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/149478/RND/Images/gallery.jpg'),
       (N'Bella Vaughan For Blue Nile Seattle Split Shank Double Pavé Diamond Engagement Ring In Platinum (3/4 Ct. Tw.)',
        'ring', 'https://ion.bluenile.com/sets/Jewelry-bn/195607/RND/Images/gallery.jpg'),
       ('Romantic Diamond Floral Halo Engagement Ring In 14k White Gold (1/2 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/150339/RND/Images/gallery.jpg'),
       ('Two Stone Engagement Ring With Heart Shaped Diamond In 14k White Gold (1/3 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/146446/RND/Images/gallery.jpg'),
       ('Bella Vaughan Cadillac Three Stone Engagement Ring In 18k Yellow Gold (1/3 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/193956/RND/Images/gallery.jpg'),
       ('Vintage Diamond Halo Engagement Ring In 14k White Gold (5/8 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/192187/RND/Images/gallery.jpg'),
       ('Studio Empress Diamond Engagement Ring In Platinum (1/3 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/195724/RND/Images/gallery.jpg'),
       ('ZAC ZAC POSEN Art Deco Hexagon Halo Diamond Engagement Ring In 14k White Gold (3/4 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/193471/RND/Images/gallery.jpg'),
       ('Cushion-Shaped Split-Shank Diamond Halo Engagement Ring In 18k White Gold', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/192287/CSH/Images/gallery.jpg'),
       ('design_X Split Shank Hidden Halo Diamond Engagement Ring In 14k White Gold (1/2 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/192267/RND/Images/gallery.jpg'),
       ('Trio Marquise Diamond Engagement Ring? In 14k White Gold', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/196227/RND/Images/gallery.jpg'),
       ('Baguette And Round Ballerina Halo Diamond Engagement Ring In 14k White Gold (1ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/192596/RND/Images/gallery.jpg'),
       ('Three-Stone Halo Diamond Engagement Ring In 14k White Gold', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/192274/RND/Images/gallery.jpg'),
       ('Two-Tone Intertwined Double Halo Diamond Engagement Ring In 14k White And Yellow Gold (1/2 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/193494/RND/Images/gallery.jpg');
go
create table [ProductShellDesign]
(
    [shell_id]          int identity (1,1),
    [product_design_id] int,
    [shell_name]        varchar(255),
    [diamond_quantity]  int,
    [e_diamond_price]   decimal(19, 4),
    [e_material_price]  decimal(19, 4),
    [production_price]  decimal(19, 4),
    [markup_rate]       decimal(19, 4)
        primary key ([shell_id]),
    foreign key ([product_design_id]) references [ProductDesign]
)
go
create table [ProductShellMaterial]
(
    [shell_id]    int,
    [material_id] int,
    [weight]      decimal(19, 4),
    primary key ([shell_id], [material_id]),
    foreign key ([material_id]) references [Material],
    foreign key ([shell_id]) references [ProductShellDesign]
)
go
-- Insert into ProductShellDesign
DECLARE @design_id INT = 1;

WHILE @design_id <= 21
    BEGIN
        INSERT INTO [ProductShellDesign] ([product_design_id], [shell_name], [diamond_quantity], [e_diamond_price],
                                          [e_material_price], [production_price], [markup_rate])
        VALUES (@design_id, 'gold shell', 1, 100.00, 200.00, 300.00, 1.0),
               (@design_id, 'platinum shell', 2, 120.00, 250.00, 350.00, 1.0),
               (@design_id, 'silver shell', 3, 80.00, 150.00, 250.00, 1.0);

        SET @design_id = @design_id + 1;
    END;
GO

-- Insert into ProductShellMaterial
DECLARE @shell_id INT = 1;

WHILE @shell_id <= 63  -- 21 designs * 3 shells each
BEGIN
    INSERT INTO [ProductShellMaterial] ([shell_id], [material_id], [weight])
    VALUES 
    (@shell_id, 1, 0.96), -- gold
    (@shell_id + 1, 3, 0.96), -- platinum
    (@shell_id + 2, 7, 0.96); -- silver
    
    SET @shell_id = @shell_id + 3;
END;
GO
select * from [Account]
select * from [Customer]
select * from [Diamond]
select * from [DiamondPriceList]
select * from [Material]
select * from [MaterialPriceList]
select * from [Order]
select * from [Payment]
select * from [Product]
select * from [ProductDesign]
select * from [ProductDiamond]
select * from [ProductMaterial]
select * from [ProductShellDesign]
select * from [ProductShellMaterial]
select * from [Staff]
select * from [Warranty]
go