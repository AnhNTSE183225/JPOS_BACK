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
('user_admin','123',1,'admin'),
('user_customer_01','123',1,'customer'),
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
	+ customer (0)
	+ staff (1)
	+ admin (2)
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
create table [ProductShellDesign] (
	[product_shell_design_id] int identity(1,1),
	[shell_name] varchar(255),
	[diamond_quantity] int,
	[e_diamond_price] decimal(19,4),
	[e_material_price] decimal(19,4),
	[production_price] decimal(19,4)
	primary key([product_shell_design_id])
)
go
insert into [ProductShellDesign]([shell_name],[diamond_quantity],[e_diamond_price],[e_material_price],[production_price])
values
('Fall Collection Necklace Silver Shell',3,1230000,1000000,3000000),
('Winter Collection Necklace Gold Shell',4,2000000,1500000,4000000),
('Spring Collection Bracelet Silver Shell',2,1500000,1200000,3500000),
('Summer Collection Ring Gold Shell',1,1800000,1300000,3200000),
('Autumn Collection Earring Silver Shell',3,2100000,1600000,4200000),
('Holiday Collection Necklace Gold Shell',5,2200000,1700000,4500000),
('Valentine Collection Bracelet Gold Shell',2,2300000,1800000,4600000),
('Easter Collection Ring Silver Shell',1,2400000,1900000,4700000),
('Halloween Collection Earring Gold Shell',3,2500000,2000000,4800000),
('Thanksgiving Collection Necklace Silver Shell',4,2600000,2100000,4900000),
('Christmas Collection Bracelet Gold Shell',2,2700000,2200000,5000000),
('New Year Collection Ring Gold Shell',1,2800000,2300000,5100000),
('Anniversary Collection Earring Silver Shell',3,2900000,2400000,5200000),
('Birthday Collection Necklace Gold Shell',4,3000000,2500000,5300000),
('Mothers Day Collection Bracelet Silver Shell',2,3100000,2600000,5400000),
('Fathers Day Collection Ring Diamond Shell',1,3200000,2700000,5500000),
('Graduation Collection Earring Gold Shell',3,3300000,2800000,5600000),
('Wedding Collection Necklace Silver Shell',4,3400000,2900000,5700000),
('Engagement Collection Bracelet Gold Shell',2,3500000,3000000,5800000),
('Baby Shower Collection Ring Silver Shell',1,3600000,3100000,5900000),
('Housewarming Collection Earring Gold Shell',3,3700000,3200000,6000000)
go
create table [ProductDesign] (
	[product_design_id] int identity(1,1),
	[product_shell_design_id] int,
	[design_name] varchar(255),
	[design_type] varchar(255),
	primary key([product_design_id]),
	foreign key([product_shell_design_id]) references [ProductShellDesign]
)
go
insert into [ProductDesign]
values
(1, 'Fall Collection Necklace Silver', 'Necklace'),
(2, 'Winter Collection Necklace Gold', 'Necklace'),
(3, 'Spring Collection Bracelet Silver', 'Bracelet'),
(4, 'Summer Collection Ring Gold', 'Ring'),
(5, 'Autumn Collection Earring Silver', 'Earring'),
(6, 'Holiday Collection Necklace Gold', 'Necklace'),
(7, 'Valentine Collection Bracelet Gold', 'Bracelet'),
(8, 'Easter Collection Ring Silver', 'Ring'),
(9, 'Halloween Collection Earring Gold', 'Earring'),
(10, 'Thanksgiving Collection Necklace Silver', 'Necklace'),
(11, 'Christmas Collection Bracelet Gold', 'Bracelet'),
(12, 'New Year Collection Ring Gold', 'Ring'),
(13, 'Anniversary Collection Earring Silver', 'Earring'),
(14, 'Birthday Collection Necklace Gold', 'Necklace'),
(15, 'Mothers Day Collection Bracelet Silver', 'Bracelet'),
(16, 'Fathers Day Collection Ring Gold', 'Ring'),
(17, 'Graduation Collection Earring Gold', 'Earring'),
(18, 'Wedding Collection Necklace Silver', 'Necklace'),
(19, 'Engagement Collection Bracelet Gold', 'Bracelet'),
(20, 'Baby Shower Collection Ring Silver', 'Ring'),
(21, 'Housewarming Collection Earring Gold', 'Earring')
go
create table [Product] (
	[product_id] int identity(1,1),
	[product_name] varchar(255),
	[e_diamond_price] decimal(19,4),
	[e_material_price] decimal(19,4),
	[production_price] decimal(19,4),
	[markup_rate] decimal(19,4),
	[product_type] varchar(255),
	[product_design_id] int,
	primary key([product_id]),
	foreign key([product_design_id]) references [ProductDesign]
)
go
insert into [Product]
values
('Diamond Necklace 1', 2000000, 3000000, 4000000, 10, 'Necklace', NULL),
('Diamond Ring 1', 2500000, 3500000, 4500000, 15, 'Ring', NULL),
('Diamond Bracelet 1', 3000000, 4000000, 5000000, 20, 'Bracelet', NULL),
('Diamond Earring 1', 3500000, 4500000, 5500000, 25, 'Earring', NULL),
('Diamond Necklace 2', 4000000, 5000000, 6000000, 30, 'Necklace', NULL),
('Diamond Ring 2', 4500000, 5500000, 6500000, 35, 'Ring', NULL),
('Diamond Bracelet 2', 5000000, 6000000, 7000000, 40, 'Bracelet', NULL),
('Diamond Earring 2', 5500000, 6500000, 7500000, 45, 'Earring', NULL),
('Diamond Necklace 3', 6000000, 7000000, 8000000, 50, 'Necklace', NULL),
('Diamond Ring 3', 6500000, 7500000, 8500000, 55, 'Ring', NULL),
('Diamond Necklace 4', 6000000, 7000000, 8000000, 50, 'Necklace', 18),
('Diamond Ring 4', 6500000, 7500000, 8500000, 55, 'Ring', 20),
('Diamond Bracelet 3', 5000000, 6000000, 7000000, 40, 'Bracelet', NULL),
('Diamond Necklace 5', 6000000, 7000000, 8000000, 50, 'Necklace', 18),
('Diamond Ring 6', 6500000, 7500000, 8500000, 55, 'Ring', 20)
go
create table [Order] (
	[order_id] int identity(1,1),
	[customer_id] int,
	[product_id] int,
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
	[model_file] varchar(255),
	[model_feedback] varchar(255),
	[product_image] varchar(255),
	[shipping_fee] decimal(19,4),
	[tax_fee] decimal(19,4),
	[discount] decimal(19,4),
	primary key([order_id]),
	foreign key([product_id]) references [Product],
	foreign key([customer_id]) references [Customer]
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
INSERT INTO [Order]([customer_id],[product_id],[status],[order_date],[order_type],[budget],[design_file],[description],[q_diamond_price],[q_material_price],[q_date],[o_diamond_price],[o_material_price],[o_date],[model_file],[model_feedback],[product_image],[shipping_fee],[tax_fee],[discount])
VALUES
(1, null, 'wait_sale_staff', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(1, 2, 'wait_manager', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(1, 3, 'manager_approved', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(1, 4, 'wait_customer', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(1, 5, 'customer_accept', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', 4000000, 5000000, '2024-05-29 15:16:00', NULL, NULL, NULL, 1000000, 2000000, 10),
(1, 6, 'designing', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', 4000000, 5000000, '2024-05-29 15:16:00', NULL, NULL, NULL, 1000000, 2000000, 10),
(1, 7, 'designing', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', 4000000, 5000000, '2024-05-29 15:16:00', 'old_model_1', 'it is too ugly', NULL, 1000000, 2000000, 10),
(1, 8, 'pending_design', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', 4000000, 5000000, '2024-05-29 15:16:00', 'model_file_1', NULL, NULL, 1000000, 2000000, 10),
(1, 13, 'pending_payment', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', 4000000, 5000000, '2024-05-29 15:16:00', 'model_file_1', NULL, NULL, 1000000, 2000000, 10),
(1, 1, 'payment_confirmed', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', 4000000, 5000000, '2024-05-29 15:16:00', 'model_file_1', NULL, NULL, 1000000, 2000000, 10),
(1, 9, 'production', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', 4000000, 5000000, '2024-05-29 15:16:00', 'model_file_1', 'all good', NULL, 1000000, 2000000, 10),
(1, 14, 'pending_payment', '2024-05-27 15:16:00', 'from_design', NULL, NULL, NULL, NULL, NULL, NULL, 5500000, 6500000, '2024-05-27 15:16:00', NULL, NULL, NULL, 1300000, 2300000, 40),
(1, 15, 'payment_confirmed', '2024-05-27 15:16:00', 'from_design', NULL, NULL, NULL, NULL, NULL, NULL, 5500000, 6500000, '2024-05-27 15:16:00', NULL, NULL, NULL, 1300000, 2300000, 40),
(1, 11, 'production', '2024-05-27 15:16:00', 'from_design', NULL, NULL, NULL, NULL, NULL, NULL, 5500000, 6500000, '2024-05-27 15:16:00', NULL, NULL, NULL, 1300000, 2300000, 40),
(1, 12, 'completed', '2024-05-27 15:16:00', 'from_design', NULL, NULL, NULL, NULL, NULL, NULL, 6000000, 7000000, '2024-05-27 15:16:00', NULL, NULL, 'product_image_1', 1400000, 2400000, 50),
(1, 10, 'completed', '2024-05-27 15:16:00', 'customize', '5000000', 'design_file_1', 'description_1', 2500000, 3500000, '2024-05-28 15:16:00', 4000000, 5000000, '2024-05-29 15:16:00', 'model_file_1', 'all good', 'completed_product_image_1', 1000000, 2000000, 10)
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
insert into [Payment]([order_id],[payment_date],[payment_method],[payment_status],[amount_paid],[amount_total])
values
(5, '2024-05-30 15:16:00', 'Credit Card', 'Paid', 4000000, 5000000),
(6, '2024-05-31 15:16:00', 'Debit Card', 'Paid', 4000000, 5000000),
(7, '2024-06-01 15:16:00', 'Bank Transfer', 'Pending', NULL, 5000000),
(8, '2024-06-02 15:16:00', 'Credit Card', 'Paid', 4000000, 5000000),
(9, '2024-06-03 15:16:00', 'Debit Card', 'Paid', 4000000, 5000000),
(10, '2024-06-04 15:16:00', 'Bank Transfer', 'Paid', 4000000, 5000000),
(11, '2024-06-05 15:16:00', 'Credit Card', 'Pending', NULL, 6500000),
(12, '2024-06-06 15:16:00', 'Debit Card', 'Paid', 6000000, 7000000)
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
insert into [Warranty]([customer_id],[product_id],[purchase_date],[end_of_support_date],[terms])
values
(1,5,'2024-05-29 15:16:00','2028-05-29 15:16:00','terms_1'),
(1,6,'2024-05-29 15:16:00','2028-05-29 15:16:00','terms_1'),
(1,7,'2024-05-29 15:16:00','2028-05-29 15:16:00','terms_1'),
(1,8,'2024-05-29 15:16:00','2028-05-29 15:16:00','terms_1'),
(1,9,'2024-05-29 15:16:00','2028-05-29 15:16:00','terms_1'),
(1,10,'2024-05-29 15:16:00','2028-05-29 15:16:00','terms_1'),
(1,11,'2024-05-27 15:16:00','2028-05-27 15:16:00','terms_1'),
(1,12,'2024-05-27 15:16:00','2028-05-27 15:16:00','terms_1')
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
INSERT INTO [ProductMaterial] ([product_id], [material_id], [weight])
VALUES
(1, 1, 10), -- Diamond Necklace 1
(1, 2, 5), -- Diamond Necklace 1
(2, 3, 8), -- Diamond Ring 1
(3, 4, 12), -- Diamond Bracelet 1
(4, 5, 15), -- Diamond Earring 1
(5, 6, 10), -- Diamond Necklace 2
(6, 7, 8), -- Diamond Ring 2
(7, 1, 12), -- Diamond Bracelet 2
(8, 2, 15), -- Diamond Earring 2
(9, 3, 10), -- Diamond Necklace 3
(10, 4, 8), -- Diamond Ring 3
(11, 7, 10), -- Diamond Necklace 4
(12, 7, 8) -- Diamond Ring 4
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
create table [ProductShellMaterial] (
	[product_shell_design_id] int,
	[material_id] int,
	[weight] decimal(19,4),
	primary key([product_shell_design_id], [material_id]),
	foreign key([material_id]) references [Material],
	foreign key([product_shell_design_id]) references [ProductShellDesign]
)
go
INSERT INTO [ProductShellMaterial]
VALUES
(1, 1, 1.2),
(2, 2, 1.5),
(3, 7, 1.3),
(4, 1, 1.1),
(5, 7, 1.4),
(6, 2, 1.6),
(7, 1, 1.2),
(8, 7, 1.3),
(9, 2, 1.4),
(10, 7, 1.5),
(11, 1, 1.2),
(12, 2, 1.3),
(13, 7, 1.4),
(14, 1, 1.5),
(15, 7, 1.2),
(16, 2, 1.3),
(17, 1, 1.4),
(18, 7, 1.5),
(19, 1, 1.2),
(20, 7, 1.3),
(21, 2, 1.4)
go
create table [Diamond] (
	[diamond_id] int identity(1,1),
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
	
	[active] bit,
	primary key([diamond_id])
)
/*
	shape: round, princess, cushion, emerald, oval, radiant, asscher, marquise, heart, pear
	origin: Canada, Angola, Namibia, Botswana, Lesotho, South Africa, 
	proportions: image link
	fluorescence: None, Faint, Medium, Strong, Very Strong
	symmetry: Poor, Fair, Good, Very Good, Excellent
	polish: Poor, Fair, Good, Very Good, Excellent
	cut: Poor, Fair, Good, Very Good, Excellent
	color: Z,Y,X,W,V,U,T,S,R,Q,P,O,N,M,L,K,J,I,H,G,F,E,D
	clarity: I3, I2, I1, SI2, SI1, VS2, VS1, VVS2, VVS1, IF, FL
	carat_weight: 0 to 30
*/
go
insert into [Diamond] ([diamond_name],[shape],[origin],[proportions],[fluorescence],
[symmetry],[polish],[cut],[color],[clarity],[carat_weight],[active])
values
('1.01 Carat Round Diamond','round','Canada','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Strong','Excellent','Excellent','Excellent','H','VS2',1.01,1),
('1.0 Carat Round Diamond','round','Canada','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','None','Excellent','Excellent','Excellent','D','FL',1.0,1),
('1.0 Carat Princess Diamond','princess','Angola','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Faint','Very Good','Very Good','Very Good','E','IF',1.0,1),
('1.0 Carat Cushion Diamond','cushion','Namibia','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Medium','Good','Good','Good','F','VVS1',1.0,0),
('1.0 Carat Emerald Diamond','emerald','Botswana','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Strong','Fair','Fair','Fair','G','VVS2',1.0,0),
('1.0 Carat Oval Diamond','oval','Lesotho','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Very Strong','Poor','Poor','Poor','H','VS1',1.0,0),
('1.0 Carat Radiant Diamond','radiant','South Africa','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','None','Excellent','Excellent','Excellent','I','VS2',1.0,1),
('1.0 Carat Asscher Diamond','asscher','Canada','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Faint','Very Good','Very Good','Very Good','J','SI1',1.0,1),
('1.0 Carat Marquise Diamond','marquise','Angola','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Medium','Good','Good','Good','K','SI2',1.0,0),
('1.0 Carat Heart Diamond','heart','Namibia','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Strong','Fair','Fair','Fair','L','I1',1.0,0),
('1.0 Carat Pear Diamond','pear','Botswana','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Very Strong','Poor','Poor','Poor','M','I2',1.0,0),
('1.0 Carat Round Diamond','round','Lesotho','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','None','Excellent','Excellent','Excellent','N','I3',1.0,1),
('1.0 Carat Princess Diamond','princess','South Africa','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Faint','Very Good','Very Good','Very Good','O','FL',1.0,1),
('1.0 Carat Cushion Diamond','cushion','Canada','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Medium','Good','Good','Good','P','IF',1.0,0),
('1.0 Carat Emerald Diamond','emerald','Angola','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Strong','Fair','Fair','Fair','Q','VVS1',1.0,0),
('1.0 Carat Oval Diamond','oval','Namibia','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Very Strong','Poor','Poor','Poor','R','VVS2',1.0,0),
('1.0 Carat Radiant Diamond','radiant','Botswana','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','None','Excellent','Excellent','Excellent','S','VS1',1.0,1),
('1.0 Carat Asscher Diamond','asscher','Lesotho','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Faint','Very Good','Very Good','Very Good','T','VS2',1.0,1),
('1.0 Carat Marquise Diamond','marquise','South Africa','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Medium','Good','Good','Good','U','SI1',1.0,0),
('1.0 Carat Heart Diamond','heart','Canada','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Strong','Fair','Fair','Fair','V','SI2',1.0,0),
('1.0 Carat Pear Diamond','pear','Angola','https://4cs.gia.edu/wp-content/uploads/2014/04/Proportions.jpg','Very Strong','Poor','Poor','Poor','W','I1',1.0,0);
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
insert into [DiamondPriceList]([origin],[carat_weight],[color],[clarity],[cut],[price],[effective_date])
values
('Canada',1.01,'D','IF','Excellent',182200000,'2024-05-27 15:16:00'),
('Canada',1.00,'E','IF','Excellent',175500000,'2024-05-27 15:16:00'),
('Angola',1.02,'F','VVS1','Very Good',185000000,'2024-05-27 15:16:00'),
('Namibia',1.03,'G','VVS2','Good',190000000,'2024-05-27 15:16:00'),
('Botswana',1.04,'H','VS1','Fair',195000000,'2024-05-27 15:16:00'),
('Lesotho',1.05,'I','VS2','Poor',200000000,'2024-05-27 15:16:00'),
('South Africa',1.06,'J','SI1','Excellent',205000000,'2024-05-27 15:16:00'),
('Canada',1.07,'K','SI2','Very Good',210000000,'2024-05-27 15:16:00'),
('Angola',1.08,'L','I1','Good',215000000,'2024-05-27 15:16:00'),
('Namibia',1.09,'M','I2','Fair',220000000,'2024-05-27 15:16:00'),
('Botswana',1.10,'N','I3','Poor',225000000,'2024-05-27 15:16:00'),
('Lesotho',1.11,'D','FL','Excellent',230000000,'2024-05-27 15:16:00'),
('South Africa',1.12,'E','IF','Very Good',235000000,'2024-05-27 15:16:00'),
('Canada',1.13,'F','VVS1','Good',240000000,'2024-05-27 15:16:00'),
('Angola',1.14,'G','VVS2','Fair',245000000,'2024-05-27 15:16:00'),
('Namibia',1.15,'H','VS1','Poor',250000000,'2024-05-27 15:16:00'),
('Botswana',1.16,'I','VS2','Excellent',255000000,'2024-05-27 15:16:00'),
('Lesotho',1.17,'J','SI1','Very Good',260000000,'2024-05-27 15:16:00'),
('South Africa',1.18,'K','SI2','Good',265000000,'2024-05-27 15:16:00'),
('Canada',1.19,'L','I1','Fair',270000000,'2024-05-27 15:16:00'),
('Angola',1.20,'M','I2','Poor',275000000,'2024-05-27 15:16:00'),
('Namibia',1.21,'N','I3','Excellent',280000000,'2024-05-27 15:16:00')
go
create table [ProductDiamond] (
	[product_id] int,
	[diamond_id] int,
	primary key([product_id], [diamond_id]),
	foreign key([product_id]) references [Product],
	foreign key([diamond_id]) references [Diamond]
)
go
insert into [ProductDiamond] ([product_id], [diamond_id])
values
(1, 1), -- Diamond Necklace 1
(2, 2), -- Diamond Ring 1
(3, 3), -- Diamond Bracelet 1
(4, 4), -- Diamond Earring 1
(5, 5), -- Diamond Necklace 2
(6, 6), -- Diamond Ring 2
(7, 7), -- Diamond Bracelet 2
(8, 8), -- Diamond Earring 2
(9, 9), -- Diamond Necklace 3
(10, 10), -- Diamond Ring 3
(11, 11), -- Diamond Necklace 4
(11, 12), -- Diamond Necklace 4
(11, 13), -- Diamond Necklace 4
(11, 14), -- Diamond Necklace 4
(12, 15) -- Diamond Ring 4
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