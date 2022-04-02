
export const getValidDate = (date, month, year) => {
    var ListofDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (month === 0 || month > 1) {
        if (date > ListofDays[month]) {
            console.log(ListofDays[month])
            return ListofDays[month];
        } else { console.log(date); return date };
    }
    if (month === 1) {
        var isLeapYear = false;
        if ((!(year % 4) && year % 100) || !(year % 400)) {
            isLeapYear = true;
        }
        if ((isLeapYear === false) && (date >= 29)) {
            console.log(ListofDays[month])
            return ListofDays[month];
        }
        if ((isLeapYear === true) && (date > 29)) {
            console.log(29)
            return 29;
        }
        console.log(date)
        return date;
    }
}