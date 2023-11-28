package org.example.seleniumScripts;

import org.example.entities.User;
import org.example.exceptions.ResponseException;
import org.example.jsonMethods.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.YearMonth;


public class DriverScriptRequest extends DriverScripts{
    public DriverScriptRequest(WebDriver driver) {
        super(driver);
    }

    private WebElement responseTextField;

    public void setResponseTextField(String xpath) {
        responseTextField = driver.findElement(By.xpath(xpath));
    }

    public String checkIsSlotAvailable(User user){


        javascriptExecutor.executeScript(
                "var url = 'https://lift-api.vfsglobal.com/appointment/CheckIsSlotAvailable';\n" +
                "const data = {\n" +
                "  countryCode: \"rus\",\n" +
                "  loginUser: sessionStorage.getItem(\"logged_email\"),\n" +
                "  missionCode: \"fra\",\n" +
                "  payCode: \"\",\n" +
                "  roleName: \"Individual\",\n" +
                "  vacCode: \""+user.getCity().toString()+"\",\n" +
                "  visaCategoryCode: \""+user.getVisaCategory()+"\"\n" +
                "};\n" +
                "\n" +
                "const options = {\n" +
                "  method: 'POST',\n" +
                "  headers: {\n" +
                "    'Content-Type': 'application/json;charset=UTF-8',\n" +
                "    'Origin' : 'https://visa.vfsglobal.com',\n" +
                "    'Accept': 'application/json, text/plain, */*',\n" +
                "    'Authorize': sessionStorage.getItem(\"JWT\")\n" +
                "  },\n" +
                "  body: JSON.stringify(data)\n" +
                "};\n" +
                "item = document.querySelector('#mat-tab-content-0-0 > div > div');\n" +
                "fetch(url, options)\n" +
                "  .then(response =>  response.text())\n" +
                "  .then(data => {\n" +
                "  item.textContent = data;\n" +
                "  })\n" +
                "  .catch(error => {\n" +
                "    console.error('Ошибка:', error);\n" +
                "  });\n"+
                "");

        String response = responseTextField.getText();


        int i = 0;
        while(!response.contains("earliestDate")){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            response = responseTextField.getText();

            if(i > 10){
                response = null;
                break;
            }
            i++;
        }


        return response;
    }

    //https://lift-api.vfsglobal.com/appointment/slots?countryCode=rus&missionCode=hun&centerCode=Kazan&loginUser=tolik.baldin.79%40mail.ru&visaCategoryCode=MED&languageCode=ru-RU&applicantsCount=1&days=180&fromDate=2%2F10%2F2023&slotType=2&toDate=30%2F10%2F2023&urn=HUN23951680733&payCode=
    public String getAvailableSlots(LocalDate date, User user, String urn){
        LocalDate endOfMonth = YearMonth.now().atEndOfMonth();

        //'&slotType=2&toDate="+now.getDayOfMonth()+"%2F"+now.getMonthValue()+"%2F"+now.getYear()+"' +\n" +
        String url2 = "https://lift-api.vfsglobal.com/appointment/slots?' +\n" +
                "        'countryCode=rus&' +\n" +
                "        'missionCode=fra' +\n" +
                "        '&centerCode="+user.getCity()+"' +\n" +
                "        '&loginUser='+sessionStorage.getItem(\"logged_email\")+\n" +
                "        '&visaCategoryCode=" + user.getVisaCategory() +"' +\n" +
                "        '&languageCode=en-US' +\n" +
                "        '&applicantsCount=1' +\n" +
                "        '&days=180' +\n" +
                "        '&fromDate="+date.getDayOfMonth()+"%2F"+date.getMonthValue()+"%2F2023' +\n" +
                "        '&slotType=2&toDate="+endOfMonth.getDayOfMonth()+"%2F"+endOfMonth.getMonthValue()+"%2F"+endOfMonth.getYear()+"' +\n" +
                "        '&urn=MOS28116988612' +\n" +
                "        '&payCode=";

        javascriptExecutor.executeScript("var url = '"+url2 +"';\n" +
                "var xhr = new XMLHttpRequest();\n" +
                "\n" +
                "xhr.open('GET', url, true);\n" +
                "var objects = document.getElementsByClassName('ng-star-inserted');\n" +
                "xhr.setRequestHeader('Content-Type', 'application/json');\n" +
                "xhr.setRequestHeader('Origin', 'https://visa.vfsglobal.com');\n" +
                "xhr.setRequestHeader('Accept', 'application/json, text/plain, */*');\n" +
                "xhr.setRequestHeader('Authorization', sessionStorage.getItem(\"JWT\"));\n" +
                "xhr.setRequestHeader('Content-Type', 'application/json;charset=utf-8');\n" +
                "item = document.querySelector('#mat-tab-content-0-0 > div > div');\n" +
                "xhr.onload = function() {\n" +
                "  if (xhr.status === 200) {\n" +
                "    item.textContent =  xhr.responseText;\n" +
                "  }\n" +
                "  else{\n" +
                "    item.textContent = xhr.status;\n" +
                "}\n" +
                "};\n" +
                "\n" +
                "xhr.send();"
                );

        String response = responseTextField.getText();

        int i = 0;

        while(!response.contains("mission")){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            response = responseTextField.getText();
            if(i > 10){
                response = null;
                break;
            }
            i++;
        }


        return response;
    }


