package org.example.seleniumScripts;

import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.ReCaptcha;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class TwoCaptchaSolverScript extends SolverCaptchaScript {

    private TwoCaptcha solver;

    public TwoCaptchaSolverScript(WebDriver driver) {
        super(driver);
        solver = new TwoCaptcha("2277c94a92955f560246de5a9ce9e16e");
    }

    @Override
    public void solveCaptcha() {

        ReCaptcha captcha = new ReCaptcha();
        captcha.setSiteKey(getTokenCaptcha("/html/body/app-root/div/div/app-login/section/div/div/mat-card/form/app-captcha-container/div/div/div/iframe"));
        captcha.setUrl("https://visa.vfsglobal.com/rus/ru/hun/login");
        captcha.setInvisible(true);
        captcha.setAction("verify");


        String code = "";
        try {
            String captchaId = solver.send(captcha);
            Thread.sleep(15000);
            System.out.println(captchaId);
            code = solver.getResult(captchaId);
            System.out.println(code);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement captchaArea = driver.findElement(By.id("g-recaptcha-response"));
        //captchaArea.sendKeys(code);
        WebElement button = driver.findElement(By.xpath("/html/body/app-root/div/div/app-login/section/div/div/mat-card/form/button"));

        javascriptExecutor.executeScript("arguments[0].value = '"+code+"';", captchaArea);

        javascriptExecutor.executeScript("function findRecaptchaClients() {\n" +
                "// eslint-disable-next-line camelcase\n" +
                "    if (typeof (___grecaptcha_cfg) !== 'undefined') {\n" +
                "// eslint-disable-next-line camelcase, no-undef\n" +
                "        return Object.entries(___grecaptcha_cfg.clients).map(([cid, client]) => {\n" +
                "            const data = { id: cid, version: cid >= 10000 ? 'V3' : 'V2' }\n" +
                "            const objects = Object.entries(client).filter(([_, value]) => value && typeof value === 'object')\n" +
                "\n" +
                "            objects.forEach(([toplevelKey, toplevel]) => {\n" +
                "                const found = Object.entries(toplevel).find(([_, value]) => (\n" +
                "                    value && typeof value === 'object' && 'sitekey' in value && 'size' in value\n" +
                "                ))\n" +
                "\n" +
                "                if (typeof toplevel === 'object' && toplevel instanceof HTMLElement && toplevel['tagName'] === 'DIV') {\n" +
                "                    data.pageurl = toplevel.baseURI\n" +
                "                }\n" +
                "\n" +
                "                if (found) {\n" +
                "                    const [sublevelKey, sublevel] = found\n" +
                "\n" +
                "                    data.sitekey = sublevel.sitekey\n" +
                "                    const callbackKey = data.version === 'V2' ? 'callback' : 'promise-callback'\n" +
                "                    const callback = sublevel[callbackKey]\n" +
                "                    if (!callback) {\n" +
                "                        data.callback = null\n" +
                "                        data.function = null\n" +
                "                    } else {\n" +
                "                        data.function = callback\n" +
                "                        const keys = [cid, toplevelKey, sublevelKey, callbackKey].map((key) => `['${key}']`).join('')\n" +
                "                        data.callback = `___grecaptcha_cfg.clients${keys}`\n" +
                "                    }\n" +
                "                }\n" +
                "            })\n" +
                "            return data\n" +
                "        })\n" +
                "\n" +
                "    }\n" +
                "    return []\n" +
                "}\n" +
                "\n" +
                "var massive = findRecaptchaClients();\n" +
                "var element = document.querySelector(\"body > app-root > div > div > app-login > section > div > div > mat-card > p\");\n" +
                "element.textContent = massive[0].callback;");

        WebElement text = driver.findElement(By.cssSelector("body > app-root > div > div > app-login > section > div > div > mat-card > p"));

        String textFunction = text.getText();

        while(!textFunction.contains("grecaptcha")){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            textFunction = text.getText();
        }


        javascriptExecutor.executeScript(textFunction+"('"+code+"');");
        javascriptExecutor.executeScript("arguments[0].click();" , button);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
