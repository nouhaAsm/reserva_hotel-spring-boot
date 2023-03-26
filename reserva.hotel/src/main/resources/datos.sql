INSERT INTO public.hotels (name,category) VALUES
	 ('Hanok GuesthouseOpens', 1),
	 ('Leega HanokOpens', 2),
	 ('SanminjaeOpens', 3),
	 ('Dowon Guesthouse', 4),
	 ('The Blue CradleOpens', 5);

INSERT INTO public.availabilities (date_av,id_hotel,rooms) VALUES
	 ('2023/03/24', 1 , 4),
	 ('2023/03/25', 1 , 4),
	 ('2023/03/26', 1 , 4),
	 ('2023/03/27', 1 , 4),
	 ('2023/03/28', 1 , 3),
	 ('2023/03/27', 2 , 5),
	 ('2023/03/28', 2 , 5);
	 
INSERT INTO public.bookings (date_from,date_to,email,id_hotel) VALUES
	 ('2023/03/26', '2023/03/26', 'test_booking1@gmail.com' , 1),
	 ('2023/03/27', '2023/03/28', 'test_booking1@gmail.com' , 2);