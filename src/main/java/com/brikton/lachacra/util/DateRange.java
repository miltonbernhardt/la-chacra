package com.brikton.lachacra.util;

import com.brikton.lachacra.constants.ValidationMessages;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class DateRange {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fromDate;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate toDate;

    public DateRange(){}

    public DateRange(LocalDate fromDate, LocalDate toDate){
        if (fromDate.isBefore(toDate)) {
            this.fromDate = fromDate;
            this.toDate = toDate;
        } else {
            this.fromDate = toDate;
            this.toDate = fromDate;
        }
    }

    public void setFromDate(LocalDate fromDate) {
        if (this.toDate == null) this.fromDate = fromDate;
        else if (this.toDate.isBefore(fromDate)){
            this.fromDate=this.toDate;
            this.toDate=fromDate;
        } else {
            this.fromDate=fromDate;
        }
    }

    public void setToDate(LocalDate toDate) {
        if (this.fromDate == null) this.toDate = toDate;
        else if (this.fromDate.isAfter(toDate)) {
            this.toDate = fromDate;
            this.fromDate = toDate;
        } else {
            this.toDate = toDate;
        }
    }

    public LocalDate getFromDate() {
        if (fromDate.isBefore(toDate)) return fromDate;
        else return toDate;
    }

    public LocalDate getToDate() {
        if (fromDate.isAfter(toDate)) return fromDate;
        else return toDate;
    }
}
