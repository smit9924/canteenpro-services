package com.app.canteenpro.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Enums {
    public static enum USER_ROLES {
        ADMIN(1),
        OWNER(2),
        MANAGER(3),
        KITCHENER(4),
        WAITER(5),
        CASHIER(6);

        private final Integer value;
        USER_ROLES(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    public static enum FILE_TYPES {
        IMAGE,
        AUDIO,
        VIDEO
    }

    public static enum FOOD_ITEM_TYPE {
        VEG(1),
        NON_VEG(2);

        private final Integer value;
        FOOD_ITEM_TYPE(Integer value) {
            this.value = value;
        }

        @JsonValue
        public Integer getValue() {
            return this.value;
        }

        @JsonCreator
        public static FOOD_ITEM_TYPE fromValue(Integer value) {
            for (FOOD_ITEM_TYPE unit : values()) {
                if (unit.value.equals(value)) {
                    return unit;
                }
            }
            throw new IllegalArgumentException("Invalid value for FOOD_ITEM_QUANTITY_UNIT: " + value);
        }
    }

    public static enum FOOD_ITEM_TASTE {
        NORMAL(1),
        SWEET(2),
        SPICY(3);

        private final Integer value;
        FOOD_ITEM_TASTE(Integer value) {
            this.value = value;
        }

        @JsonValue
        public Integer getValue() {
            return this.value;
        }

        @JsonCreator
        public static FOOD_ITEM_TASTE fromValue(Integer value) {
            for (FOOD_ITEM_TASTE unit : values()) {
                if (unit.value.equals(value)) {
                    return unit;
                }
            }
            throw new IllegalArgumentException("Invalid value for FOOD_ITEM_QUANTITY_UNIT: " + value);
        }
    }

    public static enum FOOD_ITEM_QUANTITY_UNIT {
        PIECES(1),
        GRAM(2);

        private final Integer value;
        FOOD_ITEM_QUANTITY_UNIT(Integer value) {
            this.value = value;
        }

        @JsonValue
        public Integer getValue() {
            return this.value;
        }

        @JsonCreator
        public static FOOD_ITEM_QUANTITY_UNIT fromValue(Integer value) {
            for (FOOD_ITEM_QUANTITY_UNIT unit : values()) {
                if (unit.value.equals(value)) {
                    return unit;
                }
            }
            throw new IllegalArgumentException("Invalid value for FOOD_ITEM_QUANTITY_UNIT: " + value);
        }
    }
}
