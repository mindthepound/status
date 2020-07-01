getDateMask = (obj, mask = '.') => {
  if (!obj) return '';

  let cvtDate = new Date(obj);
  return cvtDate.getFullYear() + mask + getWithZero(cvtDate.getMonth() + 1) + mask + getWithZero(cvtDate.getDate());
};

getTimeMask = (obj, mask = ':') => {
  if (!obj) return '';

  let cvtDate = new Date(obj);
  return getWithZero(cvtDate.getHours()) + mask + getWithZero(cvtDate.getMinutes()) + mask + getWithZero(cvtDate.getSeconds());
};

getDateTimeMask = (obj, dateMask = '.', timeMask = ':') => {
  return getDateMask(obj, dateMask) + ' ' + getTimeMask(obj, timeMask);
};

getWithZero = (value) => {
  if (!value) return '00';

  value = parseInt(value);
  return value < 10 ? `0${value}` : value;
};

getDiffDays = (start, end) => {
  if (!start || !end) return null;

  start = new Date(start);
  end = new Date(end);
  return Math.round((end - start) / 1000 / 60 / 60 / 24);
};

getTimeString = (milliseconds) => {
  let seconds = Math.floor(milliseconds / 1000);
  let minutes = Math.floor(seconds / 60);
  let hours = Math.floor(minutes / 60);

  let timeString = '';

  if (hours > 0) {
    timeString += `${hours}시`;
    minutes -= hours * 60;
  }
  if (minutes > 0) {
    if (timeString) timeString += ' ';
    timeString += `${minutes}분`;
    seconds -= minutes * 60;
  }
  if (seconds > 0) {
    if (timeString) timeString += ' ';
    timeString += `${seconds}초`;
  }

  return (timeString)? timeString: '0초';
};

/**
 * @param strDate YYYYMMDD
 * @param mask .
 */
getStrDateMask = (strDate, mask = '.') => {
  return `${strDate.substr(0, 4)}${mask}${strDate.substr(4, 2)}${mask}${strDate.substr(6, 2)}`;
};
