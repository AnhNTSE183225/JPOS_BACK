from get_single_page import get_single_page

links = ['https://www.allurez.com/0.17-carat-round-very-good-cut-g-color-si1-clarity-diamond/gid/4458072']

for link in links:
    name, diamondCode, price, shape, caratWeight, color, clarity, cut, fluorescence, polish, symmetry, proportions = get_single_page(link)
    print(f"Name: {name}")
    print(f"Diamond Code: {diamondCode}")
    print(f"Price: {price}")
    print(f"Shape: {shape}")
    print(f"Carat Weight: {caratWeight}")
    print(f"Color: {color}")
    print(f"Clarity: {clarity}")
    print(f"Cut: {cut}")
    print(f"Fluorescence: {fluorescence}")
    print(f"Polish: {polish}")
    print(f"Symmetry: {symmetry}")
    print(f"Proportions: {proportions}")



