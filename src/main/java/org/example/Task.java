package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.VisaCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private int allocationId;

    private VisaCategory visaCategory;
}
