package com.sunrise.clinic.pattern;

/** BOOKED / COMPLETED / CANCELLED. Completed visits cannot be cancelled. */
public enum AppointmentState {
    BOOKED {
        @Override
        public boolean canCancel() {
            return true;
        }

        @Override
        public boolean canBill() {
            return true;
        }
    },
    COMPLETED {
        @Override
        public boolean canCancel() {
            return false;
        }

        @Override
        public boolean canBill() {
            return false;
        }
    },
    CANCELLED {
        @Override
        public boolean canCancel() {
            return false;
        }

        @Override
        public boolean canBill() {
            return false;
        }
    };

    public abstract boolean canCancel();

    public abstract boolean canBill();

    public static AppointmentState from(String status) {
        if (status == null) {
            return BOOKED;
        }
        try {
            return AppointmentState.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return BOOKED;
        }
    }
}
