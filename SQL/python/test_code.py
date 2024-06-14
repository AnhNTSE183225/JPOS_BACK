from requests_html import HTMLSession

report_no = "2474506136"
session = HTMLSession()

r = session.get(f'https://www.gia.edu/report-check?reportno={report_no}')
r.html.render()  # This line will execute JavaScript code on the page
element = r.html.find('a[href^="https://pdf.gia.edu/"]', first=False)

print(element)