    public String sendApplicant(User user, String email){

        javascriptExecutor.executeScript(
                "var url = 'https://lift-api.vfsglobal.com/appointment/applicants';\n" +
                        "const data = "+ JsonParser.formApplicantsJson(user, email) +";\n" +
                        "\n" +
                        "const options = {\n" +
                        "  method: 'POST',\n" +
                        "  headers: {\n" +
                        "    'Content-Type': 'application/json;charset=UTF-8',\n" +
                        "    'Origin' : 'https://visa.vfsglobal.com',\n" +
                        "    'Accept': 'application/json, text/plain, */*',\n" +
                        "    'Authorize': sessionStorage.getItem(\"JWT\")\n" +
                        "  },\n" +
                        "  body: JSON.stringify(data)\n" +
                        "};\n" +
                        "item = document.querySelector('#mat-tab-content-0-0 > div > div');\n" +
                        "fetch(url, options)" +
                        ".then(response => response.text())\n" +
                        ".then(data => {\n" +
                        "    item.textContent = data;\n" +
                        "  })\n" +
                        "  .catch(error => {\n" +
                        "    console.error('Ошибка:', error);\n" +
                        "  });");




        String response = responseTextField.getText();


        int i = 0;
        while(!response.contains("applicantList")){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            response = responseTextField.getText();
            if(i > 10){
                response = null;
                break;
            }
            i++;
        }


        return response;
    }


    public String mapvasCounter(String urn) throws ResponseException {

        javascriptExecutor.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");




        javascriptExecutor.executeScript("const data1 = {\n" +
                "    \"loginuser\": sessionStorage.getItem(\"logged_email\"),\n" +
                "    \"missioncode\": \"fra\",\n" +
                "    \"countrycode\": \"rus\",\n" +
                "    \"urn\": \""+urn+"\",\n" +
                "    \"applicants\": [\n" +
                "    {\n" +
                "        \"arn\": \""+urn+"\"+\"/1\",\n" +
                "        \"vasId\": \"201\",\n" +
                "        \"vasCode\": \"SF\",\n" +
                "        \"courierType\": null\n" +
                "    }\n" +
                "    ]\n" +
                "};\n" +
                "const url = \"https://lift-api.vfsglobal.com/vas/mapvas\";\n" +
                "var xhr = new XMLHttpRequest();\n" +
                "xhr.open(\"POST\", url, true);\n" +
                "xhr.setRequestHeader('Content-Type', 'application/json');\n" +
                "xhr.setRequestHeader('Origin', 'https://visa.vfsglobal.com');\n" +
                "xhr.setRequestHeader('Accept', 'application/json, text/plain, */*');\n" +
                "xhr.setRequestHeader('Authorization', sessionStorage.getItem(\"JWT\"));\n" +
                "xhr.setRequestHeader('Content-Type', 'application/json;charset=utf-8');\n" +
                "item = document.querySelector('#mat-tab-content-0-0 > div > div');\n" +
                "xhr.onreadystatechange = function() {\n" +
                "    if (xhr.readyState === 4 && xhr.status === 200) {\n" +
                "        var data = xhr.responseText;\n" +
                "        item.textContent = data;\n" +
                "    }\n" +
                "};\n" +
                "\n" +
                "var jsonData = JSON.stringify(data1);\n" +
                "\n" +
                "xhr.send(jsonData);");



        String response = responseTextField.getText();

        int j = 0;
        while(!response.contains("amount")){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            response = responseTextField.getText();
            if(j>5) throw new ResponseException();
            j++;
        }


        return response;
    }

    public String scheduleBooking(int allocationId, String urn, User user, int amount){
        javascriptExecutor.executeScript(
                "var url = 'https://lift-api.vfsglobal.com/appointment/schedule';\n" +
                        "const data = {\n" +
                        "    \"missionCode\": \"fra\",\n" +
                        "    \"countryCode\": \"rus\",\n" +
                        "    \"centerCode\": \""+user.getCity()+"\",\n" +
                        "    \"loginUser\": sessionStorage.getItem(\"logged_email\"),\n" +
                        "    \"urn\": \""+urn+"\",\n" +
                        "    \"notificationType\": \"none\",\n" +
                        "    \"paymentdetails\": {\n" +
                        "        \"paymentmode\": \"Online\",\n" +
                        "        \"RequestRefNo\": \"\",\n" +
                        "        \"clientId\": \"\",\n" +
                        "        \"merchantId\": \"\",\n" +
                        "        \"amount\": "+amount+",\n" +
                        "        \"currency\": \"RUB\"\n" +
                        "    },\n" +
                        "    \"allocationId\": \""+allocationId+"\",\n" +
                        "    \"CanVFSReachoutToApplicant\": false\n" +
                        "};\n" +
                        "\n" +
                        "const options = {\n" +
                        "  method: 'POST',\n" +
                        "  headers: {\n" +
                        "    'Content-Type': 'application/json;charset=UTF-8',\n" +
                        "    'Origin' : 'https://visa.vfsglobal.com',\n" +
                        "    'Accept': 'application/json, text/plain, */*',\n" +
                        "    'Authorize': sessionStorage.getItem(\"JWT\")\n" +
                        "  },\n" +
                        "  body: JSON.stringify(data)\n" +
                        "};\n" +
                        "item = document.querySelector('#mat-tab-content-0-0 > div > div');\n" +
                        "fetch(url, options)\n" +
                        "  .then(response =>  response.text())\n" +
                        "  .then(data => {\n" +
                        "  item.textContent = data;\n" +
                        "  })\n" +
                        "  .catch(error => {\n" +
                        "    console.error('Ошибка:', error);\n" +
                        "  });");

        String response = responseTextField.getText();

        while(!response.contains("IsAppointmentBooked")){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            response = responseTextField.getText();
        }


        return response;
    }


}
