package io.shulie.takin.web.common.enums.activity.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FlowTypeEnum {

    BLEND("BLEND",-1),
    PRESSURE_MEASUREMENT("PRESSURE_MEASUREMENT",1),
    BUSINESS("BUSINESS",0),
    ;

    private String code;
    private int type;

    public static FlowTypeEnum getByType(int clusterTest) {
        for (FlowTypeEnum value : FlowTypeEnum.values()) {
            if (clusterTest == value.type) {
                return value;
            }
        }
        return null;
    }
}
