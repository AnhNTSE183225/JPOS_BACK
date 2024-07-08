def generate_link_for_query():
    shapes = ['Round','Princess','Cushion','Emerald','Asscher','Marquise','Oval','Radiant','Pear','Heart']
    carats = [0.15, 5.0, 8.0]
    lab_created = 'https://www.allurez.com/lab-created-loose-diamonds'
        
    result = []
    
    for shape in shapes:
        for carat in carats:
            result.append(f'https://www.allurez.com/loose-diamonds/?filterVal=yes&shape={shape}&fromCarat={carat}&toCarat=30.18&caratrange={carat}_30.18')
    result.append(lab_created)
    
    return result