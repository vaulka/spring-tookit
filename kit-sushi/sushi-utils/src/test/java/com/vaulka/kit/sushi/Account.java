package com.vaulka.kit.sushi;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vaulka
 */
@Data
@NoArgsConstructor
public class Account {

    public Account(String deviceName, String account, String password, Integer rechargeBalance) {
        this.deviceName = deviceName;
        this.account = account;
        this.password = password;
        this.rechargeBalance = rechargeBalance;
    }

    private String deviceName;

    private String account;

    private String password;

    private Integer balance = 0;

    private Integer rechargeBalance = 0;

}
