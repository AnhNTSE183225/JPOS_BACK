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
    [email]    varchar(MAX) not null,
    [password] varchar(MAX) not null,
	[provider] varchar(MAX) not null,
    [status]   bit          not null,
    [role]     varchar(MAX) not null, /*customer,staff,admin*/
    primary key ([username])
)
go
create table [Staff]
(
    [staff_id]   int identity (1,1),
    [username]   varchar(255) not null,
    [name]       varchar(MAX),
    [phone]      varchar(MAX),
    [staff_type] varchar(MAX), /*sale,design,produce,manage*/
    primary key ([staff_id]),
    foreign key ([username]) references [Account]
)
go
create table [Customer]
(
    [customer_id] int identity (1,1),
    [username]    varchar(255) not null,
    [name]        varchar(MAX),
    [address]     varchar(MAX),
    primary key ([customer_id]),
    foreign key ([username]) references [Account]
)
go
create table [Product]
(
    [product_id]       int identity (1,1),
    [product_name]     varchar(MAX),
    [e_diamond_price]  decimal(19, 4),
    [e_material_price] decimal(19, 4),
    [production_price] decimal(19, 4),
    [markup_rate]      decimal(19, 4),
    [product_type]     varchar(MAX),
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
    [status]              varchar(MAX), /*wait_sale_staff,wait_manager,manager_approved,wait_customer,customer_accept,designing,pending_design,production,delivered,wait_payment,completed,cancelled*/
    [order_date]          datetime,
    [order_type]          varchar(MAX), /*customize,from_design*/
    [budget]              varchar(MAX),
    [design_file]         varchar(MAX),
    [description]         varchar(MAX),
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
    [model_file]          varchar(MAX),
    [model_feedback]      varchar(MAX),
    [product_image]       varchar(MAX),
    [shipping_fee]        decimal(19, 4),
    [tax_fee]             decimal(19, 4),
    [discount]            decimal(19, 4),
    [note]                varchar(MAX),
    primary key ([order_id]),
    foreign key ([product_id]) references [Product],
    foreign key ([customer_id]) references [Customer],
    foreign key ([sale_staff_id]) references [Staff],
    foreign key ([production_staff_id]) references [Staff],
    foreign key ([design_staff_id]) references [Staff]
)
go
create table [Payment]
(
    [payment_id]     int identity (1,1),
    [order_id]       int,
    [payment_date]   datetime,
    [payment_method] varchar(MAX),
    [payment_status] varchar(MAX),
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
    [terms]               varchar(MAX),
    primary key ([warranty_id]),
    foreign key ([customer_id]) references [Customer],
    foreign key ([product_id]) references [Product]
)
go
create table [Material]
(
    [material_id]   int identity (1,1),
    [material_name] varchar(MAX),
    primary key ([material_id])
)
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
create table [Diamond]
(
    [diamond_id]   int identity (1,1),
    [diamond_code] varchar(MAX),
    [diamond_name] varchar(MAX),

    [shape]        varchar(50), /*round, princess, cushion, emerald, oval, radiant, asscher, marquise, heart, pear*/

    [origin]       varchar(50), /*LAB_GROWN, NATURAL*/
    [proportions]  varchar(MAX), /*image link*/
    [fluorescence] varchar(MAX), /*None, Faint, Medium, Strong, Very_Strong*/
    [symmetry]     varchar(MAX), /*Poor, Fair, Good, Very_Good, Excellent*/
    [polish]       varchar(MAX), /*Poor, Fair, Good, Very_Good, Excellent*/

    [cut]          varchar(50), /*(Poor,) Fair, Good, Very_Good, Excellent*/
    [color]        varchar(50), /*(Z,Y,X,W,V,U,T,S,R,Q,P,O,N,M,L,) K,J,I,H,G,F,E,D (only use from K to D)*/
    [clarity]      varchar(50), /*I3, I2, I1, SI3, SI2, SI1, VS2, VS1, VVS2, VVS1, IF, FL*/
    [carat_weight] decimal(19, 4), /* 0 to 10*/
    [note]         varchar(MAX),

    [image]        varchar(MAX),

    [active]       bit,
    primary key ([diamond_id])
)
create table [DiamondPriceList]
(
    [diamond_price_id]  int identity (1,1),
    [origin]            varchar(50),
    [shape]             varchar(50),
    [carat_weight_from] decimal(19, 4),
    [carat_weight_to]   decimal(19, 4),
    [color]             varchar(50),
    [clarity]           varchar(50),
    [cut]               varchar(50),
    [price]             decimal(19, 4),
    [effective_date]    datetime
        primary key ([diamond_price_id])
)
GO
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
    [design_name]       varchar(MAX),
    [design_type]       varchar(MAX), /*ring, necklace, earrings, bracelets*/
    [design_file]       varchar(MAX),
    primary key ([product_design_id])
)
go
create table [ProductShellDesign]
(
    [shell_id]          int identity (1,1),
    [product_design_id] int,
    [shell_name]        varchar(MAX),
    [diamond_quantity]  int,
	[image]				varchar(MAX),
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
create table [DesignConfigurations]
(
	[config_id]		int identity (1,1),
	[design_type]	varchar(MAX), /*ring, necklace, earrings, bracelets*/
	[config_info]	varchar(MAX)
)
go
insert into [DesignConfigurations]([design_type], [config_info]) values
('ring', 'Ring Size 6'),
('ring', 'Ring Size 7'),
('ring', 'Ring Size 8'),
('necklace', 'Necklace length 45cm'),
('necklace', 'Necklace length 50cm'),
('necklace', 'Necklace length 55cm'),
('earrings', 'Earrings Size 2cm'),
('earrings', 'Earrings Size 3cm'),
('earrings', 'Earrings Size 4cm'),
('bracelets', 'Bracelets Size 18cm'),
('bracelets', 'Bracelets Size 20cm'),
('bracelets', 'Bracelets Size 22cm')
go
insert into [Account]
values ('admin', 'admin@gmail.com', '$2a$10$w.D7u6ER7AmFamDj7lSSHe5TCnVRkr5gtlA4Ji9JFWSFWU0WDWUUe','LOCAL', 1, 'admin'),
       ('nguyen', 'nguyen@gmail.com', '$2a$10$pmL28xzaY6XueEJzEMIyzua48PIOpf9bBsXj5oF95M2ZzSWNRxunK','LOCAL', 1, 'customer'),
       ('manage', 'test@gmail.com', '$2a$10$S03I4mUWnhDpn4YVAaDV6eRmg3cG2Zh1w/ZhBM8yM0ndiElfAlmkC','LOCAL',1, 'staff'),
	   ('salestaff','sale@gmail.com','$2a$10$eWez7raD1lNUbXWcFh/WsejyOSgeYgGI.d2nxbKhy2tjy9mXFLk3.','LOCAL',1,'staff'),
	   ('designstaff','design@gmail.com','$2a$10$tMMuaoWkiHWufO8yqCV1pe.1ZPfLG3DHcY9iT/mwh4eBp5DCMppfS','LOCAL',1,'staff'),
	   ('productionstaff','produce@gmail.com','$2a$10$g5c6ujz8YXo85ZOgBmDzp.cFzhpgBqSZ7XR.NYiDQYqb284xIB/Tm','LOCAL',1,'staff')
go
insert into [Staff]([username], [name], [phone], [staff_type])
values	('manage', 'Manager', '02938492893', 'manage'),
		('salestaff','salestaff','09102389','sale'),
		('designstaff','designstaff','089192732','design'),
		('productionstaff','productionstaff','0192398712','produce')
go
insert into [Customer]([username], [name], [address])
values ('nguyen', 'Minh', '123 Becker Street')
go
-- Insert into Diamond
/**
INSERT INTO [Diamond] ([diamond_code], [diamond_name], [shape], [origin], [proportions], [fluorescence], [symmetry], [polish], [cut], [color], [clarity], [carat_weight], [note], [image], [active])
VALUES 
('D001', 'Round Brilliant Diamond', 'round', 'NATURAL', 'https://example.com/proportions1.jpg', 'None', 'Excellent', 'Excellent', 'Excellent', 'D', 'IF', 0.1, 'Top quality round diamond', 'https://example.com/diamond1.jpg', 1),
('D002', 'Princess Cut Diamond', 'princess', 'LAB_GROWN', 'https://example.com/proportions2.jpg', 'None', 'Excellent', 'Excellent', 'Excellent', 'D', 'IF', 2.1, 'Beautiful princess cut diamond', 'https://example.com/diamond2.jpg', 1),
('D003', 'Cushion Cut Diamond', 'cushion', 'NATURAL', 'https://example.com/proportions3.jpg', 'None', 'Excellent', 'Excellent', 'Excellent', 'D', 'IF', 4.1, 'Elegant cushion cut diamond', 'https://example.com/diamond3.jpg', 1),
('D004', 'Emerald Cut Diamond', 'emerald', 'LAB_GROWN', 'https://example.com/proportions4.jpg', 'None', 'Excellent', 'Excellent', 'Excellent', 'D', 'IF', 6.1, 'Classic emerald cut diamond', 'https://example.com/diamond4.jpg', 1),
('D005', 'Oval Cut Diamond', 'oval', 'NATURAL', 'https://example.com/proportions5.jpg', 'None', 'Excellent', 'Excellent', 'Excellent', 'D', 'IF', 7.1, 'Unique oval cut diamond', 'https://example.com/diamond5.jpg', 1);
go
-- Insert into DiamondPriceList
INSERT INTO [DiamondPriceList] ([origin], [shape], [carat_weight_from], [carat_weight_to], [color], [clarity], [cut], [price], [effective_date])
VALUES 
('NATURAL', 'round', 0.05, 0.15, 'D', 'IF', 'Excellent', 10000, '2024-07-19'),
('LAB_GROWN', 'princess', 2.05, 2.15, 'D', 'IF', 'Excellent', 10000, '2024-07-19'),
('NATURAL', 'cushion', 4.05, 4.15, 'D', 'IF', 'Excellent', 10000, '2024-07-19'),
('LAB_GROWN', 'emerald', 6.05, 6.15, 'D', 'IF', 'Excellent', 10000, '2024-07-19'),
('NATURAL', 'oval', 8.05, 8.15, 'D', 'IF', 'Excellent', 10000, '2024-07-19');
go**/
--Insert into Material
insert into [Material]([material_name])
values ('gold_sjc'),
       ('gold_pnj'),
       ('platinum'),
       ('gold_14k'),
       ('gold_18k'),
       ('gold_10k'),
       ('silver')
go
--Insert into MaterialPriceList
insert into [MaterialPriceList]
values (1, '2024-05-27 15:16:00', 5525),
       (2, '2024-05-27 15:16:00', 5200),
       (3, '2024-05-27 15:16:00', 7700),
       (4, '2024-05-27 15:16:00', 5300),
       (5, '2024-05-27 15:16:00', 5450),
       (6, '2024-05-27 15:16:00', 5000),
       (1, '2024-05-28 15:16:00', 5500),
       (2, '2024-05-28 15:16:00', 5250),
       (3, '2024-05-28 15:16:00', 7690),
       (4, '2024-05-28 15:16:00', 5330),
       (5, '2024-05-28 15:16:00', 5500),
       (6, '2024-05-28 15:16:00', 5120),
       (1, '2024-05-29 15:16:00', 5555),
       (2, '2024-05-29 15:16:00', 5274),
       (3, '2024-05-29 15:16:00', 7780),
       (4, '2024-05-29 15:16:00', 5385),
       (5, '2024-05-29 15:16:00', 5621),
       (6, '2024-05-29 15:16:00', 5234),
       (1, '2024-05-30 15:16:00', 5543),
       (2, '2024-05-30 15:16:00', 5301),
       (7, '2024-05-30 15:16:00', 1000)
go
--Insert into ProductDesign
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
       (N'Crescent Fancy Pink Pav Diamond Open Engagement Ring In 14k White Gold (1/10 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/150524/RND/Images/gallery.jpg'),
       ('Two Stone Engagement Ring With Cushion Shaped Diamond In 14k White Gold (1/2 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/149478/RND/Images/gallery.jpg'),
       (N'Bella Vaughan For Blue Nile Seattle Split Shank Double Pav Diamond Engagement Ring In Platinum (3/4 Ct. Tw.)',
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
        'https://ion.bluenile.com/sets/Jewelry-bn/193494/RND/Images/gallery.jpg'),
       ('SUNFLOWER DIAMOND PENDANT NECKLACE 14K YELLOW GOLD (0.19CT)', 'necklace',
        'https://d2d22nphq0yz8t.cloudfront.net/35aee99d-95d9-46a2-9030-ab04199b35ba/https://images.allurez.com/productimages/large/C15059-14Y.jpg'),
		('HUGGIE ROUND DIAMOND PAVE EARRINGS HOOPS', 'earrings',
		'https://d2d22nphq0yz8t.cloudfront.net/35aee99d-95d9-46a2-9030-ab04199b35ba/https://images.allurez.com/productimages/large/40978-14Y.jpg'),
		('ETERNITY DIAMOND TENNIS BRACELET','bracelets',
		'https://d2d22nphq0yz8t.cloudfront.net/35aee99d-95d9-46a2-9030-ab04199b35ba/https://images.allurez.com/productimages/large/B5641WD2.jpg')

go
-- Insert into ProductShellDesign
DECLARE @design_id INT = 1;

WHILE @design_id <= 24
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

WHILE @shell_id <= 72 -- 24 designs * 3 shells each
    BEGIN
        INSERT INTO [ProductShellMaterial] ([shell_id], [material_id], [weight])
        VALUES (@shell_id, 1, 0.96),     -- gold
               (@shell_id + 1, 3, 0.96), -- platinum
               (@shell_id + 2, 7, 0.96); -- silver

        SET @shell_id = @shell_id + 3;
    END;
GO
select *
from [Account]
select *
from [Customer]
select *
from [Diamond]
select *
from [DiamondPriceList]
select *
from [Material]
select *
from [MaterialPriceList]
select *
from [Order]
select *
from [Payment]
select *
from [Product]
select *
from [ProductDesign]
select *
from [ProductDiamond]
select *
from [ProductMaterial]
select *
from [ProductShellDesign]
select *
from [ProductShellMaterial]
select *
from [Staff]
select *
from [Warranty]
go