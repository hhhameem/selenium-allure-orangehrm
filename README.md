# Selenium Automation on Open Source OrangeHRM 

## Technologies Used:
  - Java
  - Selenium (4.6)
  - TestNG
  - Allure
  - Faker Library


## Flow Of Automation
  1. Visit the site: [Open Source OrangeHRM](https://opensource-demo.orangehrmlive.com/) .
  2. Assert the dashboard.
  3. Create 2 new employees.
  4. Search the employees with their Id and assert that 2 employees are found.
  5. Then login with the last employee credential.
  6. Update some employee info (e.g Nationality, Blood Group, License Expiry Date).
  7. Now go to my info page and assert the edited info.
  8. Finally logout.

## Test Cases For Automation
 - [Google Drive](https://docs.google.com/spreadsheets/d/1rV5zmZRtifpRC2xvnloNuAk0ri_6O85yqIyaYjh6kY0/edit?usp=sharing)

 ## Requirements
  - Java needs to be installed in device

 ## Video of Result
  - [Youtube](https://youtu.be/ZwJmDiy8qUg)

## Allure Report

![Allure Report Overview](/images/overview.png "Allure Report Overview")
![Allure Report Suites](/images/suites.png "Allure Report Suites")
![Allure Report Overview](/images/graphs.png "Allure Report Graph")