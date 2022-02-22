import validator from "validator";

export const minorToOne = (val) => val < 1
export const biggerThanThousand = (val) => val > 999
export const empty = (val) => val === ''
export const olderDate = (val) => {
    const currentDate = new Date();
    const today = `${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${currentDate.getDate()}`;
    return validator.isBefore(today, val)
}