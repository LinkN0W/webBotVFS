package org.example.testclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Payment {

    private int RequestRefNo;

    private String DigitalSignature;

}
