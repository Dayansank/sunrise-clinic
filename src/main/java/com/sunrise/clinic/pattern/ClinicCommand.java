package com.sunrise.clinic.pattern;

/**
 * Command pattern. Cancel is wrapped as one action so the servlet stays small.
 */
public interface ClinicCommand {
    void execute();
}
