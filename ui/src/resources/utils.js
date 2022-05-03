const padTo2Digits = (num) => {
    return num.toString().padStart(2, '0');
}

export const todayDateISO = () => {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const date = currentDate.getDate();
    return `${year}-${padTo2Digits(month + 1)}-${padTo2Digits(date)}`;
}

export const dateMinusWeeks = (fechaHasta, semanas) => {
    const currentDate = new Date(fechaHasta);
    currentDate.setDate(currentDate.getDate() - Math.floor(7 * semanas));
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    var date = currentDate.getDate();

    date = getValidDate(date, month, year);

    return `${year}-${padTo2Digits(month + 1)}-${padTo2Digits(date)}`;
}

export const yesterdayDateISO = () => {
    const currentDate = new Date();
    currentDate.setDate(currentDate.getDate() - 1);
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    var date = currentDate.getDate();

    date = getValidDate(date, month, year);

    return `${year}-${padTo2Digits(month + 1)}-${padTo2Digits(date)}`;
}

export const getValidDate = (date, month, year) => {
    var ListofDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (month === 0 || month > 1) {
        if (date > ListofDays[month]) {
            return ListofDays[month];
        } else return date;
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