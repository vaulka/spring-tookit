package com.vaulka.kit.sushi;

import com.alibaba.fastjson.JSON;
import com.vaulka.kit.desensitization.utils.DesensitizationUtils;
import com.vaulka.kit.watermark.enums.WatermarkPosition;
import com.vaulka.kit.watermark.model.text.impl.SingleTextRenderStyle;
import com.vaulka.kit.watermark.utils.text.impl.ImageSingleTextWatermarkUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vaulka
 */
public class SushiUtilsTest {

    private static final List<com.vaulka.kit.sushi.Account> ACCOUNTS = Arrays.asList(

    );

    private final File FILE = new File("src/test/resources/balanceTemplate.png");

    /**
     * 全屏图片水印
     */
    private final ImageSingleTextWatermarkUtils WATERMARK_UTILS = new ImageSingleTextWatermarkUtils();

    @Test
    public void sushi() throws IOException, InterruptedException {
        List<Account> canEats = new ArrayList<>(ACCOUNTS.size());
        for (Account account : ACCOUNTS) {
            String token = getToken(account.getAccount(), account.getPassword());
            if (token == null) {
                String message = "设备【{0}】账号【{1}】登录失败";
                System.out.println(MessageFormat.format(message, account.getDeviceName(), desensitization(account.getAccount())));
                continue;
            }
            int balance = getBalance(token);
            if (balance == -Integer.MAX_VALUE) {
                String message = "设备【{0}】账号【{1}】获取余额失败";
                System.out.println(MessageFormat.format(message, account.getDeviceName(), desensitization(account.getAccount())));
                continue;
            }
            account.setBalance(balance);
            String message = "设备【{0}】账号【{1}】余额【{2}】";
            System.out.println(MessageFormat.format(message, account.getDeviceName(), desensitization(account.getAccount()), balance + ""));
            if (400 >= balance) {
                canEats.add(account);
            }
        }
        if (canEats.isEmpty()) {
            return;
        }
        System.out.println("------------------------------------------------");
        System.out.println("🎉🎉🎉🎉🎉可吃🐟列表🎉🎉🎉🎉🎉");
        for (Account account : canEats) {
            if (account.getRechargeBalance() == 0) {
                String message = "设备【{0}】账号【{1}】余额【{2}】";
                System.out.println(MessageFormat.format(message, account.getDeviceName(), desensitization(account.getAccount()), account.getBalance() + ""));
            } else {
                SingleTextRenderStyle style = new SingleTextRenderStyle();
                style.setColor(new Color(117, 117, 117));
                style.setFont(new Font("黑体", Font.PLAIN, 40));
                style.setPosition(WatermarkPosition.TOP_LEFT);
                style.setFixedCoordinates(true);
                style.setX(310);
                style.setY(633);
                style.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                ByteArrayOutputStream outputStream = WATERMARK_UTILS.markByStream(style, FILE, desensitization(account.getAccount()));
                File outputFile = new File("src/test/resources/template/" + account.getAccount() + ".png");
                Path path = outputFile.toPath();
                try {
                    byte[] byteArray = outputStream.toByteArray();
                    Files.write(path, byteArray);
                } catch (IOException e) {
                    System.err.println("Error while writing to file: " + e.getMessage());
                } finally {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        System.err.println("Error closing ByteArrayOutputStream: " + e.getMessage());
                    }
                }
                style.setColor(new Color(173, 146, 135));
                style.setFont(new Font("黑体", Font.PLAIN, 45));
                style.setPosition(WatermarkPosition.TOP_LEFT);
                style.setFixedCoordinates(true);
                style.setX(560);
                style.setY(1340);
                style.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                outputStream = WATERMARK_UTILS.markByStream(style, outputFile, (account.getBalance() + account.getRechargeBalance()) + "");
                outputFile = new File("src/test/resources/template/" + account.getAccount() + ".png");
                path = outputFile.toPath();
                try {
                    byte[] byteArray = outputStream.toByteArray();
                    Files.write(path, byteArray);
                } catch (IOException e) {
                    System.err.println("Error while writing to file: " + e.getMessage());
                } finally {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        System.err.println("Error closing ByteArrayOutputStream: " + e.getMessage());
                    }
                }
            }
        }
    }

    public String desensitization(String phone) {
        return DesensitizationUtils.exec(phone, 3, 4);
    }

    private final String POI_ID = "600501827";

    public String getToken(String account, String password) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://pos.meituan.com/api/v1/adapters/crm/member/login"))
                .header("Content-Type", "application/json")
                .header("poiid", POI_ID)
                .POST(HttpRequest.BodyPublishers.ofString("{\"account\":\"" + account + "\",\"verifyCode\":\"" + DigestUtils.md5Hex(password) + "\",\"verifyType\":2,\"signAgreementDTOs\":[{\"agreementSnapshotId\":872,\"type\":20},{\"agreementSnapshotId\":216225,\"type\":10}],\"sceneType\":10}"))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            return null;
        }
        return JSON.parseObject(response.body()).getJSONObject("data")
                .getString("token");
    }

    public int getBalance(String token) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://pos.meituan.com/api/v1/crm/frontend/first-screen/recharge/index"))
                .header("Content-Type", "application/json")
                .header("poiid", POI_ID)
                .header("cookie", "UNI-TOKEN-10313118=" + token)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            return -Integer.MAX_VALUE;
        }
        return JSON.parseObject(response.body()).getJSONObject("data")
                .getJSONObject("cardInfo").getJSONObject("account")
                .getBigDecimal("balance").multiply(new BigDecimal("0.01")).intValue();
    }

}
