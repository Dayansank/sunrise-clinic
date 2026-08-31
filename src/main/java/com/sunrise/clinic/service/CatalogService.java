package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.ClinicSettingsDAO;
import com.sunrise.clinic.dao.DentistDAO;
import com.sunrise.clinic.dao.TreatmentDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Dentist;
import com.sunrise.clinic.model.Treatment;
import com.sunrise.clinic.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;

/** Dentists, treatments and the fee from MySQL. */
public class CatalogService {
    private final DentistDAO dentistDAO;
    private final TreatmentDAO treatmentDAO;
    private final ClinicSettingsDAO settingsDAO;

    public CatalogService() {
        this(new DentistDAO(), new TreatmentDAO(), new ClinicSettingsDAO());
    }

    public CatalogService(DentistDAO dentistDAO, TreatmentDAO treatmentDAO, ClinicSettingsDAO settingsDAO) {
        this.dentistDAO = dentistDAO;
        this.treatmentDAO = treatmentDAO;
        this.settingsDAO = settingsDAO;
    }

    public List<Dentist> dentists() {
        return dentistDAO.findAll();
    }

    public List<Treatment> treatments() {
        return treatmentDAO.findAll();
    }

    public BigDecimal consultationFee() {
        return settingsDAO.findConsultationFee();
    }

    public void saveConsultationFee(String feeText) {
        BigDecimal fee = parseMoney(feeText, "Consultation fee");
        settingsDAO.saveConsultationFee(fee);
    }

    public void addDentist(String name, String specialization) {
        if (!ValidationUtil.isValidName(name)) {
            throw new ClinicException("Dentist name must be between 3 and 100 characters.");
        }
        if (ValidationUtil.isBlank(specialization) || specialization.trim().length() < 3) {
            throw new ClinicException("Specialization must be at least 3 characters.");
        }
        dentistDAO.insert(name.trim(), specialization.trim());
    }

    public void addTreatment(String typeName, String costText) {
        if (!ValidationUtil.isValidName(typeName)) {
            throw new ClinicException("Treatment name must be between 3 and 100 characters.");
        }
        treatmentDAO.insert(typeName.trim(), parseMoney(costText, "Treatment cost"));
    }

    public String deleteDentist(int dentistId) {
        if (dentistDAO.countAppointments(dentistId) > 0) {
            throw new ClinicException("This dentist has appointments, so the record cannot be deleted.");
        }
        dentistDAO.deleteById(dentistId);
        return "Dentist removed.";
    }

    public String deleteTreatment(int treatmentId) {
        if (treatmentDAO.countAppointments(treatmentId) > 0) {
            throw new ClinicException("This treatment has appointments, so the record cannot be deleted.");
        }
        treatmentDAO.deleteById(treatmentId);
        return "Treatment removed.";
    }

    private BigDecimal parseMoney(String text, String label) {
        if (ValidationUtil.isBlank(text)) {
            throw new ClinicException(label + " is required.");
        }
        try {
            BigDecimal value = new BigDecimal(text.trim());
            if (value.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ClinicException(label + " must be greater than 0.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new ClinicException(label + " must be a number.");
        }
    }
}
