USE master
GO
IF EXISTS (
	SELECT name FROM master.dbo.sysdatabases
	WHERE name = 'JPOS'
)
BEGIN
	ALTER DATABASE JPOS SET SINGLE_USER WITH ROLLBACK IMMEDIATE
	DROP DATABASE JPOS
END
GO
CREATE DATABASE JPOS
GO
USE JPOS
GO

create table [Account] (
	[username] varchar(255) not null,
	[password] varchar(255) not null,
	[status] bit not null,
	[role] varchar(255) not null,
	primary key([username])
)
go
insert into [Account]
values
('user_admin','{bcrypt}$2a$12$EXmafYMsWPDLj0CS5BdHgOzV.739rBEm8uiui/SEtdAylspWuWJJq',1,'admin'),
('user_customer_01','{bcrypt}$2a$12$cM74/PBTg.N6tQXsA/FL3ef.R/bLiCoLFMmsCQolQPtFoFTSm6Q4y',1,'customer'),
('user_customer_02','123',1,'customer'),
('user_customer_03','123',1,'customer'),
('user_sale_staff','123',1,'staff'),
('user_design_staff','123',1,'staff'),
('user_manager','123',1,'staff'),
('user_production_staff','123',1,'staff'),
('disabled_user_account','323',0,'customer')
go
/*
status: enabled(1)/disabled(0)
role:
	+ customer
	+ staff
	+ admin
*/
create table [Staff] (
	[staff_id] int identity(1,1),
	[username] varchar(255) not null,
	[name] varchar(255),
	[phone] varchar(255),
	[staff_type] varchar(255),
	primary key([staff_id]),
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
insert into [Staff]([username],[name],[phone],[staff_type])
values
('user_sale_staff','Nguyen','0123456789','sale'),
('user_design_staff','Tran','0192301823','design'),
('user_production_staff','Le','08289304728','produce'),
('user_manager','Thanh','02938492893','manage')
go
create table [Customer] (
	[customer_id] int identity(1,1),
	[username] varchar(255) not null,
	[name] varchar(255),
	[address] varchar(255),
	primary key([customer_id]),
	foreign key([username]) references [Account]
)
go
insert into [Customer]([username],[name],[address])
values
('user_customer_01','Minh','123 Becker Street'),
('user_customer_02','Binh','234 New York'),
('user_customer_03','Hannah','999 6th Avenue')
go
create table [Product] (
	[product_id] int identity(1,1),
	[product_name] varchar(255),
	[e_diamond_price] decimal(19,4),
	[e_material_price] decimal(19,4),
	[production_price] decimal(19,4),
	[markup_rate] decimal(19,4),
	[product_type] varchar(255),
	primary key([product_id])
)
go
create table [Order] (
	[order_id] int identity(1,1),
	[customer_id] int,
	[product_id] int,
	[sale_staff_id] int,
	[design_staff_id] int,
	[production_staff_id] int,
	[status] varchar(255),
	[order_date] datetime,
	[order_type] varchar(255),
	[budget] varchar(255),
	[design_file] varchar(255),
	[description] varchar(255),
	[q_diamond_price] decimal(19,4),
	[q_material_price] decimal(19,4),
	[q_date] datetime,
	[o_diamond_price] decimal(19,4),
	[o_material_price] decimal(19,4),
	[o_date] datetime,
	[e_diamond_price] decimal(19,4),
	[e_material_price] decimal(19,4),
	[production_price] decimal(19,4),
	[markup_rate] decimal(19,4),
	[total_amount] decimal(19,4),
	[model_file] varchar(255),
	[model_feedback] varchar(255),
	[product_image] varchar(255),
	[shipping_fee] decimal(19,4),
	[tax_fee] decimal(19,4),
	[discount] decimal(19,4),
	primary key([order_id]),
	foreign key([product_id]) references [Product],
	foreign key([customer_id]) references [Customer],
	foreign key([sale_staff_id]) references [Staff],
	foreign key([production_staff_id]) references [Staff],
	foreign key([design_staff_id]) references [Staff]
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
create table [Payment] (
	[payment_id] int identity(1,1),
	[order_id] int,
	[payment_date] datetime,
	[payment_method] varchar(255),
	[payment_status] varchar(255),
	[amount_paid] decimal(19,4),
	[amount_total] decimal(19,4),
	primary key([payment_id]),
	foreign key([order_id]) references [Order]
)
go
create table [Warranty] (
	[warranty_id] int identity(1,1),
	[customer_id] int,
	[product_id] int,
	[purchase_date] datetime,
	[end_of_support_date] datetime,
	[terms] varchar(255),
	primary key([warranty_id]),
	foreign key([customer_id]) references [Customer],
	foreign key([product_id]) references [Product]
)
go
create table [Material] (
	[material_id] int identity(1,1),
	[material_name] varchar(255),
	primary key([material_id])
)
go
insert into [Material]([material_name])
values
('gold_sjc'),
('gold_pnj'),
('platinum'),
('gold_14k'),
('gold_18k'),
('gold_10k'),
('silver')
go
create table [ProductMaterial] (
	[product_id] int,
	[material_id] int,
	[weight] decimal(19,4),
	primary key([product_id],[material_id]),
	foreign key([product_id]) references [Product],
	foreign key([material_id]) references [Material]
)
go
create table [MaterialPriceList] (
	[material_id] int,
	[effective_date] datetime,
	[price] decimal(19,4),
	primary key([material_id],[effective_date]),
	foreign key([material_id]) references [Material]
)
go
insert into [MaterialPriceList]
values
(1,'2024-05-27 15:16:00',5000000),
(2,'2024-05-27 15:16:00',4500000),
(3,'2024-05-27 15:16:00',9000000),
(4,'2024-05-27 15:16:00',3500000),
(5,'2024-05-27 15:16:00',4000000),
(6,'2024-05-27 15:16:00',3000000),
(1,'2024-05-28 15:16:00',5100000),
(2,'2024-05-28 15:16:00',4600000),
(3,'2024-05-28 15:16:00',9100000),
(4,'2024-05-28 15:16:00',3600000),
(5,'2024-05-28 15:16:00',4100000),
(6,'2024-05-28 15:16:00',3100000),
(1,'2024-05-29 15:16:00',5200000),
(2,'2024-05-29 15:16:00',4700000),
(3,'2024-05-29 15:16:00',9200000),
(4,'2024-05-29 15:16:00',3700000),
(5,'2024-05-29 15:16:00',4200000),
(6,'2024-05-29 15:16:00',3200000),
(1,'2024-05-30 15:16:00',5300000),
(2,'2024-05-30 15:16:00',4800000),
(7,'2024-05-30 15:16:00',3000000)
go
create table [Diamond] (
	[diamond_id] int identity(1,1),
	[diamond_code] varchar(255),
	[diamond_name] varchar(255),
	
	[shape] varchar(8),
	
	[origin] varchar(12),
	[proportions] varchar(255),
	[fluorescence] varchar(11),
	[symmetry] varchar(9),
	[polish] varchar(9),
	
	[cut] varchar(9),
	[color] varchar(1),
	[clarity] varchar(4),
	[carat_weight] decimal(19,4),
	[note] varchar(255),
	
	[active] bit,
	primary key([diamond_id])
)
/*
	shape: round, princess, cushion, emerald, oval, radiant, asscher, marquise, heart, pear
	origin: Canada, Angola, Namibia, Botswana, Lesotho, South_Africa, 
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
([diamond_code], [diamond_name], [shape], [origin], [proportions], [fluorescence], [symmetry], [polish], [cut], [color], [clarity], [carat_weight], [note], [active]) 
VALUES 
('D001', 'Brilliant Star', 'round', 'Canada', 'http://example.com/proportions1', 'None', 'Excellent', 'Excellent', 'Excellent', 'D', 'FL', 1.2000, 'Perfect diamond', 1),
('D002', 'Sparkling Heart', 'heart', 'South_Africa', 'http://example.com/proportions2', 'Faint', 'Very_Good', 'Very_Good', 'Very_Good', 'G', 'VVS1', 0.7500, 'Heart-shaped diamond', 1),
('D003', 'Radiant Beauty', 'radiant', 'Botswana', 'http://example.com/proportions3', 'Medium', 'Good', 'Good', 'Good', 'J', 'SI1', 2.5000, 'Radiant cut diamond', 1),
('D004', 'Princess Charm', 'princess', 'Namibia', 'http://example.com/proportions4', 'Strong', 'Fair', 'Fair', 'Fair', 'E', 'VS2', 1.0000, 'Princess cut diamond', 1),
('D005', 'Cushion Delight', 'cushion', 'Lesotho', 'http://example.com/proportions5', 'Very_Strong', 'Poor', 'Poor', 'Poor', 'H', 'I1', 3.0000, 'Cushion cut diamond', 1),
('D006', 'Oval Grace', 'oval', 'Angola', 'http://example.com/proportions6', 'None', 'Very_Good', 'Very_Good', 'Very_Good', 'I', 'IF', 1.5000, 'Oval cut diamond', 1),
('D007', 'Emerald Elegance', 'emerald', 'South_Africa', 'http://example.com/proportions7', 'Faint', 'Excellent', 'Excellent', 'Excellent', 'F', 'VVS2', 2.2000, 'Emerald cut diamond', 1),
('D008', 'Marquise Splendor', 'marquise', 'Botswana', 'http://example.com/proportions8', 'Medium', 'Good', 'Good', 'Good', 'K', 'VS1', 0.9000, 'Marquise cut diamond', 1),
('D009', 'Asscher Radiance', 'asscher', 'Canada', 'http://example.com/proportions9', 'Strong', 'Fair', 'Fair', 'Fair', 'L', 'SI2', 1.8000, 'Asscher cut diamond', 1),
('D010', 'Pear Spark', 'pear', 'Namibia', 'http://example.com/proportions10', 'Very_Strong', 'Poor', 'Poor', 'Poor', 'M', 'I2', 2.7500, 'Pear cut diamond', 1),
('D011', 'Brilliant Gem', 'round', 'Lesotho', 'http://example.com/proportions11', 'None', 'Excellent', 'Excellent', 'Excellent', 'N', 'FL', 1.3500, 'Another perfect diamond', 1),
('D012', 'Radiant Star', 'radiant', 'Angola', 'http://example.com/proportions12', 'Faint', 'Very_Good', 'Very_Good', 'Very_Good', 'O', 'VVS1', 0.6500, 'Radiant star diamond', 1),
('D013', 'Princess Beauty', 'princess', 'South_Africa', 'http://example.com/proportions13', 'Medium', 'Good', 'Good', 'Good', 'P', 'SI1', 2.0000, 'Beautiful princess cut', 1),
('D014', 'Cushion Charm', 'cushion', 'Botswana', 'http://example.com/proportions14', 'Strong', 'Fair', 'Fair', 'Fair', 'Q', 'VS2', 0.8000, 'Charming cushion cut', 1),
('D015', 'Oval Delight', 'oval', 'Canada', 'http://example.com/proportions15', 'Very_Strong', 'Poor', 'Poor', 'Poor', 'R', 'I1', 1.7000, 'Delightful oval cut', 1),
('D016', 'Emerald Grace', 'emerald', 'Namibia', 'http://example.com/proportions16', 'None', 'Very_Good', 'Very_Good', 'Very_Good', 'S', 'IF', 2.4000, 'Graceful emerald cut', 1),
('D017', 'Marquise Radiance', 'marquise', 'Lesotho', 'http://example.com/proportions17', 'Faint', 'Excellent', 'Excellent', 'Excellent', 'T', 'VVS2', 1.1000, 'Radiant marquise cut', 1),
('D018', 'Asscher Splendor', 'asscher', 'Angola', 'http://example.com/proportions18', 'Medium', 'Good', 'Good', 'Good', 'U', 'VS1', 2.9000, 'Splendid asscher cut', 1),
('D019', 'Pear Elegance', 'pear', 'South_Africa', 'http://example.com/proportions19', 'Strong', 'Fair', 'Fair', 'Fair', 'V', 'SI2', 3.5000, 'Elegant pear cut', 1),
('D020', 'Brilliant Sparkle', 'round', 'Botswana', 'http://example.com/proportions20', 'Very_Strong', 'Poor', 'Poor', 'Poor', 'W', 'I2', 1.9500, 'Sparkling brilliant cut', 1);
go
create table [DiamondPriceList] (
	[diamond_price_id] int identity(1,1),
	[origin] varchar(255),
	[carat_weight] decimal(19,4),
	[color] varchar(255),
	[clarity] varchar(255),
	[cut] varchar(255),
	[price] decimal(19,4),
	[effective_date] datetime
	primary key([diamond_price_id])
)
go
INSERT INTO [DiamondPriceList] 
([origin], [carat_weight], [color], [clarity], [cut], [price], [effective_date]) 
VALUES 
('Canada', 1.2000, 'D', 'FL', 'Excellent', 12000.00, GETDATE()),
('South_Africa', 0.7500, 'G', 'VVS1', 'Very_Good', 7500.00, GETDATE()),
('Botswana', 2.5000, 'J', 'SI1', 'Good', 25000.00, GETDATE()),
('Namibia', 1.0000, 'E', 'VS2', 'Fair', 10000.00, GETDATE()),
('Lesotho', 3.0000, 'H', 'I1', 'Poor', 3000.00, GETDATE()),
('Angola', 1.5000, 'I', 'IF', 'Very_Good', 15000.00, GETDATE()),
('South_Africa', 2.2000, 'F', 'VVS2', 'Excellent', 22000.00, GETDATE()),
('Botswana', 0.9000, 'K', 'VS1', 'Good', 9000.00, GETDATE()),
('Canada', 1.8000, 'L', 'SI2', 'Fair', 18000.00, GETDATE()),
('Namibia', 2.7500, 'M', 'I2', 'Poor', 2750.00, GETDATE()),
('Lesotho', 1.3500, 'N', 'FL', 'Excellent', 13500.00, GETDATE()),
('Angola', 0.6500, 'O', 'VVS1', 'Very_Good', 6500.00, GETDATE()),
('South_Africa', 2.0000, 'P', 'SI1', 'Good', 20000.00, GETDATE()),
('Botswana', 0.8000, 'Q', 'VS2', 'Fair', 8000.00, GETDATE()),
('Canada', 1.7000, 'R', 'I1', 'Poor', 1700.00, GETDATE()),
('Namibia', 2.4000, 'S', 'IF', 'Very_Good', 24000.00, GETDATE()),
('Lesotho', 1.1000, 'T', 'VVS2', 'Excellent', 11000.00, GETDATE()),
('Angola', 2.9000, 'U', 'VS1', 'Good', 29000.00, GETDATE()),
('South_Africa', 3.5000, 'V', 'SI2', 'Fair', 35000.00, GETDATE()),
('Botswana', 1.9500, 'W', 'I2', 'Poor', 1950.00, GETDATE());
GO
create table [ProductDiamond] (
	[product_id] int,
	[diamond_id] int,
	primary key([product_id], [diamond_id]),
	foreign key([product_id]) references [Product],
	foreign key([diamond_id]) references [Diamond]
)
go
create table [ProductDesign] (
	[product_design_id] int identity(1,1),
	[design_name] varchar(255),
	[design_type] varchar(255),
	primary key([product_design_id])
)
go
create table [ProductShellDesign] (
	[shell_id] int identity(1,1),
	[product_design_id] int,
	[shell_name] varchar(255),
	[diamond_quantity] int,
	[design_file] varchar(MAX),
	[e_diamond_price] decimal(19,4),
	[e_material_price] decimal(19,4),
	[production_price] decimal(19,4)
	primary key([shell_id]),
	foreign key([product_design_id]) references [ProductDesign]
)
go
create table [ProductShellMaterial] (
	[shell_id] int,
	[material_id] int,
	[weight] decimal(19,4),
	primary key([shell_id], [material_id]),
	foreign key([material_id]) references [Material],
	foreign key([shell_id]) references [ProductShellDesign]
)
go
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
