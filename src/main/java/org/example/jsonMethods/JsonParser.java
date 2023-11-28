package org.example.jsonMethods;

import org.example.testclass.Payment;
import org.example.entities.Proxy;
import org.example.entities.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonParser {

    public static int findAllocationId(String jsonSlots, int number, int number_time) {
        try {
        return new JSONArray(jsonSlots).getJSONObject(number).getJSONArray("counters")
                .getJSONObject(0).getJSONArray("groups").getJSONObject(0).getJSONArray("timeSlots")
                .getJSONObject(number_time).getInt("allocationId");
        }
        catch (JSONException e){
            return -1;
        }
    }

    public static List<Integer> findAllocationIdsList(String jsonSlots){
        List<Integer> integerList = new LinkedList<>();
        int i = 0;
        int k = 0;
        int count = 0;
        int allocationId = findAllocationId(jsonSlots,0, 0);

        while(allocationId != -1){
            while(allocationId != -1) {
                integerList.add(count++, allocationId);
                allocationId = findAllocationId(jsonSlots, i, k++);
            }
            i++;
        }
        return allocationId != -1 ? integerList : null;
    }


    public static String findUrn(String jsonApplicants){
        return new JSONObject(jsonApplicants).getString("urn");
    }

    public static boolean IsPaymentRequired(String jsonSchedule){
        return new JSONObject(jsonSchedule).getBoolean("IsPaymentRequired");
    }

    public static Payment parsePayment(String jsonSchedule){
        JSONObject jsonPayment = new JSONObject(jsonSchedule);
        return new Payment(jsonPayment.getInt("RequestRefNo"), jsonPayment.getString("DigitalSignature"));
    }

    public static List<Proxy> getProxiesFromJson(String jsonProxies){
        List<Proxy> proxies = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonProxies);
        int i = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject proxyJson = jsonArray.optJSONObject(i++);
        while (proxyJson != null){
            proxies.add(new Proxy(proxyJson.getInt("id"),
                    proxyJson.getString("http"), LocalDate.parse(proxyJson.getString("expire"), formatter)));
            proxyJson = jsonArray.optJSONObject(i++);
        }
        return proxies;
    }


    public static List<Proxy> getProxiesMarketFromJson(String jsonProxies){
        List<Proxy> proxies = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonProxies);
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject("list").getJSONArray("data"));
        int i = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject proxyJson = jsonArray.optJSONObject(i++);
        while (proxyJson != null){
            proxies.add(new Proxy(proxyJson.getInt("id"),
                    proxyJson.getString("ip") +":"+ proxyJson.getString("http_port"), LocalDate.parse(proxyJson.getString("expired_at"), formatter)));
            proxyJson = jsonArray.optJSONObject(i++);
        }
        return proxies;
    }

    public static int getAmount(String jsonMapvas){
        return new JSONObject(jsonMapvas).getInt("amount");
    }
    public static String formApplicantsJson(User user, String email){
        return "{\n" +
                "  \"countryCode\": \"rus\",\n" +
                "  \"missionCode\": \"fra\",\n" +
                "  \"centerCode\": \""+user.getCity() +"\",\n" +
                "  \"loginUser\": \""+email+"\",\n" +
                "  \"visaCategoryCode\": \""+user.getVisaCategory()+"\",\n" +
                "  \"isEdit\": false,\n" +
                "  \"feeEntryTypeCode\": null,\n" +
                "  \"feeExemptionTypeCode\": null,\n" +
                "  \"feeExemptionDetailsCode\": null,\n" +
                "  \"applicantList\": [\n" +
                "    {\n" +
                "      \"urn\": \"\",\n" +
                "      \"arn\": \"\",\n" +
                "      \"loginUser\": \""+email+"\",\n" +
                "      \"firstName\": \""+user.getFirstName()+"\",\n" +
                "      \"employerFirstName\": \"\",\n" +
                "      \"middleName\": \"\",\n" +
                "      \"lastName\": \""+user.getLastName()+"\",\n" +
                "      \"employerLastName\": \"\",\n" +
                "      \"salutation\": \"\",\n" +
                "      \"gender\": 1,\n" +
                "      \"nationalId\": null,\n" +
                "      \"VisaToken\": null,\n" +
                "      \"employerContactNumber\": \"\",\n" +
                "      \"contactNumber\": \""+user.getContactNumber()+"\",\n" +
                "      \"dialCode\": \""+user.getDialCode()+"\",\n" +
                "      \"employerDialCode\": \"\",\n" +
                "      \"passportNumber\": \""+user.getPassportNumber()+"\",\n" +
                "      \"confirmPassportNumber\": \"\",\n" +
                "      \"passportExpirtyDate\": \"16/09/2027\",\n" +
                "      \"dateOfBirth\": \"25/09/1986\",\n" +
                "      \"emailId\": \""+user.getEmail()+"\",\n" +
                "      \"employerEmailId\": \"\",\n" +
                "      \"nationalityCode\": \""+user.getNationality()+"\",\n" +
                "      \"state\": null,\n" +
                "      \"city\": null,\n" +
                "      \"isEndorsedChild\": false,\n" +
                "      \"applicantType\": 0,\n" +
                "      \"addressline1\": null,\n" +
                "      \"addressline2\": null,\n" +
                "      \"pincode\": null,\n" +
                "      \"referenceNumber\": null,\n" +
                "      \"vlnNumber\": null,\n" +
                "      \"applicantGroupId\": 0,\n" +
                "      \"parentPassportNumber\": \"\",\n" +
                "      \"parentPassportExpiry\": \"\",\n" +
                "      \"dateOfDeparture\": null,\n" +
                "      \"gwfNumber\": \"\",\n" +
                "      \"entryType\": \"\",\n" +
                "      \"eoiVisaType\": \"\",\n" +
                "      \"passportType\": \"\",\n" +
                "      \"vfsReferenceNumber\": \"\",\n" +
                "      \"familyReunificationCerificateNumber\": \"\",\n" +
                "      \"ipAddress\": sessionStorage.getItem(\"ip\")\n" +
                "    }\n" +
                "  ],\n" +
                "  \"languageCode\": \"en-US\",\n" +
                "  \"isWaitlist\": false\n" +
                "}";
    }
}
