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
	[email] varchar(MAX) not null,
    [password] varchar(MAX) not null,
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
    [status]              varchar(MAX), /*wait_sale_staff,wait_manager,manager_approved,wait_customer,customer_accept,designing,pending_design,production,delivered,wait_payment,completed*/
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
	[note]				  varchar(MAX),
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

    [shape]        varchar(MAX), /*round, princess, cushion, emerald, oval, radiant, asscher, marquise, heart, pear*/

    [origin]       varchar(MAX), /*LAB_GROWN, NATURAL*/
    [proportions]  varchar(MAX), /*image link*/
    [fluorescence] varchar(MAX), /*None, Faint, Medium, Strong, Very_Strong*/
    [symmetry]     varchar(MAX), /*Poor, Fair, Good, Very_Good, Excellent*/
    [polish]       varchar(MAX), /*Poor, Fair, Good, Very_Good, Excellent*/

    [cut]          varchar(MAX), /*Poor, Fair, Good, Very_Good, Excellent*/
    [color]        varchar(MAX), /*Z,Y,X,W,V,U,T,S,R,Q,P,O,N,M,L,K,J,I,H,G,F,E,D (only use from K to D)*/
    [clarity]      varchar(MAX), /*I3, I2, I1, SI3, SI2, SI1, VS2, VS1, VVS2, VVS1, IF, FL*/
    [carat_weight] decimal(19, 4), /* 0 to 10*/
    [note]         varchar(MAX),

    [image]        varchar(MAX),

    [active]       bit,
    primary key ([diamond_id])
)
create table [DiamondPriceList]
(
    [diamond_price_id]  int identity (1,1),
    [origin]            varchar(MAX),
	[shape]				varchar(MAX),
    [carat_weight_from]	decimal(19, 4),
	[carat_weight_to]	decimal(19,4),
    [color]             varchar(MAX),
    [clarity]           varchar(MAX),
    [cut]               varchar(MAX),
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
    [design_type]       varchar(MAX),
    [design_file]       varchar(MAX),
    primary key ([product_design_id])
)
go
create table [ProductShellDesign]
(
    [shell_id]          int identity (1,1),
    [product_design_id] int,
    [shell_name]        varchar(MAX),
	[image]				varchar(MAX),
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




-- Inserting into Diamond table
/*
INSERT INTO [Diamond] ([diamond_code], [diamond_name], [shape], [origin], [proportions], [fluorescence], [symmetry], [polish], [cut], [color], [clarity], [carat_weight], [note], [image], [active]) VALUES
('DC001', 'Diamond A', 'round', 'LAB_GROWN', 'https://example.com/image1', 'None', 'Excellent', 'Excellent', 'Excellent', 'D', 'FL', 1.5000, 'A beautiful lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC002', 'Diamond B', 'princess', 'NATURAL', 'https://example.com/image2', 'Faint', 'Very_Good', 'Very_Good', 'Very_Good', 'E', 'IF', 2.0000, 'A natural diamond with faint fluorescence', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC003', 'Diamond C', 'cushion', 'LAB_GROWN', 'https://example.com/image3', 'Medium', 'Good', 'Good', 'Good', 'F', 'VVS1', 0.7500, 'A cushion cut lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC004', 'Diamond D', 'emerald', 'NATURAL', 'https://example.com/image4', 'Strong', 'Fair', 'Fair', 'Fair', 'G', 'VVS2', 1.2500, 'An emerald cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC005', 'Diamond E', 'oval', 'LAB_GROWN', 'https://example.com/image5', 'Very_Strong', 'Poor', 'Poor', 'Fair', 'H', 'VS1', 1.1000, 'An oval cut lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC006', 'Diamond F', 'radiant', 'NATURAL', 'https://example.com/image6', 'None', 'Excellent', 'Excellent', 'Excellent', 'I', 'VS2', 2.5000, 'A radiant cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC007', 'Diamond G', 'asscher', 'LAB_GROWN', 'https://example.com/image7', 'Faint', 'Very_Good', 'Very_Good', 'Very_Good', 'J', 'SI1', 1.8000, 'An asscher cut lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC008', 'Diamond H', 'marquise', 'NATURAL', 'https://example.com/image8', 'Medium', 'Good', 'Good', 'Good', 'K', 'SI2', 1.0000, 'A marquise cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC009', 'Diamond I', 'heart', 'LAB_GROWN', 'https://example.com/image9', 'Strong', 'Fair', 'Fair', 'Fair', 'D', 'VS1', 0.9000, 'A heart cut lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC010', 'Diamond J', 'pear', 'NATURAL', 'https://example.com/image10', 'Very_Strong', 'Poor', 'Poor', 'Fair', 'E', 'VS2', 1.7500, 'A pear cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC011', 'Diamond K', 'round', 'LAB_GROWN', 'https://example.com/image11', 'None', 'Excellent', 'Excellent', 'Excellent', 'F', 'VS2', 0.8500, 'A beautiful round lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC012', 'Diamond L', 'princess', 'NATURAL', 'https://example.com/image12', 'Faint', 'Very_Good', 'Very_Good', 'Very_Good', 'G', 'FL', 1.4000, 'A princess cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC013', 'Diamond M', 'cushion', 'LAB_GROWN', 'https://example.com/image13', 'Medium', 'Good', 'Good', 'Good', 'H', 'IF', 2.2000, 'A cushion cut lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC014', 'Diamond N', 'emerald', 'NATURAL', 'https://example.com/image14', 'Strong', 'Fair', 'Fair', 'Fair', 'I', 'VVS1', 2.1000, 'An emerald cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC015', 'Diamond O', 'oval', 'LAB_GROWN', 'https://example.com/image15', 'Very_Strong', 'Poor', 'Poor', 'Fair', 'J', 'VVS2', 2.3000, 'An oval cut lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC016', 'Diamond P', 'radiant', 'NATURAL', 'https://example.com/image16', 'None', 'Excellent', 'Excellent', 'Excellent', 'K', 'VS1', 0.9500, 'A radiant cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC017', 'Diamond Q', 'asscher', 'LAB_GROWN', 'https://example.com/image17', 'Faint', 'Very_Good', 'Very_Good', 'Very_Good', 'D', 'VS2', 1.6000, 'An asscher cut lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC018', 'Diamond R', 'marquise', 'NATURAL', 'https://example.com/image18', 'Medium', 'Good', 'Good', 'Good', 'E', 'SI1', 1.3000, 'A marquise cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC019', 'Diamond S', 'heart', 'LAB_GROWN', 'https://example.com/image19', 'Strong', 'Fair', 'Fair', 'Fair', 'F', 'SI2', 2.6000, 'A heart cut lab-grown diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1),
('DC020', 'Diamond T', 'pear', 'NATURAL', 'https://example.com/image20', 'Very_Strong', 'Poor', 'Poor', 'Fair', 'G', 'VS1', 1.2000, 'A pear cut natural diamond', 'https://ion.bluenile.com/sgmdirect/photoID/34760580/Diamond/20959885/nl/Diamond-round-1.04-Carat_3_first_.jpg', 1);

-- Inserting into DiamondPriceList table
INSERT INTO [DiamondPriceList] ([origin], [shape], [carat_weight_from], [carat_weight_to], [color], [clarity], [cut], [price], [effective_date]) VALUES
('LAB_GROWN', 'round', 1.0000, 2.0000, 'D', 'FL', 'Excellent', 5000.00, '2024-01-01'),
('NATURAL', 'princess', 1.5000, 2.5000, 'E', 'IF', 'Very_Good', 7500.00, '2024-02-01'),
('LAB_GROWN', 'cushion', 0.5000, 1.0000, 'F', 'VVS1', 'Good', 3000.00, '2024-03-01'),
('NATURAL', 'emerald', 1.0000, 2.0000, 'G', 'VVS2', 'Fair', 4000.00, '2024-04-01'),
('LAB_GROWN', 'round', 1.0000, 2.0000, 'D', 'FL', 'Excellent', 500.00, '2023-01-01'),
('NATURAL', 'princess', 1.5000, 2.5000, 'E', 'IF', 'Very_Good', 700.00, '2023-02-01'),
('LAB_GROWN', 'cushion', 0.5000, 1.0000, 'F', 'VVS1', 'Good', 300.00, '2023-03-01'),
('NATURAL', 'emerald', 1.0000, 2.0000, 'G', 'VVS2', 'Fair', 400.00, '2023-04-01'),
('LAB_GROWN', 'oval', 1.0000, 1.5000, 'H', 'VS1', 'Fair', 2500.00, '2024-05-01'),
('NATURAL', 'radiant', 2.0000, 3.0000, 'I', 'VS2', 'Excellent', 9000.00, '2024-06-01'),
('LAB_GROWN', 'asscher', 1.5000, 2.0000, 'J', 'SI1', 'Very_Good', 5500.00, '2024-05-01'),
('NATURAL', 'marquise', 0.5000, 1.0000, 'K', 'SI2', 'Good', 2000.00, '2024-05-01'),
('LAB_GROWN', 'heart', 0.7500, 1.0000, 'D', 'VS1', 'Fair', 1800.00, '2024-05-01'),
('NATURAL', 'pear', 1.0000, 2.0000, 'E', 'VS2', 'Fair', 2200.00, '2024-05-01'),
('LAB_GROWN', 'round', 0.5000, 1.0000, 'F', 'VS2', 'Excellent', 3500.00, '2024-05-01'),
('NATURAL', 'princess', 1.0000, 1.5000, 'G', 'FL', 'Very_Good', 6500.00, '2024-05-01'),
('LAB_GROWN', 'round', 0.5000, 1.0000, 'F', 'VS2', 'Excellent', 350000.00, '2026-11-01'),
('NATURAL', 'princess', 1.0000, 1.5000, 'G', 'FL', 'Very_Good', 650000.00, '2026-12-01'),
('LAB_GROWN', 'cushion', 2.0000, 2.5000, 'H', 'IF', 'Good', 10000.00, '2025-01-01'),
('NATURAL', 'emerald', 1.5000, 2.5000, 'I', 'VVS1', 'Fair', 8500.00, '2025-02-01'),
('LAB_GROWN', 'oval', 2.0000, 3.0000, 'J', 'VVS2', 'Fair', 12000.00, '2025-03-01'),
('NATURAL', 'radiant', 0.5000, 1.0000, 'K', 'VS1', 'Excellent', 2800.00, '2025-04-01'),
('LAB_GROWN', 'asscher', 1.0000, 2.0000, 'D', 'VS2', 'Very_Good', 7000.00, '2025-05-01'),
('NATURAL', 'marquise', 1.0000, 1.5000, 'E', 'SI1', 'Good', 4800.00, '2025-06-01'),
('LAB_GROWN', 'heart', 2.0000, 3.0000, 'F', 'SI2', 'Fair', 11000.00, '2025-07-01'),
('NATURAL', 'pear', 1.0000, 1.5000, 'G', 'VS1', 'Fair', 3200.00, '2025-08-01'),

('LAB_GROWN', 'cushion', 2.0000, 2.5000, 'H', 'IF', 'Good', 9999.00, '2024-01-01'),
('NATURAL', 'emerald', 1.5000, 2.5000, 'I', 'VVS1', 'Fair', 8499.00, '2024-02-01'),
('LAB_GROWN', 'oval', 2.0000, 3.0000, 'J', 'VVS2', 'Fair', 11999.00, '2024-03-01'),
('NATURAL', 'radiant', 0.5000, 1.0000, 'K', 'VS1', 'Excellent', 2799.00, '2024-04-01'),
('LAB_GROWN', 'asscher', 1.0000, 2.0000, 'D', 'VS2', 'Very_Good', 6999.00, '2024-05-01'),
('NATURAL', 'marquise', 1.0000, 1.5000, 'E', 'SI1', 'Good', 4799.00, '2024-05-01'),
('LAB_GROWN', 'heart', 2.0000, 3.0000, 'F', 'SI2', 'Fair', 10999.00, '2024-05-01'),
('NATURAL', 'pear', 1.0000, 1.5000, 'G', 'VS1', 'Fair', 3199.00, '2024-05-01')
go
*/
insert into [Account]
values ('user_admin','anhtnse183225@fpt.edu.vn', '123', 1, 'admin'),
       ('user_customer_01', 'anhtnse183225@fpt.edu.vn' ,'123', 1, 'customer'),
       ('user_customer_02','anhtnse183225@fpt.edu.vn', '123', 1, 'customer'),
       ('user_customer_03','anhtnse183225@fpt.edu.vn', '123', 1, 'customer'),
       ('user_sale_staff','anhtnse183225@fpt.edu.vn', '123', 1, 'staff'),
       ('user_design_staff','anhtnse183225@fpt.edu.vn', '123', 1, 'staff'),
       ('user_manager','anhtnse183225@fpt.edu.vn', '123', 1, 'staff'),
       ('user_production_staff','anhtnse183225@fpt.edu.vn', '123', 1, 'staff'),
       ('disabled_user_account','anhtnse183225@fpt.edu.vn', '323', 0, 'customer')
go
insert into [Staff]([username], [name], [phone], [staff_type])
values ('user_sale_staff', 'Nguyen', '0123456789', 'sale'),
       ('user_design_staff', 'Tran', '0192301823', 'design'),
       ('user_production_staff', 'Le', '08289304728', 'produce'),
       ('user_manager', 'Thanh', '02938492893', 'manage')
go
insert into [Customer]([username], [name], [address])
values ('user_customer_01', 'Minh', '123 Becker Street'),
       ('user_customer_02', 'Binh', '234 New York'),
       ('user_customer_03', 'Hannah', '999 6th Avenue')
go
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
       (N'Crescent Fancy Pink Pav� Diamond Open Engagement Ring In 14k White Gold (1/10 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/150524/RND/Images/gallery.jpg'),
       ('Two Stone Engagement Ring With Cushion Shaped Diamond In 14k White Gold (1/2 Ct. Tw.)', 'ring',
        'https://ion.bluenile.com/sets/Jewelry-bn/149478/RND/Images/gallery.jpg'),
       (N'Bella Vaughan For Blue Nile Seattle Split Shank Double Pav� Diamond Engagement Ring In Platinum (3/4 Ct. Tw.)',
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
		('SUNFLOWER DIAMOND PENDANT NECKLACE 14K YELLOW GOLD (0.19CT)', 'Necklace',
		'https://d2d22nphq0yz8t.cloudfront.net/35aee99d-95d9-46a2-9030-ab04199b35ba/https://images.allurez.com/productimages/large/C15059-14Y.jpg')
go
-- Insert into ProductShellDesign
DECLARE @design_id INT = 1;

WHILE @design_id <= 22
    BEGIN
        INSERT INTO [ProductShellDesign] ([product_design_id], [shell_name], [diamond_quantity], [e_diamond_price],
                                          [e_material_price], [production_price], [markup_rate], [image])
        VALUES (@design_id, 'gold shell', 1, 100.00, 200.00, 300.00, 1.0, null),
               (@design_id, 'platinum shell', 2, 120.00, 250.00, 350.00, 1.0, null),
               (@design_id, 'silver shell', 3, 80.00, 150.00, 250.00, 1.0, null);

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