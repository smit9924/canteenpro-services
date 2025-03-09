package com.app.canteenpro.common;

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
}
